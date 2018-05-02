Feature: CalendarFeature
  This feature deals with the login functionality of the applicaiton

  @P0
  @Event
  @MobileTest
  Scenario Outline: Create a event in Calendar
    Given Common: I switch to "Calendar" page from tab bar
    When Calendar: I click plus icon and select "Event" option
    Then Tact-Pin: I create a new event with "<subjectOption>" with "<subject>" subject, "<isAllDayEvent>" all-day event with "<startDate>" starts date at "<fromTime>" from time and "<endDate>" ends date at "<toTime>" to time, "<location>" location and "<description>" description
    And Tact-Pin: I "<isSyncToSF>" sync to Salesforce event with "<name>" name, "<relatedToName>" related to, "<attendeesName>" attendees and "<assignedToName>" assigned to
    And Tact-Pin: I "<isSave>" save new created

    Examples:
       | subjectOption      | subject          | isAllDayEvent | startDate    | fromTime | endDate      | toTime  | location                                   | description | isSyncToSF | name | relatedToName | attendeesName | assignedToName | isSave |
#       | w/o                | w/o           | true          | 10/10/2018   | w/o      | Jan 1, 2019  | w/o     | w/o                                        | w/o         | w/o        | w/o  | w/o           | w/o           | w/o            | w/o    |
       | Email              | fromTodayEvent   | false         | today        | 7:58 am  | Sep 12, 2018 | 3:45 pm | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes        |w/o   | w/o           | w/o           | w/o            | yes    |
#       | Call               | todayEventAllDay | true          | today        | w/o      | today        | w/o     | 2400 Broadway #210, Redwood City, CA 94063 | testing     | yes        |w/o   | w/o           | Tim Barr      | w/o            | yes    |
#       | w/o               | todayEventAllDay | true          | today        | w/o      | today        | w/o     | w/o                                         | w/o     | w/o        |w/o   | w/o           | Tim Barr      | w/o            | w/o    |

