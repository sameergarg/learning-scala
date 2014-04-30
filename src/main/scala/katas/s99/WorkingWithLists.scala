package katas.s99

/**
 *
 * User: sameer
 * Date: 18/03/2014
 * Time: 21:43
 */
object WorkingWithLists {

    def lastElement[Int] (list:List[Int]) : Int =  list match  {
      case last :: Nil => last
      case _ :: tail => lastElement(tail)
      case _ => throw new NoSuchElementException
    }

}
