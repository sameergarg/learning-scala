package katas.designpatterns.stratergy

sealed trait AssetType { def name(): String }

case object BOND extends AssetType {val name = "BOND"}

case object STOCK extends AssetType {val name = "STOCK"}


case class Asset(assetType: AssetType, value: Int)

object AssetRepository {
  val assets = List(Asset(BOND, 1000), Asset(BOND, 2000), Asset(STOCK, 5000), Asset(BOND, 10000))
}

