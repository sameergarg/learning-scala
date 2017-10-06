package typeclass

final case class Cat(name: String, age: Int, color: String)

//type class
trait Printable[A] {
  def format(a: A): String
}

//Companion
object Printable {

  //define apply to avoid passing implicit parameter or summoning implicit values from somewhere using 'implicitly'
  def apply[A: Printable]: Printable[A] = implicitly // compiler will look for any A for which Printable is defined and will give it back

  //instance
  implicit object PrintableCat extends Printable[Cat] {
    override def format(c: Cat) = s"${c.name} is a ${c.age} year-old ${c.color} cat."
  }
}

//syntax
object PrintSyntax {

  implicit class PrintableOps[A: Printable](underlying: A){
    def format: String =  Printable[A].format(underlying)
    def print: Unit = println(format)
  }
}

object PrintableTypeClass extends App {
  import PrintSyntax._

  val cat = Cat("Ruby", 4, "white")
  cat.print

}
