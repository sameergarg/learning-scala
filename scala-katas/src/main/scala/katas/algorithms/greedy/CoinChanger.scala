package katas.algorithms.greedy

/**
  * How can a given amount of money be made with the least number of coins of given denominations
  * Amount = 5
  * coins [] = {1,2,3}
  * Ways to make change = 5
  * {1,1,1,1,1} {1,1,1,2}, {1,2,2}, {1,1,3} {2,3}
  * Optimal solution is with 2 coins
  */
trait CoinChanger {

  def giveChange(coins: List[Int], amount: Int): List[Int]

  def numberOfWays(coins: List[Int], amount: Int): Int =
    if(amount == 0) 1 //there is 1 solution (do not include any coin)
    else if(coins.isEmpty || amount < 0) 0 // we have run out of coins(1st recursive scenario) or amount has become negative(second recursive scenario)
    else  numberOfWays(coins.tail, amount) + numberOfWays(coins, amount - coins.head) // count is sum of solutions (i) including nth coin (ii) excluding nth coin

}

object GreedyCoinChanger extends CoinChanger {

  override def giveChange(coins: List[Int], amount: Int) = {

    def reduceAmount(amount: Int, denomination: Int) = (amount/denomination, amount%denomination)

    def reduceHead(numberOfCoinsAndRemainderAmount: (List[Int], Int), coin: Int):(List[Int], Int) = {
      val dm = reduceAmount(numberOfCoinsAndRemainderAmount._2, coin)
      (List.fill(dm._1)(coin) ++ numberOfCoinsAndRemainderAmount._1, dm._2)
    }
    coins.sorted.reverse.foldLeft((List[Int](), amount))(reduceHead(_ , _))._1
  }

  def giveChange2(coins: List[Int], amount: Int) = {
    def loop(remCoins: List[Int], remAmount: Int, change: List[Int]): List[Int] = {
      if(remAmount == 0)
        change
      else if(remCoins.isEmpty || remAmount <0)
        Nil
      else {
        if(remCoins.head > remAmount) loop(remCoins.tail, remAmount, change)
        else loop(remCoins.tail, remAmount - (remAmount/remCoins.head) *remCoins.head, List.fill(remAmount%remCoins.head)(remCoins.head) ++ change )
      }
    }
    loop(coins.sorted.reverse, amount, List.empty)

  }


}

object DynamicCoinChanger extends CoinChanger {

  override def giveChange(coins: List[Int], amount: Int) = {
    ???

  }

  /**
    * Inefficient as it traverses same path multiple times like C({1}, 3) is called two times
    * C() --> count()
                              C({1,2,3}, 5)
                           /                \
                         /                   \
             C({1,2,3}, 2)                 C({1,2}, 5)
            /     \                        /         \
           /        \                     /           \
C({1,2,3}, -1)  C({1,2}, 2)        C({1,2}, 3)    C({1}, 5)
               /     \            /    \            /     \
             /        \          /      \          /       \
    C({1,2},0)  C({1},2)   C({1,2},1) C({1},3)    C({1}, 4)  C({}, 5)
                   / \      / \       / \        /     \
                  /   \    /   \     /   \      /       \
                .      .  .     .   .     .   C({1}, 3) C({}, 4)
                                               /  \
                                              /    \
                                             .      .
    * @param coins
    * @param amount
    * @return
    */
  def minCoinsChange(coins: List[Int], amount: Int): Int = {
    if(amount == 0) 0 // no more amount left
    else if(coins.isEmpty) Int.MaxValue // amount is not zero and run out of coin
    else if(amount < coins.head) minCoinsChange(coins.tail, amount)//amount is less than the biggest coin
    else {
        val includingMax = 1 + minCoinsChange(coins, amount - coins.head) // add 1 for the max denomination taken out from amount
        val excludingMax = minCoinsChange(coins.tail, amount) // try by not including max denomination coin
        Math.min(includingMax, excludingMax) // take the minimum of both
    }
  }

  /**
    * Base Cases:

    if amount=0 then just return empty set to make the change, so 1 way to make the change.
    if no coins given, 0 ways to change the amount.
    Rest of the cases:

    For every coin we have an option to include it in solu­tion or exclude it.
    check if the coin value is less than or equal to the amount needed, if yes then we will find ways by includ­ing that coin and exclud­ing that coin.
    Include the coin: reduce the amount by coin value and use the sub prob­lem solu­tion (amount-v[i]).
    Exclude the coin: solu­tion for the same amount with­out con­sid­er­ing that coin.
    If coin value is greater than the amount then we can’t con­sider that coin, so solu­tion will be with­out con­sid­er­ing that coin.

    solution[coins+1][amount+1]
                    =	0	if i=0
    solution[i][j]	=	1	if j=0
                    =	solution[i — 1][j] + solution[i][j — v[i — 1]]	if(coin[i]<=j)
                    =	solution[i — 1][j];	if(coin[i]>j)
    *                           Amount
    *               0       1       2       3       4
    *         0     1       0       0       0       0
    *         1     1       1       1       1       1
    * Coins   2     1       1       2       3       3
    *         3     1       1       2       3       4
    *
    * @param coins
    * @param amount
    * @return
    */
  def minCoinsChangeWithMemoization(coins: List[Int], amount: Int): Int = {
    //rows represents coins and cols represents amount
    val solution = Array.ofDim[Int](coins.size + 1, amount)//needs to have +1 row for the case of 0 coin
    //0th row when coins is 0 will always have 0 solution
    for(col <- 0 to amount)(solution(0)(col) = 0)
    //0th col when amount of 0 will have 1 solution
    for(row <- 0 to coins.size)(solution(row)(0)=1)

    for(coinIndex <- 1 to coins.size)
      for(amtIndex <- 1 to amount){
        if(coins(coinIndex-1) <= amtIndex)// check if the coin value is less than the amount needed
            solution(coinIndex)(amtIndex) = solution(coinIndex -1)(amtIndex) + solution(coinIndex)(amtIndex - coins(coinIndex-1))//reduce the amount by coin value an use the subproblem solution (amount-v[i]) and add the solution from the top to it
          else
            solution(coinIndex)(amtIndex) = solution(coinIndex -1)(amtIndex)

        //println(s"Solution[$coinIndex, $amtIndex] = ${solution(coinIndex)(amtIndex)}")
      }


    solution(coins.size)(amount-1)
  }

}

object GreedyCoinChangerOther {
  def divMod(dividend: Int, divisor: Int) = {
    (dividend / divisor, dividend % divisor)
  }

  // the list has to be sorted in descending order of denomination
  def change(coins: List[Int], amount: Int): List[Int] = {
    def changeOne(pair: (List[Int], Int), div: Int): (List[Int], Int) = {
      val dm = divMod(pair._2, div)
      ((dm._1 :: pair._1), dm._2)
    }
    (((List[Int](), amount) /: coins)(changeOne(_,_)))._1.reverse
  }

  def main(args: Array[String]) = {
    println(change(List(25, 10, 5, 1), 71))
  }
}
