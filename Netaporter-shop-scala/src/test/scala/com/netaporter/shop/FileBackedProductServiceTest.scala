package com.netaporter.shop

import org.scalatest.{Matchers, WordSpec}

class FileBackedProductServiceSpec extends WordSpec with Matchers {

  "A list of products" should {
    "be read from the csv file" in new FileBackedInventory {
      productItems.size shouldBe 7
    }

    "be split using comma separator" in new FileBackedInventory {
      productAttributes("Hello,World") shouldBe Array("Hello", "World")
    }

    "be parsed to Product" in new FileBackedInventory {
      parseToProduct("1,A shirt,£9.99") shouldBe Product(1, "A shirt", BigDecimal(9.99))
    }

    "be created from the csv file" in new FileBackedInventory {
      private val products = listProducts.toList
      products.head.toString shouldBe "ProductId=1, Name=Short Sleeve Jumper, Price=£9.99"
    }
  }

  "Product price" should {
    "be parsed to number" when {
      "price contains empty space in beginning" in new FileBackedInventory {
        parseAmount("  45.00") shouldBe BigDecimal(45.0)
      }

      "price contains currency symbol in beginning" in new FileBackedInventory {
        parseAmount("£45.00") shouldBe BigDecimal(45.0)
      }

      "price contains non numeric characters in beginning" in new FileBackedInventory {
        parseAmount("  £45.00") shouldBe BigDecimal(45.0)
      }
    }
  }

}
