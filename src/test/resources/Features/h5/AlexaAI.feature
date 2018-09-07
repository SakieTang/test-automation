Feature: Assistant - Alexa
  This feature deals with the Assistant AI functionality of the applicaiton

  @P0
  @loginAlexa
  Scenario: open browser and login Thread
    Given WebCommon: I launch browser and open "developer.amazon.com/alexa"
    When WebCommon: I login with "Amazon" user account "w/o" and password "w/o"
    Then AlexaTest: I switch to "Test" from navigate

  @P1
  @AlexaAIBasicTesting
  Scenario Outline: Sent msg to AI, and verify the reply
    Given AlexaTest: I click "testEnabledSkill" option
    When AlexaTest: I "<isCancel>" cancel session
    Then AlexaTest: I send "<cmd>" to "Alexa dev1" Assistant and "<isVerify>" verify send msg
#    When AlexaTest: I check bot response text
    When AlexaTest: I "<isCheckOutputSpeechSSML>" check bot "jSonOutputSpeechSSML" response
    Then AlexaTest: I "<isCheckOutputCardContent>" check bot "jSonOutputCardContent" response
    And AlexaTest: I "<isCheckOutputRepromptSSML>" check bot "jSonOutputRepromptSSML" response
    And AlexaTest: I "<isCheckOutputShouldEndSession>" check bot "jSonOutputShouldEndSession" response

    Examples:
      | isCancel | cmd         | isVerify | isCheckOutputSpeechSSML | isCheckOutputCardContent | isCheckOutputRepromptSSML | isCheckOutputShouldEndSession |
      | true     | askTact     | true     | true                    | true                     | true                      | true                           |
      | false    | latestDeals | true     | true                    | true                     | true                      | true                           |
      | false    | optionTwo   | true     | true                    | true                     | true                      | true                           |
      | false    | choiceSix   | true     | true                    | true                     | true                      | true                           |
      | false    | website     | true     | true                    | true                     | true                      | true                           |
      | false    | contactHelp | true     | true                    | true                     | true                      | true                           |
      | false    | reportBug   | true     | true                    | true                     | true                      | true                           |
      | false    | endReportBug| true     | true                    | true                     | true                      | true                           |
      | false    | endSession  | false    | false                   | false                    | false                     | false                          |

