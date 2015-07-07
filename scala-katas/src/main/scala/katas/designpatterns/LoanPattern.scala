package katas.designpatterns

object LoanPattern extends App {

  case class Resource(id: Int)

  def resourceLender[T](f: Resource => T) = {
    val resource = Resource(1)
    f(resource)
  }

  val lendee = {resource: Resource =>
    println(resource.id)
  }

    resourceLender(lendee)
}


