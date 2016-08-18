package com.netaporter.shop

trait Basket {

  def getInventory: List[Product]

  var products: List[Product] = List.empty

  def getBasketProducts = products

  def add(productId: Long): Basket = {
    products = getInventory
      .find(_.id == productId)
      .getOrElse(throw new IllegalArgumentException(s"product corresponding to the specified product id $productId doesn't exists in inventory")) :: products
    this
  }

  def remove(productId: Long): Basket = {
    products = products.filterNot(_.id == productId)
    this
  }

  def total = products.map(_.price).sum
}

class ShoppingBasket extends Basket {
  override def getInventory: List[Product] = Inventory.allProducts
}
