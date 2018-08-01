package ai.tact.qa.automation.steps.h5Steps;

import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkLoginHomePage;
import ai.tact.qa.automation.testcomponents.h5.CiscoSpark.SparkNavigatePage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SparkCommonSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public SparkCommonSteps() {

        SparkNavigatePage sparkNavigatePage = new SparkNavigatePage();

        Then("^SparkCommon: I switch to \"(Home|Team|HandSet)\" from navigate$", (String navigate) -> {
            log.info("^SparkCommon: I switch to " + navigate + " from navigate$");

            WebDriverWaitUtils.waitUntilElementIsVisible(sparkNavigatePage.getSparkHomeButton().getLocator());

            switch (navigate) {
                case "Home":
                    sparkNavigatePage.getSparkHomeButton().click();
                    break;
                case "Team":
                    sparkNavigatePage.getSparkTeamButton().click();
                    break;
                case "Handset":
                    sparkNavigatePage.getSparkHandsetButton().click();
                    break;
            }

            System.out.println("after click home button");
            DriverUtils.sleep(20);
        });
    }
}
