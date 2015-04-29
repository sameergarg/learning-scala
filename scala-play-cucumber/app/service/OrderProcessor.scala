package service

import scala.util.{Failure, Success, Try}

case class Customer(name: String)

case class Order(itemName: String, quantity: Int, price: Int, customer:Customer)

trait OrderProcessor {

  type RefundAmount = Int

  var orders: List[Order]

  def orderFor(customerName: String, itemName: String) = orders.find{ order =>
    order.customer.name == customerName && order.itemName == itemName
  }

  def purchase(order:Order) = {
    orders = order :: orders
  }

  def refund(customerName: String, itemName: String, hasReceipt: Boolean): Try[RefundAmount] =
    (orderFor(customerName, itemName), hasReceipt) match {
      case (Some(order:Order), true) => Success(order.price)
      case (Some(order:Order), false) => Failure(new IllegalArgumentException(s"Refunds are not permitted without receipt"))
      case (None, _) => Failure(new IllegalArgumentException(s"No record for $customerName buying $itemName"))
    }

}

object OrderProcessor extends OrderProcessor {

  override var orders = List[Order]()
}
