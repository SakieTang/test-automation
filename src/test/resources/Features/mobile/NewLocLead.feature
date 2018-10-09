Feature: LocLeadFeature
  This feature deals with the new loc lead functionality of the application

  @P0
  @MobileTest
  @createLocLead
  Scenario Outline: Create a new local Lead in TactAPP with basic information w/ save in iphone, w/o send to SF
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I go to create a new "Lead" page
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
      | saveToIphone | sendToSF | contactName                  | phoneNumber  | emailValue | isSave |
      | do           | don't    | LastNLocLead, FirstNLocLead  | randomNumber | @gmail.com | yes    |

  @P1
  @MobileTest
  @logCallSFTask
  Scenario Outline: Add Log to a loc lead calling, then add SFTask. Verify them (log, task, contact) in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "lead" from recent field and select it
    Then Contacts: I search one user "lead" from contacts list and select it
    And Contacts: I click "phone" icon
    When Saleflow: I click "Log" activity option
    Then Tact-Pin: I edit "<logSubjectName>" subject in "Log"
    And Tact-Pin: I "<logIsSave>" save new created
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<sfTaskTitle>" subject in "SFTask"
    And Tact-Pin: I "<sfTaskIsSave>" save new created
    And Contacts: I search this "Call" from "Contact" page and select it
    And Contacts: I make sure this "log" in Call Page
    And Contacts: I search this "Call" from "Lead" page and select it
    And Contacts: I make sure this "task" in Call Page
    And Contacts: I search this "task" from "Lead" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Task" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Task" from salesforce
    Then API: I verify activity "Log" is "saved" in salesforce
    When API: I delete activity "Log" from salesforce
    And Common: I click back icon
    And Contact: I search this "Lead" from recent field and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Lead" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete Object "Lead" from salesforce and wait for "10" sec

    Examples:
      | logSubjectName    | logIsSave | sfTaskTitle                | sfTaskIsSave |
      | loc_lead_call_log | yes       | loc_lead_call_SF_TaskAftLog | yes          |
