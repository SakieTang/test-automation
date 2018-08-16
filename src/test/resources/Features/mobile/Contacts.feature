Feature: ContactsFeature
  This feature deals with the Contacts functionality of the applicaiton

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
    When API: I delete Object "Contact" from salesforce and wait for "60" sec

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
  @note
  Scenario Outline: Add Note to a contact w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
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
    Then API: I check activity "Note" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Note" from salesforce
    Then Notebook: I veirfy deleted "note" from Notebook page
    And Common: I click back icon

    Examples:
      | isSync | titleText      | bodyText | isSave |
      | do     | contact_note   |          | yes    |

  @P2
  @android
  @noteAndroid
  Scenario Outline: Add Note to a contact w/  checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN LastN" from recent field and select it
    Then Contacts: I search one user "FirstN LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "note" from "Contact" page and select it
    When Common: I am waiting for syncing done
    Then API: I check activity "Note" is "saved" in salesforce
    And Contacts: I delete this Activity
    And API: I check activity "Note" is "deleted" in salesforce

    Examples:
      | isSync | titleText      | bodyText | isSave |
      | do     | contact_note   |          | yes    |

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
    Then API: I check activity "Log" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete activity "Log" from salesforce
    Then Notebook: I veirfy deleted "log" from Notebook page
    And Common: I click back icon

    Examples:
       | subjectOption | subject       | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | Send Quote    | contact_log   | test      | w/o       | no      | testing  | High           | Not Started  | yes    |

  @P2
  @android
  @logAndroid
  Scenario Outline: Add Log to a contact w/  checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Log" option
    Then Tact-Pin: I create a new log with "<subjectOption>" with "<subject>" subject, "<Name>" name, "<relatedTo>" related to, "<dueDate>" due Date, "<Comments>" comments, "<priorityOption>" priority and "<statusOption>" status
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "log" from "Contact" page and select it
    When Common: I am waiting for syncing done
    Then API: I check activity "Log" is "saved" in salesforce
    And Contacts: I delete this Activity
    And API: I check activity "Log" is "deleted" in salesforce

    Examples:
       | subjectOption | subject       | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | w/o           | contact_log   | w/o       | w/o       | no      | w/o      | w/o            | w/o          | yes    |
    
  @P1
  @MobileTest
  @Task
  Scenario Outline: Add Task to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN LastN" from recent field and select it
    Then Contacts: I search one user "FirstN LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    And Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name, "<relatedTo>" related to and "<dueDate>" due Date
    And Tact-Pin: I continue to edit iOS task "<isFollowUp>" followup-iOS, "<isReminder>" with "<reminderDate>" and "<reminderTime>" reminder-iOS
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | titleText     | description         | Name        | relatedTo | dueDate     | isFollowUp | isReminder | reminderDate | reminderTime | isSave |
#       | w/o           | w/o                 | w/o         | w/o       | Oct 3, 2018 | w/o        | w/o        | Oct 2, 2018  | 7:55 am      | w/o    |
       | testing       | description         | test        | w/o       | 10/2/2018   | no         | yes        | Oct 2, 2018  | 7:55 am      | w/o    |

  @P1
  @MobileTest
  @SFTask
  Scenario Outline: Add SFTask to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "LastN, FirstN" from recent field and select it
    Then Contacts: I search one user "LastN, FirstN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    Then Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name and "<relatedTo>" related to,  "<isFollowUp>" followup-iOS and "<isReminder>" reminder-iOS
    And Tact-Pin: I edit Salesforce task with "<Comments>" comments, "<assignedTo>" assigned to, "<priorityOption>" priority and "<statusOption>" Status
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | titleText     | description         | Name        | relatedTo | isFollowUp | isReminder | isSave | Comments | assignedTo | priorityOption | statusOption |
#       | w/o           | w/o                 | w/o         | w/o       | w/o        | w/o        | w/o    | w/o      | w/o        | w/o            | w/o          |
       | testing       | description         | test        | w/o       | no         | yes        | yes    | testing  | w/o        | High           | Not Started  |

  @P1
  @MobileTest
  @Event
    Scenario Outline: Add Event to a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "FirstN LastN" from recent field and select it
    Then Contacts: I search one user "FirstN LastN" from contacts list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Event" option
    Then Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "<isAllDayEvent>" all-day event with "<startDate>" starts date at "<fromTime>" from time and "<endDate>" ends date at "<toTime>" to time, "<location>" location and "<description>" description
    And Tact-Pin: I "<isSyncToSF>" sync to Salesforce event with "<name>" name, "<relatedToName>" related to, "<attendeesName>" attendees and "<assignedToName>" assigned to
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | subjectOption      | subject       | isAllDayEvent | startDate    | fromTime | endDate      | toTime  | location                                   | description | isSyncToSF | name | relatedToName | attendeesName | assignedToName | isSave |
#       | w/o                | w/o           | true          | 10/10/2018   | w/o      | Jan 1, 2019  | w/o     | w/o                                        | w/o         | w/o        | w/o  | w/o           | w/o           | w/o            | w/o    |
       | Send Letter/Quote  | test          | false         | Oct 2, 2018  | 7:58 am  | 12/12/2019   | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes        |w/o   | w/o           | w/o           | w/o            | yes    |

  @P0
  @MobileTest
  @addLinkedInSalesNavigator
  Scenario: login linkedIn - sales navigator inside a contact
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "Auto.Andr Tact" from recent field and select it
    Then Contacts: I search one user "Auto.Andr Tact" from contacts list and select it
    And Contacts: I click "Connect LinkedIn" action in contact obj page
    When Common: I switch to "Webview" driver
    Then Settings: I sign in the LinkedIn account
    And Settings: I match the user "Auto.Andr Tact"


  @P2
  @MobileTest
  @checkLinkedIn
  Scenario: check the contact information in linkedin
    When Common: I switch to "Contacts" page from tab bar
    Then Contacts: I search one user "Auto.Andr Tact" from recent field and select it
    Then Contacts: I search one user "Auto.Andr Tact" from contacts list and select it

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