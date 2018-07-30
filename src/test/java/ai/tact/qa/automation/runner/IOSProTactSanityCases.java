package ai.tact.qa.automation.runner;


import ai.tact.qa.automation.runner.aiRunner.AITestInnerRunCukesClass;
import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import ai.tact.qa.automation.utils.dataobjects.WebUserInfor;
import ai.tact.qa.automation.utils.report.GenerateReport;

import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.internal.platform.grid.WebDriverPlatform;

import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class IOSProTactSanityCases {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String DATA_PATH = "%s/%s";
    private static final UserTestingChannel testingMobileIOSChannel = UserTestingChannel.mobileIOS;
    private static final UserTestingChannel testingAITactIOSChannel = UserTestingChannel.aiTactiOS;

    @DataProvider(name="tactMobileIOSUserInfo")
    public Object[][] getYamlMobileDataProvider() throws IOException {
        SeLionDataProvider dataProvider = getDataProvider();
        return dataProvider.getDataByKeys(new String[] {testingMobileIOSChannel.toString()});
    }

    @DataProvider(name="tactAITactIOSUserInfo")
    public Object[][] getYamlAIDataProvider() throws IOException {
        SeLionDataProvider dataProvider = getDataProvider();
        return dataProvider.getDataByKeys(new String[] {testingAITactIOSChannel.toString()});
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        log.info("TestRunner - BeforeClass - setUpClass");
//        Appium.startServer("0.0.0.0","1234","2345");
        Appium.restartAppium();
    }

    //onboarding
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
//                    , "noReset:false"    //continue the UserInformation. false, reinstall the app; false, continue use the app
//                   , "fullReset:true"  //restart the iPhone/simulator and install the app
                    , "noReset:true"    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    , "fullReset:false"
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Cucumber Feature - onboarding", dataProvider = "tactMobileIOSUserInfo", priority = 0)
    private void TactOnboardingFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);

//        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.mobileIOS));
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //onboarding
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.OnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();

    }

    //CreateSimpleOpportunity
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dependsOnMethods = "TactOnboardingFeature")
    void TactACreateSimpleOpptyFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Create Simple Opportunity
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactCreateSimpleOpptyNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Calendar
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dependsOnMethods = "TactOnboardingFeature")
    void TactCalendarFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Calendar
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactCalendarFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Contact
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Contact object", dependsOnMethods = "TactOnboardingFeature")
    void TactContactsFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactContactsFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Contact - LinkedIn
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Contact object", dependsOnMethods = "TactOnboardingFeature")
    void TactContactLinkedInFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactContactLinkedInFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //Lead
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Contact object", dependsOnMethods = "TactOnboardingFeature")
    void TactLeadFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactLeadsFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //AddEmail
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Add emails in Tacts", dataProvider = "tactMobileIOSUserInfo", alwaysRun = true, dependsOnMethods = "TactLeadFeature")
    void TactEmailAddedFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //AddEmailAccountFromTab
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.AddEmailFromTabFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //ReAuth
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "After add emails, then re_auth exchange", dataProvider = "tactMobileIOSUserInfo", dependsOnMethods = "TactEmailAddedFeature")
    void TactEmailReauthExchangeFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);
//        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.mobileIOS));
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //reauth exchange account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactReauthExchangeRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //SendEmail
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Send emails and verify in Tact", dataProvider = "tactMobileIOSUserInfo", dependsOnMethods = "TactEmailAddedFeature")
    void TactEmailSendFeature(User user) {
        CustomPicoContainer.getInstance().setUser(user);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //SendEmail
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.SendEmailFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //getAppVersion
    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact", description = "Get Tact Version", dependsOnMethods = "TactOnboardingFeature")
    void TactGetAppVersion() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

//        get App Version
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactVersionFeatureCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //EditOpportunity
    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", alwaysRun = true, dependsOnMethods = "TactEmailSendFeature")
    void TactEditOpptyFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Edit Opportunity
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactEditOpptyFeatureNoReset.class);
        testNGCucumberRunner.runCukes();
    }

    //deleteAccout
    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact", description = "Runs Tact - delete Account", alwaysRun = true, dependsOnGroups = "Tact-Sanity")
    void TactDeleteAccount() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.TactDeleteAccountRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();

    }

    //login Tact AI account
    @MobileTest(    //iOS
            locale="US",
//            appPath="Applications/Tact Prototype.app",
            additionalCapabilities={
                    "unicodeKeyboard:true", "resetKeyboard:true"
                    , "noReset:true"    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    , "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(description="Runs Cucumber Feature - onboarding", dataProvider = "tactAITactIOSUserInfo", dependsOnMethods = "TactDeleteAccount")
    private void TactLoginTactAIAccount(User user) {
        CustomPicoContainer.getInstance().setUser(user);
//        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiTactiOS));
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //onboarding
        TestNGCucumberRunner testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.TactLogin.class);
        testNGCucumberRunner.runCukes();
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
    @Test(description="Runs Cucumber Feature - onboarding", dependsOnMethods = "TactLoginTactAIAccount")
    private void TactAIFeature() {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Tact AI Testing
        TestNGCucumberRunner testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.testTactAI.class);
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
        GenerateReport.getReport(WebDriverPlatform.IOS);
//        GenerateReport.deleteAllJsonReport();

        log.info("testNGCucumberRunner.finish(); FINISHED");
    }

    private SeLionDataProvider getDataProvider () throws IOException {
        String fileDir = "src/main/resources/testData/ArrayOfUser.yaml";
        String arrayOfUsers = String.format(DATA_PATH, System.getProperty("user.dir"), fileDir);

        FileSystemResource resource = new FileSystemResource(arrayOfUsers, User.class);

        return DataProviderFactory.getDataProvider(resource);
    }
}


