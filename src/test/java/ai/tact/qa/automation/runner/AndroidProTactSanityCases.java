package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidProTactSanityCases {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String appPackage = "appPackage:com.tactile.tact";
    private static final String appActivity = "appActivity:com.tactile.tact.onboarding.SignInActivity";

    @DataProvider(name="tactUserInfo")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                                        //Android reset the app to true, will need to re-connect with SF
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Cucumber Feature - onboarding", dataProvider = "tactUserInfo", priority = 0)
    private void TactOnboardingFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        DriverUtils.clearChromeData();

        //onboarding
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.OnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();
    }

    //CreateSimpleOpportunity
    @MobileTest(    //Android
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
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactACreateSimpleOpptyFeature(UserInfor userInfor) throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Create Simple Opportunity
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactCreateSimpleOpptyNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //ReAuth
    @MobileTest(    //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactBeReauthExchangeFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //logout
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactLogoutRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
        //login
        testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactLoginRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Calendar
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    ,"noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    ,"fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dependsOnMethods = "TactOnboardingFeature")
    void TactCalendarFeature() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dependsOnMethods = "TactOnboardingFeature")
    void TactContactsFeatureRunCukesNoReset( ) throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
                    //for Alpha only, dev do not need this part
                    , appPackage
                    , appActivity
            }
    )
    //w/ data provider
    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dependsOnMethods = "TactOnboardingFeature")
    void TactLeadFeatureRunCukesNoReset( ) throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactLeadFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //getAppVersion
    @MobileTest(  //iOS
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
    void TactGetAppVersion() throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Email
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactVersionFeatureCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //EditOpportunity
    @MobileTest(    //Android
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
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dataProvider = "tactUserInfo", dependsOnMethods = "TactGetAppVersion")
    void TactEditOpptyFeature(UserInfor userInfor) throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Edit Opportunity
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactEditOpptyFeatureNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Delete
    @MobileTest(    //Android
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
    void TactDeleteAccount() throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.TactDeleteAccountRunCukesNoReset.class);
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
        GenerateReport.getReport(WebDriverPlatform.ANDROID);

        log.info("testNGCucumberRunner.finish(); FINISHED");
    }

}
