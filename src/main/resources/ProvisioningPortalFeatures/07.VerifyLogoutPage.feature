@Logout @all
Feature: Verify the logout page of Provisioning Portal

  Background: 
    Given I access the Provisioning Portal website with user role "non-admin"
    When I am on the Home page

  @TC90
  Scenario: TC90-Do not logout website when clicking Logout > Cancel button
    Then I should see the Logout button
    When I click on the Logout button
    Then Under Logout pop-up, the logout confirmation message should be displayed
    And Under Logout pop-up, the button "Confirm" should be displayed
    And Under Logout pop-up, the button "Cancel" should be displayed
    When Under Logout pop-up, I click on the button "Cancel"
    Then I should see the Logout button

  @TC91
  Scenario: TC91-Logout website when clicking Logout > Confirm button
    Then I should see the Logout button
    When I click on the Logout button
    Then Under Logout pop-up, the logout confirmation message should be displayed
    And Under Logout pop-up, the button "Confirm" should be displayed
    And Under Logout pop-up, the button "Cancel" should be displayed
    When Under Logout pop-up, I click on the button "Confirm"
    Then The website logout successfully and I can see the login page
