package katas.twentyintermediatescalaexcercise

import scala.collection.immutable.Stream.empty
import scala.reflect.ClassTag
import scala.util.Either.LeftProjection

object SecondAttempt {

  trait PartialType[T[_, _], A] {
    type Apply[B] = T[A, B]
    type Flip[B] = T[B, A]
  }

  trait Fluffy[F[_]] {
    def furry[A, B](f: A => B, fa: F[A]): F[B]
  }

  object Fluffy {
    // Exercise 1
    // Relative Difficulty: 1
    def ListFluffy: Fluffy[List] = new Fluffy[List] {
      override def furry[A, B](f: A => B, fa: List[A]): List[B] = fa match {
        case Nil => Nil
        case x :: xs => f(x) :: furry(f, xs)
      }
    }

    // Exercise 2
    // Relative Difficulty: 1
    def OptionFluffy: Fluffy[Option] = new Fluffy[Option] {
      override def furry[A, B](f: A => B, fa: Option[A]): Option[B] = fa match {
        case None => None
        case Some(a) => Some(f(a))
      }
    }

    // Exercise 3
    // Relative Difficulty: 1
    def StreamFluffy: Fluffy[Stream] = new Fluffy[Stream] {
      override def furry[A, B](f: A => B, fa: Stream[A]): Stream[B] = fa match {
        case empty => Stream.empty[B]
        case x #:: xs => f(x) #:: furry(f, xs)
      }
    }

    // Exercise 4
    // Relative Difficulty: 1
    def ArrayFluffy: Fluffy[Array] = new Fluffy[Array] {
      override def furry[A, B](f: A => B, fa: Array[A]): Array[B] = fa.map(f).asInstanceOf[Array[B]]
    }

