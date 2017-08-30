package katas.ninetynineproblems

/**
  *
  */
object ArithmeticProblems {

  //Determine whether a given integer number is prime
  def isPrime(number: Int): Boolean = (number > 1) && primes.takeWhile(_ <= Math.sqrt(number)).forall(number %_ != 0)

  val primes = Stream.cons(2, Stream.from(3, 2).takeWhile(isPrime))

}


