
/**
  * http://www.cakesolutions.net/teamblogs/existential-types-in-scala
  */

trait ExistentialType[AnyTypeClass[A]] {
  type A //type variable, fully dependent on the enclosed trait
  val value: A //It exists but we do not know anything about it
  implicit def evidence: AnyTypeClass[A] //to add restrictions that would allow us to use it without knowing what it is
}

object ExistentialType {
  case class TheTypeClass[V, AnyTypeClass[V]](value: V)(implicit val evidence: AnyTypeClass[V]) extends ExistentialType[AnyTypeClass] {
    override type A = V
  }

  //implicit def theTypeClass[V, AnyTypeClass[V]](value: V): TheTypeClass[V, AnyTypeClass[V]] = TheTypeClass(value)
}

