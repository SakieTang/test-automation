Feature: OnboardingFeature
  This feature deals with the onboarding functionality of the applicaiton

  @smoke
  Scenario: onboarding with multi-user username and password
    Given Login: I click connect with SF button
    And Login-Webview: I enter the user email address
      | UserName | automation.tactSF@gmail.com   |
      | Password | Tact0218                      |
    And Login-Webview: I "do not" check remember me
    And Login-Webview: I click login button in "onboarding" process
    When Login-Webview: Login with existing user
#   There is step for the 1st time using SF login TactApp
    Then Login: Allow Tact to access the local Contact
    And Login: Waiting for Syncing finished in "onboarding" process
    And Login: Allow all access for Tact

  @P0
  @onboarding
  Scenario: onboarding existing app with single-user username and password
    Given Login: I click connect with SF button
    And Common: I switch to "Webview" driver
    And Login-Webview: I "do not" send usage to google chrome and "do not" sign in Chrome
    And Login-Webview: I enter the user email address
    And Login-Webview: I "do not" check remember me
    And Login-Webview: I click login button in "onboarding" process
    When Common: I switch to "Native_APP" driver
    Then Login: workaround for Android app onboarding workflow only
    When Login: Allow Tact to access salesforce user data
    Then Login: After SF connected, then Add Contacts to Tact
    And Login: Waiting for Syncing finished in "onboarding" process
    And Login: Allow all access for Tact

