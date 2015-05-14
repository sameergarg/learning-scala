package services

/**
 * Created by sameer on 14/05/15.
 */
package object bank {

  case class Account(balance: Int, holder: String)

  case class Card(valid: Boolean)

}
