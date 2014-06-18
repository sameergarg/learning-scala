import katas.streams.waterpouring.Pouring
object PouringWS {
  val pouring = new Pouring(Vector(4,7))
  pouring.moves
  //pouring.pathSets.take(3).toList
  pouring.solution(6)
}
