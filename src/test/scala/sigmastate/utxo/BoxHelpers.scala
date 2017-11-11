package sigmastate.utxo

import sigmastate.SigmaStateTree

object BoxHelpers {
  def boxWithMetadata(value: Int, proposition: SigmaStateTree, creationHeight: Int = 0, boxIndex: Short = 0) =
    BoxWithMetadata(SigmaStateBox(value, proposition), BoxMetadata(creationHeight, boxIndex))
}