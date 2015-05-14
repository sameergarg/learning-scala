package services.bank

import org.scalatest.{Matchers, GivenWhenThen, FeatureSpec}

/**
 * Created by sameer on 14/05/15.
 */
class ATMSpec extends FeatureSpec with GivenWhenThen with Matchers {

  info(
    """As an Account Holder
      |I want to withdraw cash from an ATM
      |So that I can get money
      |when the bank is closed""".stripMargin)


  feature("Account Holder withdraws cash") {

    scenario("Account has sufficient funds") {

      Given("""the account balance is £100""")
        val account = Account(balance = 100, holder = "George")

      And("the card is valid")
        val card = Card(valid = true)

      And("the machine contains enough money")
        val atm = ATM(balance = 1000)

      When("""the Account Holder requests £20""")
        val afterWithdrawal = atm.withdraw(20, account)

      Then("""the account balance should be £80""")
        afterWithdrawal.balance shouldBe 80
    }
  }
}
