package special.sigma {
  import scalan.OverloadHack.Overloaded1   // manual fix
  import scalan._

  trait SigmaDsl extends Base { self: SigmaLibrary =>
    import AnyValue._;
    import AvlTree._;
    import BigInt._;
    import Box._;
    import Coll._;
    import CollBuilder._;
    import Context._;
    import CostModel._;
    import CostedBuilder._;
    import CostedColl._;
    import CostedOption._;
    import GroupElement._;
    import Header._;
    import MonoidBuilder._;
    import Preheader._;
    import SigmaContract._;
    import SigmaDslBuilder._;
    import SigmaProp._;
    import WBigInteger._;
    import WOption._;
    @Liftable trait CostModel extends Def[CostModel] {
      def AccessBox: Rep[Int];
      def AccessAvlTree: Rep[Int];
      def GetVar: Rep[Int];
      def DeserializeVar: Rep[Int];
      def GetRegister: Rep[Int];
      def DeserializeRegister: Rep[Int];
      def SelectField: Rep[Int];
      def CollectionConst: Rep[Int];
      def AccessKiloByteOfData: Rep[Int];
      @Reified(value = "T") def dataSize[T](x: Rep[T])(implicit cT: Elem[T]): Rep[Long];
      def PubKeySize: Rep[Long] = toRep(32L.asInstanceOf[Long])
    };
    @Liftable trait BigInt extends Def[BigInt] {
      def toByte: Rep[Byte];
      def toShort: Rep[Short];
      def toInt: Rep[Int];
      def toLong: Rep[Long];
      def toBytes: Rep[Coll[Byte]];
      def toBits: Rep[Coll[Boolean]];
      def toAbs: Rep[BigInt];
      def compareTo(that: Rep[BigInt]): Rep[Int];
      def modQ: Rep[BigInt];
      def plusModQ(other: Rep[BigInt]): Rep[BigInt];
      def minusModQ(other: Rep[BigInt]): Rep[BigInt];
      def multModQ(other: Rep[BigInt]): Rep[BigInt];
      def inverseModQ: Rep[BigInt];
      def signum: Rep[Int];
      def add(that: Rep[BigInt]): Rep[BigInt];
      def subtract(that: Rep[BigInt]): Rep[BigInt];
      def multiply(that: Rep[BigInt]): Rep[BigInt];
      def divide(that: Rep[BigInt]): Rep[BigInt];
      def mod(m: Rep[BigInt]): Rep[BigInt];
      def remainder(that: Rep[BigInt]): Rep[BigInt];
      def min(that: Rep[BigInt]): Rep[BigInt];
      def max(that: Rep[BigInt]): Rep[BigInt]
    };
    @Liftable trait GroupElement extends Def[GroupElement] {
      def isInfinity: Rep[Boolean];
      def multiply(k: Rep[BigInt]): Rep[GroupElement];
      def add(that: Rep[GroupElement]): Rep[GroupElement];
      def negate: Rep[GroupElement];
      def getEncoded(compressed: Rep[Boolean]): Rep[Coll[Byte]]
    };
    @Liftable trait SigmaProp extends Def[SigmaProp] {
      def isValid: Rep[Boolean];
      def propBytes: Rep[Coll[Byte]];
      @OverloadId(value = "and_sigma") def &&(other: Rep[SigmaProp]): Rep[SigmaProp];
      // manual fix
      @OverloadId(value = "and_bool") def &&(other: Rep[Boolean])(implicit o: Overloaded1): Rep[SigmaProp];
      @OverloadId(value = "or_sigma") def ||(other: Rep[SigmaProp]): Rep[SigmaProp];
      // manual fix
      @OverloadId(value = "or_bool") def ||(other: Rep[Boolean])(implicit o: Overloaded1): Rep[SigmaProp];
    };
    @Liftable trait AnyValue extends Def[AnyValue] {
      def dataSize: Rep[Long]
    };
    @Liftable trait Box extends Def[Box] {
      def id: Rep[Coll[Byte]];
      def value: Rep[Long];
      def propositionBytes: Rep[Coll[Byte]];
      def bytes: Rep[Coll[Byte]];
      def bytesWithoutRef: Rep[Coll[Byte]];
      def cost: Rep[Int];
      def dataSize: Rep[Long];
      def registers: Rep[Coll[AnyValue]];
      def getReg[T](i: Rep[Int])(implicit cT: Elem[T]): Rep[WOption[T]];
      def R0[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(0.asInstanceOf[Int]));
      def R1[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(1.asInstanceOf[Int]));
      def R2[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(2.asInstanceOf[Int]));
      def R3[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(3.asInstanceOf[Int]));
      def R4[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(4.asInstanceOf[Int]));
      def R5[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(5.asInstanceOf[Int]));
      def R6[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(6.asInstanceOf[Int]));
      def R7[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(7.asInstanceOf[Int]));
      def R8[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(8.asInstanceOf[Int]));
      def R9[T](implicit cT: Elem[T]): Rep[WOption[T]] = this.getReg[T](toRep(9.asInstanceOf[Int]));
      def tokens: Rep[Coll[scala.Tuple2[Coll[Byte], Long]]];
      def creationInfo: Rep[scala.Tuple2[Int, Coll[Byte]]];
      def executeFromRegister[T](regId: Rep[Byte])(implicit cT: Elem[T]): Rep[T]
    };
    @Liftable trait AvlTree extends Def[AvlTree] {
      def startingDigest: Rep[Coll[Byte]];
      def keyLength: Rep[Int];
      def valueLengthOpt: Rep[WOption[Int]];
      def maxNumOperations: Rep[WOption[Int]];
      def maxDeletes: Rep[WOption[Int]];
      def cost: Rep[Int];
      def dataSize: Rep[Long];
      def digest: Rep[Coll[Byte]]
    };
    trait Header extends Def[Header] {
      def version: Rep[Byte];
      def parentId: Rep[Coll[Byte]];
      def ADProofsRoot: Rep[Coll[Byte]];
      def stateRoot: Rep[Coll[Byte]];
      def transactionsRoot: Rep[Coll[Byte]];
      def timestamp: Rep[Long];
      def nBits: Rep[Long];
      def height: Rep[Int];
      def extensionRoot: Rep[Coll[Byte]];
      def minerPk: Rep[GroupElement];
      def powOnetimePk: Rep[GroupElement];
      def powNonce: Rep[Coll[Byte]];
      def powDistance: Rep[BigInt]
    };
    trait Preheader extends Def[Preheader] {
      def version: Rep[Byte];
      def parentId: Rep[Coll[Byte]];
      def timestamp: Rep[Long];
      def nBits: Rep[Long];
      def height: Rep[Int];
      def minerPk: Rep[GroupElement]
    };
    @Liftable trait Context extends Def[Context] {
      def builder: Rep[SigmaDslBuilder];
      def OUTPUTS: Rep[Coll[Box]];
      def INPUTS: Rep[Coll[Box]];
      def HEIGHT: Rep[Int];
      def SELF: Rep[Box];
      def selfBoxIndex: Rep[Int];
      def LastBlockUtxoRootHash: Rep[AvlTree];
      def headers: Rep[Coll[Header]];
      def preheader: Rep[Preheader];
      def MinerPubKey: Rep[Coll[Byte]];
      def getVar[T](id: Rep[Byte])(implicit cT: Elem[T]): Rep[WOption[T]];
      def getConstant[T](id: Rep[Byte])(implicit cT: Elem[T]): Rep[T];
      def cost: Rep[Int];
      def dataSize: Rep[Long]
    };
    @Liftable trait SigmaContract extends Def[SigmaContract] {
      def builder: Rep[SigmaDslBuilder];
      @NeverInline @Reified(value = "T") def Collection[T](items: Rep[T]*)(implicit cT: Elem[T]): Rep[Coll[T]] = delayInvoke;
      def verifyZK(cond: Rep[Thunk[SigmaProp]]): Rep[Boolean] = this.builder.verifyZK(cond);
      def atLeast(bound: Rep[Int], props: Rep[Coll[SigmaProp]]): Rep[SigmaProp] = this.builder.atLeast(bound, props);
      def allOf(conditions: Rep[Coll[Boolean]]): Rep[Boolean] = this.builder.allOf(conditions);
      def allZK(conditions: Rep[Coll[SigmaProp]]): Rep[SigmaProp] = this.builder.allZK(conditions);
      def anyOf(conditions: Rep[Coll[Boolean]]): Rep[Boolean] = this.builder.anyOf(conditions);
      def anyZK(conditions: Rep[Coll[SigmaProp]]): Rep[SigmaProp] = this.builder.anyZK(conditions);
      def PubKey(base64String: Rep[String]): Rep[SigmaProp] = this.builder.PubKey(base64String);
      def sigmaProp(b: Rep[Boolean]): Rep[SigmaProp] = this.builder.sigmaProp(b);
      def blake2b256(bytes: Rep[Coll[Byte]]): Rep[Coll[Byte]] = this.builder.blake2b256(bytes);
      def sha256(bytes: Rep[Coll[Byte]]): Rep[Coll[Byte]] = this.builder.sha256(bytes);
      def byteArrayToBigInt(bytes: Rep[Coll[Byte]]): Rep[BigInt] = this.builder.byteArrayToBigInt(bytes);
      def longToByteArray(l: Rep[Long]): Rep[Coll[Byte]] = this.builder.longToByteArray(l);
      def proveDlog(g: Rep[GroupElement]): Rep[SigmaProp] = this.builder.proveDlog(g);
      def proveDHTuple(g: Rep[GroupElement], h: Rep[GroupElement], u: Rep[GroupElement], v: Rep[GroupElement]): Rep[SigmaProp] = this.builder.proveDHTuple(g, h, u, v);
      def isMember(tree: Rep[AvlTree], key: Rep[Coll[Byte]], proof: Rep[Coll[Byte]]): Rep[Boolean] = this.builder.isMember(tree, key, proof);
      def treeLookup(tree: Rep[AvlTree], key: Rep[Coll[Byte]], proof: Rep[Coll[Byte]]): Rep[WOption[Coll[Byte]]] = this.builder.treeLookup(tree, key, proof);
      def treeModifications(tree: Rep[AvlTree], operations: Rep[Coll[Byte]], proof: Rep[Coll[Byte]]): Rep[WOption[Coll[Byte]]] = this.builder.treeModifications(tree, operations, proof);
      def groupGenerator: Rep[GroupElement] = this.builder.groupGenerator;
      @clause def canOpen(ctx: Rep[Context]): Rep[Boolean];
      def asFunction: Rep[scala.Function1[Context, Boolean]] = fun(((ctx: Rep[Context]) => this.canOpen(ctx)))
    };
    @Liftable trait SigmaDslBuilder extends Def[SigmaDslBuilder] {
      def Colls: Rep[CollBuilder];
      def Monoids: Rep[MonoidBuilder];
      def Costing: Rep[CostedBuilder];
      def CostModel: Rep[CostModel];
      def costBoxes(bs: Rep[Coll[Box]]): Rep[CostedColl[Box]];
      def costColWithConstSizedItem[T](xs: Rep[Coll[T]], len: Rep[Int], itemSize: Rep[Long]): Rep[CostedColl[T]];
      def costOption[T](opt: Rep[WOption[T]], opCost: Rep[Int]): Rep[CostedOption[T]];
      def verifyZK(cond: Rep[Thunk[SigmaProp]]): Rep[Boolean];
      def atLeast(bound: Rep[Int], props: Rep[Coll[SigmaProp]]): Rep[SigmaProp];
      def allOf(conditions: Rep[Coll[Boolean]]): Rep[Boolean];
      def allZK(conditions: Rep[Coll[SigmaProp]]): Rep[SigmaProp];
      def anyOf(conditions: Rep[Coll[Boolean]]): Rep[Boolean];
      def anyZK(conditions: Rep[Coll[SigmaProp]]): Rep[SigmaProp];
      def PubKey(base64String: Rep[String]): Rep[SigmaProp];
      def sigmaProp(b: Rep[Boolean]): Rep[SigmaProp];
      def blake2b256(bytes: Rep[Coll[Byte]]): Rep[Coll[Byte]];
      def sha256(bytes: Rep[Coll[Byte]]): Rep[Coll[Byte]];
      def byteArrayToBigInt(bytes: Rep[Coll[Byte]]): Rep[BigInt];
      def longToByteArray(l: Rep[Long]): Rep[Coll[Byte]];
      def proveDlog(g: Rep[GroupElement]): Rep[SigmaProp];
      def proveDHTuple(g: Rep[GroupElement], h: Rep[GroupElement], u: Rep[GroupElement], v: Rep[GroupElement]): Rep[SigmaProp];
      def isMember(tree: Rep[AvlTree], key: Rep[Coll[Byte]], proof: Rep[Coll[Byte]]): Rep[Boolean];
      def treeLookup(tree: Rep[AvlTree], key: Rep[Coll[Byte]], proof: Rep[Coll[Byte]]): Rep[WOption[Coll[Byte]]];
      def treeModifications(tree: Rep[AvlTree], operations: Rep[Coll[Byte]], proof: Rep[Coll[Byte]]): Rep[WOption[Coll[Byte]]];
      def groupGenerator: Rep[GroupElement];
      @Reified(value = "T") def substConstants[T](scriptBytes: Rep[Coll[Byte]], positions: Rep[Coll[Int]], newValues: Rep[Coll[T]])(implicit cT: Elem[T]): Rep[Coll[Byte]];
      def decodePoint(encoded: Rep[Coll[Byte]]): Rep[GroupElement];
      def BigInt(n: Rep[WBigInteger]): Rep[BigInt];
      def toBigInteger(n: Rep[BigInt]): Rep[WBigInteger]
    };
    trait CostModelCompanion;
    trait BigIntCompanion;
    trait GroupElementCompanion;
    trait SigmaPropCompanion;
    trait AnyValueCompanion;
    trait BoxCompanion;
    trait AvlTreeCompanion;
    trait HeaderCompanion;
    trait PreheaderCompanion;
    trait ContextCompanion;
    trait SigmaContractCompanion;
    trait SigmaDslBuilderCompanion
  }
}