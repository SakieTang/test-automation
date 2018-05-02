package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.testcomponents.h5.Alexa.AlexaNavigatePage;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AlexaCommonSteps implements En {


    private AlexaNavigatePage alexaNavigatePage;

    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public AlexaCommonSteps() {

        alexaNavigatePage = new AlexaNavigatePage();

        Then("^AlexaTest: I switch to \"(Test)\" from navigate$", (String navigate) -> {
            log.info("^AlexaTest: I switch to " + navigate + " from navigate$");

            WebDriverWaitUtils.waitUntilElementIsVisible(alexaNavigatePage.getNavAlexaConsolesLink().getLocator());

            //mouse move to "Your Alexa Consoles"
            Actions mouse = new Actions(Grid.driver());
            mouse.moveToElement(alexaNavigatePage.getNavAlexaConsolesLink().getElement()).build().perform();

            //click Tact Automation Skills
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaNavigatePage.getSkillsButton().getLocator());
            alexaNavigatePage.getSkillsButton().click();

            //Click one of Alexa Skills - "Tact Automation"
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaNavigatePage.getTactAutomationSkillButton().getLocator());
            alexaNavigatePage.getTactAutomationSkillButton().click();

            //switch to Test Tab
            WebDriverWaitUtils.waitUntilElementIsVisible(alexaNavigatePage.getTestLink().getLocator());
            alexaNavigatePage.getTestLink().click();

        });
    }
}
