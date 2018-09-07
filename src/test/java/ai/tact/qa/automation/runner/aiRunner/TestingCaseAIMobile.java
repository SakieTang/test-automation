package ai.tact.qa.automation.runner.aiRunner;

import ai.tact.qa.automation.utils.*;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.Test;

import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;

import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestingCaseAIMobile {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String DATA_PATH = "%s/%s";

    //Tact AI Feature
    @MobileTest(    //iOS
//            mobileNodeType = "appium",
            locale="US",
//            appPath="Applications/TactNew.app",
            additionalCapabilities={
                    "runLocally:false",
                    "unicodeKeyboard:true", "resetKeyboard:true"
                    , "noReset:true"    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    , "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(description="Runs Cucumber Feature - onboarding"
            , alwaysRun = true
//            , dependsOnMethods = "stopSelenium"
    )
    private void TactAIFeature() throws InterruptedException {
        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiTactiOS));
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");
        TestNGCucumberRunner testNGCucumberRunner;
        //login
        testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.TactLogin.class);
        testNGCucumberRunner.runCukes();

        //Tact AI Testing
        testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.testTactAI.class);
        testNGCucumberRunner.runCukes();

        //logout
        testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.TactLogout.class);
        testNGCucumberRunner.runCukes();
    }

    private User getUserDataFromYaml(UserTestingChannel testingChannel) {
        String fileDir = "src/main/resources/testData/ArrayOfUser.yaml";
        String arrayOfUsers = String.format(DATA_PATH, System.getProperty("user.dir"), fileDir);

        FileSystemResource resource = new FileSystemResource(arrayOfUsers, User.class);
        SeLionDataProvider dataProvider =null;
        try {
            dataProvider=DataProviderFactory.getDataProvider(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Hashtable<String, Object> allUsers = dataProvider.getDataAsHashtable();
        return (User) allUsers.get(testingChannel.toString());
    }
}
