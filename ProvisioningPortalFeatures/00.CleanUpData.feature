@CleanUp @all
Feature: Cleaning up data for Provisioning Portal

Background: 
    Given I am on the Provisioning Portal Website

  Scenario: Removing some rate plans under local database that are not lived in cindy
    When Connect to Rate Plan table in Mongo database
    And Under Mongo Database, I delete Rate Plan with Description "Edit and save plan"
    And Under Mongo Database, I delete Rate Plan with Description "It is new rate plan"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Pending Owner"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Pending Not Owner"
    And Under Mongo Database, I delete Rate Plan with Description "Rate Plan No IDD"
    And Under Mongo Database, I delete Rate Plan with Description "Rate Plan IDD"
    And Under Mongo Database, I delete Rate Plan with Description "Dont Change Plan Of Hua"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Cancel"
    And Under Mongo Database, I delete Rate Plan with Description "Hua New"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Auto Test"
    And Under Mongo Database, I delete Rate Plan with Description "Test for edit"
    And Under Mongo Database, I delete Rate Plan with Description "Copy RP with same Bureau"
    And Under Mongo Database, I delete Rate Plan with Description "Copy RP with different Bureau"
    And Under Mongo Database, I delete Rate Plan with Description "Auto Rate Plan of ID 131"
    And Under Mongo Database, I delete Rate Plan with Description "Auto accepted for lived RP"
    And Under Mongo Database, I delete Rate Plan with Description "Auto Edited Under Admin"
    And Under Mongo Database, I delete Rate Plan with Description "Auto Rejected New RP And Cancel"
    And Under Mongo Database, I delete Rate Plan with Description "Auto Rejected New RP And Confirm"
    And Under Mongo Database, I delete Rate Plan with Description "Auto rejected for lived RP"
    And Under Mongo Database, I delete Rate Plan with Description "5GB ($66) MBB (4RP1A)"
    And Under Mongo Database, I delete Rate Plan with Description "DontSeeRevertIconOnAdmin"
    And Under Mongo Database, I delete Rate Plan with Description "Hua not IDD"
    And Under Mongo Database, I delete Rate Plan with Description "Test Compare"
    And Under Mongo Database, I delete Rate Plan with Description "Hua Not Owner"
    And Under Mongo Database, I delete Rate Plan with Description "New Hua Not Owner"
    And Under Mongo Database, I delete Rate Plan with Description "Lived Hua Not Owner" 