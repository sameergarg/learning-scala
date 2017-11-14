package katas.twentyintermediatescalaexcercise

// Replace abstract types with concrete
// Type check every step ctrl + P
// use ??? while progressing
// flatmap and map as function
object Trials extends App {

  /*
  M = Option
  A = Int
  B = String
  as = List[Int]
  f  = Int => Option[String]
  m  = Monad[Option]  //flatmap and unit

   def moppy[Option[_], Int, String](as: List[Int], f: Int => Option[String], m: Monad[Option]): Monad[List[String]]
   */
  trait Monad[_] {
    def flatMap[A, B](f: A => Option[B], ma: Option[A]): Option[B] = ma.flatMap(f)

    def unit[A](a: A): Option[A] = Option(a)
  }

  def moppy(as: List[Int], f: Int => Option[String], m: Monad[Option[String]]): Option[List[String]] = {

    as.foldLeft(m.unit(List.empty[String])){(acc: Option[List[String]], a) =>
      val aStr: Option[String] = f(a)
      acc.flatMap(l => aStr.map(_ :: l))

    }

  }

  println(moppy(List(1,2,3), i => Some(i.toString+i.toString), new Monad[Option[String]]{}))
}
