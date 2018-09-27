Feature: OpportunitiesFeature
  This feature deals with the opportunities functionality of the applicaiton

  @P0
  @MobileTest
  @createSimpleOpportunity
  Scenario Outline: Create a new opportunity in TactAPP with basic information and verify in SF(API)
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I go to create a new Oppty page
    When AddEditOppty: I do action "add" required oppty information with "<opptyName>" opptyName, "<closeDate>" closeDate, "<stage>" stage and "<probability>" probability
#    And AddEditOppty: I do action "add" more oppty information with "<isPrivate>" isPrivate, "<AccountType>" accountType, "<LeadSource>" leadSource, "<Amount>" amount and "<nextStep>" next step
#    And Contacts: I search one account "<accountName>" and select it
#    And AddEditOppty: I do action "add" DescriptionInfo with "<description>" description
    And AddOppty: I "<isSave>" save the new opportunity
    Then Oppty: I search one oppty "oppty_created_Tact" from opportunities list and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Opportunity" is "saved" in salesforce

    Examples:
    | opptyName          | closeDate | stage         | probability | isPrivate | accountName | AccountType  | LeadSource | Amount     | nextStep   | description    | isSave |
    | oppty_created_Tact | Today     | Qualification | 20        | false     | none        | New Customer | none       | 9999999999 | needToEdit | TextLength10 | true   |


  @P0
  @MobileTest
  @createOpportunity
  Scenario Outline: Create a new opportunity in TactAPP with basic information
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I go to create a new Oppty page
    When AddEditOppty: I do action "add" required oppty information with "<opptyName>" opptyName, "<closeDate>" closeDate, "<stage>" stage and "<probability>" probability
    And AddEditOppty: I do action "add" more oppty information with "<isPrivate>" isPrivate, "<AccountType>" accountType, "<LeadSource>" leadSource, "<Amount>" amount and "<nextStep>" next step
    And Contacts: I search one account "<accountName>" and select it in opportunity
    And AddEditOppty: I do action "add" Additional Information with "<orderNumber>" orderNumber, "<mainCompetitor>" mainCompetitor, "<currentGenerator>" currentGenerator, "<deliveryInstallationStatus>" deliveryInstallationStatus and "<trackingNum>" tracking number
    And AddEditOppty: I do action "add" DescriptionInfo with "<description>" description
#    And AddOppty: I add products
    And AddOppty: I "<isSave>" save the new opportunity
    When Common: I am waiting for syncing done
    Then API: I check Object "Opportunity" is "saved" in salesforce
#    And Common: I click back icon
#    When Oppty: I search one oppty "<opptyName>" from opportunities list and select it
#    Then AddOppty: I check the oppty value

    Examples:
    | opptyName                      | closeDate  | stage               | probability | isPrivate | accountName | AccountType                 | LeadSource | Amount           | nextStep      | orderNumber | mainCompetitor | currentGenerator | deliveryInstallationStatus | trackingNum  | description    | isSave |
#    | oppty 5-20-w/MaxTestField-Tact | Today      | Id. Decision Makers | null        | false     | none        | Existing Customer - Upgrade | Web        | TextLength16     | TextLength255 | TextLength8 | TextLength100  | TextLength100    | Yet to begin               | TextLength12 | TextLength8801 | true   |
    | oppty_created_Tact             | Today      | Closed Lost         | null        | false     | none        | New Customer                | Other      | 9999999999       | needToEdit    | TextLength8 | TextLength100  | TextLength100    | Completed                  | 999999999    | TextLength1000 | true   |
#    | oppty w/8700-TactPro    | Today      | Id. Decision Makers | null        | false     | none        | New Customer | Web        | 1234567890123456 | followUp | 12345678    | xyz            | xyz              | Yet to begin               | 123456789012 | 8700        | true   |
#    | oppty w/8000-TactPro    | Today      | Id. Decision Makers | null        | false     | none        | New Customer | Web        | 1234567890123456 | followUp | 12345678    | xyz            | xyz              | Yet to begin               | 123456789012 | 8000        | true   |
#    | oppty w/8799-TactPro    | Today      | Id. Decision Makers | null        | false     | none        | New Customer | Web        | 1234567890123456 | followUp | 12345678    | xyz            | xyz              | Yet to begin               | 123456789012 | 8799        | true   |

  @P1
  @MobileTest
  @editExistingOppty
  Scenario Outline: edit the existing opportunity in TactAPP with basic information
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_created_Tact" from opportunities list and select it
    When EditOppty: I start to edit
    Then AddEditOppty: I do action "edit" required oppty information with "<editOpptyName>" opptyName, "<editCloseDate>" closeDate, "<editStage>" stage and "<editProbability>" probability
    And AddEditOppty: I do action "edit" more oppty information with "<isPrivate>" isPrivate, "<AccountType>" accountType, "<LeadSource>" leadSource, "<Amount>" amount and "<nextStep>" next step
    And Contacts: I search one account "<accountName>" and select it in opportunity
    And AddEditOppty: I do action "edit" DescriptionInfo with "<description>" description
    And AddEditOppty: I do action "edit" Additional Information with "<orderNumber>" orderNumber, "<mainCompetitor>" mainCompetitor, "<currentGenerator>" currentGenerator, "<deliveryInstallationStatus>" deliveryInstallationStatus and "<trackingNum>" tracking number
    And AddOppty: I "<isSave>" save the new opportunity
    And Common: I click back icon
    Then Oppty: I search one oppty "oppty_edited_Tact" from opportunities list and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Opportunity" is "saved" in salesforce
