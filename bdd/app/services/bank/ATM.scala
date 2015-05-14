package services.bank

/**
 * Created by sameer on 14/05/15.
 */
trait Operations {

  def deposit(money: Int, acc: Account) = ???

  def withdraw(money: Int, acc: Account) = {
    val accountBalance = acc.balance
    acc.copy(accountBalance - money)
  }
}

class ATM(balance: Int) extends Operations

object ATM {
  def apply(balance: Int) = new ATM(balance)
}


