package implicits

/**
  * https://www.theguardian.com/info/developer-blog/2016/dec/22/parental-advisory-implicit-content
  */
//transform one type into another.
object ImplicitConversion extends App {

  def alert(value: Int): String = s"$value"

    //implicitly convert String to Int so that alert method can accept String
  implicit def stringToInt(str: String) = str.toInt

  println(
    alert("1")
  )

  /**
    * If you have a class whose constructor takes a single argument, as below, then it can be marked
    * as implicit and the compiler will automatically allow implicit conversions from the type of its constructor
    * argument to the type of the class.
    */
  object ImplicitClass {
    //this implicit class has converted Int into Showable
    implicit class Showable(int: Int) {
      def show: Unit = println(int)
    }

    1 show
  }

  import ImplicitClass._

  10000.show
}
