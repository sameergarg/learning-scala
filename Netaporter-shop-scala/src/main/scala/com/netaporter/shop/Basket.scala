package com.netaporter.shop

trait Basket {

  var products: List[Product] = List.empty

  def productsInside = products

  def add(product: Product): Basket = {
    products = product :: products
    this
  }

  def remove(productId: Long): Basket = {
    products = products.filterNot(_.id == productId)
    this
  }

  def total = products.map(_.price).sum
}

class ShoppingBasket extends Basket