#    And Common: I click back icon

    Examples:
    | editOpptyName   | editCloseDate | editStage           | editProbability | isPrivate | accountName | AccountType                 | LeadSource | Amount     | nextStep   | orderNumber | mainCompetitor | currentGenerator | deliveryInstallationStatus | trackingNum | description | isSave |
    | oppty_edit_Tact | Today         | Id. Decision Makers | none            | false     | none        | Existing Customer - Upgrade | Web        | 1111111111 | finishEdit | 8           | 100            | 100              | Yet to begin               | 9           | 1000        | true   |

  @P1
  @MobileTest
  @deleteExistingOppty
  Scenario: delete the existing opportunity from SF
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_created_Tact" from opportunities list and select it
    When Common: I am waiting for syncing done
    Then API: I check Object "Opportunity" is "saved" in salesforce
    And Common: I click back icon
    When API: I delete Object "Opportunity" from salesforce and wait for "5" sec


#Activities
  @P1
  @MobileTest
  @SFNote
  Scenario Outline: Add Note to an opportunity w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
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
      | isSync | titleText        | bodyText | isSave |
      | do     | opportunity_SF_note |          | yes    |

  @P2
  @android
  @SFNoteAndroid
  Scenario Outline: Android only - Add Note to an opportunity w/  checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
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
      | isSync | titleText               | bodyText | isSave |
      | do     | opportunity_SF_noteAndroid |          | yes    |

  @P1
  @LocNote
  Scenario Outline: Add Loc Note to an opportunity
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Note" option
    Then Tact-Pin: I create a new note "<isSync>" sync to SF, "<titleText>" title and "<bodyText>" body
    And Tact-Pin: I "<isSave>" save new created

    Examples:
      | isSync | titleText      | bodyText | isSave |
      | do not | oppty_loc_note |          | yes    |

  @P1
  @MobileTest
  @Log
  Scenario Outline: Add Log to an opportunity w/ checking sync to SF from Notebook page and verify in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
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
       | subjectOption | subject         | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | Send Quote    | opportunity_log | test      | w/o       | no      | testing  | High           | Not Started  | yes    |

  @P2
  @android
  @logAndroid
  Scenario Outline: Android only - Add Log to an opportunity w/  checking sync to SF from "Recent Activity" and verify in SF(API), then delete from client and checking from SF (API)
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
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
       | subjectOption | subject                | Name      | relatedTo | dueDate | Comments | priorityOption | statusOption | isSave |
#       | w/o           | w/o           | w/o       | w/o       | no      | w/o      | w/o            | w/o          | w/o    |
       | w/o           | opportunity_logAndroid | w/o       | w/o       | no      | w/o      | w/o            | w/o          | yes    |

  @P1
  @MobileTest
  @LocTask
  Scenario Outline: Add a local Task to an opportunity and delete from client
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Task" option
    And Tact-Pin: I create a new task with "<titleText>" title, "<description>" description, "<Name>" name, "<relatedTo>" related to and "<dueDate>" due Date
    And Tact-Pin: I continue to edit iOS task "<isFollowUp>" followup-iOS, "<isReminder>" with "<reminderDate>" and "<reminderTime>" reminder-iOS
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "task" from "Contact" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
       | titleText        | description         | Name        | relatedTo | dueDate     | isFollowUp | isReminder | reminderDate | reminderTime | isSave |
#       | w/o           | w/o                 | w/o         | w/o       | Oct 3, 2018 | w/o        | w/o        | Oct 2, 2018  | 7:55 am      | w/o    |
       | opportunity_task | description         | test        | w/o       | 10/2/2018   | no         | yes        | Oct 2, 2018  | 7:55 am      | yes    |

  @P1
  @MobileTest
  @SFTask
  Scenario Outline: Add SFTask to an opportunity w/ checking sync to SF and verify it in SF(SPI), then delete fro SF(API) w/o checking from client
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
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
       | titleText           | description | Name | relatedTo | isFollowUp | isReminder | isSave | Comments | assignedTo | priorityOption | statusOption |
#       | w/o           | w/o                 | w/o         | w/o       | w/o        | w/o        | w/o    | w/o      | w/o        | w/o            | w/o          |
       | opportunity_SF_task | w/o         | w/o  | w/o       | no         | w/o        | yes    | w/o      | w/o        | w/o            | w/o          |

  @P1
  @LocEvent
  Scenario Outline: Add a local Event to an opportunity and delete from client
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
    And Tact-Pin: I see a Tact pin icon display
    When Tact-Pin: I click Tact pin icon and select "Event" option
    Then Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "<isAllDayEvent>" all-day event with "<startDate>" starts date at "<fromTime>" from time and "<endDate>" ends date at "<toTime>" to time, "<location>" location and "<description>" description
    And Tact-Pin: I "<isSave>" save new created
    And Contacts: I search this "event" from "Lead" page and select it
    And Contacts: I delete this Activity
    And Contacts: I click back icon after created Salesflow activities

    Examples:
      | subjectOption     | subject           | isAllDayEvent | startDate    | fromTime | endDate      | toTime  | location                                   | description | isSave |
      | Send Letter/Quote | opportunity_Loc_event | false         | Oct 2, 2018  | 7:58 am  | 10/12/2019   | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes    |

  @P1
  @SFEvent
  Scenario Outline: Add SFEvent to an opportunity w/ checking sync to SF and verify it in SF(API), then delete from SF(API) w/o checking from client
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I search one oppty "oppty_TactName" from opportunities list and select it
    And Tact-Pin: I see a Tact pin icon display
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
      | subjectOption     | subject              | isSyncToSF | isSave |
      | Send Letter/Quote | opportunity_SF_event | yes        | yes    |
