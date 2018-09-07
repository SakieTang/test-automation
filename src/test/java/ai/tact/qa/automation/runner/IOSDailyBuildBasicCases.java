package ai.tact.qa.automation.runner;


import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import ai.tact.qa.automation.utils.report.GenerateReport;

import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;

import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOSDailyBuildBasicCases {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String DATA_PATH = "%s/%s";
    private static final UserTestingChannel testingChannel = UserTestingChannel.mobileIOS;
//    private static final String appPath = "/Users/sakie/workspace/automation/test-automation/Applications/TactNew.app";
    private static final String appPath = "/Users/sakie/workspace/automation/test-automation/Applications/Tact Prototype1699.app";
//    private static final String appPath = "/Users/sakie/workspace/automation/test-automation/Applications/Tact Beta1699.app";

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
//        Appium.startServer("0.0.0.0","1234","2345");
        Appium.restartAppium();
        log.info("after restart Appium");

        GenerateReport.deleteAllJsonReport();
        System.out.println("after delete jSonReport");
    }

    //onboarding
    @MobileTest(    //iOS
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:false",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:true"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Cucumber Feature - onboarding", dataProvider = "tactUserInfo", priority = 0)
    private void TactOnboardingFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);

        System.out.println("user " + CustomPicoContainer.getInstance().getUser().getSalesforceEmailAddress());

        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //onboarding
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.OnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();

    }

    //getAppVersion
    @MobileTest(  //iOS
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Get Tact Version", dependsOnMethods = "TactOnboardingFeature")
    void TactGetAppVersion() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Email
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactVersionFeatureCukesNoReset.class);
        testNGCucumberRunner.runCukes();

    }

    //AddEmail
    @MobileTest(    //iOS
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Add emails in Tacts", dataProvider = "tactUserInfo", dependsOnMethods = "TactGetAppVersion")
    void TactAddEmailFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //AddEmailAccountFromTab
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.AddEmailFromTabFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //ReAuth
    @MobileTest(    //iOS
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "After add emails, then re_auth exchange", dataProvider = "tactUserInfo", dependsOnMethods = "TactAddEmailFeature")
    void TactReauthExchangeFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //reauth exchange account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactReauthExchangeRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //deleteAccout
    @MobileTest(  //iOS
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Tact - delete Account", alwaysRun = true, dependsOnGroups = "Tact-Sanity")
    void TactDeleteAccount() {

        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactDeleteAccountRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();

    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");

        Appium.stopServer();
        if (!Appium.checkIfServerIsRunnning("4723")) {
            log.info("Appium does not run");
        } else {
            log.info("Appium does run, and stop again");
            Appium.stopServer();
        }

        //report
        log.info("Finished running");
        GenerateReport.getReport(WebDriverPlatform.IOS);
        GenerateReport.generteHtml();
//        GenerateReport.deleteAllJsonReport();

        log.info("testNGCucumberRunner.finish(); FINISHED");
    }
}


