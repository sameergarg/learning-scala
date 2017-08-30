package katas.typeclasses

/**
  *
  */
//type class interface
trait Eq[A] {
  def eqv(a: A, other: A): Boolean
}

//type class instance
object Eq{
  implicit val stringEqv = new Eq[String] {
    override def eqv(a: String, other: String) = a == other
  }
}

//type class syntax - adds method on String without modifying its code and allow us to use in dsl way
object EqSyntax {
  implicit class EqOps[A](a: A) {
    def ===(b: A)(implicit eq: Eq[A])= eq.eqv(a,b)
  }
}

object EqvMain extends App {
  import Eq._
  import EqSyntax._
  println("hello" === "Hello")
  println("Hello" === "Hello")
}
