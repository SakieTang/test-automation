package ai.tact.qa.automation.steps.mobileSteps;


import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactCalendar.TactCalendarMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEvent.TactEventPage;
import ai.tact.qa.automation.testcomponents.mobile.TactPinPage;
import ai.tact.qa.automation.testcomponents.mobile.TactTask.TactTaskPage;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public CalendarSteps() {
        TactCalendarMainPage tactCalendarMainPage = new TactCalendarMainPage();
        TactPinPage tactPinPage = new TactPinPage();
        TactEventPage tactEventPage = new TactEventPage();
        TactTaskPage tactTaskPage = new TactTaskPage();

        When("^Calendar: I click plus icon and select \"([^\"]*)\" option$", (String option) -> {
            log.info("^Calendar: I click plus icon and select " + option + " option$");

            WebDriverWaitUtils.waitUntilElementIsVisible(tactCalendarMainPage.getCalendarAddPlusButton());

            switch (option) {
                case "Task":
                    tactCalendarMainPage.getCalendarAddPlusButton().tap(tactPinPage.getAddTaskButton());
                    tactPinPage.getAddTaskButton().tap(tactTaskPage.getNewTaskTitleLabel());
                    break;
                case "Event":
                    tactCalendarMainPage.getCalendarAddPlusButton().tap(tactPinPage.getAddEventButton());
                    tactPinPage.getAddEventButton().tap(tactEventPage.getNewEventTitleLabel());
                    break;
                default:
                    TactAIAsserts.verifyFalse(true,"Please give a correct String (Task|Event)");
            }
        });
    }
}
