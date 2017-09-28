package katas.typeclasses

/**
  *
  */
//type class
trait CanAppend[A] {
  def append(a1: A, a2: A): A
}

//type class instance
object CanAppend {
  implicit val stringAppender = new CanAppend[String] {
    override def append(a1: String, a2: String) = a1 + a2
  }
}

//type class syntax
object CanAppendSyntax {

}

