import katas.algorithms.greedy._

GreedyCoinChanger.giveChange(List(1,2,3), 6)
GreedyCoinChanger.giveChange2(List(1,2,3), 6)
GreedyCoinChanger.giveChange(List(1,2,3), 3)
GreedyCoinChanger.giveChange(List(3,2,1), 5)


DynamicCoinChanger.minCoinsChange(List(10,5,2,1), 23)
DynamicCoinChanger.minCoinsChange(List(10,5,2,1), 30)

new CoinChanger {
  override def giveChange(coins: List[Int], amount: Int): List[Int] = ???
}.numberOfWays(coins = List(1,2,3), amount = 5)



DynamicCoinChanger.minCoinsChangeWithMemoization(List(1,2,5,10), 30)


