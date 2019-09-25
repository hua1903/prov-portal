@RatePlanAd @all
Feature: Verify the Rate Plan Admin page of the Provisioning Portal website with Admin account

  Background:
    Given I access the Provisioning Portal website with user role "admin"
    When I am on the Home page
    And I click on the box "Rate Plan Admin"

  @TC56 @FieldsAd
  Scenario: TC56-Check labels in summary info
    Then I should see the label Rate Plan changes Pending
    And I should get rate plans under Rate Plan Admin page
    When Under each rate plan, I double click on row
    Then Under each rate plan, I should see the label "Rate Plan Id"
    And Under each rate plan, I should see the label "Description"
    And Under each rate plan, I should see the label "Rate Plan Type"
    And Under each rate plan, I should see the label "Comments"
    And Under each rate plan, I should see the label "Submitted By"
    And Under each rate plan, I should see the label "Submitted Date"

  @TC57 @FieldsAd
  Scenario: TC57-Check labels in call rate table
    And Under each rate plan, I double click on row
    Then Under each rate plan, I should see the table with detail info
    And Under each table, I should see the "Call Rate Category" column
    And Under each table, I should see the "Rate per Cost" column
    And Under each table, I should see the "Rate Fixed" column
    And Under each table, I should see the "Initial Duration (sec)" column
    And Under each table, I should see the "Rate per KB" column
    And Under each table, I should see the "Rate per Second" column
    And Under each table, I should see the "Rate per 30 Sec" column
    And Under each table, I should see the "Rate per 60 Sec" column
    And Under each table, I should see the "Capped Fixed Duration" column
    And Under each table, I should see the "Capped Max Rate For Fixed Duration" column
    And Under each table, I should see the "Capped Rate (after Duration) per Second" column
    And Under each table, I should see the "Floating Credit Included" column

  @TC58 @FieldsAd
  Scenario: TC58-Check buttons under Rate Plan Admin page
    Then I should see the filter textbox
    And I should see the Show History Records button
    And I should get rate plans under Rate Plan Admin page
    And I should see the Accept button for rate plans under Rate Plan Admin
    And I should see the Edit button for rate plans under Rate Plan Admin
    And I should see the Reject button for rate plans under Rate Plan Admin

  @TC59 @FilterAd
  Scenario Outline: TC59-Filter the valid Description
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on Edit button for Rate Plan with description "<description>"
    And Under Summary fields, I enter the value "Test Filter" for the "Comments" of this new one
    And I click on the Save button of rate plan record
    And I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    Then Under each rate plan, I should see the text "<description>" in Description or Comment

    Examples: 
      | description   |
      | Hua Auto Test |

  @TC60 @FilterAd
  Scenario: TC60-Filter the invalid Description
    Then I should get rate plans under Rate Plan Admin page
    When I enter the text "invalid" in the filter textbox
    Then There is no rate plan

  @TC61 @TC62 @TC63 @CheckInfoAd
  Scenario Outline: TC61-Precondition-Create rate plan for checking info in Admin page
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "Test fot API" for the "Comments" under edit state
    And I click on the Save button of rate plan record

    Examples: 
      | description   |
      | Test for edit |

  @TC62 @CheckInfoAd
  Scenario: TC62-Verify summary info of Rate Plans between website and API
    And I create the Json file for Rate Plan Admin List
    Then The "Rate Plan Id" of Rate Plans on website should be same as Json file of Admin page
    And The "Description" of Rate Plans on website should be same as Json file of Admin page
    And The "Rate Plan Type" of Rate Plans on website should be same as Json file of Admin page
    And The "Comments" of Rate Plans on website should be same as Json file of Admin page

  @TC63 @CheckInfoAd
  Scenario Outline: TC63-Verify call rate info of a specfic Rate Plans between website and API
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then The value of call rate "Call Rate Category" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Initial Duration (sec)" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Rate per Cost" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Rate Fixed" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Rate per KB" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Rate per Second" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Rate per 30 Sec" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Rate per 60 Sec" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Capped Fixed Duration" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Capped Max Rate For Fixed Duration" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Capped Rate (after Duration) per Second" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page
    And The value of call rate "Floating Credit Included" of Rate Plan with Description "<description>" on website should be same as Json file of Admin page

    Examples: 
      | description   |
      | Test for edit |

  @TC64 @RevertIconAd
  Scenario Outline: TC64-Do not display Revert icon for new rate plans
    And I go to Rate Plan page
    And I create new rate plan with description "<description>" for bureau "CORTEL"
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    Then I should NOT see the revert icon for Rate Plan with Description "<description>"
    And I should see the New value in Rate Plan ID for Rate Plan with Description "<description>"

    Examples: 
      | description              |
      | DontSeeRevertIconOnAdmin |

  @TC65 @RevertIconAd
  Scenario: TC65-Do not display Revert icon for accepted or rejected rate plans under Show History view
    Then I should see the Show History Records button
    When I click on Show History Records button
    Then I should get rate plans under Rate Plan Admin page
    And Under show history view, I should NOT see the revert icon for Rate Plan without changes and lived in cindy

  @TC66 @RevertIconAd
  Scenario Outline: TC66-Display Revert icon for lived rate plans that had been modified
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    Then I should see the revert icon for Rate Plan with Description "<description>"
    When I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Delete" button for Rate Plan with Description "<description>"
    And I click on the Confirm button

    Examples: 
      | description   | comments                                |
      | Hua Auto Test | SeeRevertIcon AfterChaningLivedRatePlan |

  #@TC67 @TC68 @TC69 @TC70 @Accept
  #Scenario Outline: TC67-Precondition-Create rate plan for accepting new rate plan
  #And I go to Rate Plan page
  #And I create new rate plan with description "<description>" for bureau "CORTEL"
  #
  #Examples:
  #| description    |
  #| Hua Test Accept New Plan|
  #
  #@TC68 @Accept
  #Scenario Outline: TC68-Accept new Rate Plan and cancel this request
  #And I enter the text "<description>" in the filter textbox
  #And I click on Accept button for Rate Plan with description "<description>"
  #And I should see the Accept Confirmation popup
  #And I click on the Cancel button
  #Then I should see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
  #
  #Examples:
  #| description    |
  #| Hua Test Accept New Plan|
  #
  #@TC69 @Accept
  #Scenario Outline: TC69-Accept new Rate Plan and confirm this request
  #And I enter the text "<description>" in the filter textbox
  #And I click on Accept button for Rate Plan with description "<description>"
  #And I should see the Accept Confirmation popup
  #And I click on the Confirm button
  #Then I should see the ACCEPTED in Rate Plan ID for Rate Plan with description "<description>"
  #And I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
  #And I should see the number in Rate Plan ID for Rate Plan with Description "<description>"
  #And I should NOT see the revert icon for Rate Plan with Description "<description>"
  #
  #Examples:
  #| description    |
  #| Hua Test Accept New Plan|
  #
  #@TC70 @Accept
  #Scenario Outline: TC70-Verify rate plan after accepting new rate plan
  #And I go to Rate Plan page
  #And I select the bureau "CORTEL" in Select Bureau dropdown
  #And I enter the text "<description>" in the filter textbox
  #Then I should see Rate Plan with Description "<description>"
  #And I should see the number in Rate Plan ID for Rate Plan with Description "<description>"
  #And I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
  #And I should NOT see the revert icon for Rate Plan with Description "<description>"
  #
  #Examples:
  #| description    |
  #| Hua Test Accept New Plan|
  @TC71 @TC80 @Accept
  Scenario Outline: TC71-Accept the change of rate plan
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    And I should see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
    And I go to Rate Plan Admin page
    And I click on Accept button for Rate Plan with description "<description>"
    And I should see the Accept Confirmation popup
    And I click on the Confirm button
    Then I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
    And I should see the ACCEPTED in Rate Plan ID for Rate Plan with description "<description>"
    When I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then The value of "Comments" should be "<comments>" for Rate Plan with Description "<description>"

    Examples: 
      | description | comments         |
      | Auto TC71   | Accept Auto TC71 |

  @TC72 @TC73 @TC74 @TC75 @EditAd
  Scenario Outline: TC72-Precondition-Create rate plan for editing Rate Plan Admin page
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record

    Examples: 
      | description | comments                  |
      | Auto TC72   | Precondition Of Auto TC72 |

  @TC73 @EditAd
  Scenario Outline: TC73-Edit a specific Rate Plan on Rate Plan Admin page
    Then I should get rate plans under Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "<CSV Summary Info>" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "Test for editing admin page" for the "Comments" under edit state
    And Under Call Rate, I select the value "<CSV Call Rate>" for the "Call Rate Category" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per Cost" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate Fixed" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Initial Duration (sec)" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per KB" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per Second" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per 30 Sec" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per 60 Sec" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Fixed Duration" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Max Rate For Fixed Duration" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Rate (after Duration) per Second" under edit state
    And Under Call Rate, I select the value "<CSV Call Rate>" for the "Floating Credit Included" under edit state
    Then I should see the Save button of rate plan record
    Then I should see the Cancel button of rate plan record
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | description |
      | Auto TC72   |

  @TC74 @EditAd
  Scenario Outline: TC74-Calculator for Rate per Cost
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per Cost" textbox
    Then I should see the "Wholesale to Retail Calculator" dialog
    When I enter the value "40" on textbox "Wholesale Amount (ex GST)"
    And I enter the value "60" on textbox "Retail Amount (inc GST)"
    Then I should see the value "1.5" for the Rate per Cost in calculation popup
    When I click on "Set" button of calculation popup
    Then The "Wholesale to Retail Calculator" dialog is closed
    And I should see the value "1.5" for the Rate per Cost in call rate

    Examples: 
      | description |
      | Auto TC72   |

  @TC75 @EditAd
  Scenario Outline: TC75-Calculator for Rate per KB with Data Type MB
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per KB" textbox
    Then I should see the "Data Calculator to KB" dialog
    When I select the "MB" in Data Type dropdown
    And I enter the value "1" on textbox "Amount"
    Then I should see the value "0.0009765625" for the Result
    When I click on "Set" button of calculation popup
    Then The "Data Calculator to KB" dialog is closed
    And I should see the value "0.0009765625" for Rate per KB in call rate

    Examples: 
      | description |
      | Auto TC72   |

  @TC76 @Reject
  Scenario Outline: TC76-Reject new Rate Plan and cancel it
    And I go to Rate Plan page
    And I create new rate plan with description "<description>" for bureau "CORTEL"
    When I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on Reject button for Rate Plan with description "<description>"
    Then I should see the Reject Confirmation popup
    When I click on the Cancel button
    Then I should see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"

    Examples: 
      | description |
      | Auto TC76   |

  @TC77 @Reject
  Scenario Outline: TC77-Reject new Rate Plan and confirm
    And I go to Rate Plan page
    And I create new rate plan with description "<description>" for bureau "CORTEL"
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on Reject button for Rate Plan with description "<description>"
    Then I should see the Reject Confirmation popup
    When I click on the Confirm button
    Then I should see the REJECTED in Rate Plan ID for Rate Plan with description "<description>"
    When I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then There is no rate plan

    Examples: 
      | description |
      | Auto TC77   |

  @TC78 @TC81 @Reject
  Scenario Outline: TC78-Reject the change of rate plan
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I got all of curent values for fields of Rate Plan with Description "<description>"
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<New value>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    And I should see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
    And I go to Rate Plan Admin page
    And I click on Reject button for Rate Plan with description "<description>"
    And I should see the Reject Confirmation popup
    And I click on the Confirm button
    Then I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
    And I should see the REJECTED in Rate Plan ID for Rate Plan with description "<description>"
    When I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then The value of "Comments" should NOT be "<New value>" for Rate Plan with Description "<description>"
    And The value of "Comments" should be old value for Rate Plan with Description "<description>"

    Examples: 
      | description | New value               |
      | Auto TC78   | Test for rejecting plan |

  @TC79 @History
  Scenario: TC79-Check fields on History dialog
    And I should see the Show History Records button
    And I click on Show History Records button
    Then I should see the Hide history records button
    And I should get rate plans under Rate Plan Admin page
    When I enter the text "ACCEPTED" in the filter textbox
    Then Under each rate plan, I should see ACCEPTED status
    And Under each ACCEPTED rate plan, the buttons are disable
    When I enter the text "REJECTED" in the filter textbox
    Then Under each rate plan, I should see REJECTED status
    And Under each REJECTED rate plan, the buttons are disable
    When I enter the text "PENDING" in the filter textbox
    Then Under each rate plan, I should see PENDING status
    And Under each PENDING rate plan, the buttons are enable

  @TC80 @History
  Scenario Outline: TC80-Check the accepted request in History
    And I click on Show History Records button
    Then I should get rate plans under Rate Plan Admin page
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And I should see the ACCEPTED in Rate Plan ID for Rate Plan with description "<description>"

    Examples: 
      | description |
      | Auto TC71   |

  @TC81 @History
  Scenario Outline: TC81-Check the rejected request in History
    And I click on Show History Records button
    Then I should get rate plans under Rate Plan Admin page
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And I should see the REJECTED in Rate Plan ID for Rate Plan with description "<description>"

    Examples: 
      | description |
      | Auto TC78   |

  @TC82 @History
  Scenario: TC82-Check fields after hiding History
    And I click on Show History Records button
    And I click on Hide history records button
    Then I should see the Show History Records button
    And I should get rate plans under Rate Plan Admin page
    And Under each rate plan, I do not see ACCEPTED status
    And Under each rate plan, I do not see REJECTED status
    And Under each rate plan, I should see PENDING status

  @TC83 @CompareAd
  Scenario Outline: TC83-Compare old and new version of lived rate plans after changing info
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then I got all of curent values for fields of Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "<CSV Summary Info>" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "Compare rate plan" for the "Comments" under edit state
    And Under Call Rate, I select the value "Inbound" for the "Call Rate Category" under edit state
    And Under Call Rate, I enter the value "0.023" for the "Rate per Cost" under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I double click on Rate Plan with Description "<description>"
    Then I got all of values after changing Rate Plan with Description "<description>"
    And I should see the revert icon for Rate Plan with Description "<description>"
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are highlighted
    And The old values of Rate Plan with Description "<description>" should be displayed
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are NOT highlighted

    Examples: 
      | description |
      | Hua not IDD |

  @TC84 @CompareAd
  Scenario Outline: TC84-Comparing old and new version of lived rate plans after adding new call rate
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    Then I should see the filter textbox
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then I got all of curent values for fields of Rate Plan with Description "<description>"
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "Consumer Mobile - Nude" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "Compare with new call rate" for the "Comments" under edit state
    And Under Call Rate, I select the value "Intrafleet" for the "Call Rate Category" under edit state
    And Under Call Rate, I enter the value "0.0069" for the "Rate per Cost" under edit state
    And I click on Add row button
    And I select the value "Summary" for the "Call Rate Category" for new row under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    And I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And I double click on Rate Plan with Description "<description>"
    Then I got all of values after changing Rate Plan with Description "<description>"
    Then I should see the revert icon for Rate Plan with Description "<description>"
    And I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are highlighted
    Then The old values of Rate Plan with Description "<description>" should be displayed
    Then The new call rates of Rate Plan with Description "<description>" are highlighted
    Then The value of new call rates of Rate Plan with Description "<description>" should be "<New>"
    And I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are NOT highlighted

    Examples: 
      | description |
      | Test Compare|

  @TC85 @TC86 @SerNumAd
  Scenario Outline: TC85-Precondition-Change info of rate plan for verifying Service Number list
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    When I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | description           | comments            |
      | 5GB ($66) MBB (4RP1A) | Precondition SerNum |

  @TC86 @SerNumAd
  Scenario Outline: TC86-Verify fields on the service number list
    Then I should see the filter textbox
    When I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I should see the Download as CSV button
    And I should see the header "CIDN"
    And I should see the header "Salutation"
    And I should see the header "First Name"
    And I should see the header "Last Name"
    And I should see the header "Address"
    And I should see the header "Contact Number"
    And I should see the header "Email"
    And I should see the header "Service Number"

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @TC87 @SerNumAd
  Scenario Outline: TC87-Verify values of service number list
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I got a list of Service Numbers
    When Under Rate Plan Admin page, I create the Json file for Service Number of Rate Plan with Description "<description>"
    Then The value of header "CIDN" on web should be same as on Json file of Service Number
    And The value of header "Salutation" on web should be same as on Json file of Service Number
    And The value of header "First Name" on web should be same as on Json file of Service Number
    And The value of header "Last Name" on web should be same as on Json file of Service Number
    And The value of header "Address" on web should be same as on Json file of Service Number
    And The value of header "Contact Number" on web should be same as on Json file of Service Number
    And The value of header "Email" on web should be same as on Json file of Service Number
    And The value of header "Service Number" on web should be same as on Json file of Service Number

    Examples: 
      | description           | comments        |
      | 5GB ($66) MBB (4RP1A) | Test API Sernum |

  @TC88 @SerNumAd
  Scenario Outline: TC88-Verifying for downloading CSV of Service Number
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I got a list of Service Numbers
    And I should see the Download as CSV button
    And I can download file by clicking on the Download as CSV button

    Examples: 
      | description           | comments        |
      | 5GB ($66) MBB (4RP1A) | Download SerNum |

  @TC89 @SerNumAd
  Scenario Outline: TC89-Open new windows when double-clicking on Service Number
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I got a list of Service Numbers
    When I double click on the row with CIDN "950001"
    Then The new window is open with the URL "<url>"
    When I close the popup

    Examples: 
      | description           | comments            | url                                                                                     |
      | 5GB ($66) MBB (4RP1A) | Double click SerNum | https://cindy.telecombilling.com.au/cortel-admin/client/showUpdateClient.do?cidn=950001 |
