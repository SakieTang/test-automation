Feature: LeadsFeature
  This feature deals with the Lead functionality of the application

  @P0
  @createLead
  Scenario Outline: Create a new lead in TactAPP with basic information and verify in SF(API), then delete from SF(API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I go to create a new "Lead" page
    And AddLead: I input a user name "<leadName>", company name "<companyName>" and "<isSave>"
    And Contact: I search this "Lead" from recent field and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Lead" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete Object "Lead" from salesforce and wait for "5" sec
#    When API: I delete Object "Lead" from salesforce and wait for "60" sec
#    Then Contact: I verity deleted "Lead" from search field  bug2000(https://github.com/tactile/broken-droid/issues/2000)

    Examples:
      | leadName              | companyName | isSave |
      | LastNLead, FirstNLead | Tactile     | yes    |

  @P1
  @SFNote
  Scenario Outline: Add Note to a lead w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    And Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
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
      | isSync | titleText | bodyText | isSave |
      | do     | lead_SF_note |          | yes    |

  @P2
  @android
  @SFNoteAndroid
  Scenario Outline: Android only - Add Note to a lead w/ checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and verify it's deleted from SF (API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    And Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "note" from "Lead" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Note" is "saved" in salesforce
    And Contacts: I delete this Activity
    And API: I verify activity "Note" is "deleted" in salesforce

    Examples:
      | isSync | titleText        | bodyText | isSave |
      | do     | lead_SF_noteAndroid |          | yes    |

  @P1
  @LocNote
  Scenario Outline: Add Loc Note to a lead
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    And Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created

    Examples:
      | isSync | titleText     | bodyText | isSave |
      | do not | lead_loc_note |          | yes    |

  @P2
  @MobileTest
  @SFNoteCall
  Scenario Outline: Add SF Note to a lead calling w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    And Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
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
      | do     | lead_call_SF_note |          | yes    |

  @P2
  @MobileTest
  @iOS
  @LocNoteEmailIOS
  Scenario Outline: iOS only - Add local Note to a lead email w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    Then Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Contacts: I click "email" icon
    And Email: I send an empty subject email
    When Saleflow: I click "Note" activity option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created

    Examples:
      | isSync | titleText           | bodyText | isSave |
      | do not | lead_email_loc_note |          | yes    |

  @P1
  @Log
  Scenario Outline: Add Log to a lead w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstNLead LastNLead" from recent field and select it
    Then Contacts: I search one user "FirstNLead LastNLead" from contacts list and select it
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
       | subjectOption | subject  | Name | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
       | Send Quote    | lead_log | test | w/o       | no      | testing  | High           | Not Started  | yes    |

  @P2
  @android
  @logAndroid
  Scenario Outline: Android only - Add Log to a lead w/ checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    And Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Log" option
    Then Tact-Pin: I create a new log with "<subjectOption>" with "<subject>" subject, "<Name>" name, "<relatedTo>" related to, "<dueDate>" due Date, "<Comments>" comments, "<priorityOption>" priority and "<statusOption>" status
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "log" from "Lead" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Log" is "saved" in salesforce
    And Contacts: I delete this Activity
    And API: I verify activity "Log" is "deleted" in salesforce

    Examples:
       | subjectOption | subject         | Name | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
       | w/o           | lead_logAndroid | w/o   | w/o      | no      | w/o      | w/o            | w/o          | yes    |

  @P1
  @MobileTest
  @logCallSFTask
  Scenario Outline: Add Log to a lead calling, then add SFTask. Verify them in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    And Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Contacts: I click "phone" icon
    When Saleflow: I click "Log" activity option
    Then Tact-Pin: I edit "<logSubjectName>" subject in "Log"
    And Tact-Pin: I "<logIsSave>" save new created
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<sfTaskTitle>" subject in "SFTask"
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
      | logSubjectName | logIsSave | sfTaskTitle       | sfTaskIsSave |
      | lead_call_log  | yes       | lead_call_SF_task | yes          |

  @P1
  @MobileTest
  @iOS
  @logEmailSFTaskIOS
  Scenario Outline: iOS only - Add Log to a lead email, then add SFTask. Verify them in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    Then Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Contacts: I click "email" icon
    And Email: I send an empty subject email
    When Saleflow: I click "Log" activity option
    Then Tact-Pin: I edit "<logSubjectName>" subject in "Log"
#    And Contacts: I search one "Contacts" with "<nameValue>" and select it in name
    And Tact-Pin: I "<logIsSave>" save new created
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<sfTaskTitle>" subject in "SFTask"
#    And Contacts: I search one "Contacts" with "<nameValue>" and select it in name
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
      | logSubjectName | logIsSave | sfTaskTitle        | nameValue     |sfTaskIsSave |
      | lead_email_log | yes       | lead_email_SF_task | FirstN LastN  | yes          |

  @P1
  @LocTask
  Scenario Outline: Add a local Task to a lead and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    Then Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    And Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name, "<relatedTo>" related to and "<dueDate>" due Date
    And Tact-Pin: I continue to edit iOS task "<isFollowUp>" followup-iOS, "<isReminder>" with "<reminderDate>" and "<reminderTime>" reminder-iOS
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "task" from "Lead" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | titleText | description | Name | relatedTo | dueDate   | isFollowUp | isReminder | reminderDate | reminderTime | isSave |
       | lead_task | description | test | w/o       | 10/2/2018 | no         | yes        | Oct 2, 2018  | 7:55 am      | yes    |

  @P1
  @SFTask
  Scenario Outline: Add SFTask to a lead w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    Then Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    Then Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name and "<relatedTo>" related to,  "<isFollowUp>" followup-iOS and "<isReminder>" reminder-iOS
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "task" from "Lead" page and select it
    When Common: I am waiting for syncing done
    Then API: I verify activity "Task" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Task" from salesforce
    And Common: I click back icon

    Examples:
       | titleText    | description | Name | relatedTo | isFollowUp | isReminder | isSave |
       | lead_SF_task | w/o         | w/o  | w/o       | no         | w/o        | yes    |

  @P1
  @MobileTest
  @LocTaskCall
  Scenario Outline: Add a local Task to a lead and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastNLead, FirstNLead" from recent field and select it
    Then Contacts: I search one user "LastNLead, FirstNLead" from contacts list and select it
    And Contacts: I click "phone" icon
    When Saleflow: I click "Task" activity option
    Then Tact-Pin: I edit "<taskTitle>" subject in "Task"
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "task" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
      | taskTitle      | isSave |
      | lead_call_loc_task | yes    |

  @P1
  @LocEvent
    Scenario Outline: Add a local Event to a lead and delete from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstNLead LastNLead" from recent field and select it
    Then Contacts: I search one user "FirstNLead LastNLead" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Event" option
    Then Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "<isAllDayEvent>" all-day event with "<startDate>" starts date at "<fromTime>" from time and "<endDate>" ends date at "<toTime>" to time, "<location>" location and "<description>" description
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "event" from "Lead" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | subjectOption     | subject    | isAllDayEvent | startDate    | fromTime | endDate      | toTime  | location                                   | description | isSave |
       | Send Letter/Quote | lead_Loc_event | false         | Oct 2, 2018  | 7:58 am  | 10/12/2019   | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes    |

  @P1
  @SFEvent
    Scenario Outline: Add SFEvent to a lead w/ checking sync to SF and verify it in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstNLead LastNLead" from recent field and select it
    And Contacts: I search one user "FirstNLead LastNLead" from contacts list and select it
    When Tact-Pin: I see a Tact pin icon display
    Then Tact-Pin: I click Tact pin icon and select "Event" option
    When Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "" all-day event with "" starts date at "" from time and "" ends date at "" to time, "" location and "" description
    Then Tact-Pin: I "<isSyncToSF>" sync to Salesforce event with "" name, "" related to, "" attendees and "" assigned to
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "event" from "Lead" page and select it
    When Common: I am waiting for syncing done
    And API: I verify activity "Event" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Event" from salesforce
    And Contacts: I search this "event" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | subjectOption     | subject    | isSyncToSF | isSave |
       | Send Letter/Quote | lead_SF_event | yes        | yes    |

