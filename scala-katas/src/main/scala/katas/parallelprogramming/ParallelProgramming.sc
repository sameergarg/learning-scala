import katas.parallelprogramming.{ParallelMap, ParallelScan}

val in = Array(2,3,4,5,6)
val out = Array(0,0,0,0,0)
val out2 = Array(0,0,0,0,0,0)

ParallelMap.ParallelArray.mapASegSeq[Int, Int](in, 0, 5, x => x*x , out)
println(out.toList)

ParallelScan.scanLeftSeq[Int](in, 100, (a,b) => (a + b), out2)
println(out2.toList)




