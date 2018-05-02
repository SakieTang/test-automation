package ai.tact.qa.automation.steps;

import ai.tact.qa.automation.utils.LogUtil;
import io.appium.java_client.AppiumDriver;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.Capabilities;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hooks {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public Hooks( ) {}

//    @Before("@MobileTest")
    @Before()
    public void InitializeTest() {

        log.info("Hook - before - relaunch the app");

        Capabilities capabilities = Grid.driver().getCapabilities();
        if ((boolean)capabilities.getCapability("noReset"))
        {
            ((AppiumDriver) Grid.driver()).launchApp();
        }

        log.info("Grid.driver().getCapabilities() ==> " +
                Grid.driver().getCapabilities() + "\n");
    }

    @Before("@TactAIBasicTesting")
    public void cleanFile(){
        System.out.println("inside AI before hook");

        Capabilities capabilities = Grid.driver().getCapabilities();
        if ((boolean)capabilities.getCapability("noReset"))
        {
            ((AppiumDriver) Grid.driver()).launchApp();
        }
    }

    @After
    public void TearDownTest(Scenario scenario){

        if (scenario.isFailed())
        {
            log.info(scenario.getName());
        }
        log.info("Hook - After - Close");
    }
}
