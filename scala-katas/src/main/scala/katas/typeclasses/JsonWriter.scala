package katas.typeclasses

/**
  *
  */
//AST - Custom object
sealed trait Json
case class JString(value: String) extends Json
case class JNumber(value: Number) extends Json
case class JObject(map: Map[String, Json]) extends Json

//type class containing behaviour
trait JsonWriter[A] {
  def write(value: A): Json
}

case class Person(name: String, age: Number)

//type instances for inbuilt and custom types
object JsonWriterInstances {
  implicit val stringJsonWriter = new JsonWriter[String] {
    override def write(value: String) = JString(value)
  }

  implicit val personJsonWriter = new JsonWriter[Person] {
    override def write(value: Person) = JObject(
      Map(
        "name" -> JString(value.name),
        "age" -> JNumber(value.age)
      )
    )
  }
}

//interfaces or syntax for type classes - Adding capabilities to existing classes without modifying their source code
 object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit writer: JsonWriter[A]) = {
      writer.write(value)
    }
  }
}

object main extends App {
  import JsonWriterInstances._
  import JsonSyntax._

  val tom = Person("Tom", 25)
  println(tom toJson)
}
