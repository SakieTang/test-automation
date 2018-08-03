package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEvent.TactEventPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEvent.TactSFDetailsEventPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEvent.TactSelectOptionPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLog.TactLogPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNote.TactNotePage;
import ai.tact.qa.automation.testcomponents.mobile.TactPinPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchContactsPage;
import ai.tact.qa.automation.testcomponents.mobile.TactTask.TactTaskPage;
import ai.tact.qa.automation.testcomponents.mobile.TactTimer.IOSDateTimePage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.AndroidClock;
import ai.tact.qa.automation.utils.dataobjects.AndroidDate;
import ai.tact.qa.automation.utils.dataobjects.IOSTime;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TactPinSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    private IOSDateTimePage iOSDateTimePage = new IOSDateTimePage();
    private AndroidDate androidDate = new AndroidDate();

    public static String eventSubject;
    public static String logSubject;
    public static String taskSubject;
    public static String noteTitle;

    public TactPinSteps() {

        And("^Tact-Pin: I see a Tact pin icon display$", () -> {
            log.info("^Tact-Pin: I see a Tact pin icon display$");
            TactPinPage tactPinPage = new TactPinPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactPinPage.getTactPinIconButton());
        });
        Then("^Tact-Pin: I click Tact pin icon and select \"([^\"]*)\" option$", (String pinOption) -> {
            log.info("^Tact-Pin: I click Tact pin icon and select " + pinOption + " option$");
            TactPinPage tactPinPage = new TactPinPage();
            TactNotePage tactNotePage = new TactNotePage();
            TactLogPage tactLogPage = new TactLogPage();
            TactEventPage tactEventPage = new TactEventPage();

            switch (pinOption) {
                case "Log":
                    tactPinPage.getTactPinIconButton().tap(tactPinPage.getLogActivityToSalesforceButton());
                    tactPinPage.getLogActivityToSalesforceButton().tap(tactLogPage.getNewLogTitleLabel());
                    break;
                case "Note":
                    tactPinPage.getTactPinIconButton().tap(tactPinPage.getAddNoteButton());
                    tactPinPage.getAddNoteButton().tap(tactNotePage.getNewNoteTitleLabel());
                    break;
                case "Task":
                    if (DriverUtils.isAndroid()) {
                        tactPinPage.getTactPinIconButton().tap(tactPinPage.getAddTaskButton());
                    }
                    tactPinPage.getAddTaskButton().tap();
                    break;
                case "Event":
                    tactPinPage.getTactPinIconButton().tap(tactPinPage.getAddEventButton());
                    tactPinPage.getAddEventButton().tap(tactEventPage.getNewEventTitleLabel());
                    break;
                case "Opportunity": //only iOS - contact has opportunity
                    tactPinPage.getTactPinIconButton().tap(tactPinPage.getAddOpportunityButton());
                    tactPinPage.getAddOpportunityButton().tap();
                    break;
                default:
                    if (DriverUtils.isIOS()) {
                        TactAIAsserts.verifyFalse(true, "Please give a correct String (Log|Note|Task|Event|Opportunity)");
                    } else {
                        TactAIAsserts.verifyFalse(true, "Please give a correct String (Log|Task|Note|Event)");
                    }
            }
        });
        Then("^Tact-Pin: I create a new note \"([^\"]*)\" sync to SF, \"([^\"]*)\" title and \"([^\"]*)\" body$", (String isSyncToSF, String titleText, String bodyText) -> {
            log.info("^Tact-Pin: I create a new note " + isSyncToSF + " sync to SF, " +
                    titleText + " title, " + bodyText + " body$");
            TactNotePage tactNotePage = new TactNotePage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactNotePage.getNewNoteTitleLabel());
            //Sync to SF  //android party is wrong, w/o syc it saved
            if (DriverUtils.isTextEmpty(isSyncToSF)){
                tactNotePage.getSyncToSaleforceSwitch().changeValue();
            }
            //Title (required)      //element  changed <<<<<<<<<<<<<<<<<<<<<<<
            tactNotePage.getEditNoteTitleTextField().sendKeys(titleText);
            //note Body
            if (!DriverUtils.isTextEmpty(bodyText))
            {
                tactNotePage.getBodyTextField().sendKeys(bodyText);
            }
        });
        And("^Tact-Pin: I \"([^\"]*)\" save new created$", (String isSave) -> {
            log.info("^Tact-Pin: I " + isSave + " save new created$");
            TactNotePage tactNotePage = new TactNotePage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            if (DriverUtils.isTextEmpty(isSave)) {
                log.info("w/o save the new created");

                tactNotePage.getCancelNewNoteButton().tap();
                if (DriverUtils.isAndroid())
                {
                    tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
                }
            }
            else {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNotePage.getSaveNewNoteButton());
                tactNotePage.getSaveNewNoteButton().tap();
            }

//            if (DriverUtils.isAndroid()) {
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactPinPage.getTactPinIconButton());
//            }
//            if (Grid.driver().findElementsByXPath(tactContactObjPage.getGoBackToContactsMainPageButton().getLocator()).size() != 0) {
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap();
//                System.out.println("back to search page");
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap(tactContactsMainPage.getTactContactsTitleLabel());
//                System.out.println("back contact main page");
//            }
        });
        And("^Tact-Pin: I create a new task with \"([^\"]*)\" title, \"([^\"]*)\" description, \"([^\"]*)\" name, \"([^\"]*)\" related to and \"([^\"]*)\" due Date$", (String titleText, String description, String name, String relatedTo, String dueDate) -> {
            log.info("^Tact-Pin: I create a new task with " + titleText + " title, " + description + " description, "
                    + name + " name, " + relatedTo + " related to and " + dueDate + " due Date$");
            TactTaskPage tactTaskPage = new TactTaskPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getSyncToSaleforceTaskSwitch());

            //Title
            if (!DriverUtils.isTextEmpty(titleText)) {
                if (tactTaskPage.getSyncToSaleforceTaskSwitch().getValue().equals("1") ||
                        tactTaskPage.getSyncToSaleforceTaskSwitch().getValue().contains("ON"))
                {
                    log.info("SF : " + tactTaskPage.getSyncToSaleforceTaskSwitch().getValue());
                    tactTaskPage.getSyncToSaleforceTaskSwitch().changeValue();
                }
                tactTaskPage.getTitleTextField().clearText();
                tactTaskPage.getTitleTextField().sendKeys(titleText);
            }
            //Description
            if (!DriverUtils.isTextEmpty(description))
            {
                tactTaskPage.getDescriptionTextField().sendKeys(description);
            }
            //name(iOS) | Contact(Android)
            if (!DriverUtils.isTextEmpty(name))
            {
                log.info("Do not implement yet");
            }
            //relatedTo
            if (!DriverUtils.isTextEmpty(relatedTo))
            {
                log.info("Do not implement yet");
            }
            //dueDate
            if (DriverUtils.isTextEmpty(dueDate) && DriverUtils.isIOS()) {
                if (!tactTaskPage.getDueDateButton().getValue().equalsIgnoreCase("None")){
                    tactTaskPage.getDueDateButton().tap(tactTaskPage.getSelectDueReminderDateDoneAndOKButton());
                    tactTaskPage.getIosRemoveDueReminderDateButton().tap(tactTaskPage.getNewTaskTitleLabel());
                }
            }
            else if (!DriverUtils.isTextEmpty(dueDate) && AndroidDate.isDateValid(dueDate))
            {
                tactTaskPage.getDueDateButton().tap(tactTaskPage.getSelectDueReminderDateDoneAndOKButton());
                if (DriverUtils.isAndroid()) {
                    androidDate.changeDate(dueDate);
                }
                else {
                    log.info("Do not implement yet. Waiting for the uniq ID");
                    tactTaskPage.getSelectDueReminderDateDoneAndOKButton().tap();
                }
                WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getNewTaskTitleLabel());
            }
        });
        And("^Tact-Pin: I continue to edit iOS task \"([^\"]*)\" followup-iOS, \"([^\"]*)\" with \"([^\"]*)\" and \"([^\"]*)\" reminder-iOS$", (String isFollowUp, String isReminder, String reminderDate, String reminderTime) -> {
            log.info("^Tact-Pin: I continue to edit iOS task " + isFollowUp + " followup-iOS, " + isReminder + " with " + reminderDate + " and " + reminderTime + " reminder-iOS$");
            TactTaskPage tactTaskPage = new TactTaskPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getSyncToSaleforceTaskSwitch());

            //isFollowUp(iOS)
            if (DriverUtils.isIOS() && !DriverUtils.isTextEmpty(isFollowUp))
            {
                tactTaskPage.getIosFollowUpButton().tap();
            }
            //isReminder(iOS)
            if (DriverUtils.isIOS() && DriverUtils.isTextEmpty(isFollowUp))
            {
                if (DriverUtils.isTextEmpty(isReminder)) {
                    log.info("Do not do the reminder");
                    tactTaskPage.getIosReminderButton().tap(tactTaskPage.getSelectDueReminderDateDoneAndOKButton());
                    tactTaskPage.getSelectDueReminderDateDoneAndOKButton().tap(tactTaskPage.getNewTaskTitleLabel());
                    tactTaskPage.getIosReminderButton().tap(tactTaskPage.getSelectDueReminderDateDoneAndOKButton());
                    tactTaskPage.getIosRemoveDueReminderDateButton().tap(tactTaskPage.getNewTaskTitleLabel());
                }
                else {
                    log.info("Do a simple reminder");
                    tactTaskPage.getIosReminderButton().tap(tactTaskPage.getSelectDueReminderDateDoneAndOKButton());
                    IOSTime.changeDateAndTime(reminderDate,reminderTime);
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getNewTaskTitleLabel());
                }
            }
        });
        And("^Tact-Pin: I create a new task with \"([^\"]*)\" title, \"([^\"]*)\" description, \"([^\"]*)\" name and \"([^\"]*)\" related to,  \"([^\"]*)\" followup-iOS and \"([^\"]*)\" reminder-iOS$", (String titleText, String description, String name, String relatedTo, String isFollowUp, String isReminder) -> {

            log.info("^Tact-Pin: I create a new task with " + titleText + " title, " + description + " description, "
                    + name + " name and " + relatedTo + " related to,  " + isFollowUp + " followup-iOS and " + isReminder + " reminder-iOS$");
            TactTaskPage tactTaskPage = new TactTaskPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getSyncToSaleforceTaskSwitch());

            //Title
            if (!DriverUtils.isTextEmpty(titleText))
            {
                if (tactTaskPage.getSyncToSaleforceTaskSwitch().getValue().equals("1") ||
                        tactTaskPage.getSyncToSaleforceTaskSwitch().getValue().contains("ON"))
                {
                    log.info("SF : " + tactTaskPage.getSyncToSaleforceTaskSwitch().getValue());
                    tactTaskPage.getSyncToSaleforceTaskSwitch().changeValue();
                }
                tactTaskPage.getTitleTextField().clearText();
                tactTaskPage.getTitleTextField().sendKeys(titleText);
            }
            //Description
            if (!DriverUtils.isTextEmpty(description))
            {
                tactTaskPage.getDescriptionTextField().sendKeys(description);
            }
            //name(iOS) | Contact(Android)
            if (!DriverUtils.isTextEmpty(name))
            {
                log.info("Do not implement yet");
            }
            //relatedTo
            if (!DriverUtils.isTextEmpty(relatedTo))
            {
                log.info("Do not implement yet");
            }
            //isFollowUp(iOS)
            if (DriverUtils.isIOS() && !DriverUtils.isTextEmpty(isFollowUp))
            {
                tactTaskPage.getIosFollowUpButton().tap();
            }
            //isReminder(iOS)
            if (DriverUtils.isIOS() && DriverUtils.isTextEmpty(isFollowUp) &&
                    !DriverUtils.isTextEmpty(isReminder))
            {
                log.info("Do a simple reminder");
                tactTaskPage.getIosReminderButton().tap(tactTaskPage.getSelectDueReminderDateDoneAndOKButton());
                tactTaskPage.getSelectDueReminderDateDoneAndOKButton().tap(tactTaskPage.getNewTaskTitleLabel());
            }
        });
        And("^Tact-Pin: I edit Salesforce task with \"([^\"]*)\" comments, \"([^\"]*)\" assigned to, \"([^\"]*)\" priority and \"([^\"]*)\" Status$", (String comments, String assignedTo, String priorityOption, String statusOption) -> {
            log.info("^Tact-Pin: I edit Salesforce task with " + comments + " comments, " + assignedTo + " assigned to, " + priorityOption + " priority and " + statusOption + " Status$");
            TactTaskPage tactTaskPage = new TactTaskPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getSyncToSaleforceTaskSwitch());
            //Sync salesforce
            //0 - Salesforce Task OFF, 1 - Salesforce Task ON (0:ios, off:Android)
            if (tactTaskPage.getSyncToSaleforceTaskSwitch().getValue().equals("0") ||
                    tactTaskPage.getSyncToSaleforceTaskSwitch().getValue().contains("OFF"))
            {
                log.info("SF : " + tactTaskPage.getSyncToSaleforceTaskSwitch().getValue());
                tactTaskPage.getSyncToSaleforceTaskSwitch().changeValue();
            }

            //Comments
            if (!DriverUtils.isTextEmpty(comments))
            {
                tactTaskPage.getSyncSFTaskCommentsTextField().clearText();
                tactTaskPage.getSyncSFTaskCommentsTextField().sendKeys(comments);
            }
            //assignTo
            if (!DriverUtils.isTextEmpty(assignedTo))
            {
                log.info("Do not implement yet");
            }
            //priority
            selectPriorityAndStatus(priorityOption);
            WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getNewTaskTitleLabel());
            //Android scroll to bottom
            if (DriverUtils.isAndroid())
            {
                DriverUtils.scrollToBottom();
            }
            //status
            if (!DriverUtils.isTextEmpty(statusOption))
            {
                tactTaskPage.getSyncSFTaskStatusButton().tap(tactTaskPage.getSyncSFTaskStatusOptionButton());
                String statusBtnLoc = tactTaskPage.getSyncSFTaskStatusOptionButton().getLocator().replaceAll("Not Started", statusOption);
                Grid.driver().findElementByXPath(statusBtnLoc).click();
                WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getNewTaskTitleLabel());
            }

            DriverUtils.sleep(20);
        });
        Then("^Tact-Pin: I create a new log with \"([^\"]*)\" with \"([^\"]*)\" subject, \"([^\"]*)\" name, \"([^\"]*)\" related to, \"([^\"]*)\" due Date, \"([^\"]*)\" comments, \"([^\"]*)\" priority and \"([^\"]*)\" status$", (String subjectOption, String subject, String name, String relatedTo, String dueDate, String comments, String priorityOption, String statusOption) -> {
            log.info("^Tact-Pin: I create a new log with " + subjectOption + " with " + subject + " subject, " + name + " name, " + relatedTo + " related to, " + dueDate + " due Date, " + comments + " comments, " + priorityOption + " priority and " + statusOption + " status$");
            TactLogPage tactLogPage = new TactLogPage();

            //name(iOS) | Contact(Android)
            if (!DriverUtils.isTextEmpty(name))
            {
                log.info("Do not implement yet");
            }
            //relatedTo
            if (!DriverUtils.isTextEmpty(relatedTo))
            {
                log.info("Do not implement yet");
            }
            //comments
            if (!DriverUtils.isTextEmpty(comments))
            {
                tactLogPage.getLogCommentsTextField().setText(comments);
            }
            //subject
            if (!DriverUtils.isTextEmpty(subjectOption))
            {
                tactLogPage.getSubjectButton().tap(tactLogPage.getSubjectTextField());
                String subjectOptionLoc = tactLogPage.getSubjectOptionButton().getLocator().replaceAll("subjectOption", subjectOption);
                Grid.driver().findElementByXPath(subjectOptionLoc).click();
                if (!DriverUtils.isTextEmpty(subject))
                {
                    tactLogPage.getSubjectTextField().sendKeys(" " + subject);
                }
                tactLogPage.getSubjectConfirmButton().tap();
                WebDriverWaitUtils.waitUntilElementIsVisible(tactLogPage.getNewLogTitleLabel());
            }
            //dueDate
            if (!DriverUtils.isTextEmpty(dueDate))
            {
                tactLogPage.getDueDateButton().tap(tactLogPage.getDueDateDoneAndOKButton());
            }
            //Priority
            if (!DriverUtils.isTextEmpty(priorityOption))
            {
                selectPriorityAndStatus(priorityOption);
                WebDriverWaitUtils.waitUntilElementIsVisible(tactLogPage.getNewLogTitleLabel());
            }
            //Status
            if (!DriverUtils.isTextEmpty(statusOption))
            {
                tactLogPage.getLogStatusButton().tap(tactLogPage.getLogStatusOptionButton());
                String statusBtnLob = tactLogPage.getLogStatusOptionButton().getLocator().replaceAll("Completed", statusOption);
                Grid.driver().findElementByXPath(statusBtnLob).click();
                WebDriverWaitUtils.waitUntilElementIsVisible(tactLogPage.getNewLogTitleLabel());
            }
        });
        And("^Tact-Pin: I edit Salesforce event with \"([^\"]*)\" name, \"([^\"]*)\" related to, \"([^\"]*)\" attendees and \"([^\"]*)\" assigned to$", (String name, String relatedTo, String attendees, String assignedTo) -> {
            log.info("^Tact-Pin: I edit Salesforce event with " + name + " name, " + relatedTo + " related to, " + attendees + " attendees and " + assignedTo + " assigned to$");

            if (DriverUtils.isIOS())
            {

            }
            //name
            if (!DriverUtils.isTextEmpty(name))
            {
                log.info("Do not implement yet");
            }
            //relatedTo
            if (!DriverUtils.isTextEmpty(relatedTo))
            {
                log.info("Do not implement yet");
            }
            //attendees
            if (!DriverUtils.isTextEmpty(attendees))
            {
                log.info("Do not implement yet");
            }
            //assignedTo
            if (!DriverUtils.isTextEmpty(assignedTo))
            {
                log.info("Do not implement yet");
            }
        });
        And("^Tact-Pin: I \"([^\"]*)\" sync to Salesforce event with \"([^\"]*)\" name, \"([^\"]*)\" related to, \"([^\"]*)\" attendees and \"([^\"]*)\" assigned to$", (String isSyncToSF, String name, String relatedTo, String attendees, String assignedTo) -> {
            log.info("^Tact-Pin: I " + isSyncToSF + " sync to Salesforce event with " + name + " name, " + relatedTo + " related to, " + attendees + " attendees and " + assignedTo + " assigned to$");
            TactEventPage tactEventPage = new TactEventPage();
            TactSFDetailsEventPage tactSFDetailsEventPage = new TactSFDetailsEventPage();
            TactSelectOptionPage tactSelectOptionPage = new TactSelectOptionPage();
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();

            //Sync salesforce       //need to modify this part<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            //0 - Salesforce Task OFF, 1 - Salesforce Task ON (0:ios, off:Android)
            if (DriverUtils.isIOS() && !DriverUtils.isTextEmpty(isSyncToSF))
            {
                if (tactEventPage.getIosSyncSFSwitch().getValue().equals("0"))
                {
                    tactEventPage.getIosSyncSFSwitch().changeValue();
                }
                DriverUtils.slideDown();
                tactEventPage.getIosSFEventDetailsButton().tap(tactEventPage.getIosSFDetailsTitleLabel());
            }
            //name
            if (!DriverUtils.isTextEmpty(name))
            {
                log.info("Do not implement yet");
            }
            //relatedTo
            if (!DriverUtils.isTextEmpty(relatedTo))
            {
                log.info("Do not implement yet");
            }
            //attendees
            if (!DriverUtils.isTextEmpty(attendees))
            {
                tactSFDetailsEventPage.getSfAttendeesButton().tap(tactSelectOptionPage.getAttendeesTitleLabel());

                if (DriverUtils.isAndroid())
                {
                    tactSearchContactsPage.getAndroidAttendeesSearchIconButton().tap(tactSearchContactsPage.getSearchAllContactsTextField());
                }

                //search the name from search field     element id changed <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                tactSearchContactsPage.getSearchAllContactsTextField().setText(attendees);

                //get the name location, and click it
                if (attendees.contains(",") && !attendees.contains(", ")) { //Testing,fname
                    attendees = attendees.split(",")[1] + " " + attendees.split(",")[0];
                }
                else if (attendees.contains(", ")) {
                    attendees = attendees.split(", ")[1] + " " + attendees.split(", ")[0];
                }

                log.info("attendees :" + attendees);
                String nameLoc = tactSearchContactsPage.getNameEditButton().getLocator().replace("contactName", attendees);
                log.info("loc is ==> " + nameLoc);
                DriverUtils.sleep(1);

                Grid.driver().findElementByXPath(nameLoc).click();

                //go back to SF details page
                if (DriverUtils.isIOS()) {
                    tactSelectOptionPage.getIosBackToSalesforceDetailsPageButton().tap(tactSFDetailsEventPage.getSfAndAndroidSubjectButton());
                }
                else {
                    tactSelectOptionPage.getSfAndAndroidSubjectConfirmButton().tap(tactSelectOptionPage.getAttendeesTitleLabel());
                    tactSelectOptionPage.getSfAndAndroidSubjectConfirmButton().tap();
                    DriverUtils.sleep(10);
                    //save button did not click
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSFDetailsEventPage.getSfAndAndroidSubjectButton());
                }
            }
            //assignedTo
            if (!DriverUtils.isTextEmpty(assignedTo))
            {
                log.info("Do not implement yet");
            }
            //from Sf details to new event page
            if (DriverUtils.isIOS() && !DriverUtils.isTextEmpty(isSyncToSF))
            {
                tactEventPage.getIosBackToNewEventPageButton().tap();
                WebDriverWaitUtils.waitUntilElementIsVisible(tactEventPage.getNewEventTitleLabel());
            }
        });
        Then("^Tact-Pin: I create a new event with \"([^\"]*)\" with \"([^\"]*)\" subject, \"([^\"]*)\" all-day event with \"([^\"]*)\" starts date at \"([^\"]*)\" from time and \"([^\"]*)\" ends date at \"([^\"]*)\" to time, \"([^\"]*)\" location and \"([^\"]*)\" description$", (String subjectOption, String subject, String isAllDayEvent, String startDate, String fromTime, String endDate, String toTime, String location, String description) -> {
            log.info("^Tact-Pin: I create a new event with " + subjectOption + " with " + subject + " subject, " + isAllDayEvent + " all-day event with " + startDate + " starts date at " + fromTime + " from time and " + endDate + " ends date at " + toTime + " to time, " + location + " location and " + description + " description$");
            TactEventPage tactEventPage = new TactEventPage();

            //subject
            if (DriverUtils.isAndroid() && !DriverUtils.isTextEmpty(subjectOption))
            {
                tactEventPage.getSfAndAndroidSubjectButton().tap(tactEventPage.getSfAndAndroidSubjectTextField());
                String subjectOptionLoc = tactEventPage.getSfAndAndroidSubjectOptionButton().getLocator().replaceAll("subjectOption", subjectOption);
                Grid.driver().findElementByXPath(subjectOptionLoc).click();
                if (!DriverUtils.isTextEmpty(subject))
                {
                    tactEventPage.getSfAndAndroidSubjectTextField().sendKeys(" " + subject);
                }
                tactEventPage.getSfAndAndroidSubjectConfirmButton().tap();
                WebDriverWaitUtils.waitUntilElementIsVisible(tactEventPage.getNewEventTitleLabel());
            }
            if (DriverUtils.isIOS() && !DriverUtils.isTextEmpty(subject))
            {
                tactEventPage.getIosSubjectTextField().sendKeys(subject);
            }
            //isAllDayEvent     10/10/2018    Oct 2, 2018
            if (isAllDayEvent.equalsIgnoreCase("true") &&
                    (tactEventPage.getAllDaySwitch().getValue().equals("0") ||
                            tactEventPage.getAllDaySwitch().getValue().equals("OFF"))) {
                log.info("SF : " + tactEventPage.getAllDaySwitch().getValue());
                tactEventPage.getAllDaySwitch().changeValue();

                if (!DriverUtils.isTextEmpty(startDate)){
                    //10/10/2018    Oct 2, 2018
                    tactEventPage.getStartsButton().tap();
                    IOSTime.changeDate(startDate);
                }

                if (!DriverUtils.isTextEmpty(endDate)){
                    //10/10/2018    Oct 2, 2018
                    tactEventPage.getEndsButton().tap();
                    IOSTime.changeDate(endDate);
                }

                //
                log.info("After change the time");

            }
            else if (isAllDayEvent.equalsIgnoreCase("false")) {
                if (tactEventPage.getAllDaySwitch().getValue().equals("1") ||
                        tactEventPage.getAllDaySwitch().getValue().equals("ON"))
                {
                    log.info("SF : " + tactEventPage.getAllDaySwitch().getValue());
                    tactEventPage.getAllDaySwitch().changeValue();
                }

                //Android - from time
                if (!DriverUtils.isTextEmpty(fromTime) && DriverUtils.isAndroid())
                {
                    //10/10/2018    Oct 2, 2018
                    tactEventPage.getAndroidFromTimeButton().tap();
                    AndroidClock.changeDigitalClock(fromTime);
                }
                //Android - to time
                if (!DriverUtils.isTextEmpty(toTime) && DriverUtils.isAndroid() )
                {
                    //10/10/2018    Oct 2, 2018
                    tactEventPage.getAndroidToTimeButton().tap();
                    AndroidClock.changeAnalogClock(toTime);
                }

                //iOS - set date and time
                if (!DriverUtils.isTextEmpty(fromTime) && DriverUtils.isIOS())
                {
                    tactEventPage.getStartsButton().tap();
                    IOSTime.changeDateAndTime(startDate, fromTime);
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactEventPage.getNewEventTitleLabel());
                }
                if (!DriverUtils.isTextEmpty(toTime) && DriverUtils.isIOS())
                {
                    tactEventPage.getEndsButton().tap();
                    IOSTime.changeDateAndTime(endDate, toTime);
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactEventPage.getNewEventTitleLabel());
                }

                //
                log.info("After change the time");
            }

            //startDate
            if (!DriverUtils.isTextEmpty(startDate) && DriverUtils.isAndroid() && AndroidDate.isDateValid(startDate))
            {
                tactEventPage.getStartsButton().tap();
                androidDate.changeDate(startDate);
            }
            //endDate
            if (!DriverUtils.isTextEmpty(endDate) && DriverUtils.isAndroid() && AndroidDate.isDateValid(endDate))
            {
                tactEventPage.getEndsButton().tap();
                androidDate.changeDate(endDate);
            }
            //location
            if (!DriverUtils.isTextEmpty(location))
            {
                tactEventPage.getLocationTextField().sendKeys(location);
            }
            //description
            if (!DriverUtils.isTextEmpty(description))
            {
                if (DriverUtils.isAndroid())
                {
                    DriverUtils.scrollToBottom();
                }
                tactEventPage.getDescriptionTextField().sendKeys(description);
            }
        });
    }

    private void selectPriorityAndStatus( String priorityOption ){
        TactTaskPage tactTaskPage = new TactTaskPage();

        //priority
        if (!DriverUtils.isTextEmpty(priorityOption))
        {
            tactTaskPage.getSyncSFTaskPriorityButton().tap(tactTaskPage.getSyncSFTaskPriorityOptionButton());
            WebDriverWaitUtils.waitUntilElementIsVisible(tactTaskPage.getSyncSFTaskPriorityOptionButton());
            String priorityBtnLoc = tactTaskPage.getSyncSFTaskPriorityOptionButton().getLocator().replaceAll("Normal", priorityOption);
            Grid.driver().findElementByXPath(priorityBtnLoc).click();
        }
    }
}
