@loginpage @all
Feature: Verify the login function of the Provisioning Portal website

  Background: 
    Given I am on the Provisioning Portal Website

  @TC1
  Scenario: TC1-User cannot login the Provisioning Portal website under a wrong password
    When I enter a wrong username or password to LogIn
      | stephenr | hua |
    And I click on the LogIn button
    Then There should be the message "Wrong username or password. Please try again."

  @TC2
  Scenario: TC2-User cannot login the Provisioning Portal website under a wrong username
    When I enter a wrong username or password to LogIn
      | hua | stephenrBTB2018 |
    And I click on the LogIn button
    Then There should be the message "Wrong username or password. Please try again."

  @TC3
  Scenario: TC3-User can login the Provisioning Portal website under a correct account
    When I access the Provisioning Portal website with user role "non-admin"
    Then Log in successfully and I can see the main screen