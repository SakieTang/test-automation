Feature: EmailFeature
  This feature deals with the Email functionality of the applicaiton

  @P0
  @MobileTest
  @ConnectGmailTabBar
  @Gmail
  Scenario: Connect Gmail in Tact Email tab bar
    Given Common: I switch to "Email" page from tab bar
    When Email: I connect with "Gmail" email account inside Email tab bar
    Then Common: I switch to "Webview" driver
    And Settings: I sign in the Gmail account
#    Then Settings: I switch to "Gmail" option in Sources settings page
#    And Settings: I disconnect the "Gmail" account

  @P0
  @MobileTest
  @ConnectExchangeTabBar
  @Exchange
  Scenario: Connect Exchange in Tact Email tab bar
    Given Common: I switch to "Email" page from tab bar
    When Email: I connect with "Exchange" email account inside Email tab bar
    Then Settings: I sign in the Exchange account
#    Then Settings: I switch to "Exchange" option in Sources settings page
#    And Settings: I disconnect the "Exchange" account

  @P0
  @ViewExchangeEmail
  @android
  Scenario: View Android email fields (From, Date, Subtitle, Body)
    Given Common: I switch to "Email" page from tab bar
    And Email: I verify the email field "emailFrom" with "Automation TactiOS"
    And Email: I verify the email field "emailSubject" with "0821-iOS to Android"
    And Email: I verify the email field "emailBody" with "Workbench is "
  
  @P1
  @MobileTest
  @ExchangeSend
  Scenario: send an email from iOS-exchange account to iOS-gmail in Tact
    Given Common: I switch to "Email" page from tab bar
    When Email: I switch to "Exchange" mailType, "Inbox" option and "do" create a new email
    Then Email: I send an empty subject email
    Then Email: I create a simply email To "samePlatformDiffEmail", Subject "Subject" and body ""
#    And Email: I "do not" send email and "w/o" save draft
    And Email: I "do" send email and "w/o" save draft
    When Common: I switch to "Email" page from tab bar
    Then Email: I switch to "" mailType, "Sent" option and "w/o" create a new email
    And Email: I verify the email
    When Common: I switch to "Email" page from tab bar
    Then Email: I switch to "Gmail" mailType, "Inbox" option and "w/o" create a new email
    And Email: I verify the email

  @P1
  @MobileTest
  @gmailSend
  Scenario: send an email from iOS-gmail account to iOS-exchange in Tact
    Given Common: I switch to "Email" page from tab bar
    When Email: I switch to "gmail" mailType, "Inbox" option and "do" create a new email
    Then Email: I create a simply email To "samePlatformDiffEmail", Subject "Subject" and body ""
#    And Email: I "do not" send email and "w/o" save draft
    And Email: I "do" send email and "w/o" save draft
    When Common: I switch to "Email" page from tab bar
    Then Email: I switch to "" mailType, "Sent" option and "w/o" create a new email
    And Email: I verify the email
    When Common: I switch to "Email" page from tab bar
    Then Email: I switch to "Exchange" mailType, "Inbox" option and "w/o" create a new email
    And Email: I verify the email

  @P2
  @MobileTest
  @ExchangeDraft
  Scenario: save a draft email from exchange account in Tact
    Given Common: I switch to "Email" page from tab bar
    When Email: I switch to "" mailType, "All Inboxes" option and "do" create a new email
    Then Email: I create a simply email To "samePlatformDiffEmail", Subject "Subject" and body ""
#    And Email: I "do not" send email and "w/o" save draft
    And Email: I "w/o" send email and "do" save draft
    When Common: I switch to "Email" page from tab bar
    Then Email: I switch to "" mailType, "Drafts" option and "w/o" create a new email
    And Email: I verify the email

  @P2
  @MobileTest
  @gmailDraft
  Scenario: save a draft email from gmail account in Tact
    Given Common: I switch to "Email" page from tab bar
    When Email: I switch to "gmail" mailType, "Inbox" option and "do" create a new email
    Then Email: I create a simply email To "samePlatformDiffEmail", Subject "Subject" and body ""
#    And Email: I "do not" send email and "w/o" save draft
    And Email: I "w/o" send email and "do" save draft
    When Common: I switch to "Email" page from tab bar
    Then Email: I switch to "" mailType, "Drafts" option and "w/o" create a new email
    And Email: I verify the email

  @P1
  @swipeTheEmail
  Scenario Outline: check right swipe in email
    Given Common: I am waiting for "50" sec
    When Email: I "right" swipe the email with "Signature" subject

    Examples:
      | subjectName |
      | Signature   |