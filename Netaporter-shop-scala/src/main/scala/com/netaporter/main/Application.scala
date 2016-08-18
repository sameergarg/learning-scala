package com.netaporter.main

import com.netaporter.shop._

object Application extends App {

  println("************************************")
  println("* Welcome to the Net-A-Porter Shop *")
  println("************************************")
  println("Enter \"Q\" to Quit")
  println("Enter \"add <ProductId>\" to add to basket")
  println("Enter \"remove <ProductId>\" to remove from basket")
  println("Enter \"list\" to show a list of products in the inventory")
  println("Enter \"total\" to show the total price of the basket")

  def toId: String => Long = _.trim.toLong

  val input  = io.Source.stdin.getLines.takeWhile(!_.equals("Q")).map(_.split(" ").toList)

  var basket = ShoppingBasket()

  input foreach {
      case "add" :: productId :: Nil =>
        val id = toId(productId)
        NetAPorterInventory.findProduct(id) match {
          case Some(product) => basket = basket.add(product)
          case _ => println(s"There is no product for productId $productId")
        }
      case "remove" :: productId :: Nil =>
        val id = toId(productId)
        basket = basket.remove(id)
      case "list" :: Nil =>
        NetAPorterInventory.listProducts.foreach(println)
      case "total" :: Nil =>
        println(s"Total to pay: £${basket.total}")
      case _ =>
        println(s"Sorry, that is not a valid command")
    }

  println("Thanks for shopping at Net-a-Porter!")

}