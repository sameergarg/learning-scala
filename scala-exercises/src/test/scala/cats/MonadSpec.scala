package cats

import cats.implicits._
import org.scalatest.{Matchers, WordSpec}

class MonadSpec extends WordSpec with Matchers {

  case class MyOptionT[F[_], A](value: F[Option[A]])

  implicit def myOptionT[F[_]](implicit mf: Monad[F]) = new Monad[MyOptionT[F, ?]] {

    override def pure[A](x: A): MyOptionT[F, A] = MyOptionT(mf.pure(Some(x)))

    override def flatMap[A, B](fa: MyOptionT[F, A])(f: A => MyOptionT[F, B]): MyOptionT[F, B] = {
      val fb: F[Option[B]] = fa.value.flatMap {
        case None => mf.pure(None)
        case Some(a) => f(a).value

      }
      MyOptionT(fb)
    }

    override def tailRecM[A, B](a: A)(f: A => MyOptionT[F, Either[A, B]]): MyOptionT[F, B] = {
      val value: F[Option[Either[A, B]]] = f(a).value
      val ob: F[Option[B]] = value.flatMap {
        case None => mf.pure(None)
        case Some(eab) => mf.pure(eab.map(Some(_)).getOrElse(None))
      }
      MyOptionT(ob)
    }

  }

  "pure" in {
    myOptionT[List].pure(42) shouldBe MyOptionT[List, Int](List(Some(42)))
  }

}
