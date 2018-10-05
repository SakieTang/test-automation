Feature: LocContactsFeature
  This feature deals with the new loc Contacts functionality of the application

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
      | saveToIphone | sendToSF | contactName | phoneNumber  | emailValue | isSave |
#      | don't        | don't    | Umi, Singh  | randomNumber | @gmail.com | yes    |
      | do        | don't    | Umi, Singh  | randomNumber | @gmail.com | yes    |

  @P1
  @MobileTest
  @logCallSFTask
  Scenario Outline: Add Log to a loc contact calling, then add SFTask. Verify under the recent activity of contact details, show in the head of call and them (log, task, contact) in SF(API), then delete from SF(API) w/o checking from client
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
      | logSubjectName       | logIsSave | sfTaskTitle                    | sfTaskIsSave |
      | loc_contact_call_log | yes       | loc_contact_call_SF_TaskAftLog | yes          |

  @P1
  @MobileTest
  @iOS
  @logEmailSFTask
  Scenario Outline: iOS only - Add Log to a loc contact email, then add SFTask. Verify under the recent activity of contact details, show in the head of email and them (log, task, contact) in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "contact" from recent field and select it
    Then Contacts: I search one user "contact" from contacts list and select it
    And Contacts: I click "email" icon
    And Email: I send an empty subject email
    When Saleflow: I click "Log" activity option
    Then Tact-Pin: I edit "<logSubjectName>" subject in "Log"
    And Contacts: I search one "Contacts" with "<nameValue>" and select it in name
    And Tact-Pin: I "<logIsSave>" save new created
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<sfTaskTitle>" subject in "SFTask"
    And Contacts: I search one "Contacts" with "<nameValue>" and select it in name
    And Tact-Pin: I "<sfTaskIsSave>" save new created
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
      | logSubjectName        | logIsSave | sfTaskTitle               | nameValue    |sfTaskIsSave |
      | loc_contact_email_log | yes       | loc_contact_email_SF_task | FirstN LastN | yes         |

  @P2
  @MobileTest
  @SFNoteCall
  Scenario Outline: Add SF Note to a loc contact calling w/ checking sync to SF from Notebook page and verify them (note, contact) in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "contact" from recent field and select it
    Then Contacts: I search one user "contact" from contacts list and select it
    And Contacts: I click "phone" icon
    When Saleflow: I click "Note" activity option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I click back icon after created Salesflow activities
    When Common: I switch to "Notebook" page from tab bar
    Then Notebook: I search this "note" from Notebook and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Note" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Note" from salesforce
    Then Notebook: I veirfy deleted "note" from Notebook page
    And Common: I click back icon
    When Common: I switch to "Contacts" page from tab bar
    And Contact: I search this "Contact" from recent field and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Contact" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete Object "Contact" from salesforce and wait for "10" sec

    Examples:
      | isSync | titleText                | bodyText | isSave |
      | do     | loc_contact_call_SF_note |          | yes    |

  @P2
  @MobileTest
  @SFNoteEmail
  Scenario Outline: iOS only - Add SF Note to a loc contact email w/ checking sync to SF from Notebook page and verify them (note, contact) in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "contact" from recent field and select it
    Then Contacts: I search one user "contact" from contacts list and select it
    And Contacts: I click "email" icon
    And Email: I send an empty subject email
    When Saleflow: I click "Note" activity option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I click back icon after created Salesflow activities
    When Common: I switch to "Notebook" page from tab bar
    Then Notebook: I search this "note" from Notebook and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Note" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Note" from salesforce
    Then Notebook: I veirfy deleted "note" from Notebook page
    And Common: I click back icon
    When Common: I switch to "Contacts" page from tab bar
    And Contact: I search this "Contact" from recent field and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Contact" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete Object "Contact" from salesforce and wait for "10" sec

    Examples:
      | isSync | titleText              | bodyText | isSave |
      | do     | loc_contact_email_loc_note |          | yes    |