package ai.tact.qa.automation.runner.aiRunner;

import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.WebUserInfor;

import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;

import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestingCaseTact {

    private static final Logger log=LogUtil.setLoggerHandler(Level.ALL);

    @DataProvider(name = "yamlWebDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfWebUser.yaml", WebUserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun=true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");
//        Appium.startServer("0.0.0.0","1234","2345");
//        Appium.restartAppium();
    }

    //Tact AI Testing
    @MobileTest(    //iOS
            locale="US",
//            appPath="Applications/Tact Prototype.app",
            additionalCapabilities={
                    "unicodeKeyboard:true", "resetKeyboard:true"
                    , "noReset:true"    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    , "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(description="Runs Cucumber Feature - onboarding")//, dependsOnMethods = "TactLoginTactAIAccount")
    private void TactAIFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Tact AI Testing
        TestNGCucumberRunner testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.testTactAI.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun=true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");

        //report
        log.info("Finished running");

        log.info("testNGCucumberRunner.finish(); FINISHED");
    }

}

