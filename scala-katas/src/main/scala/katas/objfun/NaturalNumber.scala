package katas.objfun


abstract class NaturalNumber{
  def isZero: Boolean
  def predecessor: NaturalNumber
  def successor: NaturalNumber
  def +(that: NaturalNumber): NaturalNumber
  def -(that: NaturalNumber): NaturalNumber
}

object Zero extends NaturalNumber {
  override def isZero: Boolean = true

  override def successor: NaturalNumber = new Succ(this)

  override def +(that: NaturalNumber): NaturalNumber = that

  override def -(that: NaturalNumber): NaturalNumber = if(that == Zero) this else throw new Error("0.-")

  override def predecessor: NaturalNumber = throw new Error("0.predecessor")
}

class Succ(n: NaturalNumber) extends NaturalNumber {
  override def isZero: Boolean = false

  override def successor: NaturalNumber = new Succ(this)

  override def +(that: NaturalNumber): NaturalNumber = new Succ(n + that)

  override def -(that: NaturalNumber): NaturalNumber = if(that == Zero) this else n - that.predecessor

  override def predecessor: NaturalNumber = n
}


