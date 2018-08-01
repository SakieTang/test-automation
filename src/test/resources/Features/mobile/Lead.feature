Feature: ContactsFeature
  This feature deals with the Lead functionality of the applicaiton

  @P0
  @createLead
  Scenario Outline: Create a new contact in TactAPP with basic information and w/o save in iphone and SF
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I go to create a new "Lead" page
#    When AddContact: I "<saveToIphone>" save to Phone and "<sendToSF>" send to Salesforce
#    And AddContact: I input a user name "<contactName>" and "<isSave>"

    Examples:
      | saveToIphone | sendToSF | contactName             | isSave |
  #    | don't        | don't    | DemoLName, DemoFName    | w/o    |
      | do           | do       | contactName2            | yes     |
#    | don't        | don't    | FirstN,LastN            | yes    |

  @P1
  @note
  Scenario Outline: Add Note to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN,LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created
    And Common: I click back icon

    Examples:
      | isSync | titleText | bodyText | isSave |
      | w/o    | testing   |          | yes    |

  @P1
  @Task
  Scenario Outline: Add Task to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN,LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    And Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name, "<relatedTo>" related to and "<dueDate>" due Date
    And Tact-Pin: I continue to edit iOS task "<isFollowUp>" followup-iOS, "<isReminder>" with "<reminderDate>" and "<reminderTime>" reminder-iOS
    And Tact-Pin: I "<isSave>" save new created

    Examples:
       | titleText     | description         | Name        | relatedTo | dueDate     | isFollowUp | isReminder | reminderDate | reminderTime | isSave |
#       | w/o           | w/o                 | w/o         | w/o       | Oct 3, 2018 | w/o        | w/o        | Oct 2, 2018  | 7:55 am      | w/o    |
       | testing       | description         | test        | w/o       | 10/2/2018   | no         | yes        | Oct 2, 2018  | 7:55 am      | w/o    |

  @P1
  @SFTask
  Scenario Outline: Add SFTask to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN,LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    Then Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name and "<relatedTo>" related to,  "<isFollowUp>" followup-iOS and "<isReminder>" reminder-iOS
    And Tact-Pin: I edit Salesforce task with "<Comments>" comments, "<assignedTo>" assigned to, "<priorityOption>" priority and "<statusOption>" Status
    And Tact-Pin: I "<isSave>" save new created

    Examples:
       | titleText     | description         | Name        | relatedTo | isFollowUp | isReminder | isSave | Comments | assignedTo | priorityOption | statusOption |
#       | w/o           | w/o                 | w/o         | w/o       | w/o        | w/o        | w/o    | w/o      | w/o        | w/o            | w/o          |
       | testing       | description         | test        | w/o       | no         | yes        | yes    | testing  | w/o        | High           | Not Started  |

  @P1
  @Log
  Scenario Outline: Add Log to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN,LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Log" option
    Then Tact-Pin: I create a new log with "<subjectOption>" with "<subject>" subject, "<Name>" name, "<relatedTo>" related to, "<dueDate>" due Date, "<Comments>" comments, "<priorityOption>" priority and "<statusOption>" status
    And Tact-Pin: I "<isSave>" save new created

    Examples:
       | subjectOption | subject       | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | Send Quote    | test          | test      | w/o       | no      | testing  | High           | Not Started  | yes    |

  @P1
  @Event
    Scenario Outline: Add Event to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN,LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Event" option
    Then Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "<isAllDayEvent>" all-day event with "<startDate>" starts date at "<fromTime>" from time and "<endDate>" ends date at "<toTime>" to time, "<location>" location and "<description>" description
    And Tact-Pin: I "<isSyncToSF>" sync to Salesforce event with "<name>" name, "<relatedToName>" related to, "<attendeesName>" attendees and "<assignedToName>" assigned to
    And Tact-Pin: I "<isSave>" save new created

    Examples:
       | subjectOption      | subject       | isAllDayEvent | startDate    | fromTime | endDate      | toTime  | location                                   | description | isSyncToSF | name | relatedToName | attendeesName | assignedToName | isSave |
#       | w/o                | w/o           | true          | 10/10/2018   | w/o      | Jan 1, 2019  | w/o     | w/o                                        | w/o         | w/o        | w/o  | w/o           | w/o           | w/o            | w/o    |
       | Send Letter/Quote  | test          | false         | Oct 2, 2018  | 7:58 am  | 12/12/2019   | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes        |w/o   | w/o           | w/o           | w/o            | yes    |

  @P0
  @linkedIn
  Scenario: login linkedIn inside a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "Tim Barr" from contacts list and select it
    And Contacts: I click "Connect LinkedIn" action in contact obj page
    When Settings: I sign in the LinkedIn account
    Then Settings: I back to Settings page from Sources page
    Then Contacts: I back to Contacts Main page from "Contact" page
#    getGoBackToContactsMainPageButton


  @Test
  Scenario: testing navigation in Android
    When Common: I switch to "Settings" page from tab bar