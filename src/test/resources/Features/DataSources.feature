Feature: DataSourcesFeature
  This feature deals with the Data Sources functionality of the applicaiton

  @P0
  @add
  @addExchange
  Scenario: Add Exchange dataSources in Tact
    Given Common: I switch to "Settings" page from tab bar
    When Settings: I switch to "Data Sources" option in settings page
    Then Settings: I switch to "Exchange" option in Sources settings page
    When Settings: I sign in the Exchange account
#    Then Settings: I switch to "Exchange" option in Sources settings page
#    And Settings: I disconnect the "Exchange" account

  @P0
  @add
  @addGmail
  Scenario: Add Gmail dataSources in Tact
    Given Common: I switch to "Settings" page from tab bar
    When Settings: I switch to "Data Sources" option in settings page
    Then Settings: I switch to "Gmail" option in Sources settings page
    When Common: I switch to "Webview" driver
    Then Settings: I sign in the Gmail account
#    Then Settings: I switch to "Gmail" option in Sources settings page
#    And Settings: I disconnect the "Gmail" account

  @P0
  @add
  @addLinkedIn
  Scenario: Add LinkedIn dataSources in Tac
    Given Common: I switch to "Settings" page from tab bar
    When Settings: I switch to "Data Sources" option in settings page
    Then Settings: I switch to "LinkedIn" option in Sources settings page
    When Settings: I sign in the LinkedIn account
#    Then Settings: I switch to "LinkedIn" option in Sources settings page
#    And Settings: I disconnect the "LinkedIn" account

#    11.3 + Creating new XCUITestDriver (v2.64.0) session

  @P0
  @delete
  @deleteExchange
  Scenario: Add Exchange dataSources in Tact
    Given Common: I switch to "Settings" page from tab bar
    When Settings: I switch to "Data Sources" option in settings page
    Then Settings: I switch to "Exchange" option in Sources settings page
    And Settings: I disconnect the "Exchange" account

  @P0
  @delete
  @deleteGmail
  Scenario: Add Gmail dataSources in Tact
    Given Common: I switch to "Settings" page from tab bar
    When Settings: I switch to "Data Sources" option in settings page
    Then Settings: I switch to "Gmail" option in Sources settings page
    And Settings: I disconnect the "Gmail" account

  @P0
  @delete
  @deleteLinkedIn
  Scenario: Add LinkedIn dataSources in Tac t
    Given Common: I switch to "Settings" page from tab bar
    When Settings: I switch to "Data Sources" option in settings page
    Then Settings: I switch to "LinkedIn" option in Sources settings page
    And Settings: I disconnect the "LinkedIn" account