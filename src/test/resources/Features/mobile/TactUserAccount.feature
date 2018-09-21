Feature: TactUserAccountFeature
  This feature deals with the User Account functionality of the applicaiton

  @P1
  @MobileTest
  @login
  Scenario: Login existing app with single-user username and password
    Given Login: I click connect with SF button
    And Common: I switch to "Webview" driver
    And Login-Webview: I "do not" send usage to google chrome and "do not" sign in Chrome
    And Login-Webview: I enter the user email address
    And Login-Webview: I "do not" check remember me
    And Login-Webview: I click login button in "login" process
    When Common: I switch to "Native_APP" driver
    And Login: Waiting for Syncing finished in "login" process

  @P1
  @MobileTest
  @login-Android
  Scenario: Login existing app with single-user username and password in Android
    Given Login: I click connect with SF button
    And Login: Waiting for Syncing finished in "login" process

  @P1
  @MobileTest
  @login-Sandbox
  Scenario: Login existing app with single-user Sandbox username and password
    Given Login: I switch to connect with "Sandbox" channel
    When Common: I switch to "Webview" driver
    And Login-Webview: I "do not" send usage to google chrome and "do not" sign in Chrome
    And Login-Webview: I setup the sandbox domain
    And Login-Webview: I enter the sandbox user email address and password
    When Common: I switch to "Native_APP" driver
    And Login: Waiting for Syncing finished in "login" process

  @P1
  @MobileTest
  @logout
  Scenario: Logout from current user
    When Common: I switch to "Settings" page from tab bar
    And More: I log out from the app

  @P0
  @MobileTest
  @deleteAccount
  Scenario: Delete account from TactAI app
    Given Common: I switch to "Settings" page from tab bar
    When Settings: I switch to "Account" option in settings page
    Then More: I delete current account from the app

  @P1
  @MobileTest
  @getAppVersion
  Scenario: Get Tact App Information
    Given Common: I switch to "Settings" page from tab bar
    Then More: I get App Version and "yes" save in file
    And More: I switch back to More page

  @P1
  @MobileTest
  @LearnMore
  Scenario: Check "Learn More" link
    Given Login: I click Learn More from login page
    When Common: I switch to "Webview" driver
    And Login-Webview: I "do not" send usage to google chrome and "do not" sign in Chrome
    Then Login-Webview: I check the learn more page title and back to welcome page
    When Common: I switch to "Native_APP" driver

  @P1
  @MobileTest
  @reauthExchange
  Scenario: re-auth exchange account during logout-login workflow
    When Common: I switch to "Settings" page from tab bar
    Then More: I log out from the app
    When Login: I click connect with SF button
    Then Common: I switch to "Webview" driver
    And Login-Webview: I "do not" send usage to google chrome and "do not" sign in Chrome
    And Login-Webview: I enter the user email address
    And Login-Webview: I "do not" check remember me
    And Login-Webview: I click login button in "login" process
    When Common: I switch to "Native_APP" driver
    Then Login: Waiting for Syncing finished in "login" process
