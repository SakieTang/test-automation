Feature: Assistant - Thread
  This feature deals with the Assistant AI functionality of the applicaiton

  @P0
  @loginThread
  Scenario: open browser and login Thread
    Given WebCommon: I launch browser and open "thread.id/service3/"
    When WebCommon: I login with "thread" user account and password
    Then ThreadCommon: I switch to "Timeline" from navigate

  @P1
  @ThreadAIBasicTesting
  Scenario Outline: Sent msg to AI, and verify the reply
    And ThreadTimeline: I click "Tact" option
    When ThreadTimeline: I send "<inputText>" to "Thread" Assistant and "<isVerify>" verify sent msg
    Then ThreadTimeline: I check bot "<responseText>"

    Examples:
       | inputText                              | isVerify | responseText                                                                      |
       | What is the latest on xyz deals?       | with     |                                                                                   |
       | What is the latest on GenePoint deals? | with     |                                                                                   |
       | Bye                                    | with     | Goodbye.; Bye; See you later!                                                     |
       | Hi                                     | with     | Howdy.; Hi there! How can I help?; Hello! Grad to help.; Welcome.; Hi.; Hi there! |
       | Thanks                                 | with     | Thanks.; Thank you.                                                               |
