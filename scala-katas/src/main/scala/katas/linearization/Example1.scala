package katas.linearization


object Example1 extends App{



  class C1 {
    def m = print("C1 ")
  }



  trait T1 extends C1 {
    override def m = {print("T1 "); super.m}
  }


  trait T2 extends C1 {
    override def m = {print("T2 "); super.m}
  }


  trait T3 extends C1 {
    override def m = {print("T3 "); super.m}
  }

  class C2 extends T1 with T2 with T3 {
    override def m = {print("C2 "); super.m}
  }

  val c2 = new C2

  /**
   * Actual type of instance as first : C2
   * Move Right to Left and append the linearization (T3 to T1) : C2 -> T3 -> C1 -> T2 -> C1 -> T1 -> C1
   * Working Left to Right, remove any type that appears again.
   * Any classes already appeared in hierarchy is not considered again
   * Pass 1: C2 -> T3 -> T2 -> C1 -> T1 -> C1(C1 removed after T3 as it appears again in right after T2)
   * Pass 2: C2 -> T3 -> T2 -> T1 -> C1(C1 removed after T2 as it appears again in right after T1)
   * Append AnyRef and Any C2 -> T3 -> T2 -> T1 -> C1 -> AnyRef -> Any
   */
  println(s"C2 Linearization: ${c2.m}")



}