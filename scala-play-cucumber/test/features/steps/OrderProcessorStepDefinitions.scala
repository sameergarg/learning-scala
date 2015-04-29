package features.steps

import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import service.{Customer, Order, OrderProcessor}
import scala.collection.mutable.Map
/**
 * Created by sameer on 28/04/15.
 */
class OrderProcessorStepDefinitions extends ScalaDsl with EN with Matchers {

  val orderDetails: Map[String, String] = Map()

  Given("""^(.*) has bought a (.*) for (\d+) dollars$""") {
    (customerName: String, itemName: String, amount: Int) =>
    val order = Order(itemName, 1, amount, Customer(customerName))
    OrderProcessor.purchase(order)
  }

  And("""^he has a receipt$""") {
    () =>
      orderDetails += ("hasReceipt"->"true")
  }

  When("""^(.*) returns the (.*)$""") {
    (customerName: String, itemName: String) =>
      orderDetails += ("customerName" -> customerName)
      orderDetails += ("itemName" -> itemName)
  }

  Then("""^he should be refunded (\d+) dollars$""") {
    (refundAmount: Int) =>
      val refund = OrderProcessor.refund(orderDetails("customerName"), orderDetails("itemName"), orderDetails("hasReceipt").toBoolean)
      refund.get shouldBe refundAmount
  }
}
