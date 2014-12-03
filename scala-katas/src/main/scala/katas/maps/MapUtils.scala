package katas.maps

import scala.util.Try

/**
 * Created by sameer on 02/12/14.
 */
object MapUtils {

  implicit def toMapOfBoolean(toConvert: Map[String, Option[String]]): Map[String, Boolean] = (for{
    (key, optionValue) <- toConvert
    value <- optionValue
  } yield (key->Try(value.toBoolean).getOrElse(false))) toMap

  def print(m: Map[String, Boolean]) = {
    println(m)
    m
  }
}
