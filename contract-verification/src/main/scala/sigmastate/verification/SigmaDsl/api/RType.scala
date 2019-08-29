package sigmastate.verification.SigmaDsl.api

import scala.reflect.ClassTag

trait RType[A] {
  def classTag: ClassTag[A]

  def name: String = this.toString

  /** Returns true is data size of `x: A` is the same for all `x`.
    * This is useful optimizations of calculating sizes of collections. */
  def isConstantSize: Boolean
}

