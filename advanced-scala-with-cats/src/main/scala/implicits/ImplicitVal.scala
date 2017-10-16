package implicits

object ImplicitVal extends App {

  def multiply(int1: Int)(implicit int2: Int): Int = {
    int1 * int2
  }

  //implicit parameter
  implicit val int2: Int = 100

  println(multiply(2))
}
