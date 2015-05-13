package services

import scala.collection.mutable

/**
 * Created by sameer on 13/05/15.
 */
trait Calculator {

  def calculate(op1: Number, op2: Number, operation:Char) = operation match {
    case '*' =>  op1.intValue() * op2.intValue()
    case c: Char => throw new IllegalArgumentException(s"operator $c not supported")
  }
}