    // Exercise 5
    // Relative Difficulty: 5
    def Function1Fluffy[X]: Fluffy[PartialType[Function1, X]#Apply] = new Fluffy[PartialType[Function1, X]#Apply] {
      override def furry[A, B](f: A => B, fa: PartialType[Function, X]#Apply[A]): PartialType[Function, X]#Apply[B] = x => f(fa(x))
    }

    // Exercise 6
    // Relative Difficulty: 6
    def EitherLeftFluffy[X]: Fluffy[PartialType[Either.LeftProjection, X]#Flip] = new Fluffy[PartialType[Either.LeftProjection, X]#Flip] {
      override def furry[A, B](f: A => B, fa: PartialType[Either.LeftProjection, X]#Flip[A]): PartialType[Either.LeftProjection, X]#Flip[B] = fa.map(f).left
    }

    // Exercise 7
    // Relative Difficulty: 4
    def EitherRightFluffy[X]: Fluffy[PartialType[Either.RightProjection, X]#Apply] = new Fluffy[PartialType[Either.RightProjection, X]#Apply] {
      override def furry[A, B](f: A => B, fa: PartialType[Either.RightProjection, X]#Apply[A]): PartialType[Either.RightProjection, X]#Apply[B] = fa.map(f).right
    }
  }

  trait Misty[M[_]] extends Fluffy[M] {

    //flatmap
    def banana[A, B](f: A => M[B], ma: M[A]): M[B]

    //unit
    def unicorn[A](a: A): M[A]

    // Exercise 8
    // Relative Difficulty: 3
    // (use banana and/or unicorn)
    //map
    def furry[A, B](f: A => B, ma: M[A]): M[B] = banana((a: A) => unicorn(f(a)), ma)
  }

  object Misty {
    // Exercise 9
    // Relative Difficulty: 2
    def ListMisty: Misty[List] = new Misty[List] {
      override def banana[A, B](f: A => List[B], ma: List[A]): List[B] = ma match {
        case a :: as => f(a) ++ banana(f, as)
        case Nil => Nil
      }

      override def unicorn[A](a: A): List[A] = List(a)
    }

    // Exercise 10
    // Relative Difficulty: 2
    def OptionMisty: Misty[Option] = new Misty[Option] {
      override def banana[A, B](f: A => Option[B], ma: Option[A]): Option[B] = ma match {
        case Some(a) => f(a)
        case _ => None
      }

      override def unicorn[A](a: A): Option[A] = Some(a)
    }

    // Exercise 11
    // Relative Difficulty: 2
    def StreamMisty: Misty[Stream] = new Misty[Stream] {
      override def banana[A, B](f: A => Stream[B], ma: Stream[A]): Stream[B] = ma match {
        case a #:: as => f(a) ++ banana(f, as)
        case _ => Stream.empty
      }

      override def unicorn[A](a: A): Stream[A] = Stream(a)
    }

    // Exercise 12
    // Relative Difficulty: 2
    def ArrayMisty: Misty[Array] = ???/*new Misty[Array] {
      override def banana[A, B](f: A => Array[B], ma: Array[A]): Array[B] = ma.flatMap((a:A) => f(a))

      override def unicorn[A](a: A): Array[A] = Array(a)
    }*/

    // Exercise 13
    // Relative Difficulty: 6
    def Function1Misty[X]: Misty[PartialType[Function1, X]#Apply] = new Misty[PartialType[Function1, X]#Apply]{
      override def banana[A, B](f: A => PartialType[Function, X]#Apply[B], ma: PartialType[Function, X]#Apply[A]): PartialType[Function, X]#Apply[B] = (x: X) => f(ma)(x)


      override def unicorn[A](a: A): PartialType[Function, X]#Apply[A] = x => a
    }

    // Exercise 14
    // Relative Difficulty: 7
    def EitherLeftMisty[X]: Misty[PartialType[Either.LeftProjection, X]#Flip] = new Misty[PartialType[Either.LeftProjection, X]#Flip] {
      override def banana[A, B](f: A => PartialType[LeftProjection, X]#Flip[B], ma: PartialType[LeftProjection, X]#Flip[A]): PartialType[LeftProjection, X]#Flip[B] =
        f(ma.get)

      override def unicorn[A](a: A): PartialType[LeftProjection, X]#Flip[A] = Either.LeftProjection(Left(a))
    }

    // Exercise 15
    // Relative Difficulty: 5
    def EitherRightMisty[X]: Misty[PartialType[Either.RightProjection, X]#Apply] = new Misty[PartialType[Either.RightProjection, X]#Apply] {
      override def banana[A, B](f: A => PartialType[Either.RightProjection, X]#Apply[B], ma: PartialType[Either.RightProjection, X]#Apply[A]): PartialType[Either.RightProjection, X]#Apply[B] = f(ma.get)

      override def unicorn[A](a: A): PartialType[Either.RightProjection, X]#Apply[A] = Either.RightProjection(Right(a))
    }


    // Exercise 16
    // Relative Difficulty: 3
    //flatten
    def jellybean[M[_], A](ma: M[M[A]], m: Misty[M]): M[A] = m.banana[M[A], A]((a:M[A]) => a, ma)

    // Exercise 17
    // Relative Difficulty: 6
    //applicative
    def apple[M[_], A, B](ma: M[A], mf: M[A => B], m: Misty[M]): M[B] =
      m.banana[A => B, B]((fa:(A => B)) => m.furry(fa, ma), mf)

    // Exercise 18
    // Relative Difficulty: 6
    //traverse
    def moppy[M[_], A, B](as: List[A], f: A => M[B], m: Misty[M]): M[List[B]] = ???
  }

  object AdvancedFun {

    case class State[S, A](f: S => (S, A))

    // Exercise 19
    // Relative Difficulty: 9
    def StateFluffy[S]: Fluffy[PartialType[State, S]#Apply] = ???

    // Exercise 20
    // Relative Difficulty: 10
    def StateMisty[S]: Misty[PartialType[State, S]#Apply] = ???
  }

}
