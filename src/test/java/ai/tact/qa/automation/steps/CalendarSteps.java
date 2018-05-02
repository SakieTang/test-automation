package ai.tact.qa.automation.steps;


import ai.tact.qa.automation.testcomponents.mobile.TactCalendar.TactCalendarMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEvent.TactEventPage;
import ai.tact.qa.automation.testcomponents.mobile.TactPinPage;
import ai.tact.qa.automation.testcomponents.mobile.TactTask.TactTaskPage;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarSteps implements En {

    private TactCalendarMainPage tactCalendarMainPage;
    private TactPinPage tactPinPage;
    private TactEventPage tactEventPage;
    private TactTaskPage tactTaskPage;
    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public CalendarSteps() {
        tactCalendarMainPage = new TactCalendarMainPage();
        tactPinPage = new TactPinPage();
        tactEventPage = new TactEventPage();
        tactTaskPage = new TactTaskPage();

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
                    SeLionAsserts.verifyFalse(true,"Please give a correct String (Task|Event)");
            }
        });
    }
}
