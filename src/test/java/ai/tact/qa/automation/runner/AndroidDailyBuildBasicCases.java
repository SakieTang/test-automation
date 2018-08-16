package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import ai.tact.qa.automation.utils.report.GenerateReport;

import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;
import com.paypal.selion.platform.grid.Grid;

import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class AndroidDailyBuildBasicCases {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String DATA_PATH = "%s/%s";
    private static final UserTestingChannel testingChannel = UserTestingChannel.mobileAndroid;

    private static final String appPackage = "appPackage:com.tactile.tact";
    private static final String appActivity = "appActivity:com.tactile.tact.onboarding.SignInActivity";
//    private static final String appPath = "/Users/sakie/workspace/automation/test-automation/Applications/TactApplication-alpha-debug1529.apk";
    private static final String appPath = "/Users/sakie/workspace/automation/test-automation/Applications/TactApplication-production-release1529.apk";

//    private User getUserDataFromYaml() {
//        String fileDir = "src/main/resources/testData/ArrayOfUser.yaml";
//        String arrayOfUsers = String.format(DATA_PATH, System.getProperty("user.dir"), fileDir);
//
//        FileSystemResource resource = new FileSystemResource(arrayOfUsers, User.class);
//        SeLionDataProvider dataProvider =null;
//        try {
//            dataProvider=DataProviderFactory.getDataProvider(resource);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Hashtable<String, Object> allUsers = dataProvider.getDataAsHashtable();
//        return (User) allUsers.get(testingChannel.toString());
//    }

    @DataProvider(name="tactUserInfo")
    public Object[][] getYamlNewDataProvider() throws IOException {
        String fileDir = "src/main/resources/testData/ArrayOfUser.yaml";
        String arrayOfUsers = String.format(DATA_PATH, System.getProperty("user.dir"), fileDir);

        FileSystemResource resource = new FileSystemResource(arrayOfUsers, User.class);

        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getDataByKeys(new String[] {testingChannel.toString()});
    }

//    @DataProvider(name="tactUserInfo")
//    public Object[][] getYamlDataProvider() throws IOException {
//        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
//        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
//        return dataProvider.getAllData();
//    }

//=======
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class AndroidDailyBuildBasicCases {
//
//    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
//    private static final String appPackage = "appPackage:com.tactile.tact";
//    private static final String appActivity = "appActivity:com.tactile.tact.onboarding.SignInActivity";
////    private static final String appPath = "/Users/sakie/workspace/automation/test-automation/Applications/TactApplication-alpha-debug1529.apk";
//    private static final String appPath = "/Users/sakie/workspace/automation/test-automation/Applications/TactApplication-production-release1529.apk";
//
//    @DataProvider(name="tactUserInfo")
//    public Object[][] getYamlDataProvider() throws IOException {
//        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
//        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
//        return dataProvider.getAllData();
//    }
//
//>>>>>>> c4185b1... - Stop selenium server stops
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");

        DriverUtils.runCommand(new String[] {"bash", "-c", "adb root"});
        System.out.println("enable Root Access ROMs");
        GenerateReport.deleteAllJsonReport();
        System.out.println("delete json files");
//        Appium.startServer("127.0.0.1","2345","3456");
        Appium.restartAppium();
    }

    //onboarding
    @MobileTest(  //Android
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",
                    "fullReset:false"
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Cucumber Feature - onboarding", dataProvider = "tactUserInfo", priority = 0)
    private void TactOnboardingFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        DriverUtils.clearChromeData();

        //onboarding
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.OnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();
    }

    //AddEmail
    @MobileTest(    //Android
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    //Android reset the app to true, will need to re-connect with SF
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactAddEmailFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //AddExchangeEmailAccountFromTab
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.AddExchangeEmailFromTabFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //ReAuth
    @MobileTest(    //Android
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    //Android reset the app to true, will need to re-connect with SF
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactBeReauthExchangeFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //logout
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactLogoutRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();

        //clear chrome date
        DriverUtils.clearChromeData();

        //login
        testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactLoginRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //getAppVersion
    @MobileTest(  //iOS
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    //Android reset the app to true, will need to re-connect with SF
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "Get Tact Version", dependsOnMethods = "TactOnboardingFeature")
    void TactGetAppVersion() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Email
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactVersionFeatureCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Delete
    @MobileTest(    //Android
            appPath = appPath,
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "TactDataSourcesTest", alwaysRun = true, dependsOnGroups = "Tact-Sanity")
    void TactDeleteAccount() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactDeleteAccountRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
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
        GenerateReport.getReport(WebDriverPlatform.ANDROID);

        log.info("testNGCucumberRunner.finish(); FINISHED");
    }

}
