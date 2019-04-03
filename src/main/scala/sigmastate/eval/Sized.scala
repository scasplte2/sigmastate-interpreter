package sigmastate.eval

import scalan.{Nullable, RType}
import special.collection.{CSizePrim, CSizePair, Size, CSizeOption, CollType, Coll, CSizeColl}
import scalan.RType._
import sigmastate._
import sigmastate.SBigInt.MaxSizeInBytes
import special.sigma._
import SType.AnyOps
import sigmastate.interpreter.CryptoConstants

trait Sized[T] {
    def size(x: T): Size[T]
}
trait SizedLowPriority {
  implicit def collIsSized[T: Sized: RType]: Sized[Coll[T]] = (xs: Coll[T]) => new CSizeColl(xs.map(Sized[T].size))
  implicit def optionIsSized[T: Sized]: Sized[Option[T]] = (xs: Option[T]) => new CSizeOption(xs.map(Sized[T].size))
  implicit def pairIsSized[A: Sized, B: Sized]: Sized[(A,B)] = (in: (A,B)) => new CSizePair(Sized[A].size(in._1), Sized[B].size(in._2))
}
object Sized extends SizedLowPriority {
  def apply[T](implicit sz: Sized[T]): Sized[T] = sz
  def sizeOf[T: Sized](x: T): Size[T] = Sized[T].size(x)

  val SizeBoolean: Size[Boolean] = new CSizePrim(1L, BooleanType)
  val SizeByte: Size[Byte] = new CSizePrim(1L, ByteType)
  val SizeShort: Size[Short] = new CSizePrim(2L, ShortType)
  val SizeInt: Size[Int] = new CSizePrim(4L, IntType)
  val SizeLong: Size[Long] = new CSizePrim(8L, LongType)
  val SizeBigInt: Size[BigInt] = new CSizePrim(MaxSizeInBytes, BigIntRType)
  val SizeGroupElement: Size[GroupElement] = new CSizePrim(CryptoConstants.EncodedGroupElementLength, GroupElementRType)
  val SizeAvlTree: Size[AvlTree] = new CSizePrim(AvlTreeData.TreeDataSize, AvlTreeRType)

  implicit val BooleanIsSized: Sized[Boolean] = (x: Boolean) => SizeBoolean
  implicit val ByteIsSized: Sized[Byte] = (x: Byte) => SizeByte
  implicit val ShortIsSized: Sized[Short] = (x: Short) => SizeShort
  implicit val IntIsSized: Sized[Int] = (x: Int) => SizeInt
  implicit val LongIsSized: Sized[Long] = (x: Long) => SizeLong
  implicit val BigIntIsSized: Sized[BigInt] = (x: BigInt) => SizeBigInt
  implicit val GroupElementIsSized: Sized[GroupElement] = (x: GroupElement) => SizeGroupElement
  implicit val AvlTreeIsSized: Sized[AvlTree] = (x: AvlTree) => SizeAvlTree

  def typeToSized[T](t: RType[T]): Sized[T] = (t match {
    case BooleanType => Sized[Boolean]
    case ByteType => Sized[Byte]
    case ShortType => Sized[Short]
    case IntType => Sized[Int]
    case LongType => Sized[Long]
    case BigIntRType => Sized[BigInt]
    case GroupElementRType => Sized[GroupElement]
    case AvlTreeRType => Sized[AvlTree]
    case SigmaPropRType => sigmaPropIsSized
    case AnyValueRType => anyValueIsSized
    case BoxRType => boxIsSized
    case HeaderRType => headerIsSized
    case PreHeaderRType => preHeaderIsSized
    case ContextRType => contextIsSized
    case ct: CollType[a] => collIsSized(typeToSized(ct.tItem), ct.tItem)
    case ct: OptionType[a] => optionIsSized(typeToSized(ct.tA))
    case ct: PairType[a, b] => pairIsSized(typeToSized(ct.tFst), typeToSized(ct.tSnd))
    case _ => sys.error(s"Don't know how to compute Sized for type $t")
  }).asInstanceOf[Sized[T]]

  implicit val anyValueIsSized: Sized[AnyValue] = (x: AnyValue) => {
    if (x.tVal == null) {
      val size = new CSizePrim[Any](0L, NothingType.asInstanceOf[RType[Any]])
      new CSizeAnyValue(NothingType.asInstanceOf[RType[Any]], size)
    } else {
      assert(x.value != null, s"Invalid AnyValue: non-null type ${x.tVal} and null value.")
      val sized = typeToSized(x.tVal)
      val size = sized.size(x.value)
      new CSizeAnyValue(x.tVal, size)
    }
  }

  implicit val CollByteIsSized: Sized[Coll[Byte]] = (xs: Coll[Byte]) => {
    new CSizeColl(Colls.replicate(xs.length, SizeByte))
  }

  private def sizeOfAnyValue(v: AnyValue): Size[Option[AnyValue]] = {
    if (v == null) return new CSizeOption[AnyValue](None)
    val size = sizeOf(v)
    new CSizeOption[AnyValue](Some(size))
  }

  private def sizeOfRegisters(b: Box): Size[Coll[Option[AnyValue]]] = {
    new CSizeColl(b.registers.map(sizeOfAnyValue))
  }

  private def sizeOfTokens(b: Box): Size[Coll[(Coll[Byte], Long)]] = {
    val sId = new CSizeColl(Colls.replicate(CryptoConstants.hashLength, SizeByte))
    val sToken = new CSizePair(sId, SizeLong)
    new CSizeColl(Colls.replicate(b.tokens.length, sToken))
  }

  implicit val sigmaPropIsSized: Sized[SigmaProp] = (b: SigmaProp) => {
    new CSizeSigmaProp(sizeOf(b.propBytes))
  }
  implicit val boxIsSized: Sized[Box] = (b: Box) => {
    new EvalSizeBox(
      sizeOf(b.propositionBytes),
      sizeOf(b.bytes),
      sizeOf(b.bytesWithoutRef),
      sizeOfRegisters(b),
      sizeOfTokens(b)
      )
  }
  implicit val headerIsSized: Sized[Header] = (b: Header) => new CSizePrim(SHeader.dataSize(0.asWrappedType), HeaderRType)
  implicit val preHeaderIsSized: Sized[PreHeader] = (b: PreHeader) => new CSizePrim(SPreHeader.dataSize(0.asWrappedType), PreHeaderRType)
  implicit val contextIsSized: Sized[Context] = (ctx: Context) => {
    val outputs = sizeOf(ctx.OUTPUTS)
    val inputs = sizeOf(ctx.INPUTS)
    val dataInputs = sizeOf(ctx.dataInputs)
    val selfBox = sizeOf(ctx.SELF)
    val rootHash = sizeOf(ctx.LastBlockUtxoRootHash)
    val headers = sizeOf(ctx.headers)
    val preHeader = sizeOf(ctx.preHeader)
    val vars = ctx.vars.map { v =>
      val anyV = if(v == null) TestValue(null, null) else v
      sizeOf(anyV)
    }
    new CSizeContext(outputs, inputs, dataInputs, selfBox, rootHash, headers, preHeader, vars)
  }

}

