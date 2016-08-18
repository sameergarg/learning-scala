package com.netaporter.shop

case class ShoppingBasket(products: List[Product] = List.empty) {

  def add(product: Product) =
    ShoppingBasket(product:: products)

  def remove(productId: Long) =
    ShoppingBasket(products.filterNot(_.id == productId))

  def total = products.map(_.price).sum

}
