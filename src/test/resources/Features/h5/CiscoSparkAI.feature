Feature: Assistant - Cisco Spark
  This feature deals with the Assistant AI functionality of the applicaiton

  @P0
  @loginCiscoSpark
  Scenario: open browser and login Thread
    Given WebCommon: I launch browser and open "teams.webex.com/signin"
    When WebCommon: I login with "Cisco Spark" user account and password
    Then SparkCommon: I switch to "Home" from navigate

  @P1
  @CiscoSparkAIBasicTesting
  Scenario Outline: Sent msg to AI, and verify the reply
    And SparkHome: I click "TactProdBot" option
    When SparkHome: I send "<inputText>" to "Spark" Assistant and "<isVerify>" verify sent msg
    Then SparkHome: I check bot "<responseText>"

    Examples:
      | inputText                              | isVerify | responseText                                                           |
      | What is the latest on xyz deals?       | without  |                                           |
      | What is the latest on GenePoint deals? | without  |                                            |
      | Bye                                    | with     | Goodbye.; Bye; See you later!                                          |
      | Hi                                     | with     | Howdy.; Hi there! How can I help?; Hello! Grad to help.; Welcome.; Hi. |
      | Thanks                                 | with     | Thanks.; Thank you.                                                    |
