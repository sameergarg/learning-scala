package stepdefs

import cucumber.api.PendingException
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers

/**
 * Created by sameer on 14/05/15.
 */
class ATMStepDefs extends ScalaDsl with EN with Matchers {

  Given("""^the account balance is £(\d+)$"""){ (arg0:Int) =>

  }
}
