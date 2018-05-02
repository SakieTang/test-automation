package ai.tact.qa.automation.steps;

import ai.tact.qa.automation.testcomponents.mobile.ContactsAPP.ContactsAppPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactsAPPSteps implements En {

    private ContactsAppPage contactsAppPage;
    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public ContactsAPPSteps() {

        contactsAppPage = new ContactsAppPage();

        And("^ContactsAPP: I am in Contacts APP$", () -> {
            log.info("^ContactsAPP: I am in Contacts APP$");

            WebDriverWaitUtils.waitUntilElementIsVisible(contactsAppPage.getContactsTitleLabel());
        });
        Then("^I search for contact's name \"([^\"]*)\"$", (String contactName) -> {
            log.info("^I search for contact's name \"([^\"]*)\"$");

            if (contactName.contains(","))
            { //Testing,fname
                contactName = contactName.split(",")[1] + " " + contactName.split(",")[0];
            }

            contactsAppPage.getSearchTextField().setText(contactName);
            //"Add //XCUIElementTypeStaticText[@name='contactName']"
            log.info("name :" + contactName);
            String contactNameLoc = contactsAppPage.getNameEditButton().getLocator().replace("contactName", contactName);
            log.info("loc is ==> " + contactNameLoc);
            Grid.driver().findElementByXPath(contactNameLoc).click();
        });
        And("^I edit and delete it from Contacts App$", () -> {
            log.info("^I edit and delete it from Contacts App$");

            WebDriverWaitUtils.waitUntilElementIsVisible(contactsAppPage.getEditContactButton());
            contactsAppPage.getEditContactButton().tap(contactsAppPage.getCancelEditButton());

            DriverUtils.scrollToBottom();
            WebDriverWaitUtils.waitUntilElementIsVisible(contactsAppPage.getDeleteContactButton());

            contactsAppPage.getDeleteContactButton().tap(contactsAppPage.getConfirmDeleteContactButton());
            contactsAppPage.getConfirmDeleteContactButton().tap(contactsAppPage.getContactsTitleLabel());
        });


    }
}
