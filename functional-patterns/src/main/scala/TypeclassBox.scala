import Greeter.{Dog, Person}
//A Typeclass Box in Scala. Allows for a single type to represent any type that responds to a typeclass.
//https://gist.github.com/pjrt/f11273cf0616d7ec1d82

//our typeclass
trait Typeclass[A] {
  def show(a: A): String
}

object Typeclass {
  def apply[A: Typeclass]: Typeclass[A] = implicitly
}

//our typeclass box using existential type
trait TypeclassBox[L[_]] {
  type A

  def value: A

  def instance: L[A]
}

object TypeclassBox {
  def apply[L[_], B](v: B)(implicit ins: L[B]) = new TypeclassBox[L] {
    override type A = B

    override def value = v

    override def instance = ins
  }

  implicit val typeclassBoxTypeclassInstance: Typeclass[TypeclassBox[Typeclass]] = new Typeclass[TypeclassBox[Typeclass]] {
    override def show(a: TypeclassBox[Typeclass]): String = a.instance.show(a.value)
  }
}

case class StringPrinter(s: String)
case object StringPrinter {
  implicit val stringPrinterTypeclass = new Typeclass[StringPrinter] {
    override def show(a: StringPrinter): String = a.s
  }
}

case class IntPrinter(i: Int)
case object IntPrinter {
  implicit val intPrinterTypeClass = new Typeclass[IntPrinter] {
    override def show(a: IntPrinter): String = a.i.toString
  }
}

object Main extends App {
  override def main(args: Array[String]): Unit = {
    val listTypeclassBox: List[TypeclassBox[Typeclass]] = TypeclassBox[Typeclass, StringPrinter](new StringPrinter("hello")) ::
      TypeclassBox[Typeclass, IntPrinter](new IntPrinter(111)) :: Nil

    listTypeclassBox.foreach(l => println(TypeclassBox.typeclassBoxTypeclassInstance.show(l)))
  }
}
