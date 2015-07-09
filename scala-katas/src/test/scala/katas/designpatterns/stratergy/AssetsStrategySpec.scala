package katas.designpatterns.stratergy

import java.util.function.Predicate

import katas.designpatterns.stratergy.AssetsStrategyImperative.assets
import org.scalatest.{Matchers, FlatSpec}
import java.util.function.{ Function ⇒ JFunction, Predicate ⇒ JPredicate, BiPredicate }

/**
 *
 */
class AssetsStrategySpec extends FlatSpec with Matchers {

  implicit def toJavaPredicate[A](f: Function1[A, Boolean]) = new JPredicate[A] {
    override def test(a: A): Boolean = f(a)
  }

  "AssetsStrategy" should "total asset values" in new AssetsStrategyImperative() {
    totalAssetValuesOf(assets) shouldBe 18000

    assetValues(assets, BOND) shouldBe 3000
  }

  it should "total bonds asset values" in new AssetsStrategyImperative() {

    assetValues(assets, BOND) shouldBe 3000
  }

  it should "total stock asset values" in new AssetsStrategyImperative() {

    assetValues(assets, STOCK) shouldBe 15000
  }

  "it" should "total asset values" in new AssetsStrategyFunctional() {

    AssetsStrategyFunctional.assetValuesOf(assets,toJavaPredicate(asset => true)) shouldBe 18000

  }

  it should "total bonds asset values" in new AssetsStrategyFunctional() {
    AssetsStrategyFunctional.assetValuesOf(assets,toJavaPredicate(asset => asset.assetType == BOND)) shouldBe 3000
  }

  it should "total stock asset values" in new AssetsStrategyFunctional() {

  }


}
