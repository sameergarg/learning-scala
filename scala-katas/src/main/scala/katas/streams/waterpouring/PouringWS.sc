import katas.streams.waterpouring.Pouring
object PouringWS {
  val pouring = new Pouring(Vector(3,2))
  pouring.moves
  pouring.pathSets.take(2).toList
}
