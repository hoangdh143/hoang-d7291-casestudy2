  Feature: ATM Transaction
    Scenario: User performs withdrawal
      Given authenticated user has a balance of 1000

      When user performs a withdrawal of 100
      And user performs a withdrawal of 100
      And user performs a withdrawal of 50

      Then the balance is 750
      And user gets the list of transactions from newest to oldest