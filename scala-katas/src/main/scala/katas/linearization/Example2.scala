package katas.linearization

/**
 *
 */
object Example2 extends App {

  class C1 {
    def m = print("C1 ")
  }

  trait T1 extends C1 {
    override def m: Unit = {print("T1 ");super.m}
  }

  trait T2 extends C1 {
    override def m: Unit = {print("T2 ");super.m}
  }

  trait T3 extends C1 {
    override def m: Unit = {print("T3 ");super.m}
  }

  class C2A extends T2 {
    override def m = {print("C2A ");super.m}
  }

  class C2 extends C2A with T1 with T2 with T3 {
    override def m = {print("C2 ");super.m}
  }

  def printLinearization(o: C1, name: String) = {
    print(s"$name: ")
    o.m
    print("AnyRef ")
    print("Any ")
  }

  printLinearization (new C2, "C2") //C2: C2 T3 T1 C2A T2 C1

}
