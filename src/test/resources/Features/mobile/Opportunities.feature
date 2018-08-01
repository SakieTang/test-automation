Feature: OpportunitiesFeature
  This feature deals with the opportunities functionality of the applicaiton

  @P0
  @MobileTest
  @createSimpleOpportunity
  Scenario Outline: Create a new opportunity in TactAPP with basic information
    When Common: I switch to "Opportunities" page from tab bar
    Then Oppty: I go to create a new Oppty page
    When AddEditOppty: I do action "add" required oppty information with "<opptyName>" opptyName, "<closeDate>" closeDate, "<stage>" stage and "<probability>" probability
    And AddEditOppty: I do action "add" more oppty information with "<isPrivate>" isPrivate, "<AccountType>" accountType, "<LeadSource>" leadSource, "<Amount>" amount and "<nextStep>" next step
    And Contacts: I search one account "<accountName>" and select it
    And AddEditOppty: I do action "add" DescriptionInfo with "<description>" description
    And AddOppty: I "<isSave>" save the new opportunity
#    When Oppty: I search one oppty "<opptyName>" from opportunities list and select it
#    Then AddOppty: I check the oppty value


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
    And Contacts: I search one account "<accountName>" and select it
    And AddEditOppty: I do action "add" Additional Information with "<orderNumber>" orderNumber, "<mainCompetitor>" mainCompetitor, "<currentGenerator>" currentGenerator, "<deliveryInstallationStatus>" deliveryInstallationStatus and "<trackingNum>" tracking number
    And AddEditOppty: I do action "add" DescriptionInfo with "<description>" description
#    And AddOppty: I add products
    And AddOppty: I "<isSave>" save the new opportunity
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
    Then Oppty: I search one oppty oppty_created_Tact from opportunities list and select it
    When EditOppty: I start to edit
    Then AddEditOppty: I do action "edit" required oppty information with "<editOpptyName>" opptyName, "<editCloseDate>" closeDate, "<editStage>" stage and "<editProbability>" probability
    And AddEditOppty: I do action "edit" more oppty information with "<isPrivate>" isPrivate, "<AccountType>" accountType, "<LeadSource>" leadSource, "<Amount>" amount and "<nextStep>" next step
    And Contacts: I search one account "<accountName>" and select it
    And AddEditOppty: I do action "edit" DescriptionInfo with "<description>" description
    And AddEditOppty: I do action "edit" Additional Information with "<orderNumber>" orderNumber, "<mainCompetitor>" mainCompetitor, "<currentGenerator>" currentGenerator, "<deliveryInstallationStatus>" deliveryInstallationStatus and "<trackingNum>" tracking number
    And AddOppty: I "<isSave>" save the new opportunity

    Examples:
    | editOpptyName   | editCloseDate | editStage           | editProbability | isPrivate | accountName | AccountType                 | LeadSource | Amount     | nextStep   | orderNumber | mainCompetitor | currentGenerator | deliveryInstallationStatus | trackingNum | description | isSave |
    | oppty_edit_Tact | Today         | Id. Decision Makers | none            | false     | none        | Existing Customer - Upgrade | Web        | 1111111111 | finishEdit | 8           | 100            | 100              | Yet to begin               | 9           | 1000        | true   |