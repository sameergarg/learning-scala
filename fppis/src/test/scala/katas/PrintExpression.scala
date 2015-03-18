package katas

trait Expr {

  def eval(): Int = this match {
    case Num(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
    case Product(v1, v2) => v1.eval * v2.eval
    case _ => throw new IllegalArgumentException
  }
}
case class Num(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Var(name: String) extends Expr
case class Product(e1: Expr, e2: Expr) extends Expr

object PrintExpression extends App {
   def show(e: Expr): String = e match {
     case Num(n) => n.toString
     case Sum(e1, e2) => show(e1) + " + " + show(e2)
     case Var(x) => x
     case Product(v1:Sum, v2) => s"(${show(v1)}) * ${show(v2)}"
     case Product(v1, v2) => s"${show(v1)} * ${show(v2)}"
   }

  println(show(Sum(Num(1), Num(44)))+ " = "+(Sum(Num(1), Num(44))).eval)
  println(show(Product(Num(2),Var("x"))))
  println(show(Sum(Product(Num(2),Var("x")),Var("y"))))//2 * x + y
  println(show(Product(Sum(Num(2),Var("x")),Var("y"))))//(2 + x) * y

}
