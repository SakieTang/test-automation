Feature: ContactsFeature
  This feature deals with the Contacts functionality of the application

  @P0
  @MobileTest
  @createContact
  Scenario Outline: Create a new contact in TactAPP with basic information w/o save in iphone, w/ send to SF, and verify in SF(API), then delete it from SF(API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I go to create a new "Contact" page
    When AddContact: I "<saveToIphone>" save to Phone and "<sendToSF>" send to Salesforce
    Then AddContact: I input a user name "<contactName>" and "<isSave>"
    And Contact: I search this "Contact" from recent field and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Contact" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete Object "Contact" from salesforce and wait for "10" sec

    Examples:
    | saveToIphone | sendToSF | contactName             | isSave |
    | don't        | do       | Umi, Singh              | yes    |
#    | do           | do       | contactName2            | no     |
#    | don't        | don't    | FirstN LastN            | yes    |

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

  @P1
  @MobileTest
  @SFNote
  Scenario Outline: Add SF Note to a contact w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN LastN" from recent field and select it
    Then Contacts: I search one user "FirstN LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
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

    Examples:
      | isSync | titleText         | bodyText | isSave |
      | do     | contact_SF_note |          | yes    |

  @P2
  @android
  @SFNoteAndroid
  Scenario Outline: Android only - Add SF Note to a contact w/ checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN LastN" from recent field and select it
    Then Contacts: I search one user "FirstN LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "note" from "Contact" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Note" is "saved" in salesforce
    And Contacts: I delete this Activity
    And API: I verify activity "Note" is "deleted" in salesforce

    Examples:
      | isSync | titleText           | bodyText | isSave |
      | do     | contact_SF_noteAndroid |          | yes    |

  @P1
  @LocNote
  Scenario Outline: Add Loc Note to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN LastN" from recent field and select it
    And Contacts: I search one user "FirstN LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created

    Examples:
      | isSync | titleText        | bodyText | isSave |
      | do not | contact_loc_note |          | yes    |

  @P2
  @MobileTest
  @SFNoteCall
  Scenario Outline: Add SF Note to a contact calling w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
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

    Examples:
      | isSync | titleText         | bodyText | isSave |
      | do     | contact_call_SF_note |          | yes    |

  @P2
  @MobileTest
  @iOS
  @LocNoteEmailIOS
  Scenario Outline:iOS only - Add local Note to a contact email w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Contacts: I click "email" icon
    And Email: I send an empty subject email
    When Saleflow: I click "Note" activity option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created

    Examples:
      | isSync | titleText              | bodyText | isSave |
      | do not | contact_email_loc_note |          | yes    |

  @P1
  @MobileTest
  @Log
  Scenario Outline: Add Log to a contact w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Log" option
    Then Tact-Pin: I create a new log with "<subjectOption>" with "<subject>" subject, "<Name>" name, "<relatedTo>" related to, "<dueDate>" due Date, "<Comments>" comments, "<priorityOption>" priority and "<statusOption>" status
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I click back icon after created Salesflow activities
    When Common: I switch to "Notebook" page from tab bar
    Then Notebook: I search this "log" from Notebook and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Log" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Log" from salesforce
    Then Notebook: I veirfy deleted "log" from Notebook page
    And Common: I click back icon

    Examples:
       | subjectOption | subject     | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | Send Quote    | contact_log | test      | w/o       | no      | testing  | High           | w/o          | yes    |

  @P2
  @android
  @logAndroid
  Scenario Outline: Android only - Add Log to a contact w/  checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Log" option
    Then Tact-Pin: I create a new log with "<subjectOption>" with "<subject>" subject, "<Name>" name, "<relatedTo>" related to, "<dueDate>" due Date, "<Comments>" comments, "<priorityOption>" priority and "<statusOption>" status
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "log" from "Contact" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Log" is "saved" in salesforce
    And Contacts: I delete this Activity
    And API: I verify activity "Log" is "deleted" in salesforce

    Examples:
       | subjectOption | subject            | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | w/o           | contact_logAndroid | w/o       | w/o       | no      | w/o      | w/o            | w/o          | yes    |

  @P1
  @MobileTest
  @logCallSFTask
  Scenario Outline: Add Log to a contact calling, then add SFTask. Verify them in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
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

    Examples:
       | logSubjectName   | logIsSave | sfTaskTitle                | sfTaskIsSave |
       | contact_call_log | yes       | contact_call_SF_TaskAftLog | yes          |

  @P1
  @MobileTest
  @iOS
  @logEmailSFTaskIOS
  Scenario Outline: iOS only - Add Log to a contact email, then add SFTask. Verify them in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
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

    Examples:
      | logSubjectName    | logIsSave | sfTaskTitle           | nameValue     |sfTaskIsSave |
      | contact_email_log | yes       | contact_email_SF_task | FirstN LastN  | yes          |

  @P1
  @MobileTest
  @LocTask
  Scenario Outline: Add a local Task to a contact and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN LastN" from recent field and select it
    Then Contacts: I search one user "FirstN LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    And Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name, "<relatedTo>" related to and "<dueDate>" due Date
    And Tact-Pin: I continue to edit iOS task "<isFollowUp>" followup-iOS, "<isReminder>" with "<reminderDate>" and "<reminderTime>" reminder-iOS
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "task" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | titleText    | description | Name | relatedTo | dueDate | isFollowUp | isReminder | reminderDate | reminderTime | isSave |
#       | w/o         | w/o         | w/o  | w/o       | Oct 3, 2018 | w/o        | w/o        | Oct 2, 2018  | 7:55 am      | w/o    |
       | contact_task | description | test | w/o       | 2       | no         | yes        | 1            | 7:55 am      | yes    |

  @P1
  @MobileTest
  @SFTask
  Scenario Outline: Add SFTask to a contact w/ checking sync to SF and verify it in SF(SPI), then delete fro SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    Then Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name and "<relatedTo>" related to,  "<isFollowUp>" followup-iOS and "<isReminder>" reminder-iOS
    And Tact-Pin: I edit Salesforce task with "<Comments>" comments, "<assignedTo>" assigned to, "<priorityOption>" priority and "<statusOption>" Status
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "task" from "Contact" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Task" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Task" from salesforce
    And Common: I click back icon

    Examples:
       | titleText       | description | Name | relatedTo | isFollowUp | isReminder | isSave | Comments | assignedTo | priorityOption | statusOption |
#       | w/o           | w/o                 | w/o         | w/o       | w/o        | w/o        | w/o    | w/o      | w/o        | w/o            | w/o          |
       | contact_SF_task | w/o         | w/o  | w/o       | no         | w/o        | yes    | w/o      | w/o        | w/o            | w/o          |

  @P1
  @MobileTest
  @LocTaskCall
  Scenario Outline: After call, add a local Task to a contact and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Contacts: I click "phone" icon
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<taskTitle>" subject in "Task"
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "task" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | taskTitle         | isSave |
       | contact_call_loc_task | yes    |

  @P1
  @MobileTest
  @iOS
  @LocTaskEmailIOS
  Scenario Outline: After email, add a local Task to a contact and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Contacts: I click "email" icon
    And Email: I send an empty subject email
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<taskTitle>" subject in "Task"
    And Tact-Pin: I "<isSave>" save new created
#    bug https://github.com/tactile/bugs/issues/5712
    And Contacts: I search this "task" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
      | taskTitle         | isSave |
      | contact_email_loc_task | yes    |

  @P1
  @LocEvent
    Scenario Outline: Add a local Event to a contact and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Event" option
    Then Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "<isAllDayEvent>" all-day event with "<startDate>" starts date at "<fromTime>" from time and "<endDate>" ends date at "<toTime>" to time, "<location>" location and "<description>" description
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "event" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | subjectOption     | subject           | isAllDayEvent | startDate | fromTime | endDate | toTime | location                                   | description | isSave |
#       | w/o               | true          | 10/10/2018   | w/o      | Jan 1, 2019  | w/o     | w/o                                        | w/o         | w/o    |
       | Send Letter/Quote | contact_Loc_event | false         | today     |          | 1       |        | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes    |

  @P1
  @SFEvent
    Scenario Outline: Add SFEvent to a contact w/ checking sync to SF and verify it in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    And Contacts: I search one user "LastN, FirstN" from contacts list and select it
    When Tact-Pin: I see a Tact pin icon display
    Then Tact-Pin: I click Tact pin icon and select "Event" option
    When Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "" all-day event with "" starts date at "" from time and "" ends date at "" to time, "" location and "" description
    Then Tact-Pin: I "<isSyncToSF>" sync to Salesforce event with "" name, "" related to, "" attendees and "" assigned to
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "event" from "Contact" page and select it
    When Common: I am waiting for syncing done
#    Then Common: I am waiting for "10" sec
    And API: I verify activity "Event" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Event" from salesforce
    And Contacts: I search this "event" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | subjectOption      | subject          | isAllDayEvent | startDate    | fromTime | endDate      | toTime  | location                                   | description | isSyncToSF | name | relatedToName | attendeesName | assignedToName | isSave |
#       | w/o                | w/o           | true          | 10/10/2018   | w/o      | Jan 1, 2019  | w/o     | w/o                                        | w/o         | w/o        | w/o  | w/o           | w/o           | w/o            | w/o    |
       | Send Letter/Quote  | contact_SF_event | false         | Oct 2, 2018  | 7:58 am  | 10/12/2019   | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes        |w/o   | w/o           | w/o           | w/o            | yes    |

  @P0
  @MobileTest
  @iOS
  @addLinkedInSalesNavigator
  Scenario: iOS - login linkedIn - sales navigator inside a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "Umi Singh" from recent field and select it
    Then Contacts: I search one user "Umi Singh" from contacts list and select it
    And Contacts: I click "linkedIn" icon
    When Common: I switch to "Webview" driver
    Then Settings: I sign in the LinkedIn account
    And Settings: I match the user "Umi Singh"

  @P2
  @MobileTest
  @iOS
  @checkLinkedIn
  Scenario: iOS - check the contact information in linkedin
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "Auto.Andr Tact" from recent field and select it
    Then Contacts: I search one user "Auto.Andr Tact" from contacts list and select it

  @P1
  @LocEventTesting
    Scenario Outline: Add a local Event to a contact and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Event" option
    Then Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "<isAllDayEvent>" all-day event with "<startDate>" starts date at "<fromTime>" from time and "<endDate>" ends date at "<toTime>" to time, "<location>" location and "<description>" description
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "event" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | subjectOption     | subject             | isAllDayEvent | startDate | fromTime | endDate | toTime  | location | description | isSave |
       | Send Letter/Quote | contact_Loc_meeting | false         | today     |          | today   |         |          |             | yes    |




#  @Test
#  Scenario: testing navigation in Android
#    When Common: I switch to "Settings" page from tab bar

#  @P0
#  @createDupAccount
#  Scenario Outline: Create a new contact in TactAPP with basic information and w/o save in iphone and SF
##    automation.tactSF@gmail.com/Tact0218
#    When Common: I switch to "Contacts" page from tab bar
#    Then Contacts: I go to create a new "Contact" page
#    And AddAccount: I create "<time>" times a "<optionUser>" and "<sendToSF>" send to Salesforce, with username "<accountName>" and "<isSave>"
#    And AddContact: I create "<time>" time a "<optionUser>" and "<saveToIphone>" save to Phone and "<sendToSF>" send to Salesforce, with username "<contactName>" and "<isSave>"
#
#    Examples:
#      | time | optionUser | saveToIphone | sendToSF | contactName | isSave |
#      | 1  | Contact    | don't        | do       | Dupluser, Tom   | yes    |