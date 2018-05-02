Feature: Assistant - Tact proto 2
  This feature deals with the Assistant AI functionality of the applicaiton

  @P0
  @MobileTest
  @login
  Scenario: Login existing app with single-user username and password
    Given Login: I click connect with SF button
    And Common: I switch to "Webview" driver
    And Login-Webview: I "do not" send usage to google chrome and "do not" sign in Chrome
    And Login-Webview: I enter the user email address with dataTable
       | automation.AI.tactsf.s@gmail.com | Tact0218 |
    And Login-Webview: I "do not" check remember me
    And Login-Webview: I click login button in "login" process
    When Common: I switch to "Native_APP" driver
    And Login: Waiting for Syncing finished in "login" process

  @P1
  @TactAIBasicTesting
  Scenario Outline: Sent msg to AI, and verify the reply
    Given Common: I switch to "Assistant" page from tab bar
    When Assistant: I send "<inputText>" to "Tact" Assistant and "<isVerify>" verify sent msg
    Then Assistant: I check bot "<responseText>"

    Examples:
       | inputText                              | isVerify | responseText                                                               |
       | What is the latest on xyz deals?       | without  |                                                                            |
       | What is the latest on GenePoint deals? | without  |                                                                            |
       | Bye                                    | with     | Goodbye.; Bye; See you later!                                              |
       | Hi                                     | with     | Howdy.; Hi there! How can I help?; Hello! Glad to help.; Welcome.; Hi.     |
       | Thanks                                 | with     | Thanks.; Thank you.                                                        |
       | Report a bug                           | with     | Please describe the problem you're facing or improvement you'd like to see.|
       | Cancel                                 | with     | Bug cancelled.                                                             |


  @P2
  @offWifiErrorMsg
  Scenario Outline: Sent msg before wifi is off
    Given Common: I switch to "Assistant" page from tab bar
    When Common: I turn on wifi
    Then Assistant: I send "<inputText>" to Assistant, and then disconnect with wifi and with verify sent msg
    And Assistant: I check the un-deliver error msg
    And Common: I turn on wifi

    Examples:
       | inputText      |
       | Bye            |