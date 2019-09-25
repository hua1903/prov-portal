@RatePlan @all
Feature: Verify the Rate Plan page of the Provisioning Portal website with Non-Admin account

  @RatePlanTC0
  Scenario: RatePlanTC0-Login website and go to Rate Plan page under Cortel bureau
    Given I access the Provisioning Portal website with user role "non-admin"
    When I am on the Home page
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown

  @RatePlanTC1
  Scenario: RatePlanTC1-Check labels in summary info under collapse view
    Then I should see the title Rate Plans for the bureau "CORTEL"
    And I should get rate plans under Rate Plan page
    And Under first rate plan, I should see the label "Rate Plan Id"
    And Under first rate plan, I should see the label "Description"
    And Under first rate plan, I should see the label "Rate Plan Type"
    And Under first rate plan, I should see the label "Comments"

  @RatePlanTC2
  Scenario: RatePlanTC2-Check labels in call rate table of rate plan without IDD under expand view
    And I double click on first rate plan without IDD
    Then I should see a table with detail info
    And Under table, I should see the "Call Rate Category" column
    And Under table, I should see the "Initial Duration (sec)" column
    And Under table, I should see the "Rate per Cost" column
    And Under table, I should see the "Rate Fixed" column
    And Under table, I should see the "Rate per KB" column
    And Under table, I should see the "Rate per Second" column
    And Under table, I should see the "Rate per 30 Sec" column
    And Under table, I should see the "Rate per 60 Sec" column
    And Under table, I should see the "Capped Fixed Duration" column
    And Under table, I should see the "Capped Max Rate For Fixed Duration" column
    And Under table, I should see the "Capped Rate (after Duration) per Second" column
    And Under table, I should see the "Floating Credit Included" column

  @RatePlanTC3
  Scenario: RatePlanTC3-Check labels in call rate table of rate plan IDD under expand view
    And I double click on first rate plan IDD
    Then I should see a table with detail info
    And Under table, I should see the "Country" column
    And Under table, I should see the "Call Prefix" column
    And Under table, I should see the "Initial Duration (sec)" column
    And Under table, I should see the "Rate per Cost" column
    And Under table, I should see the "Rate Fixed" column
    And Under table, I should see the "Rate per KB" column
    And Under table, I should see the "Rate per Second" column
    And Under table, I should see the "Rate per 30 Sec" column
    And Under table, I should see the "Rate per 60 Sec" column
    And Under table, I should see the "Capped Fixed Duration" column
    And Under table, I should see the "Capped Max Rate For Fixed Duration" column
    And Under table, I should see the "Capped Rate (after Duration) per Second" column
    And Under table, I should see the "Floating Credit Included" column

  @RatePlanTC4
  Scenario Outline: RatePlanTC4-Check buttons of Rate plan with pending and owner
    And I create new rate plan with description "<description>"
    And I double click on Rate Plan with Description "<description>"
    Then Under pending Rate Plan and owner "<description>", "Edit" button is active
    And Under pending Rate Plan and owner "<description>", "Copy" button is active
    And Under pending Rate Plan and owner "<description>", "Delete" button is active

    Examples: 
      | description       |
      | Hua Pending Owner |

  @RatePlanTC5
  Scenario: RatePlanTC5-Check buttons of Rate plan with not-pending and owner
    Then I should see the Create New Rate Plan button
    And Under not pending Rate Plan, "Edit" button is active
    And Under not pending Rate Plan, "Copy" button is active
    And Under not pending Rate Plan, Delete button is inactive

  @RatePlanTC6
  Scenario Outline: RatePlanTC6-Check buttons of Rate plan with pending and not owner
    And I create new rate plan with description "<description>"
    And I access the Provisioning Portal website with user role "prov-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then Under pending Rate Plan and not owner "<description>", "Edit" button is inactive
    And Under pending Rate Plan and not owner "<description>", Copy button is active
    And Under pending Rate Plan and not owner "<description>", "Delete" button is inactive   

    Examples: 
      | description           |
      | Hua Pending Not Owner |

  @RatePlanTC7
  Scenario: RatePlanTC7-Verify the list in Select Bureau dropdown
    When I access the Provisioning Portal website with user role "non-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I click on the Select Bureau dropdown
    And I create the Json file for Bureau List
    Then The list of bureaus on website should be same as Json file

  @RatePlanTC8
  Scenario: RatePlanTC8-Verify Summary Info of all rate plans
    And I create the Json file for Rate Plan List under bureau "CORTEL"
    Then The "Rate Plan Id" of Rate Plans on website should be same as Json file of Rate Plan page
    And The "Description" of Rate Plans on website should be same as Json file of Rate Plan page
    And The "Rate Plan Type" of Rate Plans on website should be same as Json file of Rate Plan page
    And The "Comments" of Rate Plans on website should be same as Json file of Rate Plan page

  @RatePlanTC9
  Scenario Outline: RatePlanTC9-Verify the Call Rate Info of a specific rate plan
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I create the Json file for Call Rate of Rate Plan with Description "<description>"
    Then All call rate info of Rate Plan with Description "<description>" on website should be same as Json file

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @RatePlanTC10 @RPFilter
  Scenario: RatePlanTC10-Filter the valid Description or Comments
    Then I should get rate plans under Rate Plan page
    When I click on Description, comment button of the filter group
    And I enter the text "50Gb MBB (4R6PA)" in the filter textbox
    Then Under each rate plan, I should see the text "50Gb MBB (4R6PA)" in Description or Comments

  @RatePlanTC11 @RPFilter
  Scenario: RatePlanTC11-Filter the valid Rate Plan ID
    Then I should get rate plans under Rate Plan page
    When I click on Rate Plan ID button of the filter group
    And I enter the text "105" in the filter textbox
    Then Under each rate plan, I should see the value "105" in Rate Plan ID

  @RatePlanTC12 @RPFilter
  Scenario: RatePlanTC12-Filter the valid Description or Commetns or Rate Plan ID
    Then I should get rate plans under Rate Plan page
    When I click on All button of the filter group
    And I enter the text "66" in the filter textbox
    Then Under each rate plan, I should see the value "66" in Description or Comments or Rate Plan ID

  @RatePlanTC13 @RPFilter
  Scenario: RatePlanTC13-Filter the invalid Description
    Then I should get rate plans under Rate Plan page
    When I enter the text "invalid" in the filter textbox
    Then There is no rate plan

  @RatePlanTC14
  Scenario: RatePlanTC14-Check fields on Create New Rate Plan dialog
    And I click on the Create New Rate Plan
    Then The New Rate Plan Dialog is displayed
    And I should see the dropdown Bureau for target Rate Plan in dialog
    And I should see the textbox Description for the Rate Plan in dialog
    And I should see the International Direct Dial rate plan in dialog
    And I should see the Create button in dialog
    And I should see the Copy button in dialog
    And I should see the Cancel button in dialog
    When I click on the Cancel button under Create dialog

  @RatePlanTC15
  Scenario: RatePlanTC15-Check the list in Bureau dropdown on Create New Plan Dialog
    And I click on the Create New Rate Plan
    And I click on the dropdown Please select a bureau for target rate plan
    And I create the Json file for Bureau List
    Then I should see the list of Bureau for Target Rate Plan should be same as Json file
    When I click on the Cancel button under Create dialog

  @RatePlanTC16
  Scenario Outline: RatePlanTC16-Only Create Rate Plan without IDD, but dont save
    And I click on the Create New Rate Plan
    And I enter "<description>" in Description for the Rate Plan textbox
    And I click on the Create button under Create dialog
    Then New rate plan with description "<description>" is on top of Rate Plan list for bureau "CORTEL"
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" of this new one
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" of this new one
    And Under Call Rate without IDD, I select the value "<Call Rate Cat>" for the "Call Rate Category" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Cost>" for the "Rate per Cost" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Fixed>" for the "Rate Fixed" of first row
    And Under Call Rate without IDD, I enter the value "<Initial>" for the "Initial Duration (sec)" of first row
    And Under Call Rate without IDD, I enter the value "<Rate KB>" for the "Rate per KB" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Second>" for the "Rate per Second" of first row
    And Under Call Rate without IDD, I enter the value "<Rate 30s>" for the "Rate per 30 Sec" of first row
    And Under Call Rate without IDD, I enter the value "<Rate 60s>" for the "Rate per 60 Sec" of first row
    And Under Call Rate without IDD, I enter the value "<Cap Fix>" for the "Capped Fixed Duration" of first row
    And Under Call Rate without IDD, I enter the value "<Cap Max>" for the "Capped Max Rate For Fixed Duration" of first row
    And Under Call Rate without IDD, I enter the value "<Cap Rate>" for the "Capped Rate (after Duration) per Second" of first row
    And Under Call Rate without IDD, I select the value "<Float Credit>" for the "Floating Credit Included" of first row
    And I click on the Cancel button of rate plan record
    Then The Rate Plan with Description "<description>" is NOT saved successfully

    Examples: 
      | description      | Call Rate Cat | Rate Cost | Rate Fixed | Initial | Rate KB | Rate Second | Rate 30s | Rate 60s | Cap Fix | Cap Max | Cap Rate | Float Credit |
      | Rate Plan No IDD | Calls to 13   |      0.25 |        0.3 |     0.6 |    0.12 |        0.35 |     0.25 |     0.17 |    0.46 |    0.28 |     0.18 | Yes          |

  @RatePlanTC17
  Scenario Outline: RatePlanTC17-Only Create Rate Plan with IDD, but dont save
    And I click on the Create New Rate Plan
    And I enter "<description>" in Description for the Rate Plan textbox
    And I click on the checkbox IDD
    And I click on the Create button under Create dialog
    Then New rate plan with description "<description>" is on top of Rate Plan list for bureau "CORTEL"
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" of this new one
    And Under Call Rate with IDD, I enter the value "<Country>" for the "Country" of first row
    And Under Call Rate with IDD, I enter the value "<Call Prefix>" for the "Call Prefix" of first row
    And Under Call Rate with IDD, I enter the value "<Rate Cost>" for the "Rate per Cost" of first row
    And Under Call Rate with IDD, I enter the value "<Rate Fixed>" for the "Rate Fixed" of first row
    And Under Call Rate with IDD, I enter the value "<Initial>" for the "Initial Duration (sec)" of first row
    And Under Call Rate with IDD, I enter the value "<Rate KB>" for the "Rate per KB" of first row
    And Under Call Rate with IDD, I enter the value "<Rate Second>" for the "Rate per Second" of first row
    And Under Call Rate with IDD, I enter the value "<Rate 30s>" for the "Rate per 30 Sec" of first row
    And Under Call Rate with IDD, I enter the value "<Rate 60s>" for the "Rate per 60 Sec" of first row
    And Under Call Rate with IDD, I enter the value "<Cap Fix>" for the "Capped Fixed Duration" of first row
    And Under Call Rate with IDD, I enter the value "<Cap Max>" for the "Capped Max Rate For Fixed Duration" of first row
    And Under Call Rate with IDD, I enter the value "<Cap Rate>" for the "Capped Rate (after Duration) per Second" of first row
    And Under Call Rate with IDD, I select the value "<Float Credit>" for Floating Credit Included of first row
    And I click on the Cancel button of rate plan record
    Then The Rate Plan with Description "<description>" is NOT saved successfully

    Examples: 
      | description   | Country | Call Prefix | Rate Cost | Rate Fixed | Initial | Rate KB | Rate Second | Rate 30s | Rate 60s | Cap Fix | Cap Max | Cap Rate | Float Credit |
      | Rate Plan IDD | Aus     | +61         |      0.25 |        0.3 |     0.6 |    0.12 |        0.35 |     0.25 |     0.17 |    0.46 |    0.28 |     0.18 | Yes          |

  @RatePlanTC18
  Scenario Outline: RatePlanTC18-Create new rate plan with NO IDD and save it
    And I click on the Create New Rate Plan
    And I enter "<description>" in Description for the Rate Plan textbox
    And I click on the Create button under Create dialog
    Then New rate plan with description "<description>" is on top of Rate Plan list for bureau "CORTEL"
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" of this new one
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" of this new one
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
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | description      | RP Type               | IDD | Comments          | Call Rate Cat        | Initial | Rate Cost | Rate Fixed | Rate KB | Rate Second | Rate 30s | Rate 60s | Cap Fix | Cap Max | Cap Rate | Float Credit |
      | Rate Plan No IDD | Business Mobile - CAP | 126 | This is a comment | Directory Assistance |    0.25 |       0.3 |        0.6 |    0.12 |        0.35 |     0.25 |     0.17 |    0.46 |    0.28 |     0.18 | Yes          |

  @RatePlanTC19
  Scenario Outline: RatePlanTC19-Only Create Rate Plan with IDD and save it
    And I click on the Create New Rate Plan
    And I enter "<description>" in Description for the Rate Plan textbox
    And I click on the checkbox IDD
    And I click on the Create button under Create dialog
    Then New rate plan with description "<description>" is on top of Rate Plan list for bureau "CORTEL"
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" of this new one
    And Under Call Rate with IDD, I enter the value "<Country>" for the "Country" of first row
    And Under Call Rate with IDD, I enter the value "<Call Prefix>" for the "Call Prefix" of first row
    And Under Call Rate with IDD, I enter the value "<Initial>" for the "Initial Duration (sec)" of first row
    And Under Call Rate with IDD, I enter the value "<Rate Cost>" for the "Rate per Cost" of first row
    And Under Call Rate with IDD, I enter the value "<Rate Fixed>" for the "Rate Fixed" of first row
    And Under Call Rate with IDD, I enter the value "<Rate KB>" for the "Rate per KB" of first row
    And Under Call Rate with IDD, I enter the value "<Rate Second>" for the "Rate per Second" of first row
    And Under Call Rate with IDD, I enter the value "<Rate 30s>" for the "Rate per 30 Sec" of first row
    And Under Call Rate with IDD, I enter the value "<Rate 60s>" for the "Rate per 60 Sec" of first row
    And Under Call Rate with IDD, I enter the value "<Cap Fix>" for the "Capped Fixed Duration" of first row
    And Under Call Rate with IDD, I enter the value "<Cap Max>" for the "Capped Max Rate For Fixed Duration" of first row
    And Under Call Rate with IDD, I enter the value "<Cap Rate>" for the "Capped Rate (after Duration) per Second" of first row
    And Under Call Rate with IDD, I select the value "<Float Credit>" for Floating Credit Included of first row
    And I only click on the Save button
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | description   | Country | Call Prefix | Initial | Rate Cost | Rate Fixed | Rate KB | Rate Second | Rate 30s | Rate 60s | Cap Fix | Cap Max | Cap Rate | Float Credit |
      | Rate Plan IDD | Aus     | +61         |    0.25 |       0.3 |        0.6 |    0.12 |        0.35 |     0.25 |     0.17 |    0.46 |    0.28 |     0.18 | Yes          |

  @RatePlanTC20
  Scenario Outline: RatePlanTC20-Edit rate plan and save it
    And I create new rate plan with description "<OriginalDes>"
    And I enter the text "<OriginalDes>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<OriginalDes>"
    And Under Summary fields, I enter the value "<description>" for the "Description" under edit state
    And Under Summary fields, I select the value "<RP Type>" for the "Rate Plan Type" under edit state
    And Under Summary fields, I select the value "<IDD>" for the "IDD reference" under edit state
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
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | OriginalDes         | description        | RP Type               | IDD | Comments          | Call Rate Cat        | Initial | Rate Cost | Rate Fixed | Rate KB | Rate Second | Rate 30s | Rate 60s | Cap Fix | Cap Max | Cap Rate | Float Credit |
      | It is new rate plan | Edit and save plan | Business Mobile - CAP | 126 | This is a comment | Directory Assistance |    0.25 |       0.3 |        0.6 |    0.12 |        0.35 |     0.25 |     0.17 |    0.46 |    0.28 |     0.18 | Yes          |

  @RatePlanTC21
  Scenario Outline: RatePlanTC21-Check fields in calculator dialog of Rate per Cost
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per Cost" textbox
    Then I should see the "Wholesale to Retail Calculator" dialog
    Then I should see the textbox "Wholesale Amount (ex GST)"
    Then I should see the textbox "Retail Amount (inc GST)"
    Then I should see the field "Rate per Cost"
    Then I should see the "Set" button of calculation popup
    Then I should see the "Close" button of calculation popup
    And I click on "Close" button of calculation popup
    Then The Wholesale to Retail Calculator dialog is closed

    Examples: 
      | description        |
      | Edit and save plan |

  @RatePlanTC22
  Scenario Outline: RatePlanTC22-Check values in calculator dialog of Rate per Cost
    And I enter the text "<description>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per Cost" textbox
    Then I should see the "Wholesale to Retail Calculator" dialog
    When I enter the value "<Wholesale Amount>" on textbox "Wholesale Amount (ex GST)"
    And I enter the value "<Retail Amount>" on textbox "Retail Amount (inc GST)"
    Then I should see the value "<Result>" for the Rate per Cost in calculation popup
    When I click on "Set" button of calculation popup
    Then The Wholesale to Retail Calculator dialog is closed
    And I should see the value "<Result>" for the Rate per Cost in call rate

    Examples: 
      | description        | Wholesale Amount | Retail Amount | Result |
      | Edit and save plan |               40 |            60 |    1.5 |

  @RatePlanTC23
  Scenario Outline: RatePlanTC23-Check fields in calculator dialog of Rate per KB
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per KB" textbox
    Then I should see the "Data Calculator to KB" dialog
    And I should see the dropdown "Data Type" in calculation popup
    And I should see the textbox "Amount"
    And I should see the field "Result"
    And I should see the "Set" button of calculation popup
    And I should see the "Close" button of calculation popup
    When I click on "Close" button of calculation popup
    Then The Data Calculator to KB dialog is closed

    Examples: 
      | description        |
      | Edit and save plan |

  @RatePlanTC24
  Scenario Outline: RatePlanTC24-Check values in calculator dialog of Rate per KB with Data Type <type>
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per KB" textbox
    Then I should see the "Data Calculator to KB" dialog
    When I select the "<type>" in Data Type dropdown
    And I enter the value "<amount>" on textbox "Amount"
    Then I should see the value "<result>" for the Result
    When I click on "Set" button of calculation popup
    Then The Data Calculator to KB dialog is closed
    And I should see the value "<result>" for Rate per KB in call rate

    Examples: 
      | description        | type | amount | result             |
      | Edit and save plan | MB   |      1 |       0.0009765625 |
      | Edit and save plan | GB   |      1 | 9.5367431640625e-7 |
      | Edit and save plan | TB   |      1 | 9.3132257462e-10   |

  @RatePlanTC25
  Scenario Outline: RatePlanTC25-Copy Rate Plan under one Bureau and save it
    And I enter the text "<description>" in the filter textbox
    And I click on "Copy" button for Rate Plan with Description "<description>"
    Then The Copy Rate Plan Dialog is displayed
    And I should see the dropdown Bureau for target Rate Plan in dialog
    And The bureau "CORTEL" is selected in dropdown Bureau for target Rate Plan under popup
    And I should see the textbox Description for the Rate Plan in dialog
    And The description textbox is auto filled "<description>"
    And Copy button should be inactive
    When I enter "<new description>" in Description for the Rate Plan textbox
    Then Copy button should be active
    And I should see the Cancel button in dialog
    When I click on the Copy button under Copy dialog
    Then New rate plan with description "<new description>" is on top of Rate Plan list for bureau "CORTEL"
    When I click on the Save button of rate plan record
    And I enter the text "<new description>" in the filter textbox
    And I double click on Rate Plan with Description "<new description>"
    Then The summary info of Rate Plan with Description "<new description>" is correct
    And The call rate info of Rate Plan with Description "<new description>" is correct

    Examples: 
      | description   | new description          |
      | Test for edit | Copy RP with same Bureau |

  @RatePlanTC26
  Scenario Outline: RatePlanTC26-Copy Rate Plan under different Bureau and save it
    And I select the bureau "ABACUS" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I click on "Copy" button for Rate Plan with Description "<description>"
    Then The Copy Rate Plan Dialog is displayed
    And The bureau "ABACUS" is selected in dropdown Bureau for target Rate Plan under popup
    When I select bureau "CORTEL" in dropdown Bureau target for Rate Plan
    Then The description textbox is auto filled "<description>"
    When I enter "<copiedDes>" in Description for the Rate Plan textbox
    Then Copy button should be active
    And I should see the Cancel button in dialog
    When I click on the Copy button under Copy dialog
    Then New rate plan with description "<copiedDes>" is on top of Rate Plan list for bureau "CORTEL"
    When I click on the Save button of rate plan record
    And I enter the text "<copiedDes>" in the filter textbox
    And I double click on Rate Plan with Description "<copiedDes>"
    Then The summary info of Rate Plan with Description "<copiedDes>" is correct
    And The call rate info of Rate Plan with Description "<copiedDes>" is correct

    Examples: 
      | description            | copiedDes                     |
      | Inbound WS Rates (1.1) | Copy RP with different Bureau |

  @RatePlanTC27
  Scenario Outline: RatePlanTC27-Cannot copy Rate Plan with existed description
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I click on "Copy" button for Rate Plan with Description "<description>"
    Then The Copy Rate Plan Dialog is displayed
    And I should see the dropdown Bureau for target Rate Plan in dialog
    And The bureau "CORTEL" is selected in dropdown Bureau for target Rate Plan under popup
    And I should see the textbox Description for the Rate Plan in dialog
    And The description textbox is auto filled "<description>"
    And Copy button should be inactive
    Then I should see the message that Description existed
    And Copy button should be inactive
    And I should see the Cancel button in dialog

    Examples: 
      | description   |
      | Test for edit |

  @RatePlanTC28
  Scenario Outline: RatePlanTC28-Delete new Rate Plan and confirm to delete
    And I create new rate plan with description "<description>"
    Then Delete button of Rate Plan with Description "<description>" is active
    When I click on "Delete" button for Rate Plan with Description "<description>"
    Then I should see the Delete Confirmation popup
    When I click on the Confirm button
    And I enter the text "<description>" in the filter textbox
    Then There is no rate plan

    Examples: 
      | description     |
      | Delete new plan |

  @RatePlanTC29
  Scenario Outline: RatePlanTC29-Delete the change of Rate Plan and confirm to delete
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    Then Delete button of Rate Plan with Description "<description>" is inactive
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "Hua Test Delete Of Change" for the "Description" under edit state
    And I click on the Save button of rate plan record
    Then Delete button of Rate Plan with Description "Hua Test Delete Of Change" is active
    When I click on "Delete" button for Rate Plan with Description "Hua Test Delete Of Change"
    Then I should see the Delete Confirmation popup
    When I click on the Confirm button
    Then I should see Rate Plan with Description "<description>"
    When I enter the text "Hua Test Delete Of Change" in the filter textbox
    Then There is no rate plan

    Examples: 
      | description             |
      | Dont Change Plan Of Hua |

  @RatePlanTC30
  Scenario Outline: RatePlanTC30-Delete new Rate Plan and cancel to delete
    And I create new rate plan with description "<description>"
    Then Delete button of Rate Plan with Description "<description>" is active
    When I click on "Delete" button for Rate Plan with Description "<description>"
    Then I should see the Delete Confirmation popup
    When I click on the Cancel button
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Delete" button for Rate Plan with Description "<description>"
    Then I should see the Delete Confirmation popup
    When I click on the Cancel button
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"

    Examples: 
      | description |
      | Hua Cancel  |

  @RatePlanTC31
  Scenario Outline: RatePlanTC31-Cannot delete new Rate Plan with no owner
    And I create new rate plan with description "<description>"
    And I access the Provisioning Portal website with user role "prov-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then Delete button of Rate Plan with Description "<description>" is inactive
    And I access the Provisioning Portal website with user role "non-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown

    Examples: 
      | description       |
      | New Hua Not Owner |

  @RatePlanTC32
  Scenario Outline: RatePlanTC32-Cannot delete the change of lived Rate Plan with no owner
    When Connect to Rate Plan table in Mongo database
    And Under Mongo Database, I delete Rate Plan Id "105"
    And I enter the text "<description>" in the filter textbox
    And Delete button of Rate Plan with Description "<description>" is inactive
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<changedDes>" for the "Description" under edit state
    And I click on the Save button of rate plan record
    And I access the Provisioning Portal website with user role "prov-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<changedDes>" in the filter textbox
    Then Delete button of Rate Plan with Description "<changedDes>" is inactive
    And I access the Provisioning Portal website with user role "non-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown

    Examples: 
      | description             | changedDes          |
      | Dont Change Plan Of Hua | Lived Hua Not Owner |

  @RatePlanTC33
  Scenario Outline: RatePlanTC33-Verify fields on the service number list
    And I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I should see the Download as CSV button
    Then I should see the header "CIDN"
    And I should see the header "Salutation"
    And I should see the header "First Name"
    And I should see the header "Last Name"
    And I should see the header "Address"
    And I should see the header "Contact Number"
    And I should see the header "Email"
    And I should see the header "Service Number"
    When I close the popup
    Then I should NOT see the Service Numbers dialog

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @RatePlanTC34
  Scenario Outline: RatePlanTC34-Verify values of service number list
    And I enter the text "<RP Id>" in the filter textbox
    And I click on the list icon of Rate Plan Id "<RP Id>"
    Then I got a list of Service Numbers
    When I create Json file for Service Number of Rate Plan ID "<RP Id>" under bureau "CORTEL"
    Then All values on web should be same as on Json file of Service Number under Rate Plan ID "<RP Id>"

    Examples: 
      | RP Id |
      |    63 |

  @RatePlanTC35
  Scenario Outline: RatePlanTC35-Verify the Download CSV
    And I enter the text "<description>" in the filter textbox
    And I click on the list icon of Rate Plan with Description "<description>"
    Then I got a list of Service Numbers
    And I should see the Download as CSV button
    And I can download file by clicking on the Download as CSV button

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @RatePlanTC36
  Scenario Outline: RatePlanTC36-Open new windows when double-clicking on Service Number
    And I enter the text "<description>" in the filter textbox
    And I click on the list icon of Rate Plan with Description "<description>"
    Then I got a list of Service Numbers
    When I double click on the row with CIDN "950001"
    Then The new window is open with the URL "<url>"

    Examples: 
      | description           | url                                                                                     |
      | 5GB ($66) MBB (4RP1A) | https://cindy.telecombilling.com.au/cortel-admin/client/showUpdateClient.do?cidn=950001 |

  @RatePlanTC37
  Scenario: RatePlanTC37-Dont see revert icon for new Rate Plan
    When I click on the Create New Rate Plan
    And I enter "Hua New" in Description for the Rate Plan textbox
    And I click on the Create button under Create dialog
    And Under Summary fields, I enter the value "Hua New" for the "Comments" of this new one
    And I click on the Save button of rate plan record
    And I enter the text "Hua New" in the filter textbox
    Then I should NOT see the revert icon for Rate Plan with Description "Hua New"

  @RatePlanTC38
  Scenario Outline: RatePlanTC38-Dont see revert icon for lived Rate Plan with no change
    When Connect to Rate Plan table in Mongo database
    And Under Mongo Database, I delete Rate Plan Id "105"
    And I enter the text "<description>" in the filter textbox
    Then I should see the number in Rate Plan ID for Rate Plan with Description "<description>"
    And I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
    And I should NOT see the revert icon for Rate Plan with Description "<description>"

    Examples: 
      | description             |
      | Dont Change Plan Of Hua |

  @RatePlanTC39
  Scenario Outline: RatePlanTC39-Compare old and new version of lived Rate plan after changing info
    When Connect to Rate Plan table in Mongo database
    And Under Mongo Database, I delete Rate Plan Id "<RP Id>"
    And I enter the text "<RP Id>" in the filter textbox
    And I double click on Rate Plan Id "<RP Id>"
    And I got all of curent values for fields of Rate Plan Id "<RP Id>"
    And I click on "Edit" button of Rate Plan Id "<RP Id>"
    And Under Summary fields, I select the value "<RP Type>" for the "Rate Plan Type" under edit state
    And Under Summary fields, I select the value "<IDD>" for the "IDD reference" under edit state
    And Under Summary fields, I enter the value "<Comments>" for the "Comments" under edit state
    And Under Call Rate without IDD, I select the value "<Call Rate Cat>" for the "Call Rate Category" of first row
    And Under Call Rate without IDD, I enter the value "<Rate Cost>" for the "Rate per Cost" of first row
    And I click on the Save button of rate plan record
    Then I got all of values after changing Rate Plan Id "<RP Id>"
    And I should see the revert icon of Rate Plan Id "<RP Id>"
    And I click on revert icon for Rate Plan Id "<RP Id>"
    Then All changes of Rate Plan Id "<RP Id>" are highlighted
    And The old Summary Info of Rate Plan Id "<RP Id>" should be displayed
    And The old Call Rate Info of Rate Plan Id "<RP Id>" should be displayed
    When I click on revert icon for Rate Plan Id "<RP Id>"
    Then All changes of Rate Plan Id "<RP Id>" are NOT highlighted

    Examples: 
      | RP Id | RP Type               | IDD | Comments          | Call Rate Cat        | Rate Cost |
      |   105 | Business Mobile - CAP | 126 | This is a comment | Directory Assistance |         2 |

  @RatePlanTC40
  Scenario Outline: RatePlanTC40-Compare old and new version of lived Rate plan after adding new call rate
    And I enter the text "<RP Id>" in the filter textbox
    And I double click on Rate Plan Id "<RP Id>"
    Then I got all of curent values for fields of Rate Plan Id "<RP Id>"
    And I click on "Edit" button of Rate Plan Id "<RP Id>"
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" under edit state
    And Under Call Rate without IDD, I select the value "Local Data" for the "Call Rate Category" of first row
    And Under Call Rate without IDD, I enter the value "1" for the "Rate per Cost" of first row
    And I click on Add row button
    And Under Call Rate without IDD, I select the value "Fixed" for the "Call Rate Category" of new row
    And I click on the Save button of rate plan record
    And I got all of values after changing Rate Plan Id "<RP Id>"
    Then I should see the revert icon of Rate Plan Id "<RP Id>"
    When I click on revert icon for Rate Plan Id "<RP Id>"
    Then All changes of Rate Plan Id "<RP Id>" are highlighted
    And The old Summary Info of Rate Plan Id "<RP Id>" should be displayed
    And The old Call Rate Info of Rate Plan Id "<RP Id>" should be displayed
    Then The new call rates of Rate Plan Id "<RP Id>" are highlighted
    And The value of new call rates of Rate Plan Id "<RP Id>" should be "<New>"
    When I click on revert icon for Rate Plan Id "<RP Id>"
    Then All changes of Rate Plan Id "<RP Id>" are NOT highlighted

    Examples: 
      | RP Id |
      |   131 |
