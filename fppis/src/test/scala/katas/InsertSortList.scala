package katas

object InsertSortList extends App{

  def iSort(toSort:List[Int]) :List[Int] = toSort match {
    case Nil => List()
    case x::xs => insert(x, iSort(xs));
  }

  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List() => List(x)
    case y :: ys => if(x<y) x::xs else y:: insert(x,ys)
  }

  println(iSort(List(4,5,2,1)))


}
