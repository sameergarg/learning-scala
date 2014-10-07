package katas.supermarket

trait Offer {

  val itemPrice = Map("Apple" -> 60, "Orange" -> 25, "Banana" -> 100, "Melon" -> 200)

  def offerPrice(shoppingBasket: List[String]): Int
}

class BuyNGetOneFreeOffer(n: Int) extends Offer {

  override def offerPrice(shoppingBasket: List[String]): Int = {
    val totalPrice = shoppingBasket.flatMap(itemPrice.get(_)).sorted.reverse
    val offerItemsPrice = totalPrice.zipWithIndex.filter(pair => (pair._2+1) % n == 0).map(_._1).toList.sum
    totalPrice.sum - offerItemsPrice
  }
}

class NoOffer() extends Offer {

  override def offerPrice(shoppingBasket: List[String]): Int = shoppingBasket.flatMap(itemPrice.get(_)).sum
}

class Checkout {

  type ShoppingCart = List[String]

  def totalFor(shoppingCart: ShoppingCart) = {
    threeForTwoOffer(shoppingCart) + oneForOneOffer(shoppingCart) + notOnOffer(shoppingCart)
  }

  def notOnOffer(shoppingCart: ShoppingCart): Int = {
    new NoOffer().offerPrice(shoppingCart.filter(_ == "Melon"))
  }

  def oneForOneOffer(shoppingCart: ShoppingCart): Int = {
    new BuyNGetOneFreeOffer(2).offerPrice(shoppingCart.filter(name => name == "Apple" || name == "Banana"))
  }

  private def threeForTwoOffer(shoppingCart: ShoppingCart): Int = {
    new BuyNGetOneFreeOffer(3).offerPrice(shoppingCart.filter(_ == "Orange"))
  }


}
