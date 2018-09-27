package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactCompany.TactAddNewCompanyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactAddNewContactPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.TactMailBoxesPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLead.TactAddNewLeadPage;
import ai.tact.qa.automation.testcomponents.mobile.TactPinPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchContactsPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchNamePage;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.DriverUtils;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.mobile.elements.MobileButton;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactsSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public ContactsSteps() {

        Then("^Contacts: I go to create a new \"(Contact|Lead|Account)\" page$", (String userType) -> {
            log.info("^Contacts: I go to create a new " + userType + " page$");
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactAddNewContactPage tactAddNewContactPage = new TactAddNewContactPage();
            TactAddNewLeadPage tactAddNewLeadPage = new TactAddNewLeadPage();
            TactAddNewCompanyPage tactAddNewCompanyPage = new TactAddNewCompanyPage();

            switch (userType) {
                case "Contact":
                    //add_contact_button  --> fab_create_contact
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewContactPage.getTactAddNewContactButton());
                    tactAddNewContactPage.getTactAddNewContactButton().tap(tactAddNewContactPage.getNewContactTitleLabel());
                    break;
                case "Lead":
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewLeadPage.getTactAddNewLeadButton());
                    tactAddNewLeadPage.getTactAddNewLeadButton().tap(tactAddNewLeadPage.getNewLeadTitleLabel());
                    break;
                case "Account":
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewCompanyPage.getTactAddNewCompanyButton());
                    tactAddNewCompanyPage.getTactAddNewCompanyButton().tap(tactAddNewCompanyPage.getNewCompanyTitleLabel());
                    break;
                default:
                    TactAIAsserts.verifyFalse(true,"Please give a correct String (Contact|Lead|Company)");
            }

        });
        Then("^Contacts: I search one user \"([^\"]*)\" from recent field and select it$", (String name) -> {
            log.info("^Contacts: I search one user " + name + " from recent field and select it$");
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();
            String formattedName = null;

            //get format name
            switch(name) {
                case "contact":
                    formattedName = AddDeleteContactSteps.contactName;
                    break;
                case "lead":
                    formattedName = AddDeleteLeadSteps.leadName;
                    break;
                case "company":
                    formattedName = AddDeleteAccountSteps.accountName;
                    break;
                case "account":
                    formattedName = AddDeleteAccountSteps.accountName;
                    break;
                default:
                    formattedName = DriverUtils.convertToFormatName(name);

            }

            String stageLoc = tactSearchContactsPage.getNameEditButton().getLocator().replace("contactName", formattedName);
            System.out.println("stageLoc ==> " + stageLoc);

            if (Grid.driver().findElementsByXPath(tactContactsMainPage.getTactContactsTitleLabel().getLocator()).size() != 0){
                if (DriverUtils.isAndroid())
                {
                    tactContactsMainPage.getTactAndroidRecentFavoritesButton().tap();
                    DriverUtils.sleep(0.5);
                }
                if (DriverUtils.isAndroid() && Grid.driver().findElementsByXPath(stageLoc).size()!=0){
//                if (Grid.driver().findElementsByXPath(tactContactsMainPage.getTactRecentTitleLabel().getLocator()).size() != 0){
                    System.out.println("in Android");
                    DriverUtils.clickOption(tactSearchContactsPage.getNameEditButton(), "contactName", formattedName);
                } else if (DriverUtils.isIOS() && Grid.driver().findElementsByXPath(stageLoc).size() > 1) {
                    System.out.println("in iOS");
                    DriverUtils.clickOption(tactSearchContactsPage.getNameEditButton(), "contactName", formattedName);
                }
                else {
                    log.info("not find the name from recent list");
                }
            } else {
                log.info("already find the name from searching");
            }

        });
        When("^Contacts: I search one user \"([^\"]*)\" from contacts list and select it$", (String name) -> {
            log.info("^Contacts: I search one user " + name + " from contacts list and select it$");
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();

            String formattedName = null;

            if ( (DriverUtils.isIOS() && Grid.driver().findElementsByXPath(tactContactsMainPage.getMapsIconButton().getLocator()).size() != 0) ||
                    (DriverUtils.isAndroid() && Grid.driver().findElementsByXPath(tactContactsMainPage.getTactContactsTitleLabel().getLocator()).size() != 0))
            {
                if (DriverUtils.isAndroid()){
                    //click
                    DriverUtils.sleep(1);
                    System.out.println("*** try the new way to click");
                    AppiumDriver driver = (AppiumDriver) Grid.driver();
                    driver.tap(1, 1160, 182, 30);
                    System.out.println("*** try the new way to click done");
                    DriverUtils.sleep(2);

                    if (Grid.driver().findElementsByXPath(tactContactsMainPage.getTactContactsTitleLabel().getLocator()).size() != 0){
                        System.out.println("retry");
                        DriverUtils.tapXY(1160, 182, 5, 0);
                        System.out.println("less time to click it");
                    }
                }

                //get format name
                switch(name) {
                    case "contact":
                        formattedName = AddDeleteContactSteps.contactName;
                        break;
                    case "lead":
                        formattedName = AddDeleteLeadSteps.leadName;
                        break;
                    case "company":
                        formattedName = AddDeleteAccountSteps.accountName;
                        break;
                    case "account":
                        formattedName = AddDeleteAccountSteps.accountName;
                        break;
                    default:
                        formattedName = DriverUtils.convertToFormatName(name);

                }
                System.out.println("formattedName : " + formattedName);
                DriverUtils.slideDown();
                tactSearchContactsPage.getSearchAllContactsTextField().setText(formattedName);

                //get the name location, and click it
                System.out.println("modify the text method");
                DriverUtils.sleep(3);

                DriverUtils.clickOption(tactSearchContactsPage.getNameEditButton(), "contactName", formattedName);
            } else {
                System.out.println("already find the name from recent list");
            }
        });
        And("^Contacts: I add \"(Task|Opportunity)\" action in contact obj page$", (String actionOption) -> {
            log.info("^Contacts: I add " + actionOption + " action in contact obj page$");
            TactContactObjPage tactContactObjPage = new TactContactObjPage();

            switch (actionOption) {
                case "Add Task":
                    tactContactObjPage.getAddTaskActionButton().tap();
                    break;
                case "Add Opportunity":
                    if (Grid.driver().findElementsByXPath(tactContactObjPage.getAddOpportunityActionButton().getLocator()).size() !=0) {
                        DriverUtils.slideDown();
                    }
                    tactContactObjPage.getAddOpportunityActionButton().tap();
                    break;
                default:
                    TactAIAsserts.verifyFalse(true,"Please give a correct String " +
                            "(Add Task|Connect LinkedIn|Add Opportunity|emailIcon|phoneIcon|mapIcon|commentIcon)");
            }
        });
        And("^Contacts: I click \"(email|phone|location|message|linkedIn)\" icon$", (String iconOption) -> {
            log.info("^Contacts: I click " + iconOption + " icon$");
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();
            TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();

            switch (iconOption) {
                case "email":
                    tactContactObjPage.getToolbarEmailButton().tap();
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactMailBoxesPage.getComposeButton());
                    break;
                case "phone":
                    tactContactObjPage.getToolbarPhoneButton().tap();
                    if (DriverUtils.isAndroid() &&
                            Grid.driver().findElementsById(tactAlertsPopUpPage.getAlertsAllowButton().getLocator()).size() != 0) {
                        tactAlertsPopUpPage.getAlertsAllowButton().tap();
                    }
                    DriverUtils.sleep(3);

                    if (DriverUtils.isAndroid() &
                            Grid.driver().findElementsByXPath("//android.widget.ImageButton[@content-desc='End call']").size() != 0){
                        DriverUtils.tapAndroidHardwareBackBtn();
                    }
                    break;
                case "location":
                    tactContactObjPage.getToolbarLocationButton().tap();
                    break;
                case "message":
                    tactContactObjPage.getToolbarMessageButton().tap();
                    break;
                case "linkedIn":
                    tactContactObjPage.getToolbarLinkedInButton().tap();
                    break;
            }


        });
        Then("^Contacts: I back to Contacts Main page from \"([^\"]*)\" page$", (String page) -> {
            log.info("^Contacts: I back to Contacts Main page from " + page + " page$");
            TactContactObjPage tactContactObjPage = new TactContactObjPage();

            switch (page) {
                case "Contact":
                    tactContactObjPage.getProfilePhoneActionButton().tap();
                    break;
                case "Lead":
                    log.info("Not implement yet");
                    break;
                case "Company":
                    log.info("Not implement yet");
                    break;
                default:
                    TactAIAsserts.verifyFalse(true,"Please give a correct String " +
                            "(Contact|Lead|Company)");
            }
        });
        And("^Contact: I search this \"(Contact|Lead|Account|[^\"]*)\" from recent field and select it$", (String objName) -> {
            log.info("^Contact: I search this " + objName + " from recent field and select it$");
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel().getLocator());

            //get the contact location in Recent field
            String searchName;
            switch (objName) {
                case "Contact":
                    searchName = AddDeleteContactSteps.contactName;
                    break;
                case "Lead":
                    searchName = AddDeleteLeadSteps.leadName;
                    break;
                case "Account":
                    searchName = AddDeleteAccountSteps.accountName;
                    break;
                default:
                    searchName = objName;
            }

            String stageLoc = tactSearchContactsPage.getNameEditButton().getLocator().replace("contactName", searchName);
            System.out.println("stageLoc ==> " + stageLoc);

            //click it
            if (DriverUtils.isAndroid())
            {
                tactContactsMainPage.getTactAndroidRecentFavoritesButton().tap();
                DriverUtils.sleep(0.5);
            }
            DriverUtils.sleep(1);
            if (Grid.driver().findElementsByXPath(stageLoc).size() == 0){
                System.out.println("Cannot find the " + stageLoc + ", wait for extral 5 sec");
                DriverUtils.sleep(5);
            }
            Grid.driver().findElementByXPath(stageLoc).click();     //failed here 9/12, 9/16(create account, account event)  9/20 (account SFEvent)
        });
        And("^Contacts: I click back icon after created Salesflow activities$", () -> {
            log.info("^^Contacts: I click back icon after created Salesflow activities$");
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            String backLoc;

            if (DriverUtils.isIOS()) {
                backLoc = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]";
            } else {
                backLoc = "//android.widget.ImageButton[@content-desc='Navigate up']";
            }

            if (DriverUtils.isAndroid()) {
                while (Grid.driver().findElementsByXPath(backLoc).size() != 0) {
                    System.out.println("inside the loop, and still can see the back button");
                    DriverUtils.sleep(0.5);
                    Grid.driver().findElementByXPath(backLoc).click();
                }
            } else {
                Grid.driver().findElementByXPath(backLoc).click();
            }
            DriverUtils.sleep(0.5);
        });
        Then("^Contact: I verity deleted \"(Contact|Lead|Account)\" from search field$", (String objOption) -> {
            log.info("^Contact: I verity deleted " + objOption + " from search field$");
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();

            long beginTime = System.currentTimeMillis();
            System.out.println("1 contacts beginTime " + beginTime);

            System.out.println("waiting for the title");
            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());

            String name;
            switch (objOption) {
                case "Contact":
                    name = AddDeleteContactSteps.contactName;
                    break;
                case "Lead":
                    name = AddDeleteLeadSteps.leadName;
                    break;
                case "Account":
                    name = AddDeleteAccountSteps.accountName;
                    break;
                default:
                    name = null;
                    break;
            }
            System.out.println("name : " + name);

            //search the name from search field - workaround for android only [bug1776]
            if (DriverUtils.isAndroid())
            {
                DriverUtils.sleep(0.5);
                MobileButton androidContactsSearchIconButton = tactSearchContactsPage.getAndroidContactsTabSearchIconButton();
                WebDriverWaitUtils.waitUntilElementIsVisible(androidContactsSearchIconButton);
                androidContactsSearchIconButton.tap();
                if ( (Grid.driver().findElementsByXPath(androidContactsSearchIconButton.getLocator())).size() != 0 ) {
                    System.out.println("did not click it, need to re-click");
//                    DriverUtils.tapXY(1160,182);
                    Grid.driver().findElementById("menu_search").submit();
                    System.out.println("after submit");
                    DriverUtils.sleep(10);
                    Grid.driver().findElementById("menu_search").click();
                    System.out.println("after click");
                    DriverUtils.sleep(10);

                    System.out.println("another way to click");
                    AppiumDriver driver = (AppiumDriver) Grid.driver();

                    TouchActions action = new TouchActions(driver);
                    action.singleTap((WebElement)tactSearchContactsPage.getAndroidContactsTabSearchIconButton());
                    action.perform();

                    DriverUtils.sleep(2);
                    System.out.println("after action click ");
                    DriverUtils.sleep(10);

                }
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchContactsPage.getSearchAllContactsTextField());

                //element id changed <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                System.out.println("name : " + name);
                tactSearchContactsPage.getSearchAllContactsTextField().setText(name);
                DriverUtils.sleep(0.5);

                //Should not see the name in the list
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchContactsPage.getNoContactsFoundLabel());
                System.out.println("now the contacts is invisible");
                DriverUtils.sleep(10);
            }
            else
            {    //verify from recent feild
                String stageLoc = tactSearchContactsPage.getNameEditButton().getLocator().replace("contactName", name);
                System.out.println("stageLoc ==> " + stageLoc);

                WebDriverWaitUtils.waitUntilElementIsInvisible(stageLoc);
                long endTime = System.currentTimeMillis();
                long time = (endTime - beginTime)/(1000*1000);
                System.out.println("sync time " + time );

                System.out.println("now the contacts is invisible");
                DriverUtils.sleep(30);
            }
        });
        And("^Contacts: I search this \"(note|log|task|event|Call)\" from \"(Lead|Contact)\" page and select it$", (String activityOption, String pageOption) -> {
            log.info("^Contacts: I search this " + activityOption + " from " + pageOption + " page and select it$");
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            String activityName = null;

            switch (activityOption) {
                case "note":
                    activityName = TactPinSteps.noteTitle;
                    break;
                case "log":
                    activityName = TactPinSteps.logSubject;
                    break;
                case "task":
                    activityName = TactPinSteps.taskSubject;
                    if (DriverUtils.isIOS() && activityName.contains("oppty")){
                        String clickButtonLoc = "//XCUIElementTypeStaticText[@name=\"oppty_TactName\"]";
                        Grid.driver().findElementByXPath(clickButtonLoc).click();
                        DriverUtils.sleep(0.5);
                    }
                    break;
                case "event":
                    activityName = TactPinSteps.eventSubject;
                    break;
                case "Call":
                    activityName = "Call";
                    break;
            }
            System.out.println("activityName : " + activityName);

            if (!DriverUtils.clickOption(tactContactObjPage.getRecentActivityLabel(), "activityName", activityName)) {
                if (activityOption.equalsIgnoreCase("task") &&
                        Grid.driver().findElementsByXPath(tactContactObjPage.getTaskActivitySeeAllButton().getLocator()).size()!=0 ) {
                    tactContactObjPage.getTaskActivitySeeAllButton().tap();
                    DriverUtils.clickOption(tactContactObjPage.getRecentActivityLabel(), "activityName", activityName);
                } else if (Grid.driver().findElementsByXPath(tactContactObjPage.getRecentActivitySeeAllButton().getLocator()).size()!=0){
                    tactContactObjPage.getRecentActivitySeeAllButton().tap();
                    DriverUtils.clickOption(tactContactObjPage.getRecentActivityLabel(), "activityName", activityName);         //9/16 lead-event
                }
            }

            System.out.println("after search");
            DriverUtils.sleep(5);

        });
        And("^Contacts: I delete this Activity$", () -> {
            log.info("^Contacts: I delete this Activity$");
            TactPinPage tactPinPage = new TactPinPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            if (DriverUtils.isAndroid()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactPinPage.getActivityViewPageMoreOptionsAndroidButton());
                tactPinPage.getActivityViewPageMoreOptionsAndroidButton().tap(tactPinPage.getActivityViewPageDeleteButton());   //0917 failed company-task
                tactPinPage.getActivityViewPageDeleteButton().tap(tactAlertsPopUpPage.getAlertsOKButton());
                tactAlertsPopUpPage.getAlertsOKButton().tap();
            } else {
                if (Grid.driver().findElementsByXPath(tactPinPage.getActivityViewPageDeleteButton().getLocator()).size()==0){
                    DriverUtils.scrollToBottom();
                }
                tactPinPage.getActivityViewPageDeleteButton().tap();//tactAlertsPopUpPage.getDeleteTaskButton());   //0913-failed lead-@P1 @Task  0916 Lead-Task
                tactAlertsPopUpPage.getDeleteTaskButton().tap();
            }
            DriverUtils.sleep(0.5);
        });
        And("^Contacts: I search one \"(Contacts|Leads|New)\" with \"([^\"]*)\" and select it in name$", (String nameType, String nameValue) -> {
            log.info("^Contacts: I search one " + nameType + " with " + nameValue + " and select it in name$");
            TactSearchNamePage tactSearchNamePage = new TactSearchNamePage();

            if (tactSearchNamePage.getNameFieldLabel().getValue().equals("None")) {
                tactSearchNamePage.getNameButton().tap(tactSearchNamePage.getNamePageTitleLabel());

                switch (nameType) {
                    case "Contacts":
                        tactSearchNamePage.getContactsTabButton().tap(tactSearchNamePage.getSearchAllContactsTextField());
                        tactSearchNamePage.getSearchAllContactsTextField().setText(nameValue);
                        break;
                    case "Leads":
                        tactSearchNamePage.getLeadsTabButton().tap(tactSearchNamePage.getSearchAllLeadsTextField());
                        tactSearchNamePage.getSearchAllLeadsTextField().setText(nameValue);
                        break;
                }

                //replace the element value, then click it
                DriverUtils.clickOption(tactSearchNamePage.getNameEditTextField(), "nameValue", nameValue);
            }
        });
        And("^Contacts: I make sure this \"(log|task|note)\" in Call Page$", (String activityType) -> {
            log.info("^Contacts: I make sure this " + activityType + " in Call Page$");
            String activityName = null;
            TactContactObjPage tactContactObjPage = new TactContactObjPage();

            switch (activityType) {
                case "log":
                    activityName = TactPinSteps.logSubject;
                    break;
                case "task":
                    activityName = TactPinSteps.taskSubject;
                    break;
                case "note":
                    activityName = TactPinSteps.noteTitle;
                    break;
            }
            System.out.println("activityName : " + activityName);

            DriverUtils.clickOption(tactContactObjPage.getRecentActivityLabel(), "activityName", activityName);
            tactContactObjPage.getGoBackToCallPageButton().tap(tactContactObjPage.getGoBackToContactsMainPageButton());
            tactContactObjPage.getGoBackToContactsMainPageButton().tap();
        });

    }
}
