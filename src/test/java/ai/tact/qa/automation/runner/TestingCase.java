package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.*;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestingCase  {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String DATA_PATH = "%s/%s";
    private static final UserTestingChannel testingChannel = UserTestingChannel.mobileIOS;

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @DataProvider(name="tactUserInfo")
    public Object[][] getYamlNewDataProvider() throws IOException {
        String fileDir = "src/main/resources/testData/ArrayOfUser.yaml";
        String arrayOfUsers = String.format(DATA_PATH, System.getProperty("user.dir"), fileDir);

        FileSystemResource resource = new FileSystemResource(arrayOfUsers, User.class);

        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getDataByKeys(new String[] {testingChannel.toString()});
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");
    }

    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    ,"newCommandTimeout:300"
            }
    )
    //w/ data provider
    @Test(description = "Runs Tact - login", dataProvider = "tactUserInfo")
    void TestingCase(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        TestNGCucumberRunner testNGCucumberRunner;

        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

//        testNGCucumberRunner = new TestNGCucumberRunner(testCase.class);
//        testNGCucumberRunner.runCukes();


        //AddEmailAccountFromTab
        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactCreateSimpleOpptyNoReset.class);
        testNGCucumberRunner.runCukes();

        //Grid.driver().getCapabilities() ==> Capabilities [{app=/Users/sakie/workspace/automation/test-automation/Applications/TactNew.app, networkConnectionEnabled=false, noReset=true, mobileNodeType=appium, language=en, databaseEnabled=false, locale=US, version=, deviceName=iPhone X, fullReset=false, platform=MAC, acceptSslCerts=true, platformVersion=11.2, webStorageEnabled=false, locationContextEnabled=false, name=ai.tact.qa.automation.runner.TestingCase:TestingCase()[ai.tact.qa.automation.utils.dataobjects.User@5b04476e], browserName=, takesScreenshot=true, javascriptEnabled=true, unicodeKeyboard=true, platformName=iOS, udid=C536BF74-716A-4003-A917-A41D59DBD6A1, resetKeyboard=true, unexpectedAlertBehaviour=ignore}]
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");
    }

    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Account.feature")
            ,glue = ("ai.tact.qa.automation.steps")
            ,tags={
                "@createAccount"
//                +
//                "@createSimpleOpportunity"
            }
    )
    public class testCase extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ log.info("@Test Contacts Feature RunCukesTest"); }

    }
}


