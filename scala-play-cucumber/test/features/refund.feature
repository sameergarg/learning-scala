Feature: Refund item

  Sales assistants should be able to refund customers' purchases.
  This is required by the law, and is also essential in order to
  keep customers happy.

  Scenario: Jeff returns a faulty microwave

    Given Jeff has bought a microwave for 100 dollars
    And he has a receipt
    When Jeff returns the microwave
    Then he should be refunded 100 dollars