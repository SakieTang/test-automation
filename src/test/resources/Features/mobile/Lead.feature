Feature: LeadsFeature
  This feature deals with the Lead functionality of the applicaiton

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
    When API: I delete Object "Lead" from salesforce and wait for "60" sec
    Then Contact: I verity deleted "Lead" from search field

    Examples:
      | leadName              | companyName | isSave |
#      | LeadLName, LeadFName  | Tactile     | w/o    |
#      | leadName2             | United Oil  | yes    |
      | LastNLead, FirstNLead | Tactile     | yes    |

  @P1
  @note
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
      | do     | lead_note |          | yes    |

  @P2
  @android
  @noteAndroid
  Scenario Outline: Add Note to a lead w/ checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and verify it's deleted from SF (API)
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
      | isSync | titleText | bodyText | isSave |
      | do     | lead_note |          | yes    |

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
       | subjectOption | subject       | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | Send Quote    | lead_log      | test      | w/o       | no      | testing  | High           | Not Started  | yes    |

  @P2
  @android
  @logAndroid
  Scenario Outline: Add Log to a lead w/ checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
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
       | subjectOption | subject       | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | w/o           | lead_log      | w/o       | w/o       | no      | w/o      | w/o            | w/o          | yes    |

  @P1
  @Task
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
       | titleText     | description         | Name        | relatedTo | dueDate     | isFollowUp | isReminder | reminderDate | reminderTime | isSave |
#       | w/o           | w/o                 | w/o         | w/o       | Oct 3, 2018 | w/o        | w/o        | Oct 2, 2018  | 7:55 am      | w/o    |
       | lead_task     | description         | test        | w/o       | 10/2/2018   | no         | yes        | Oct 2, 2018  | 7:55 am      | yes    |

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
       | titleText     | description | Name | relatedTo | isFollowUp | isReminder | isSave | Comments | assignedTo | priorityOption | statusOption |
#       | w/o           | w/o         | w/o  | w/o       | w/o        | w/o        | w/o    | w/o      | w/o        | w/o            | w/o          |
       | lead_SF_task  | w/o         | w/o  | w/o       | no         | w/o        | yes    | w/o      | w/o        | w/o            | w/o          |


  @P1
  @Event
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
#       | w/o               | true          | 10/10/2018   | w/o      | Jan 1, 2019  | w/o     | w/o                                        | w/o         | w/o    |
       | Send Letter/Quote | lead_event | false         | Oct 2, 2018  | 7:58 am  | 10/12/2019   | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes    |

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
       | subjectOption      | subject    | isAllDayEvent | startDate    | fromTime | endDate      | toTime  | location                                   | description | isSyncToSF | name | relatedToName | attendeesName | assignedToName | isSave |
#       | w/o                | w/o           | true          | 10/10/2018   | w/o      | Jan 1, 2019  | w/o     | w/o                                        | w/o         | w/o        | w/o  | w/o           | w/o           | w/o            | w/o    |
       | Send Letter/Quote  | lead_event | false         | Oct 2, 2018  | 7:58 am  | 10/12/2019   | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes        |w/o   | w/o           | w/o           | w/o            | yes    |

  @P2
  @linkedIn
  Scenario: login linkedIn inside a lead
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "Tim Barr" from recent field and select it
    Then Contacts: I search one user "Tim Barr" from contacts list and select it
    And Contacts: I click "Connect LinkedIn" action in contact obj page
    When Settings: I sign in the LinkedIn account
    Then Settings: I back to Settings page from Sources page
    Then Contacts: I back to Contacts Main page from "Contact" page
#    getGoBackToContactsMainPageButton


  @Test
  Scenario: testing navigation in Android
    When Common: I switch to "Settings" page from tab bar