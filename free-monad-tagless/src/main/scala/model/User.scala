package model

sealed trait UserADT

object UserADT{

  val isEven: Int => Boolean = i => i % 2 == 0
  val isOdd: Int => Boolean = i => !isEven(i)

  final case class UserId(id: Int) extends AnyVal
  final case class Name(id: String) extends AnyVal
  case class User(userId: UserId, name:Name)  extends UserADT
}

