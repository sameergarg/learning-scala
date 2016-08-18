package com.netaporter.shop

import org.scalatest.{Matchers, WordSpec}

class BasketSpec extends WordSpec with Matchers {
  val milk = Product(1, "Milk", BigDecimal(1.99))
  val eggs = Product(2, "Eggs", BigDecimal(2.99))
  val bread = Product(3, "Bread", BigDecimal(0.99))


  "Shopping Basket" should {
    "allow to add a product in empty basket" in new ShoppingBasket {
      add(milk).products shouldBe List(milk)
    }

    "allow to add multiple product in empty basket" in new ShoppingBasket {
      add(milk).add(eggs).products shouldBe List(eggs, milk)
    }

    "allow to remove products from the basket" in new ShoppingBasket {
      add(milk).remove(milk.id).products shouldBe empty
    }

    "not fail to remove a product which isn't in basket" in new ShoppingBasket {
      remove(100).products shouldBe empty
    }

    "not empty the basket when basket is emptied partially" in new ShoppingBasket {
      add(milk).add(eggs).remove(milk.id).products shouldBe List(eggs)
    }

    "give total price of items as 0" when {
      "basket is empty" in new ShoppingBasket {
        total shouldBe 0
      }
    }

    "give price of the item" when {
      "basket has got one of that item" in new ShoppingBasket {
        add(milk).total shouldBe BigDecimal(1.99)
      }
    }

    "give total as the sum of individual prices of all the items" when {
      "there are multiple items in the basket" in new ShoppingBasket {
        add(milk).add(eggs).total shouldBe BigDecimal(4.98)
      }
    }

  }
}