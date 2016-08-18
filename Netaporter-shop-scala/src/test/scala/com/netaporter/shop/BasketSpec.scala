package com.netaporter.shop

import org.scalatest.{Matchers, WordSpec}

class BasketSpec extends WordSpec with Matchers {
  val milk_1 = Product(1, "Milk", BigDecimal(1.99))
  val eggs_2 = Product(2, "Eggs", BigDecimal(2.99))
  val bread_3 = Product(3, "Bread", BigDecimal(0.99))

  trait BasketWithSetup extends Basket {
    override def getInventory: List[Product] = {
      List(milk_1, eggs_2, bread_3)
    }
  }

  "Shopping Basket" should {
    "allow to add a product in empty basket" in new BasketWithSetup {
      add(1).getBasketProducts shouldBe List(milk_1)
    }

    "allow to add multiple product in empty basket" in new BasketWithSetup {
      add(1).add(2).getBasketProducts shouldBe List(eggs_2, milk_1)
    }

    "detect if non existing product is added" in new BasketWithSetup {
      an[IllegalArgumentException] should be thrownBy add(100)
    }

    "allow to remove products from the basket" in new BasketWithSetup {
      add(1).remove(1).getBasketProducts shouldBe empty
    }

    "not fail to remove a product which isn't in basket" in new BasketWithSetup {
      remove(100).getBasketProducts shouldBe empty
    }

    "not empty the basket when basket is emptied partially" in new BasketWithSetup {
      add(1).add(2).remove(1).getBasketProducts shouldBe List(eggs_2)
    }

    "give total price of items as 0" when {
      "basket is empty" in new BasketWithSetup {
        total shouldBe 0
      }
    }

    "give price of the item" when {
      "basket has got one of that item" in new BasketWithSetup {
        add(1).total shouldBe BigDecimal(1.99)
      }
    }

    "give total as the sum of individual prices of all the items" when {
      "there are multiple items in the basket" in new BasketWithSetup {
        add(1).add(2).total shouldBe BigDecimal(4.98)
      }
    }

  }
}