
package katas.twentyintermediatescalaexcercise

import scala.collection.immutable.Stream.Empty
import scala.util.Either.{LeftProjection, RightProjection}

trait PartialType[T[_, _], A] {
  type Apply[B] = T[A, B]
  type Flip[B] = T[B, A]
}

//functor
trait Fluffy[F[_]] {
  def furry[A, B](f: A => B, fa: F[A]): F[B] //map
}

object Fluffy {
  // Exercise 1
  // Relative Difficulty: 1
  def ListFluffy: Fluffy[List] = new Fluffy[List] {
    override def furry[A, B](f: A => B, fa: List[A]): List[B] = fa match {
      case Nil => Nil
      case a :: as => f(a) :: furry(f, as)
    }
  }

  // Exercise 2
  // Relative Difficulty: 1
  def OptionFluffy: Fluffy[Option] = new Fluffy[Option] {
    override def furry[A, B](f: A => B, fa: Option[A]): Option[B] = fa match {
      case Some(a) => Some(f(a))
      case None => None
    }
  }

  // Exercise 3
  // Relative Difficulty: 1
  def StreamFluffy: Fluffy[Stream] = new Fluffy[Stream] {
    override def furry[A, B](f: A => B, fa: Stream[A]): Stream[B] = fa match {
      case Empty => Empty
      case head #:: tail => f(head) #:: furry(f, tail)
    }
  }

  // Exercise 4
  // Relative Difficulty: 1
  def ArrayFluffy: Fluffy[Array] = new Fluffy[Array] {
    override def furry[A, B](f: A => B, fa: Array[A]): Array[B] = fa.map(f).asInstanceOf[Array[B]]

    /*
    fa match {
    case Array.empty => Array()
    case as @ Array(_) =>
  }*/
  }

