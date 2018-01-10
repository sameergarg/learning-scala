package katas.sorting

import org.joda.time.{DateTime, LocalDate}


case class Person(name: String, dob: DateTime, dod: Option[DateTime] = None)

object Compare {
  val family = Seq(
    Person(name = "John", dob= LocalDate.parse("1900-01-01").toDateTimeAtCurrentTime, dod = Some(LocalDate.parse("2000-12-31").toDateTimeAtCurrentTime)),
    Person(name = "Mary", dob= LocalDate.parse("1910-01-01").toDateTimeAtCurrentTime, dod = Some(LocalDate.parse("2010-12-31").toDateTimeAtCurrentTime)),
    Person(name = "Harry", dob= LocalDate.parse("1970-01-01").toDateTimeAtCurrentTime),
    Person(name = "Sally", dob= LocalDate.parse("1980-01-01").toDateTimeAtCurrentTime)
  )

  def orderFamily(fam: Seq[Person] = family) = {}
}
