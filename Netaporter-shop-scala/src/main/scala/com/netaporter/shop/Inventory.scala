package com.netaporter.shop

import scala.io.Source

trait Inventory {
  def listProducts: List[Product]

  def findProduct(productId: Long) = listProducts.find(_.id == productId)
}

trait FileBackedInventory extends Inventory {

  private[shop] val productItems: Iterator[String] = Source.fromInputStream(getClass.getResourceAsStream("/Items.csv"))
    .getLines()
    .drop(1)

  private[shop] val productAttributes: String => Array[String] = _.split(",")

  private[shop] val parseAmount: String => BigDecimal = price => BigDecimal(price.dropWhile(!_.isDigit))

  private[shop] val parseToProduct: String => Product = line => productAttributes(line) match {
    case Array(id, name, price) => Product(id.toLong, name, parseAmount(price))
  }

  lazy val listProducts: List[Product] = productItems.map(parseToProduct).toList
}

object NetAPorterInventory extends FileBackedInventory

