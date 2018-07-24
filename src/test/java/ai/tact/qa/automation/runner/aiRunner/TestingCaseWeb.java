package ai.tact.qa.automation.runner.aiRunner;

import ai.tact.qa.automation.utils.*;
import ai.tact.qa.automation.utils.dataobjects.AlexaResponseInfo;
import ai.tact.qa.automation.utils.dataobjects.WebUserInfor;
import ai.tact.qa.automation.utils.report.GenerateReport;
import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.annotations.WebTest;
import com.paypal.selion.configuration.Config;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;

import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestingCaseWeb {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    @DataProvider(name = "yamlWebUserInforDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfWebUser.yaml", WebUserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "yamlAlexaResponseDataProvider")
    public Object[][] getYamlAlexaDataProvider() throws  IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfAlexaResponse.yaml", AlexaResponseInfo.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name = "getList")
    public static Object[][] dataProviderGetList() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfAlexaResponse.yaml");
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");
        /**
         run this cmd in terminal to restart the server
         java -jar selenium-server-standalone-3.9.1.jar -port 4723
         */
//        Selenium.restartSelenium("4723");
    }

    @WebTest
    @Test(groups = "Web", description = "Runs Web AI testing", dataProvider = "yamlWebUserInforDataProvider")//, dependsOnMethods = "testTactRun")
    public void testAASparkRun (WebUserInfor webUserInfor) {
        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testSparkAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

    @WebTest
    @Test(groups = "Web", description = "Runs Web AI testing", dataProvider = "yamlWebUserInforDataProvider")//, dependsOnMethods = "testTactRun")
    public void testAAThreadRun (WebUserInfor webUserInfor) {
        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testThreadAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

    @WebTest
    @Test(groups = "Web", description = "Runs Web AI testing", dataProvider = "yamlWebUserInforDataProvider")
    public void testAlexaAIRun(WebUserInfor webUserInfor) throws IOException {
        //Get web User infor
        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);
        //Get Alexa Response data
        String alexaResponseFile = "src/main/resources/testData/ListOfAlexaResponse.yaml";
        FileSystemResource resource = new FileSystemResource(alexaResponseFile, AlexaResponseInfo.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);

        Hashtable<String, Object> allAlexaResponse = dataProvider.getDataAsHashtable();
        CustomPicoContainer.getInstance().setAlexaResponseInfos(allAlexaResponse);

        TestNGCucumberRunner testAlexaLogin = new TestNGCucumberRunner(AITestInnerRunCukesClass.testAlexaAILogin.class);
        testAlexaLogin.runCukes();

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testAlexaAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

    @WebTest
    @Test(description = "stop selenium, start appium", alwaysRun = true, dependsOnGroups = "Web")
    public void stopSelenium () {
        Selenium.stopServer();
        System.out.println("selenium stopped");
    }

    @WebTest
    @Test(description = "stop selenium, start appium", alwaysRun = true, dependsOnMethods = "stopSelenium")
    public void startAppium () {
        Appium.startServer();
        System.out.println("start appium");
    }
////
//    //login Tact AI account
//    @MobileTest(    //iOS
//            locale="US",
////            appPath="Applications/Tact Prototype.app",
//            additionalCapabilities={
//                    "unicodeKeyboard:true", "resetKeyboard:true"
//                    , "noReset:false"    //continue the testing. false, reinstall the app; false, continue use the app
//                    , "fullReset:true"  //restart the iPhone/simulator and install the app
//            }
//    )
//    @Test(description="Runs Cucumber Feature - onboarding", dependsOnMethods = "startAppium")
//    private void TactLoginTactAIAccount() throws InterruptedException {
//        log.info("TestRunner - Test - feature");
//        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");
//
//        //onboarding
//        TestNGCucumberRunner testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.TactLogin.class);
//        testNGCucumberRunner.runCukes();
//    }

    //Tact AI Testing
    @MobileTest(    //iOS
            locale="US",
//            appPath="Applications/Tact Prototype.app",
            additionalCapabilities={
                    "unicodeKeyboard:true", "resetKeyboard:true"
                    , "noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    , "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(description="Runs Cucumber Feature - onboarding"
            , dependsOnMethods = "startAppium"
//            , dependsOnMethods = "TactLoginTactAIAccount"
    )
    private void TactAIFeature() throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Tact AI Testing
        TestNGCucumberRunner testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.testTactAI.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");

//        Selenium.stopServer();
//        if (!Selenium.isSeleniumServerRunning("4723")) {
//            log.info("Selenium does not run");
//        } else {
//            log.info("Selenium does run, and stop again");
//            Selenium.stopServer();
//        }
        Appium.stopServer();

        log.info("Finished running");
        log.info("testNGCucumberRunner.finish(); FINISHED");
    }
}
