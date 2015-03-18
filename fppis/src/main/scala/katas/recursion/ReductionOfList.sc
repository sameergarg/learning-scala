  def mapFun[T, U](xs: List[T], f: T => U): List[U] =
    (xs foldRight List[U]())( (x,subList) => f(x) :: subList )

  def lengthFun[T](xs: List[T]): Int =
    (xs foldRight 0)( (x,size) => size+1  )

  def f(x: Int): Int = math.pow(x, 2).toInt
  val mapped = mapFun(List(1,2,3) , f )

  lengthFun(List(1,2,3))


