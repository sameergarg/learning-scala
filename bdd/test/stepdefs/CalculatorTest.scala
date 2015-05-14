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

  Given("""^the digit (\d+)$"""){ (operand:Int) =>
    op1 = operand
  }

  When("""^it is multiplied by (\d+)$"""){ (operand:Int) =>
    op2 = operand
    operator = '*'
  }

  Then("""^the result is (\d+)$"""){ (result:Int) =>
    calculate(op1, op2, operator) shouldBe result
  }
}
