package stepdefs

import cucumber.api.PendingException
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import services.Calculator

/**
 * Created by sameer on 13/05/15.
 */
class CalculatorTest extends ScalaDsl with EN with Matchers with Calculator {

  var op1: Number = _
  var op2: Number = _
  var operator: Char = _

  Given("""^the digit (\d+)$"""){ (arg0:Int) =>
    op1 = arg0
  }

  When("""^it is multiplied by (\d+)$"""){ (arg0:Int) =>
    op2 = arg0
    operator = '*'
  }

  Then("""^the result is (\d+)$"""){ (arg0:Int) =>
    calculate(op1, op2, operator) shouldBe arg0
  }
}
