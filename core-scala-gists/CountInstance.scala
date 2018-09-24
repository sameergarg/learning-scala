object CountInstance {

  sealed trait Status

  case object Ok extends Status

  final case class Error(message: String) extends Status

  def count[S <: Status](statuses: List[Status], status: Status) = statuses.collect{ case s => s == status }.size

}

object Main extends App {
  import CountInstance._	
  assert(count(List(Ok, Ok), Ok) == 2)
}