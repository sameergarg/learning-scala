package katas.signals

import scala.collection.mutable

trait Subscriber {
  def handler(publisher: Publisher)
}

trait Publisher {

  var subscribers: Set[Subscriber] = Set()

  def subscribe(subscriber: Subscriber) = subscribers += subscriber

  def unsubscribe(subscriber: Subscriber) = subscribers -= subscriber

  def publish = subscribers.foreach(_.handler(this))

}

class BankAccount extends Publisher {

  private var balance = 0.0

  def currentBalance = balance

  def deposit(money: Double) = {
    if(money > 0)
      balance = balance + money
    publish
  }

  def withdraw(money: Double) = {
    if (balance < money)
      throw new IllegalStateException("Insufficient funds")
    else {
      balance = balance - money
      publish
    }
  }
}

class ConsolidatedAccounts(observed: List[BankAccount]) extends Subscriber {

  observed.foreach(_.subscribe(this))

  private var total: Double = _
  calculateTotal

  def accountsTotal = total

  override def handler(publisher: Publisher): Unit =
    calculateTotal


  private def calculateTotal = {
    total = observed.map(_.currentBalance).sum
  }
}



