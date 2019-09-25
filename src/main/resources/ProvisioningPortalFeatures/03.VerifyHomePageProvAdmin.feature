@HomePageProvAdmin @all
Feature: Verify the Home page of the Provisioning Portal website with Prov Admin account

  Background: 
    Given I access the Provisioning Portal website with user role "prov-admin"
    When I am on the Home page

  @TC9
  Scenario Outline: TC9-Should see the box <box Name> on Home page
    Then I should see the box "<box Name>"

    Examples: 
      | box Name        |
      | Rate Plan       |
      | APT (Gladice)   |
      | Rate Plan Admin |

  @TC10
  Scenario: TC10-Should not see the box Number Groups on Home page
    Then I should not see the box "Number Groups"

  @TC11
  Scenario: TC11-open Rate Plan page from Home page
    And I click on the box "Rate Plan"
    Then I should see the "Rate Plan" page
    Then I should see the Home button
    Then I should see the Logout button
    And I go to Home page by clicking on Home button
    Then I should see the box "Rate Plan"

  @TC12
  Scenario: TC12-Go to Home page by clicking BTB logo
    And I click on the box "Rate Plan"
    Then I should see the "Rate Plan" page
    Then I should see the BTB logo
    Then I should see the Logout button
    And I go to Home page by clicking on BTB logo
    Then I should see the box "Rate Plan"

  @TC13
  Scenario: TC13-open Rate Plan Admin page from Home page
    And I click on the box "Rate Plan Admin"
    Then I should see the "Rate Plan" page
    Then I should see the Home button
    Then I should see the Logout button
    And I go to Home page by clicking on Home button
    Then I should see the box "Rate Plan Admin"

  @TC14
  Scenario: TC14-Open APT page from Home page
    #And I click on the box "APT (Gladice)"
    #Then I should see the APT home page on new windows tab
    #Then I should see the Logout button
    #And Under APT website, I click on the Logout button
    #Then The windows tab of APT is closed
    #And I should see the Provisiong Portal windows
    #Then I should see the Home button
    #Then I should see the Logout button
