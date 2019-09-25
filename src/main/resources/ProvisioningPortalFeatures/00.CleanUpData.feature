@CleanUp
Feature: Cleaning up data for Provisioning Portal

Background: 
    Given I access the Provisioning Portal website with user role "non-admin"

  Scenario: Removing some rate plans under local database that are not lived in cindy
    And Under Mongo Database, I delete Rate Plan with Description "Edit and save plan"
    And Under Mongo Database, I delete Rate Plan with Description "It is new rate plan"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Pending Owner"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Pending Not Owner"
    And Under Mongo Database, I delete Rate Plan with Description "Test for creating new plan"
    And Under Mongo Database, I delete Rate Plan with Description "Dont Change Plan Of Hua"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Cancel"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Auto Test"
    And Under Mongo Database, I delete Rate Plan with Description "Test for edit"
    And Under Mongo Database, I delete Rate Plan with Description "Auto Test Case 666"
    And Under Mongo Database, I delete Rate Plan with Description "Copy of TC40"
    And Under Mongo Database, I delete Rate Plan with Description "Copy of TC41"
    And Under Mongo Database, I delete Rate Plan with Description "Auto TC71"
    And Under Mongo Database, I delete Rate Plan with Description "Auto TC72"
    And Under Mongo Database, I delete Rate Plan with Description "Auto TC76"
    And Under Mongo Database, I delete Rate Plan with Description "Auto TC77"
    And Under Mongo Database, I delete Rate Plan with Description "Auto TC78"
    And Under Mongo Database, I delete Rate Plan with Description "Auto TC83"
    And Under Mongo Database, I delete Rate Plan with Description "Auto TC84"
    And Under Mongo Database, I delete Rate Plan with Description "5GB ($66) MBB (4RP1A)"
    And Under Mongo Database, I delete Rate Plan with Description "DontSeeRevertIconOnAdmin"
    And Under Mongo Database, I delete Rate Plan with Description "Hua not IDD"
    And Under Mongo Database, I delete Rate Plan with Description "Test Compare"
    
    