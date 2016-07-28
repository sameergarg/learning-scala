import katas.parallelprogramming.ParallelMap

val in = Array(2,3,4,5,6)
  val out = Array(0,0,0,0,0)

  ParallelMap.mapASegSeq[Int, Int](in, 0, 5, x => x*x , out)
  println(out.toList)


