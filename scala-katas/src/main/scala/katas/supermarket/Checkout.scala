package katas.supermarket

trait Offer {

  val itemPrice = Map("Apple" -> 60, "Orange" -> 25, "Banana" -> 100)

  def offerPrice(shoppingBasket: List[String]): Int
}

class BuyNGetMFreeOffer(n: Int, m: Int) extends Offer {

  override def offerPrice(shoppingBasket: List[String]): Int = {
    val size = shoppingBasket.size
    val prices = shoppingBasket.flatMap(itemPrice.get(_)).sortWith((x, y) => x > y)
    val totalPrice = prices.foldRight(0)(_ + _)
    val offerItemsPrice = prices.takeRight(size / n).foldRight(0)(_ + _)

    totalPrice - offerItemsPrice
  }
}

class Checkout {

  type ShoppingCart = List[String]

  def totalFor(shoppingCart: ShoppingCart) = {
    threeForTwoOffer(shoppingCart) + oneForOneOffer(shoppingCart)
  }

  def oneForOneOffer(shoppingCart: ShoppingCart): Int = {
    new BuyNGetMFreeOffer(2,1).offerPrice(shoppingCart.filter(name => name == "Apple" || name == "Banana"))
  }

  private def threeForTwoOffer(shoppingCart: ShoppingCart): Int = {
    new BuyNGetMFreeOffer(3,2).offerPrice(shoppingCart.filter(_ == "Orange"))
  }


}
