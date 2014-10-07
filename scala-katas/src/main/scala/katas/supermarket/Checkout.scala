package katas.supermarket

trait Offer {

  val itemPrice = Map("Apple" -> 60, "Orange" -> 25, "Banana" -> 100)

  def offerPrice(shoppingBasket: List[String]): Int
}

class BuyNGetMFreeOffer(n: Int, m: Int) extends Offer {

  override def offerPrice(shoppingBasket: List[String]): Int = {
    val size = shoppingBasket.size
    val prices = shoppingBasket.flatMap(itemPrice.get(_)).sortWith((x, y) => x > y)
    prices.foldRight(0)(_ + _) - prices.takeRight(size/n).foldRight(0)(_ + _)
  }
}

class Checkout {

  type ShoppingCart = List[String]

  def totalFor(shoppingCart: ShoppingCart) = {
    threeForTwoOffer(shoppingCart) + oneForOneOffer(shoppingCart)
  }

  def oneForOneOffer(shoppingCart: ShoppingCart): Int = {
    val offerItemPrices = shoppingCart.filter(name => name == "Apple" || name == "Banana").map(name => if (name == "Apple") 60 else if (name == "Banana") 100 else 0)

    val sortedPrices = offerItemPrices.sortWith((x, y) => x > y)

    sortedPrices.take((sortedPrices.size + 1) % 2).foldRight(0)(_ + _)

    //new BuyNGetMFreeOffer(2,1).offerPrice(shoppingCart.filter(name => name == "Apple" || name == "Banana"))
    //(apples/2 * 60 + apples%2 * 60) + (bananas/2 * 100 + bananas%2 * 100)
  }

  private def threeForTwoOffer(shoppingCart: ShoppingCart): Int = {
    val count = shoppingCart.count(_ == "Orange")
    count * 2 / 3 * 25 + count % 3 * 25
    //new BuyNGetMFreeOffer(3,2).offerPrice(shoppingCart.filter(_ == "Orange"))
  }


}
