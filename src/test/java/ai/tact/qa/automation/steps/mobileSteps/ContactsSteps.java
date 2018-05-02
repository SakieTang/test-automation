package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactCompany.TactAddNewCompanyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactAddNewContactPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLead.TactAddNewLeadPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearchContactsPage;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.DriverUtils;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactsSteps implements En {

    private TactContactsMainPage tactContactsMainPage;
    private TactContactObjPage tactContactObjPage;
    private TactAddNewContactPage tactAddNewContactPage;
    private TactAddNewLeadPage tactAddNewLeadPage;
    private TactAddNewCompanyPage tactAddNewCompanyPage;
    private TactSearchContactsPage tactSearchContactsPage;
    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public ContactsSteps() {

        tactContactsMainPage = new TactContactsMainPage();
        tactContactObjPage = new TactContactObjPage();
        tactAddNewContactPage = new TactAddNewContactPage();
        tactAddNewLeadPage = new TactAddNewLeadPage();
        tactAddNewCompanyPage = new TactAddNewCompanyPage();
        tactSearchContactsPage = new TactSearchContactsPage();

        Then("^Contacts: I go to create a new \"([^\"]*)\" page$", (String userType) -> {
            log.info("^Contacts: I go to create a new " + userType + " page$");

            switch (userType) {
                case "Contact":
                    //add_contact_button  --> fab_create_contact
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewContactPage.getTactAddNewContactButton());
                    tactAddNewContactPage.getTactAddNewContactButton().tap(tactAddNewContactPage.getNewContactTitleLabel());
                    break;
                case "Lead":
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewLeadPage.getTactAddNewLeadButton());
                    tactAddNewLeadPage.getTactAddNewLeadButton().tap(tactAddNewLeadPage.getNewLeadTitleLabel());
                    log.info("inside Lead");
                    DriverUtils.sleep(30);
                    break;
                case "Company":
                    tactContactsMainPage.getContactsPlusIconButton().tap(tactAddNewCompanyPage.getTactAddNewCompanyButton());
                    tactAddNewCompanyPage.getTactAddNewCompanyButton().tap(tactAddNewCompanyPage.getNewCompanyTitleLabel());
                    break;
                default:
                    TactAIAsserts.verifyFalse(true,"Please give a correct String (Contact|Lead|Company)");
            }

        });
        When("^Contacts: I search one user \"([^\"]*)\" from contacts list and select it$", (String name) -> {
            log.info("^Contacts: I search one user " + name + " from contacts list and select it$");
            //search the name from search field
            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
            if (DriverUtils.isAndroid())
            {
                DriverUtils.sleep(0.5);
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchContactsPage.getAndroidContactsTabSearchIconButton());
                tactSearchContactsPage.getAndroidContactsTabSearchIconButton().tap();
                if ( (Grid.driver().findElementsByXPath(tactSearchContactsPage.getAndroidContactsTabSearchIconButton().getLocator())).size() != 0 ) {
                    System.out.println("did not click it, need to re-click");
                    DriverUtils.tapXY(1160,182);
                }
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSearchContactsPage.getSearchAllContactsTextField());
            }
            //element id changed <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            System.out.println("name : " + name);
            DriverUtils.slideDown();
            tactSearchContactsPage.getSearchAllContactsTextField().setText(name);

            //get the name location, and click it
             String[] parts = name.split(",\\s?");
            if (parts.length > 1)
            {
                name = parts[1] + " " + parts[0];
            }

            log.info("name :" + name);
            System.out.println("modify the text method");
            DriverUtils.sleep(20);

            DriverUtils.clickOption(tactSearchContactsPage.getNameEditButton(), "contactName", name);

//            String nameLoc = tactSearchContactsPage.getNameEditButton().getLocator().replace("contactName", name);
//            log.info("loc is ==> " + nameLoc);
//            DriverUtils.sleep(1);
//
//            Grid.driver().findElementByXPath(nameLoc).click();
        });
        And("^Contacts: I click \"([^\"]*)\" action in contact obj page$", (String actionOption) -> {
            log.info("^Contacts: I click " + actionOption + " action in contact obj page$");

            switch (actionOption) {
                case "Add Task":
                    tactContactObjPage.getAddTaskActionButton().tap();
                    break;
                case "Connect LinkedIn":
//                    if ( Grid.driver().findElementsByXPath(tactContactObjPage.getConnectLinkedInActionButton().getLocator()).size()!=0 ) {
//                        tactContactObjPage.getConnectLinkedInActionButton().tap();
//                    }
                    tactContactObjPage.getLinkedInButton().tap();
                    DriverUtils.sleep(20);
                    break;
                case "Add Opportunity":
                    if ( Grid.driver().findElementsByXPath(tactContactObjPage.getAddOpportunityActionButton().getLocator()).size() !=0 ) {
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
    }
}
