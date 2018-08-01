package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.testcomponents.h5.Thread.ThreadNavigatePage;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadCommonSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public ThreadCommonSteps() {

        ThreadNavigatePage threadNavigatePage = new ThreadNavigatePage();

        Then("^ThreadCommon: I switch to \"(Timeline|Categories|Contacts|Meet|Integrations|My To-Dos|Favorities|Mentions|User)\" from navigate$", (String navigate) -> {
            log.info("^ThreadCommon: I switch to " + navigate + " from navigate$");

            WebDriverWaitUtils.waitUntilElementIsVisible(threadNavigatePage.getThreadIconLink().getLocator());

            switch (navigate) {
                case "Timeline":
                    threadNavigatePage.getTimelineIconImage().click();
                    break;
                case "Categories":
                    threadNavigatePage.getCategoriesIconImage().click();
                    break;
                case "Contacts":
                    threadNavigatePage.getContactsIconImage().click();
                    break;
                case "Meet":
                    threadNavigatePage.getMeetIconImage().click();
                    break;
                case "Integrations":
                    threadNavigatePage.getIntegrationsIconImage().click();
                    break;
                case "My To-Dos":
                    threadNavigatePage.getMyToDosIconImage().click();
                    break;
                case "Favorities":
                    threadNavigatePage.getFavoritesIconImage().click();
                    break;
                case "Mentions":
                    threadNavigatePage.getMentionsIconImage().click();
                    break;
                case "User":
                    threadNavigatePage.getUserNegButton().click();
                    break;
            }
        });
    }
}
