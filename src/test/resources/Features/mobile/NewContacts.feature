Feature: ContactsFeature
  This feature deals with the new Contacts functionality of the application

  @P0
  @MobileTest
  @createLocContact
  Scenario Outline: Create a new local contact in TactAPP with basic information w/ save in iphone, w/o send to SF
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I go to create a new "Contact" page
    When AddContact: I "<saveToIphone>" save to Phone and "<sendToSF>" send to Salesforce
    Then AddContact: I input a user name "<contactName>"
    And AddContact: I input phone "<phoneNumber>", email "<emailValue>"
    And AddContact: I save "<isSave>"
#    And Contact: I search this "Contact" from recent field and select it
#    When Common: I am waiting for syncing done
#    Then API: I check Object "Contact" is "saved" in salesforce
#    And Common: I click back icon
#    When API: I delete Object "Contact" from salesforce and wait for "10" sec

    Examples:
      | saveToIphone | sendToSF | contactName | phoneNumber | emailValue | isSave |
      | do           | don't    | Umi, Singh  | 6501231234  | @gmail.com | yes    |

  @P1
  @MobileTest
  @logCallSFTask
  Scenario Outline: Add Log to a contact calling, then add SFTask. Verify them in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "contact" from recent field and select it
    Then Contacts: I search one user "contact" from contacts list and select it
    And Contacts: I click "phone" icon
    When Saleflow: I click "Log" activity option
    Then Tact-Pin: I edit "<logSubjectName>" subject in "Log"
    And Tact-Pin: I "<logIsSave>" save new created
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<sfTaskTitle>" subject in "SFTask"
    And Tact-Pin: I "<sfTaskIsSave>" save new created
    And Contacts: I search this "Call" from "Contact" page and select it
    And Contacts: I make sure this "log" in Call Page
    And Contacts: I search this "Call" from "Contact" page and select it
    And Contacts: I make sure this "task" in Call Page
    And Contacts: I search this "task" from "Contact" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Task" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Task" from salesforce
    Then API: I verify activity "Log" is "saved" in salesforce
    When API: I delete activity "Log" from salesforce
    And Common: I click back icon
    And Contact: I search this "Contact" from recent field and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Contact" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete Object "Contact" from salesforce and wait for "10" sec

    Examples:
      | logSubjectName   | logIsSave | sfTaskTitle                | sfTaskIsSave |
      | loc_contact_call_log | yes       | loc_contact_call_SF_TaskAftLog | yes          |


  @P2
  @MobileTest
  @createDupContact
  Scenario Outline: Create a new contact in TactAPP with basic information and w/o save in iphone and SF
#    automation.tactSF@gmail.com/Tact0218
    When Common: I switch to "Contacts" page from tab bar
    And AddContact: I create "<time>" time a "<optionUser>" and "<saveToIphone>" save to Phone and "<sendToSF>" send to Salesforce, with username "<contactName>" and "<isSave>"

    Examples:
      | time | optionUser | saveToIphone | sendToSF | contactName   | isSave |
      | 1    | Contact    | don't        | do       | Dupluser, Tom | yes    |