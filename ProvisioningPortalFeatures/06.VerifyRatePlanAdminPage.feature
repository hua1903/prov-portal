@RatePlanAd @all
Feature: Verify the Rate Plan Admin page of the Provisioning Portal website with Admin account

  @RatePlanAdTC0
  Scenario: RatePlanAdTC0-Login website and go to Rate Plan Admin
    Given I access the Provisioning Portal website with user role "prov-admin"
    When I am on the Home page
    And I click on the box "Rate Plan Admin"

  @RatePlanAdTC1 @FieldsAd
  Scenario: RatePlanAdTC1-Check labels in summary info
    Then I should see the label Rate Plan changes Pending
    And I should get rate plans under Rate Plan Admin page
    When I double click on row of first rate plan
    Then Under first rate plan, I should see the label "Rate Plan Id"
    And Under first rate plan, I should see the label "Description"
    And Under first rate plan, I should see the label "Rate Plan Type"
    And Under first rate plan, I should see the label "Comments"
    And Under first rate plan, I should see the label "Submitted By"
    And Under first rate plan, I should see the label "Submitted Date"

  @RatePlanAdTC2 @FieldsAd
  Scenario: RatePlanAdTC2-Check labels in call rate table
    And I double click on first rate plan without IDD
    Then I should see a table with detail info
    And Under table, I should see the "Call Rate Category" column
    And Under table, I should see the "Rate per Cost" column
    And Under table, I should see the "Rate Fixed" column
    And Under table, I should see the "Initial Duration (sec)" column
    And Under table, I should see the "Rate per KB" column
    And Under table, I should see the "Rate per Second" column
    And Under table, I should see the "Rate per 30 Sec" column
    And Under table, I should see the "Rate per 60 Sec" column
    And Under table, I should see the "Capped Fixed Duration" column
    And Under table, I should see the "Capped Max Rate For Fixed Duration" column
    And Under table, I should see the "Capped Rate (after Duration) per Second" column
    And Under table, I should see the "Floating Credit Included" column

  @RatePlanAdTC3 @FieldsAd
  Scenario: RatePlanAdTC3-Check buttons under Rate Plan Admin page
    Then I should see the filter textbox
    And I should see the Show History Records button
    And I should get rate plans under Rate Plan Admin page
    And I should see the Accept button for rate plans under Rate Plan Admin
    And I should see the Edit button for rate plans under Rate Plan Admin
    And I should see the Reject button for rate plans under Rate Plan Admin

  @RatePlanAdTC4 @FilterAd
  Scenario Outline: RatePlanAdTC4-Filter the valid Description, Comments, Rate Plan ID
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on Edit button for Rate Plan with description "<description>"
    And Under Summary fields, I enter the value "<Comments>" for the "Comments" of this new one
    And I click on the Save button of rate plan record
    And I go to Rate Plan Admin page
    And I click on Description, comment button of the filter group
    And I enter the text "<description>" in the filter textbox
    Then Under each rate plan, I should see the text "<description>" in Description or Comments
    And I click on Rate Plan ID button of the filter group
    And I enter the text "<RP Id>" in the filter textbox
    Then Under each rate plan, I should see the value "<RP Id>" in Rate Plan ID
    And I click on All button of the filter group
    And I enter the text "<description>" in the filter textbox
    Then Under each rate plan, I should see the value "<description>" in Description or Comments or Rate Plan ID

    Examples: 
      | RP Id | description   | Comments    |
      |   114 | Hua Auto Test | Test Filter |

  @RatePlanAdTC5 @FilterAd
  Scenario: RatePlanAdTC5-Filter the invalid Description
    Then I should get rate plans under Rate Plan Admin page
    When I enter the text "invalid" in the filter textbox
    Then There is no rate plan

  @RatePlanAdTC6 @CheckInfoAd
  Scenario Outline: RatePlanAdTC6-Precondition-Create rate plan for checking info in Admin page
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<Comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record

    Examples: 
      | description   | Comments     |
      | Test for edit | Test fot API |

  @RatePlanAdTC7 @CheckInfoAd
  Scenario: RatePlanAdTC7-Verify summary info of Rate Plans between website and API
    And I create the Json file for Rate Plan Admin List
    Then The "Rate Plan Id" of Rate Plans on website should be same as Json file of Admin page
    And The "Description" of Rate Plans on website should be same as Json file of Admin page
    And The "Rate Plan Type" of Rate Plans on website should be same as Json file of Admin page
    And The "Comments" of Rate Plans on website should be same as Json file of Admin page

  @RatePlanAdTC8 @CheckInfoAd
  Scenario Outline: RatePlanAdTC8-Verify call rate info of a specfic Rate Plans between website and API
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

  @RatePlanAdTC9 @RevertIconAd
  Scenario Outline: RatePlanAdTC9-Do not display Revert icon for new rate plans
    And I go to Rate Plan page
    And I create new rate plan with description "<description>" for bureau "CORTEL"
    Then The Rate Plan with Description "<description>" is saved successfully
    When I go to Rate Plan Admin page
    Then I should NOT see the revert icon for Rate Plan with Description "<description>"
    And I should see the New value in Rate Plan ID for Rate Plan with Description "<description>"

    Examples: 
      | description              |
      | DontSeeRevertIconOnAdmin |

  @RatePlanAdTC10 @RevertIconAd
  Scenario: RatePlanAdTC10-Do not display Revert icon for accepted or rejected rate plans under Show History view
    Then I should see the Show History Records button
    When I click on Show History Records button
    Then I should get rate plans under Rate Plan Admin page
    And Under show history view, I should NOT see the revert icon for Rate Plan without changes and lived in cindy

  @RatePlanAdTC11 @RevertIconAd
  Scenario Outline: RatePlanAdTC11-Display Revert icon for lived rate plans that had been modified
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

  #@RatePlanAdTC12 @Accept
  #Scenario Outline: RatePlanAdTC12-Precondition-Create rate plan for accepting new rate plan
  #And I go to Rate Plan page
  #And I create new rate plan with description "<description>" for bureau "CORTEL"
  #
  #Examples:
  #| description    |
  #| Hua Test Accept New Plan|
  #
  #@RatePlanAdTC13 @Accept
  #Scenario Outline: RatePlanAdTC13-Accept new Rate Plan and cancel this request
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
  #@RatePlanAdTC14 @Accept
  #Scenario Outline: RatePlanAdTC14-Accept new Rate Plan and confirm this request
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
  #@RatePlanAdTC15 @Accept
  #Scenario Outline: RatePlanAdTC15-Verify rate plan after accepting new rate plan
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
  @RatePlanAdTC16 @Accept
  Scenario Outline: RatePlanAdTC16-Accept the change of rate plan <description>
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I only click on the Save button
    And I should see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
    And I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
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
      | description                | comments                   |
      | Auto accepted for lived RP | Auto accepted for lived RP |
      | IDD NO 1                   | Accept IDD No1             |

  @RatePlanAdTC17 @EditAd
  Scenario Outline: RatePlanAdTC17-Precondition-Create rate plan for editing Rate Plan Admin page
    And I access the Provisioning Portal website with user role "non-admin"
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    And I access the Provisioning Portal website with user role "prov-admin"
    And I click on the box "Rate Plan Admin"

    Examples: 
      | description             | comments                                |
      | Auto Edited Under Admin | Precondition Of Auto Edited Under Admin |

  @RatePlanAdTC18 @EditAd
  Scenario Outline: RatePlanAdTC18-Edit a specific Rate Plan on Rate Plan Admin page
    Then I should get rate plans under Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "<RP Type>" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "<Comments>" for the "Comments" under edit state
    And Under Call Rate without IDD, I select the value "<Call Rate Cat>" for the "Call Rate Category" of first row
    And Under Call Rate without IDD, I enter the value "<Initial>" for the "Initial Duration (sec)" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Cost>" for the "Rate per Cost" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Fixed>" for the "Rate Fixed" of first row
    And Under Call Rate without IDD, I enter the value "<Rate KB>" for the "Rate per KB" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Second>" for the "Rate per Second" of first row
    And Under Call Rate without IDD, I enter the value "<Rate 30s>" for the "Rate per 30 Sec" of first row
    And Under Call Rate without IDD, I enter the value "<Rate 60s>" for the "Rate per 60 Sec" of first row
    And Under Call Rate without IDD, I enter the value "<Cap Fix>" for the "Capped Fixed Duration" of first row
    And Under Call Rate without IDD, I enter the value "<Cap Max>" for the "Capped Max Rate For Fixed Duration" of first row
    And Under Call Rate without IDD, I enter the value "<Cap Rate>" for the "Capped Rate (after Duration) per Second" of first row
    And Under Call Rate without IDD, I select the value "<Float Credit>" for the "Floating Credit Included" of first row
    Then I should see the Save button of rate plan record
    Then I should see the Cancel button of rate plan record
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | description             | RP Type                     | Comments                    | Call Rate Cat | Initial | Rate Cost | Rate Fixed | Rate KB | Rate Second | Rate 30s | Rate 60s | Cap Fix | Cap Max | Cap Rate | Float Credit |
      | Auto Edited Under Admin | Business Wireless Broadband | Test for editing admin page | Calls to 13   |    0.25 |       0.3 |        0.6 |    0.12 |        0.35 |     0.25 |     0.17 |    0.46 |    0.28 |     0.18 | Yes          |

  @RatePlanAdTC19 @EditAd
  Scenario Outline: RatePlanAdTC19-Calculator for Rate per Cost
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per Cost" textbox
    Then I should see the "Wholesale to Retail Calculator" dialog
    When I enter the value "<Wholesale Amount>" on textbox "Wholesale Amount (ex GST)"
    And I enter the value "<Retail Amount>" on textbox "Retail Amount (inc GST)"
    Then I should see the value "<Result>" for the Rate per Cost in calculation popup
    When I click on "Set" button of calculation popup
    Then The Wholesale to Retail Calculator dialog is closed
    And I should see the value "<Result>" for the Rate per Cost in call rate

    Examples: 
      | description             | Wholesale Amount | Retail Amount | Result |
      | Auto Edited Under Admin |               40 |            60 |    1.5 |

  @RatePlanAdTC20 @EditAd
  Scenario Outline: RatePlanAdTC20-Calculator for Rate per KB with Data Type MB
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per KB" textbox
    Then I should see the "Data Calculator to KB" dialog
    When I select the "<Type>" in Data Type dropdown
    And I enter the value "<Amount>" on textbox "Amount"
    Then I should see the value "<Result>" for the Result
    When I click on "Set" button of calculation popup
    Then The Data Calculator to KB dialog is closed
    And I should see the value "<Result>" for Rate per KB in call rate

    Examples: 
      | description             | Type | Amount | Result       |
      | Auto Edited Under Admin | MB   |      1 | 0.0009765625 |

  @RatePlanAdTC21 @Reject
  Scenario Outline: RatePlanAdTC21-Reject new Rate Plan and cancel it
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
      | description                     |
      | Auto Rejected New RP And Cancel |

  @RatePlanAdTC22 @Reject
  Scenario Outline: RatePlanAdTC22-Reject new Rate Plan and confirm
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
      | description                      |
      | Auto Rejected New RP And Confirm |

  @RatePlanAdTC23 @Reject
  Scenario Outline: RatePlanAdTC23-Reject the change of rate plan
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
    Then The value of "Comments" should NOT be "<New value>" for Rate Plan with Description "<description>"
    And The value of "Comments" should be old value for Rate Plan with Description "<description>"

    Examples: 
      | description                | New value                  |
      | Auto rejected for lived RP | Auto rejected for lived RP |

  @RatePlanAdTC24 @History
  Scenario: RatePlanAdTC24-Check fields on History dialog
    When I go to Rate Plan Admin page
    And I should see the Show History Records button
    And I click on Show History Records button
    Then I should see the Hide history records button
    And I should get rate plans under Rate Plan Admin page
    When I enter the text "ACCEPTED" in the filter textbox
    Then I should see some rate plans with "ACCEPTED" status
    And Under a "ACCEPTED" rate plan, action buttons are disable
    When I enter the text "REJECTED" in the filter textbox
    Then I should see some rate plans with "REJECTED" status
    And Under a "REJECTED" rate plan, action buttons are disable

  @RatePlanAdTC25 @History
  Scenario Outline: RatePlanAdTC25-Check the accepted request in History
    And I click on Show History Records button
    Then I should get rate plans under Rate Plan Admin page
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And I should see the ACCEPTED in Rate Plan ID for Rate Plan with description "<description>"

    Examples: 
      | description                |
      | Auto accepted for lived RP |

  @RatePlanAdTC26 @History
  Scenario Outline: RatePlanAdTC26-Check the rejected request in History
    And I click on Show History Records button
    Then I should get rate plans under Rate Plan Admin page
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And I should see the REJECTED in Rate Plan ID for Rate Plan with description "<description>"

    Examples: 
      | description                |
      | Auto rejected for lived RP |

  @RatePlanAdTC27 @History
  Scenario: RatePlanAdTC27-Check fields after hiding History
    And I click on Show History Records button
    And I click on Hide history records button
    Then I should see the Show History Records button
    And I should get rate plans under Rate Plan Admin page
    And I should NOT see any rate plan with "ACCEPTED" status
    And I should NOT see any rate plan with "REJECTED" status

  @RatePlanAdTC28 @CompareAd
  Scenario Outline: RatePlanAdTC28-Compare old and new version of lived rate plans after changing info
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then I got all of curent values for fields of Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "<RP Type>" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "<Comments>" for the "Comments" under edit state
    And Under Call Rate without IDD, I select the value "<Call Cat>" for the "Call Rate Category" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Cost>" for the "Rate per Cost" of first row
    And I click on the Save button of rate plan record
    When I go to Rate Plan Admin page
    When I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I got all of values after changing Rate Plan with Description "<description>"
    Then I should see the revert icon for Rate Plan with Description "<description>"
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are highlighted
    And The old values of Rate Plan with Description "<description>" should be displayed
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are NOT highlighted

    Examples: 
      | description | RP Type                     | Comments          | Call Cat | Rate Cost |
      | Hua not IDD | Business Wireless Broadband | Compare rate plan | Inbound  |     0.023 |

  @RatePlanAdTC29 @CompareAd
  Scenario Outline: RatePlanAdTC29-Comparing old and new version of lived rate plans after adding new call rate
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I got all of curent values for fields of Rate Plan with Description "<description>"
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "<RP Type>" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "<Comments>" for the "Comments" under edit state
    And Under Call Rate without IDD, I select the value "<Call Rate Cat1>" for the "Call Rate Category" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Cost>" for the "Rate per Cost" of first row
    And I click on Add row button
    And Under Call Rate without IDD, I select the value "<Call Rate Cat2>" for the "Call Rate Category" of new row
    And I click on the Save button of rate plan record
    And I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I got all of values after changing Rate Plan with Description "<description>"
    Then I should see the revert icon for Rate Plan with Description "<description>"
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are highlighted
    And The old values of Rate Plan with Description "<description>" should be displayed
    And The new call rates of Rate Plan with Description "<description>" are highlighted
    And The value of new call rates of Rate Plan with Description "<description>" should be "<New>"
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are NOT highlighted

    Examples: 
      | description  | RP Type                | Comments                   | Call Rate Cat1 | Rate Cost | Call Rate Cat2 |
      | Test Compare | Consumer Mobile - Nude | Compare with new call rate | Intrafleet     |    0.0069 | Summary        |

  @RatePlanAdTC30 @SerNumAd
  Scenario Outline: RatePlanAdTC30-Precondition-Change info of rate plan for verifying Service Number list
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

  @RatePlanAdTC31 @SerNumAd
  Scenario Outline: RatePlanAdTC31-Verify fields on the service number list
    When I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
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

  @RatePlanAdTC32 @SerNumAd
  Scenario Outline: RatePlanAdTC32-Verify values of service number list
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
    When I create Json file for Service Number of Rate Plan ID "63" under bureau "CORTEL"
    Then All values on web should be same as on Json file of Service Number under Rate Plan ID "63"

    Examples: 
      | description           | comments        |
      | 5GB ($66) MBB (4RP1A) | Test API Sernum |

  @RatePlanAdTC33 @SerNumAd
  Scenario Outline: RatePlanAdTC33-Verifying for downloading CSV of Service Number
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

  @RatePlanAdTC34 @SerNumAd
  Scenario Outline: RatePlanAdTC34-Open new windows when double-clicking on Service Number
    And I go to Rate Plan page
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<comments>" for the "Comments" under edit state
    And I click on the Save button of rate plan record
    And I go to Rate Plan Admin page
    And I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I got a list of Service Numbers
    When I double click on the row with CIDN "950001"
    Then The new window is open with the URL "<url>"

    Examples: 
      | description           | comments            | url                                                                                     |
      | 5GB ($66) MBB (4RP1A) | Double click SerNum | https://cindy.telecombilling.com.au/cortel-admin/client/showUpdateClient.do?cidn=950001 |

  @RatePlanTC35
  Scenario: RatePlanTC35-Removing some rate plans under local database that are not lived in cindy
    When Connect to Rate Plan table in Mongo database
    And Under Mongo Database, I delete Rate Plan with Description "5GB ($66) MBB (4RP1A)"