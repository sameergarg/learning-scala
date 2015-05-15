Feature: Account Holder withdraws cash

As an Account Holder
I want to withdraw cash from an ATM
So that I can get money when the bank is closed

  #@wip
  Scenario: Account has sufficient funds

  Given the account balance is £100
  And the card is valid
  And the machine contains enough money
  When the Account Holder requests £20
  Then the account balance should be £80

Scenario: Account has insufficient funds
  Given the account balance is £10
  And the card is valid
  And the machine contains enough money
  When the Account Holder requests £20
  Then the ATM should not dispense any money
  And the ATM should say there are insufficient funds
  And the account balance should be £20
  And the card should be returned

Scenario: Card has been disabled
  Given the card is disabled
  When the Account Holder requests £20
  Then the ATM should retain the card
  And the ATM should say the card has been retained