import org.joda.time.{LocalDate, DateTime}
object Comparison {
  case class Person(name: String, dob: DateTime, dod: Option[DateTime] = None)

  val family = Seq(
    Person(name = "John", dob= LocalDate.parse("1900-01-01").toDateTimeAtCurrentTime, dod = Some(LocalDate.parse("2010-12-31").toDateTimeAtCurrentTime)),
    Person(name = "Mary", dob= LocalDate.parse("1910-01-01").toDateTimeAtCurrentTime, dod = Some(LocalDate.parse("2000-12-31").toDateTimeAtCurrentTime)),
    Person(name = "Harry", dob= LocalDate.parse("1980-01-01").toDateTimeAtCurrentTime),
    Person(name = "Sally", dob= LocalDate.parse("1970-01-01").toDateTimeAtCurrentTime)
  )

  implicit def orderingByDod[A <: Person]: Ordering[A] = new Ordering[A] {
    override def compare(x: A, y: A): Int = (x.dod, y.dod) match {
      case (Some(d1), Some(d2)) => if(d1.isAfter(d2)) -1 else 1
      case (None, Some(d2)) => -1
      case (Some(d1), None) => +1
      case (None, None) => -1
    }
  }

  def orderFamily(fam: Seq[Person] = family) =
    family.sorted




}

println(Comparison.orderFamily().map(_.name))