  // Exercise 5
  // Relative Difficulty: 5
  def Function1Fluffy[X]: Fluffy[PartialType[Function1, X]#Apply] = new Fluffy[PartialType[Function, X]#Apply] {
    override def furry[A, B](f: A => B, fa: PartialType[Function, X]#Apply[A]): PartialType[Function, A]#Apply[B] =
      (v1: A) => f(v1)
  }


  // Exercise 6
  // Relative Difficulty: 6
  def EitherLeftFluffy[X]: Fluffy[PartialType[LeftProjection, X]#Flip] = new Fluffy[PartialType[LeftProjection, X]#Flip] {
    override def furry[A, B](f: A => B, fa: PartialType[LeftProjection, X]#Flip[A]): PartialType[LeftProjection, X]#Flip[B] =
      fa.map(f).left
  }

  // Exercise 7
  // Relative Difficulty: 4
  def EitherRightFluffy[X]: Fluffy[PartialType[Either.RightProjection, X]#Apply] = new Fluffy[PartialType[Either.RightProjection, X]#Apply] {
    override def furry[A, B](f: A => B, fa: PartialType[Either.RightProjection, X]#Apply[A]) = fa.map(f).right
  }
}

//monad
trait Misty[M[_]] extends Fluffy[M] {
  def banana[A, B](f: A => M[B], ma: M[A]): M[B] //flat map or bind

  def unicorn[A](a: A): M[A] //unit

  // Exercise 8
  // Relative Difficulty: 3
  // (use banana and/or unicorn)
  def furry[A, B](f: A => B, ma: M[A]): M[B] = banana((a: A) => unicorn(f(a)), ma)
}

object Misty {
  // Exercise 9
  // Relative Difficulty: 2
  def ListMisty: Misty[List] = new Misty[List] {
    override def banana[A, B](f: A => List[B], ma: List[A]): List[B] = ma.flatMap(f)

    override def unicorn[A](a: A): List[A] = List(a)
  }

  // Exercise 10
  // Relative Difficulty: 2
  def OptionMisty: Misty[Option] = new Misty[Option] {
    override def banana[A, B](f: A => Option[B], ma: Option[A]): Option[B] = ma.flatMap(f)

    override def unicorn[A](a: A): Option[A] = Option(a)
  }

  // Exercise 11
  // Relative Difficulty: 2
  def StreamMisty: Misty[Stream] = new Misty[Stream] {
    override def banana[A, B](f: A => Stream[B], ma: Stream[A]): Stream[B] = ma.flatMap(f)

    override def unicorn[A](a: A): Stream[A] = Stream(a)
  }

  // Exercise 12
  // Relative Difficulty: 2
  def ArrayMisty: Misty[Array] = new Misty[Array] {
    override def banana[A, B](f: A => Array[B], ma: Array[A]): Array[B] = ma.flatMap(f(_)).asInstanceOf[Array[B]]

    override def unicorn[A](a: A): Array[A] = ??? //Array(a)
  }

  // Exercise 13
  // Relative Difficulty: 6
  def Function1Misty[X]: Misty[PartialType[Function1, X]#Apply] = new Misty[PartialType[Function, X]#Apply] {
    override def banana[A, B](f: A => PartialType[Function, X]#Apply[B], ma: PartialType[Function, X]#Apply[A]): PartialType[Function, X]#Apply[B] =
      new PartialType[Function, X]#Apply[B] {
        override def apply(v1: X): PartialType[Function, X]#Apply[B] = f(ma.apply(v1))
      }

    override def unicorn[A](a: A): PartialType[Function, X]#Apply[A] = _ => a
  }

  // Exercise 14
  // Relative Difficulty: 7
  def EitherLeftMisty[X]: Misty[PartialType[Either.LeftProjection, X]#Flip] = new Misty[PartialType[LeftProjection, X]#Flip] {
    override def banana[A, B](f: A => PartialType[LeftProjection, X]#Flip[B], ma: PartialType[LeftProjection, X]#Flip[A]): PartialType[LeftProjection, X]#Flip[B] =
      LeftProjection(ma.flatMap(f andThen (_.e)))

    override def unicorn[A](a: A): PartialType[LeftProjection, X]#Flip[A] = Left(a).left
  }

  // Exercise 15
  // Relative Difficulty: 5
  def EitherRightMisty[X]: Misty[PartialType[Either.RightProjection, X]#Apply] = new Misty[PartialType[Either.RightProjection, X]#Apply] {

    override def banana[A, B](f: A => PartialType[Either.RightProjection, X]#Apply[B], ma: PartialType[Either.RightProjection, X]#Apply[A]): PartialType[Either.RightProjection, X]#Apply[B] =
      RightProjection(ma.flatMap(f andThen (_.e)))

    override def unicorn[A](a: A): PartialType[Either.RightProjection, X]#Apply[A] = RightProjection(Right(a))
  }

  // Exercise 16
  // Relative Difficulty: 3
  def jellybean[M[_], A](ma: M[M[A]], m: Misty[M]): M[A] = m.banana((a: M[A]) => a, ma)

  // Exercise 17
  // Relative Difficulty: 6
  def apple[M[_], A, B](ma: M[A], mf: M[A => B], m: Misty[M]): M[B] =
    m.banana((a: A) => m.furry((fn: A => B) => fn(a), mf), ma)

  // Exercise 18
  // Relative Difficulty: 6
  def moppy[M[_], A, B](as: List[A], f: A => M[B], m: Misty[M]): M[List[B]] =
    as.foldLeft(m.unicorn(List.empty[B])){(mlb, a) =>
      val mb: M[B] = f(a)
      m.banana((lb:List[B]) => m.furry((b:B) => b::lb , mb), mlb)
    }
  //TODO using apple
}

object AdvancedFun {

  case class State[S, A](f: S => (S, A))

  // Exercise 19
  // Relative Difficulty: 9
  def StateFluffy[S]: Fluffy[PartialType[State, S]#Apply] = new Fluffy[PartialType[State, S]#Apply] {
    override def furry[A, B](f: A => B, fa: PartialType[State, S]#Apply[A]): PartialType[State, S]#Apply[B] = ???
  }

  // Exercise 20
  // Relative Difficulty: 10
  def StateMisty[S]: Misty[PartialType[State, S]#Apply] = ???
}

