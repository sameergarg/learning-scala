package calculator

import calculator.Polynomial.computeSolutions
import org.scalatest._

/**
 * Created by sameer on 01/05/15.
 */
class PolynomialTestSuite extends FunSuite with ShouldMatchers {

  test("compute solutions") {
    assert(computeSolutions(Signal(1.0), Signal(2.0), Signal(1.0), Signal(0))() == Signal(Set(-1.0))())
    assert(computeSolutions(Signal(1.0), Signal(3.0), Signal(2.0), Signal(1))() == Signal(Set(-1.0,-2.0))())
    assert(computeSolutions(Signal(1.0), Signal(3.0), Signal(2.0), Signal(-1))() == Signal(Set())())
  }
}
