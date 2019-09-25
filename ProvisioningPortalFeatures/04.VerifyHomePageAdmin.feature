@HomePageAdmin @all
Feature: Verify the Home page of the Provisioning Portal website with Admin account

  Background: 
    Given I access the Provisioning Portal website with user role "admin"
    When I am on the Home page

  @TC15
  Scenario Outline: TC15-Should see the box <box Name> on Home page
    Then I should see the box "<box Name>"

    Examples: 
      | box Name        |
      | Rate Plan       |
      | Rate Plan Admin |
      | APT (Gladice)   |
      | Number Groups   |

  @TC16
  Scenario: TC16-Open Rate Plan page from Home page
    And I click on the box "Rate Plan"
    Then I should see the "Rate Plan" page
    Then I should see the Home button
    Then I should see the Logout button
    And I go to Home page by clicking on Home button
    Then I should see the box "Rate Plan"

  @TC17
  Scenario: TC17-Go to Home page by clicking on BTB logo
    And I click on the box "Rate Plan"
    Then I should see the "Rate Plan" page
    Then I should see the BTB logo
    Then I should see the Logout button
    And I go to Home page by clicking on BTB logo
    Then I should see the box "Rate Plan"

  @TC18
  Scenario: TC18-Open Rate Plan Admin page from Home page
    And I click on the box "Rate Plan Admin"
    Then I should see the "Rate Plan Admin" page
    Then I should see the Home button
    Then I should see the Logout button
    And I go to Home page by clicking on Home button
    Then I should see the box "Rate Plan Admin"

  @TC19
  Scenario: TC19-Open APT page from Home page

  #And I click on the box "APT (Gladice)"
  #Then I should see the APT home page on new windows tab
  #Then I should see the Logout button
  #And Under APT website, I click on the Logout button
  #Then The windows tab of APT is closed
  #And I should see the Provisiong Portal windows
  #Then I should see the Home button
  #Then I should see the Logout button
  @TC20
  Scenario: TC20-Open Number Group from Home page
    #And I click on the box "Number Groups"
    #Then I should see the NumberGroup home page on new windows tab
    #Then I should see the Logout button
    #And I click on the Logout button
    #Then The windows tab of NumberGroup is closed
    #And I should see the Provisiong Portal windows
    #Then I should see the Home button
    #Then I should see the Logout button
