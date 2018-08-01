Feature: Assistant - Alexa
  This feature deals with the Assistant AI functionality of the applicaiton

  @P0
  @loginAlexa
  Scenario: open browser and login Thread
    Given WebCommon: I launch browser and open "developer.amazon.com/alexa"
    When WebCommon: I login with "Amazon" user account and password
    Then AlexaTest: I switch to "Test" from navigate

  @P1
  @AlexaAIBasicTesting
  Scenario Outline: Sent msg to AI, and verify the reply
    And AlexaTest: I click "testEnabledSkill" option
    When AlexaTest: I send "<inputText>" to "Alexa" Assistant and "<isVerify>" verify sent msg
#    Then AlexaTest: I check bot "<responseText>"

    Examples:
      | inputText                              | isVerify | responseText                                                           |
      | What is the latest on xyz deals?       | without  |                                           |
#      | What is the latest on GenePoint deals? | without  |                                            |
#      | Bye                                    | with     | Goodbye.; Bye; See you later!                                          |
#      | Hi                                     | with     | Howdy.; Hi there! How can I help?; Hello! Grad to help.; Welcome.; Hi. |
#      | Thanks                                 | with     | Thanks.; Thank you.                                                    |