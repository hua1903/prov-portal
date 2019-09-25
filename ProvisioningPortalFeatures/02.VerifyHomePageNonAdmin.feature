@HomePageNonAdmin @all
Feature: Verify the Home page of the Provisioning Portal website with Non-Admin account

  Background: 
    Given I access the Provisioning Portal website with user role "non-admin"
    When I am on the Home page

  @TC4
  Scenario Outline: TC4-Should see the box <box Name> on Home page
    Then I should see the box "<box Name>"

    Examples: 
      | box Name      |
      | Rate Plan     |
      | APT (Gladice) |

  @TC5
  Scenario Outline: TC5-Should not see the box <box Name> on Home page
    Then I should not see the box "<box Name>"

    Examples: 
      | box Name        |
      | Rate Plan Admin |
      | Number Groups   |

  @TC6
  Scenario: TC6-Open Rate Plan page from Home page
    And I click on the box "Rate Plan"
    Then I should see the "Rate Plan" page
    Then I should see the Home button
    Then I should see the Logout button
    And I go to Home page by clicking on Home button
    Then I should see the box "Rate Plan"

  @TC7
  Scenario: TC7-Go to Home page by clicking on BTB logo
    And I click on the box "Rate Plan"
    Then I should see the "Rate Plan" page
    Then I should see the BTB logo
    Then I should see the Logout button
    And I go to Home page by clicking on BTB logo
    Then I should see the box "Rate Plan"

  @TC8
  Scenario: TC8-Open APT page from Home page
