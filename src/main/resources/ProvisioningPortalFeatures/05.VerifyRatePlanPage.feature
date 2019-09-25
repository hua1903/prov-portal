@RatePlan @all
Feature: Verify the Rate Plan page of the Provisioning Portal website with Non-Admin account

  Background: 
    Given I access the Provisioning Portal website with user role "non-admin"
    When I am on the Home page
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown

  @TC21 @FieldsRP
  Scenario: TC21-Check labels in summary info
    Then I should see the title Rate Plans for the bureau "CORTEL"
    And I should get rate plans under Rate Plan page
    And Under each rate plan, I should see the label "Rate Plan Id"
    And Under each rate plan, I should see the label "Description"
    And Under each rate plan, I should see the label "Rate Plan Type"
    And Under each rate plan, I should see the label "Comments"

  @TC22 @FieldsRP
  Scenario: TC22-Check labels in call rate table
    And Under each rate plan, I double click on row
    Then Under each rate plan, I should see the table with detail info
    And Under each table, I should see the "Call Rate Category" column
    And Under each table, I should see the "Initial Duration (sec)" column
    And Under each table, I should see the "Rate per Cost" column
    And Under each table, I should see the "Rate Fixed" column
    And Under each table, I should see the "Rate per KB" column
    And Under each table, I should see the "Rate per Second" column
    And Under each table, I should see the "Rate per 30 Sec" column
    And Under each table, I should see the "Rate per 60 Sec" column
    And Under each table, I should see the "Capped Fixed Duration" column
    And Under each table, I should see the "Capped Max Rate For Fixed Duration" column
    And Under each table, I should see the "Capped Rate (after Duration) per Second" column
    And Under each table, I should see the "Floating Credit Included" column

  @TC23 @ButtonsRP
  Scenario Outline: TC23-Check buttons of Rate plan with pending and owner
    And I create new rate plan with description "<description>"
    And I double click on Rate Plan with Description "<description>"
    Then Under pending Rate Plan and owner, "Edit" button is active
    And Under pending Rate Plan and owner, "Copy" button is active
    And Under pending Rate Plan and owner, "Delete" button is active
    When I click on "Delete" button for Rate Plan with Description "<description>"
    And I click on the Confirm button
    And I enter the text "<description>" in the filter textbox
    Then There is no rate plan

    Examples: 
      | description       |
      | Hua Pending Owner |

  @TC24 @ButtonsRP
  Scenario: TC24-Check buttons of Rate plan with not-pending and owner
    Then I should see the Create New Rate Plan button
    And Under not pending Rate Plan, "Edit" button is active
    And Under not pending Rate Plan, "Copy" button is active
    And Under not pending Rate Plan, Delete button is inactive

  @TC25 @ButtonsRP
  Scenario Outline: TC25-Check buttons of Rate plan with pending and not owner
    And I create new rate plan with description "<description>"
    And I click on the Logout button
    And Under Logout pop-up, I click on the button "Confirm"
    And I access the Provisioning Portal website with user role "admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    Then Under pending Rate Plan and not owner, "Edit" button is inactive
    And Under pending Rate Plan and not owner, Copy button is active
    And Under pending Rate Plan and not owner, "Delete" button is inactive
    When I click on the Logout button
    And Under Logout pop-up, I click on the button "Confirm"
    And I access the Provisioning Portal website with user role "non-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then Delete button of Rate Plan with Description "<description>" is active
    When I click on "Delete" button for Rate Plan with Description "<description>"
    And I click on the Confirm button
    And I enter the text "<description>" in the filter textbox
    Then There is no rate plan

    Examples: 
      | description           |
      | Hua Pending Not Owner |

  @TC26 @CheckInfoRP
  Scenario: TC26-Verify the list in Select Bureau dropdown
    And I click on the Select Bureau dropdown
    And I create the Json file for Bureau List
    Then The list of bureaus on website should be same as Json file

  @TC27 @CheckInfoRP
  Scenario: TC27-Verify Summary Info of all rate plans
    And I create the Json file for Rate Plan List
    Then The "Rate Plan Id" of Rate Plans on website should be same as Json file of Rate Plan page
    And The "Description" of Rate Plans on website should be same as Json file of Rate Plan page
    And The "Rate Plan Type" of Rate Plans on website should be same as Json file of Rate Plan page
    And The "Comments" of Rate Plans on website should be same as Json file of Rate Plan page

  @TC28 @CheckInfoRP
  Scenario Outline: TC28-Verify the Call Rate Info of a specific rate plan
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I create the Json file for Call Rate of Rate Plan with Description "<description>"
    Then The value of call rate "Call Rate Category" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Initial Duration (sec)" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Rate per Cost" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Rate Fixed" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Rate per KB" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Rate per Second" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Rate per 30 Sec" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Rate per 60 Sec" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Capped Fixed Duration" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Capped Max Rate For Fixed Duration" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Capped Rate (after Duration) per Second" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page
    And The value of call rate "Floating Credit Included" of Rate Plan with Description "<description>" on website should be same as Json file of Rate Plan page

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @TC29 @FilterRP
  Scenario: TC29-Filter the valid Description
    Then I should get rate plans under Rate Plan page
    When I enter the text "50Gb MBB (4R6PA)" in the filter textbox
    Then Under each rate plan, I should see the text "50Gb MBB (4R6PA)" in Description or Comment

  @TC30 @FilterRP
  Scenario: TC30-Filter the invalid Description
    Then I should get rate plans under Rate Plan page
    When I enter the text "invalid" in the filter textbox
    Then There is no rate plan

  @TC31 @CreateRP
  Scenario: TC31-Check fields on Create New Rate Plan dialog
    And I click on the Create New Rate Plan
    Then The New Rate Plan Dialog is displayed
    And I should see the dropdown "Please select a bureau for target Rate Plan" in "New Rate Plan Dialog" popup
    And I should see the textbox "Please enter a description for the Rate Plan" in "Copy Rate Plan Dialog" popup
    And I should see the "Create" button in "Create" dialog
    And I should see the "Cancel" button in "Copy" dialog
    When I click on the Cancel button under Create dialog

  @TC32 @CreateRP
  Scenario: TC32-Check the list in Bureau dropdown on Create New Plan Dialog
    And I click on the Create New Rate Plan
    And I click on the dropdown Please select a bureau for target rate plan
    And I create the Json file for Bureau List
    Then I should see the list of Bureau for Target Rate Plan should be same as Json file
    When I click on the Cancel button under Create dialog

  @TC33 @CreateRP
  Scenario Outline: TC33-Only Create Rate Plan but dont save
    And I click on the Create New Rate Plan
    And I enter "<description>" in Description for the Rate Plan textbox
    And I click on the Create button under Create dialog
    Then The new rate plan with description "<description>" on top of Rate Plan list for bureau "CORTEL"
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" of this new one
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" of this new one
    And Under Call Rate, I select the value "<CSV Call Rate>" for the "Call Rate Category" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per Cost" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate Fixed" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Initial Duration (sec)" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per KB" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per Second" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per 30 Sec" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per 60 Sec" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Fixed Duration" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Max Rate For Fixed Duration" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Rate (after Duration) per Second" of this new one
    And Under Call Rate, I select the value "<CSV Call Rate>" for the "Floating Credit Included" of this new one
    And I click on the Cancel button of rate plan record
    Then The Rate Plan with Description "<description>" is NOT saved successfully

    Examples: 
      | description             |
      | Dont save new rate plan |

  @TC34 @CreateRP
  Scenario Outline: TC34-Create new rate plan and save it
    And I click on the Create New Rate Plan
    And I enter "<description>" in Description for the Rate Plan textbox
    And I click on the Create button under Create dialog
    Then The new rate plan with description "<description>" on top of Rate Plan list for bureau "CORTEL"
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" of this new one
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" of this new one
    And Under Call Rate, I select the value "<CSV Call Rate>" for the "Call Rate Category" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per Cost" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate Fixed" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Initial Duration (sec)" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per KB" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per Second" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per 30 Sec" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per 60 Sec" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Fixed Duration" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Max Rate For Fixed Duration" of this new one
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Capped Rate (after Duration) per Second" of this new one
    And Under Call Rate, I select the value "<CSV Call Rate>" for the "Floating Credit Included" of this new one
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | description                |
      | Test for creating new plan |

  @TC35 @EditRP
  Scenario Outline: TC35-Edit rate plan and save it
    And I create new rate plan with description "<OriginalDes>"
    And I enter the text "<OriginalDes>" in the filter textbox
    And I click on "Edit" button for Rate Plan with Description "<OriginalDes>"
    And Under Summary fields, I enter the value "<description>" for the "Description" under edit state
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" under edit state
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
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully

    Examples: 
      | OriginalDes         | description        |
      | It is new rate plan | Edit and save plan |

  @TC36 @EditRP
  Scenario Outline: TC36-Check fields in calculator dialog of Rate per Cost
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
    Then The "Wholesale to Retail Calculator" dialog is closed

    Examples: 
      | description        |
      | Edit and save plan |

  @TC37 @EditRP
  Scenario Outline: TC37-Check values in calculator dialog of Rate per Cost
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
      | description        |
      | Edit and save plan |

  @TC38 @EditRP
  Scenario Outline: TC38-Check fields in calculator dialog of Rate per KB
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
    Then The "Data Calculator to KB" dialog is closed

    Examples: 
      | description        |
      | Edit and save plan |

  @TC39 @EditRP
  Scenario Outline: TC39-Check values in calculator dialog of Rate per KB with Data Type <type>
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And I click on the caculation icon next to "Rate per KB" textbox
    Then I should see the "Data Calculator to KB" dialog
    When I select the "<type>" in Data Type dropdown
    And I enter the value "<amount>" on textbox "Amount"
    Then I should see the value "<result>" for the Result
    When I click on "Set" button of calculation popup
    Then The "Data Calculator to KB" dialog is closed
    And I should see the value "<result>" for Rate per KB in call rate

    Examples: 
      | description        | type | amount | result             |
      | Edit and save plan | MB   |      1 |       0.0009765625 |
      | Edit and save plan | GB   |      1 | 9.5367431640625e-7 |
      | Edit and save plan | TB   |      1 | 9.3132257462e-10   |

  @TC40 @CopyRP
  Scenario Outline: TC40-Copy Rate Plan under one Bureau and save it
    And I enter the text "<description>" in the filter textbox
    And I double click on Rate Plan with Description "<description>"
    And I click on "Copy" button for Rate Plan with Description "<description>"
    Then The Copy Rate Plan Dialog is displayed
    And I should see the dropdown "Please select a bureau for target Rate Plan" in "Copy Rate Plan Dialog" popup
    And The bureau "CORTEL" is selected by default in dropdown "Please select a bureau for target rate plan"
    And I should see the textbox "Please enter a description for the Rate Plan" in "Copy Rate Plan Dialog" popup
    And The description textbox is auto filled "<description>"
    And Copy button should be inactive
    When I enter "<new description>" in Description for the Rate Plan textbox
    Then Copy button should be active
    And I should see the "Cancel" button in "Copy" dialog
    When I click on the Copy button under Copy dialog
    Then The new rate plan with description "<new description>" on top of Rate Plan list for bureau "CORTEL"
    When I click on the Save button of rate plan record
    And I enter the text "<new description>" in the filter textbox
    Then I should see Rate Plan with Description "<new description>"
    When I double click on Rate Plan with Description "<new description>"
    And The summary info of Rate Plan with Description "<new description>" is correct
    And The call rate info of Rate Plan with Description "<new description>" is correct

    Examples: 
      | description   | new description |
      | Test for edit | Copy of TC40    |

  @TC41 @CopyRP
  Scenario Outline: TC41-Copy Rate Plan under different Bureau and save it
    And I select the bureau "ABACUS" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I double click on Rate Plan with Description "<description>"
    And I click on "Copy" button for Rate Plan with Description "<description>"
    Then The Copy Rate Plan Dialog is displayed
    And I should see the dropdown "Please select a bureau for target Rate Plan" in "Copy Rate Plan Dialog" popup
    Then The bureau "ABACUS" is selected by default in dropdown "Please select a bureau for target rate plan"
    When I select a specific bureau "CORTEL" in dropdown Target rate plan
    Then I should see the textbox "Please enter a description for the Rate Plan" in "Copy Rate Plan Dialog" popup
    And The description textbox is auto filled "<description>"
    And Copy button should be active
    When I enter "<copiedDes>" in Description for the Rate Plan textbox
    Then Copy button should be active
    And I should see the "Cancel" button in "Copy" dialog
    When I click on the Copy button under Copy dialog
    Then The new rate plan with description "<copiedDes>" on top of Rate Plan list for bureau "CORTEL"
    When I click on the Save button of rate plan record
    And I enter the text "<copiedDes>" in the filter textbox
    Then I should see Rate Plan with Description "<copiedDes>"
    When I double click on Rate Plan with Description "<copiedDes>"
    And The summary info of Rate Plan with Description "<copiedDes>" is correct
    And The call rate info of Rate Plan with Description "<copiedDes>" is correct

    Examples: 
      | description         | copiedDes    |
      | Inbound WS Rates (1.1) | Copy of TC41 |

  @TC42 @CopyRP
  Scenario Outline: TC42-Cannot copy Rate Plan with existed description
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I double click on Rate Plan with Description "<description>"
    And I click on "Copy" button for Rate Plan with Description "<description>"
    Then The Copy Rate Plan Dialog is displayed
    And I should see the dropdown "Please select a bureau for target Rate Plan" in "Copy Rate Plan Dialog" popup
    And The bureau "CORTEL" is selected by default in dropdown "Please select a bureau for target rate plan"
    And I should see the textbox "Please enter a description for the Rate Plan" in "Copy Rate Plan Dialog" popup
    And The description textbox is auto filled "<description>"
    And Copy button should be inactive
    Then I should see the message that Description existed
    And Copy button should be inactive
    And I should see the "Cancel" button in "Copy" dialog

    Examples: 
      | description   |
      | Test for edit |

  @TC43 @DeleteRP
  Scenario Outline: TC43-Delete new Rate Plan and confirm to delete
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

  @TC44 @DeleteRP
  Scenario Outline: TC44-Delete the change of Rate Plan and confirm to delete
    Then I should get rate plans under Rate Plan page
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When Under Mongo Database, I delete Rate Plan with Description "<description>"
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

  @TC45 @DeleteRP
  Scenario Outline: TC45-Delete new Rate Plan and cancel to delete
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

  @TC46 @DeleteRP
  Scenario Outline: TC46-Cannot delete new Rate Plan with no owner
    And I create new rate plan with description "<description>"
    And I click on the Logout button
    And Under Logout pop-up, I click on the button "Confirm"
    And I access the Provisioning Portal website with user role "admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then Delete button of Rate Plan with Description "<description>" is inactive
    When Logout the website
    And I access the Provisioning Portal website with user role "non-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<description>" in the filter textbox
    Then Delete button of Rate Plan with Description "<description>" is active
    When I click on "Delete" button for Rate Plan with Description "<description>"
    And I click on the Confirm button
    And I enter the text "<description>" in the filter textbox
    Then There is no rate plan

    Examples: 
      | description   |
      | Hua Not Owner |

  @TC47 @DeleteRP
  Scenario Outline: TC47-Cannot delete the change of lived Rate Plan with no owner
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And Under Mongo Database, I delete Rate Plan with Description "<description>"
    And Delete button of Rate Plan with Description "<description>" is inactive
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I enter the value "<changedDes>" for the "Description" under edit state
    And I click on the Save button of rate plan record
    And Logout the website
    And I access the Provisioning Portal website with user role "admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<changedDes>" in the filter textbox
    Then Delete button of Rate Plan with Description "<changedDes>" is inactive
    When Logout the website
    And I access the Provisioning Portal website with user role "non-admin"
    And I click on the box "Rate Plan"
    And I select the bureau "CORTEL" in Select Bureau dropdown
    And I enter the text "<changedDes>" in the filter textbox
    Then Delete button of Rate Plan with Description "<changedDes>" is active
    When I click on "Delete" button for Rate Plan with Description "<changedDes>"
    And I click on the Confirm button
    And I enter the text "<changedDes>" in the filter textbox
    Then There is no rate plan
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"

    Examples: 
      | description             | changedDes    |
      | Dont Change Plan Of Hua | Hua Not Owner |

  @TC48 @SerNumRP
  Scenario Outline: TC48-Verify fields on the service number list
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

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @TC49 @SerNumRP
  Scenario Outline: TC49-Verify values of service number list
    And I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I got a list of Service Numbers
    When Under Rate Plan page, I create the Json file for Service Number of Rate Plan with Description "<description>"
    Then The value of header "CIDN" on web should be same as on Json file of Service Number
    And The value of header "Salutation" on web should be same as on Json file of Service Number
    And The value of header "First Name" on web should be same as on Json file of Service Number
    And The value of header "Last Name" on web should be same as on Json file of Service Number
    And The value of header "Address" on web should be same as on Json file of Service Number
    And The value of header "Contact Number" on web should be same as on Json file of Service Number
    And The value of header "Email" on web should be same as on Json file of Service Number
    And The value of header "Service Number" on web should be same as on Json file of Service Number
    When I close the popup

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @TC50 @SerNumRP
  Scenario Outline: TC50-Verify the Download CSV
    And I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I got a list of Service Numbers
    And I should see the Download as CSV button
    And I can download file by clicking on the Download as CSV button
    When I close the popup

    Examples: 
      | description           |
      | 5GB ($66) MBB (4RP1A) |

  @TC51 @SerNumRP
  Scenario Outline: TC51-Open new windows when double-clicking on Service Number
    And I enter the text "<description>" in the filter textbox
    Then I should see the list icon for Rate Plan with Description "<description>"
    When I click on the list icon of Rate Plan with Description "<description>"
    Then I should see the Service Numbers dialog with Description "<description>"
    And I got a list of Service Numbers
    When I double click on the row with CIDN "950001"
    Then The new window is open with the URL "<url>"
    When I close the popup

    Examples: 
      | description           | url                                                                                     |
      | 5GB ($66) MBB (4RP1A) | https://cindy.telecombilling.com.au/cortel-admin/client/showUpdateClient.do?cidn=950001 |

  @TC52 @CompareRP
  Scenario: TC52-Dont see revert icon for new Rate Plan
    Then I should see the Create New Rate Plan button
    When I click on the Create New Rate Plan
    And I enter "Hua New" in Description for the Rate Plan textbox
    And I click on the Create button under Create dialog
    Then The new rate plan with description "Hua New" on top of Rate Plan list for bureau "CORTEL"
    When Under Summary fields, I enter the value "Hua New" for the "Comments" of this new one
    And I click on the Save button of rate plan record
    Then I should NOT see the revert icon for Rate Plan with Description "Hua New"
    When I click on "Delete" button for Rate Plan with Description "Hua New"
    And I click on the Confirm button
    And I enter the text "Hua New" in the filter textbox
    Then There is no rate plan

  @TC53 @CompareRP
  Scenario Outline: TC53-Dont see revert icon for lived Rate Plan with no change
    Then I should see the Create New Rate Plan button
    When I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And I should see the number in Rate Plan ID for Rate Plan with Description "<description>"
    And I should NOT see the PENDING in Rate Plan ID for Rate Plan with Description "<description>"
    And I should NOT see the revert icon for Rate Plan with Description "<description>"

    Examples: 
      | description             |
      | Dont Change Plan Of Hua |

  @TC54 @CompareRP
  Scenario Outline: TC54-Compare old and new version of lived Rate plan after changing info
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    When I double click on Rate Plan with Description "<description>"
    Then I got all of curent values for fields of Rate Plan with Description "<description>"
    When I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" under edit state
    And Under Call Rate, I select the value "<CSV Call Rate>" for the "Call Rate Category" under edit state
    And Under Call Rate, I enter the value "<CSV Call Rate>" for the "Rate per Cost" under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    Then I got all of values after changing Rate Plan with Description "<description>"
    And I should see the revert icon for Rate Plan with Description "<description>"
    And I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are highlighted
    And The old values of Rate Plan with Description "<description>" should be displayed
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are NOT highlighted

    Examples: 
      | description             |
      | Dont Change Plan Of Hua |

  @TC55 @CompareRP
  Scenario Outline: TC55-Compare old and new version of lived Rate plan after adding new call rate
    And I enter the text "<description>" in the filter textbox
    Then I should see Rate Plan with Description "<description>"
    And I double click on Rate Plan with Description "<description>"
    Then I got all of curent values for fields of Rate Plan with Description "<description>"
    And I click on "Edit" button for Rate Plan with Description "<description>"
    And Under Summary fields, I select the value "Business Mobile - CAP" for the "Rate Plan Type" under edit state
    And Under Summary fields, I enter the value "This is a comment" for the "Comments" under edit state
    And Under Call Rate, I select the value "Local Data" for the "Call Rate Category" under edit state
    And Under Call Rate, I enter the value "1" for the "Rate per Cost" under edit state
    And I click on Add row button
    And I select the value "Fixed" for the "Call Rate Category" for new row under edit state
    And I click on the Save button of rate plan record
    Then The Rate Plan with Description "<description>" is saved successfully
    Then I got all of values after changing Rate Plan with Description "<description>"
    And I should see the revert icon for Rate Plan with Description "<description>"
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are highlighted
    And The old values of Rate Plan with Description "<description>" should be displayed
    Then The new call rates of Rate Plan with Description "<description>" are highlighted
    And The value of new call rates of Rate Plan with Description "<description>" should be "<New>"
    When I click on revert icon for Rate Plan with Description "<description>"
    Then All changes of Rate Plan with Description "<description>" are NOT highlighted

    Examples: 
      | description        |
      | Auto Test Case 666 |
