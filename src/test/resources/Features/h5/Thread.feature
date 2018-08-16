Feature: Thread
  This feature deals with the Thread functionality

  @ThreadDismissMention
  Scenario Outline: Give a user/pwd, to dismiss mention(s)
    Given WebCommon: I launch browser and open "thread.id/service5/"
    When WebCommon: I login with "Thread" user account "<account>" and password "<pwd>"
    Then ThreadCommon: I switch to "Priority" from navigate
    And ThreadPriority: I click "<num>" times dismiss

    Examples:
      | account                    | pwd          | num |
#      | mchillakanti@gmail.com | testpassword | all |
      | mukund.chillakanti@tact.ai | temppassword | all |