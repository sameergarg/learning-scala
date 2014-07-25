package katas.forcomp.generator

import scala.util.Random

object Generators {

  trait Generator[+T] {

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate = f(Generator.this.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      def generate = f(Generator.this.generate).generate
    }
  }

  val integers = new Generator[Integer] {
    override def generate: Integer = Random.nextInt()
  }

  val booleans =  for (x <- integers) yield x > 0

  def pairs[X,Y](x: Generator[X], y: Generator[Y]):(X,Y)  = for(a <- x.generate; b <- y.generate) yield (a,b)

  def single[T](x:T) = {
    x
  }
}
