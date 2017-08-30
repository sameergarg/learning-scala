package katas.typeclasses

import java.awt.print.Printable

/**
  *
  */
//type class
trait Printable[A]{
  def show(value: A): String
}

case class Login(username: String, password: String)

// type class instances
object PrintableInstances {
  implicit val intPrinter = new Printable[Int] {
    override def show(value: Int) = value.toString
  }

  implicit val loginPrinter = new Printable[Login] {
    override def show(value: Login) = s"username: ${value.username}"
  }
}

//type class syntax
object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def asString(implicit printable: Printable[A]) = {
      printable.show(value)
    }
  }
}

object PrintableMain extends App {
  import PrintableSyntax._
  import PrintableInstances._

  print(Login("tom", "secret") asString)
}