package com.netaporter.shop

case class Product(id: Long, name: String, price: BigDecimal) {

  override def toString: String = s"ProductId=$id, Name=$name, Price=£$price"
}


