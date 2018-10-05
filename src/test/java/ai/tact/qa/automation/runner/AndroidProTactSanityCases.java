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
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidProTactSanityCases {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String appPackage = "appPackage:com.tactile.tact";
    private static final String appActivity = "appActivity:com.tactile.tact.onboarding.SignInActivity";
    private static final String timeout = "newCommandTimeout:360";
    private static final String DATA_PATH = "%s/%s";
    private static final UserTestingChannel testingChannel = UserTestingChannel.mobileAndroid;

    @DataProvider(name="tactUserInfo")
    public Object[][] getYamlNewDataProvider() throws IOException {
        String fileDir = "src/main/resources/testData/ArrayOfUser.yaml";
        String arrayOfUsers = String.format(DATA_PATH, System.getProperty("user.dir"), fileDir);

        FileSystemResource resource = new FileSystemResource(arrayOfUsers, User.class);

        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getDataByKeys(new String[] {testingChannel.toString()});
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        log.info("TestRunner - BeforeClass - setUpClass");

        DriverUtils.runCommand(new String[] {"bash", "-c", "adb root"});
        System.out.println("enable Root Access ROMs");
//        Appium.startServer("127.0.0.1","2345","3456");
        Appium.restartAppium();
    }

    //onboarding
    @MobileTest(  //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                                        //Android reset the app to true, will need to re-connect with SF
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
                    , timeout
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

    //Calendar
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
//                    , timeout
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactCalendarFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Calendar
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactCalendarFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //contacts
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
                    , timeout
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactContactsFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactContactsFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //lead
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
                    , timeout
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactLeadFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactLeadFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Account-Company　
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
                    , timeout
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactCompanyFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactAccountFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Opportunity
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dependsOnMethods = "TactOnboardingFeature")
    void TactOpptyFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Create Simple Opportunity, edit, delete opportunity. Add activities
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactOpptyNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //AddEmail
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
//                    , timeout
            }
    )
    @Test(groups = "Tact-Sanity", description = "Add emails in Tacts", dataProvider = "tactUserInfo", alwaysRun = true, dependsOnMethods = "TactOnboardingFeature")//TactLeadFeature")//"")
    void TactEmailAddedFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //AddEmailAccountFromTab
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.AddExchangeEmailFromTabFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(15);
    }

    //ViewEmail
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
//                    , timeout
            }
    )
    @Test(groups = "Tact-Sanity", description = "Add emails in Tacts", dataProvider = "tactUserInfo", alwaysRun = true, dependsOnMethods = "TactEmailAddedFeature")//"")
    void TactVewEmailFieldFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //ViewEmailField
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.ViewEmailFieldFromTabFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //ReAuth
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "Re_auth exchange", dataProvider = "tactUserInfo", dependsOnMethods = "TactEmailAddedFeature")
    void TactBeReauthExchangeFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //reauth exchange account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactReauthExchangeRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //getAppVersion
    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    //Android reset the app to true, will need to re-connect with SF
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "Get Tact Version", alwaysRun = true, dependsOnMethods = "TactBeReauthExchangeFeature")
    void TactGetAppVersion() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //getAppVersion
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactVersionFeatureCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Delete
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Delete account", alwaysRun = true, dependsOnGroups = "Tact-Sanity")
    void TactDeleteAccount() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactDeleteAccountRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //LearnMore
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Sandbox", description = "Learn More Link",  alwaysRun = true, dependsOnGroups = "Tact")
    void TactLearnMore() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactLearnMoreLinkFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Sandbox
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Sandbox", description = "TactDataSourcesTest",  dataProvider = "tactUserInfo", alwaysRun = true, dependsOnGroups = "Tact")
    void TactSandboxAccount(User user) {
        CustomPicoContainer.getInstance().setUser(user);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactSandboxFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");

        String appPackageBundleId = appPackage.split(":")[1];
//        ((AppiumDriver)Grid.driver()).removeApp(appPackageBundleId);

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
