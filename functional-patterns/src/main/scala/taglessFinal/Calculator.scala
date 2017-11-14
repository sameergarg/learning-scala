package taglessFinal

object Calculator extends App {

  //algebra
  trait Exp[A] {
    def literal(i: Int): A
    def add(a1: A, a2: A): A
    def negate(a: A): A
  }

  //program
  class Program {
    //pass interpreter as method parameter or constructor argument
    def calculate[A](implicit interpreter: Exp[A]) = {
      import interpreter._
      add(
        literal(40),
        negate(
          add(
            literal(5),
            literal(7)
          )
        )
      )
    }
  }

  //interpreters
  implicit val mathematicalInterpreter = new Exp[Int] {

    override def literal(i: Int) = i

    override def add(a1: Int, a2: Int) = a1 + a2

    override def negate(a: Int) = - a
  }

  implicit val prettyPrintInterpreter = new Exp[String] {
    override def literal(i: Int) = i.toString

    override def add(a1: String, a2: String) = s"$a1 + $a2"

    override def negate(a: String) = s"- ($a)"
  }

  println(
    new Program().calculate[Int]
  )

  println(
    new Program().calculate[String]
  )



}
