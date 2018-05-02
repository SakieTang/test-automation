package ai.tact.qa.automation.runner.aiRunner;

import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.dataobjects.WebUserInfor;
import com.paypal.selion.annotations.WebTest;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;

import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestingCaseWeb {

    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    @DataProvider(name = "yamlWebDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfWebUser.yaml", WebUserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");

//        Appium.startServer();

        /**
         run this cmd in terminal to restart the server
         java -jar selenium-server-standalone-3.9.1.jar -port 4723
         */
    }

//    @MobileTest(    //iOS
//            locale = "US",
//            appPath= "Applications/Tact Prototype.app",
//            additionalCapabilities = {
//                    "unicodeKeyboard:true","resetKeyboard:true"
//                    ,"noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
//                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
//            }
//    )
//    @Test(description = "Runs Web AI testing", dataProvider = "yamlWebDataProvider", priority = 0)
//    public void testTactRun (WebUserInfor webUserInfor) {
//
//        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);
//
//        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testTactAI.class);
//        testNGCucumberRunner.runCukes();
//
//        DriverUtils.sleep(5);
//
//        Appium.stopServer();
//
//        DriverUtils.sleep(20);
//    }

    @WebTest
    @Test(description = "Runs Web AI testing", dataProvider = "yamlWebDataProvider")//, dependsOnMethods = "testTactRun")
    public void testThreadRun (WebUserInfor webUserInfor) {
        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testThreadAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

    @WebTest
    @Test(description = "Runs Web AI testing", dataProvider = "yamlWebDataProvider")//, dependsOnMethods = "testTactRun")
    public void testSparkRun (WebUserInfor webUserInfor) {
        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testSparkAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

//    @WebTest
//    @Test(description = "Runs Web AI testing", dataProvider = "yamlWebDataProvider")//, dependsOnMethods = "testTactRun")
//    public void testAlexaRun (WebUserInfor webUserInfor) {
//        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);
//
//        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testAlexaAI.class);
//        testNGCucumberRunner.runCukes();
//
//        DriverUtils.sleep(5);
//    }

}
