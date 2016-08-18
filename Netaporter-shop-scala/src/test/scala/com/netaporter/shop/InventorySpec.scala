package com.netaporter.shop

import org.scalatest.{Matchers, WordSpec}

class InventorySpec extends WordSpec with Matchers {
  val milk = Product(1, "Milk", BigDecimal(1.99))

  "An inventory" should {
    "be able to find an existing product" in new Inventory {
      override def listProducts = List(milk)

      findProduct(1) shouldBe Some(milk)
    }

    "not be able to find a non existing product" in new Inventory {
      override def listProducts = List.empty

      findProduct(1) shouldBe None
    }
  }
}