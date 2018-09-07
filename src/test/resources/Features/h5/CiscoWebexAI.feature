Feature: Assistant - Cisco Spark
  This feature deals with the Assistant AI functionality of the applicaiton

  @P0
  @loginCiscoWebex
  Scenario: open browser and login Thread
    Given WebCommon: I launch browser and open "teams.webex.com/signin"
    When WebCommon: I login with "Cisco Spark" user account "w/o" and password "w/o"
    Then SparkCommon: I switch to "Home" from navigate
    And SparkHome: I click "TactProdBot" option

  @P1
  @CiscoWebexAIBasicTesting
  Scenario Outline: Sent msg to AI, and verify the reply
    When SparkHome: I send "<inputText>" to "Cisco-Webex" Assistant and "<isVerify>" verify sent msg
    Then SparkHome: I check bot "<responseText>"
#    And WebCommon: I close driver

    Examples:
      | inputText                              | isVerify | responseText                                                                      |
#      | What is the latest on xyz deals?       | without  |                                                                                   |
      | Hi                                     | with     | Howdy.; Hi there! How can I help?; Hello! Glad to help.; Welcome.; Hi.; Hi there! |
      | What is the latest on GenePoint deals? | without  |                                                                                   |
      | Bye                                    | with     | Goodbye.; Bye; See you later!                                                     |
      | Hi                                     | with     | Howdy.; Hi there! How can I help?; Hello! Glad to help.; Welcome.; Hi.; Hi there! |
      | Thanks                                 | with     | Thanks.; Thank you.                                                               |

  @P3
  Scenario: testing my cases
    Given WebCommon: I testing this msg
    When WebCommon: I launch browser and open "Thread"