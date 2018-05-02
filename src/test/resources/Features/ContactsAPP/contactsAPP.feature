Feature: ContactsFeature
  This feature deals with the contacts App

  @P0
  Scenario: Delete the contact which created in Tact app
    When ContactsAPP: I am in Contacts APP
    Then I search for contact's name "DFName, DLName"
    And I edit and delete it from Contacts App
