package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactCompany.TactAddNewCompanyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactAddNewContactPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLead.TactAddNewLeadPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchContactsPage;
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

        Then("^Contacts: I go to create a new \"([^\"]*)\" page$", (String userType) -> {
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
                case "Company":
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

            String formattedName = DriverUtils.convertToFormatName(name);

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
                    DriverUtils.clickOption(tactSearchContactsPage.getNameEditButton(), "contactName", formattedName);
                } else if (DriverUtils.isIOS() && Grid.driver().findElementsByXPath(stageLoc).size() > 1) {
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

            if (Grid.driver().findElementsByXPath(tactContactsMainPage.getTactContactsTitleLabel().getLocator()).size() != 0) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());

                //search the name from search field
                WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
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
                }
                //element id changed <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                System.out.println("name : " + name);
                DriverUtils.slideDown();
                tactSearchContactsPage.getSearchAllContactsTextField().setText(name);

                //get format name
                String formattedName = DriverUtils.convertToFormatName(name);

                //get the name location, and click it
                System.out.println("modify the text method");
                DriverUtils.sleep(3);

                DriverUtils.clickOption(tactSearchContactsPage.getNameEditButton(), "contactName", formattedName);
            } else {
                System.out.println("already find the name from recent list");
            }
        });
        And("^Contacts: I click \"([^\"]*)\" action in contact obj page$", (String actionOption) -> {
            log.info("^Contacts: I click " + actionOption + " action in contact obj page$");
            TactContactObjPage tactContactObjPage = new TactContactObjPage();

            switch (actionOption) {
                case "Add Task":
                    tactContactObjPage.getAddTaskActionButton().tap();
                    break;
                case "Connect LinkedIn":
//                    if (Grid.driver().findElementsByXPath(tactContactObjPage.getConnectLinkedInActionButton().getLocator()).size()!=0) {
//                        tactContactObjPage.getConnectLinkedInActionButton().tap();
//                    }
                    tactContactObjPage.getLinkedInButton().tap();
                    DriverUtils.sleep(20);
                    break;
                case "Add Opportunity":
                    if (Grid.driver().findElementsByXPath(tactContactObjPage.getAddOpportunityActionButton().getLocator()).size() !=0) {
                        DriverUtils.slideDown();
                    }
                    tactContactObjPage.getAddOpportunityActionButton().tap();
                    break;
                case "emailIcon":
                    tactContactObjPage.getProfileEmailActionButton().tap();
                    break;
                case "phoneIcon":
                    tactContactObjPage.getProfilePhoneActionButton().tap();
                    break;
                case "mapIcon":
                    tactContactObjPage.getMapIconActionButton().tap();
                    break;
                case "commentIcon":
                    tactContactObjPage.getCommentProfileActionButton().tap();
                    break;
                default:
                    TactAIAsserts.verifyFalse(true,"Please give a correct String " +
                            "(Add Task|Connect LinkedIn|Add Opportunity|emailIcon|phoneIcon|mapIcon|commentIcon)");
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
        And("^Contact: I search this \"(Contact|Lead|Account)\" from recent field and select it$", (String objName) -> {
            log.info("^Contact: I search this " + objName + " from recent field and select it$");
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();

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
                    searchName = AddDeleteContactSteps.contactName;
            }
            String stageLoc = tactSearchContactsPage.getNameEditButton().getLocator().replace("contactName", searchName);
            System.out.println("stageLoc ==> " + stageLoc);

            //click it
            if (DriverUtils.isAndroid())
            {
                tactContactsMainPage.getTactAndroidRecentFavoritesButton().tap();
                DriverUtils.sleep(0.5);
            }
            Grid.driver().findElementByXPath(stageLoc).click();
        });
    }
}
