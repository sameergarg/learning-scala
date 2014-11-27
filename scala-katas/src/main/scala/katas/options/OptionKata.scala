package katas.options

case class User(id: Int, name: String, age: Int)

object UserRepository {
 
  val users = Seq(User(1, "foo", 27), User(2, "bar",30))
  
  def findUser(id: Int) =
      users.find(_.id == id)
}