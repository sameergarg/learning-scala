Feature: Search google

  As a tester,
  I would like to utilize cucumber,
  So that I can create BDD style selenium-webdriver tests.

  Scenario: Google search, using selenium
    Given I have navigated to google
    When I search for "selenium"
    Then the page title should be "Google"