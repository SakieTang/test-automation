package ai.tact.qa.automation.runner;


import ai.tact.qa.automation.runner.aiRunner.AITestInnerRunCukesClass;
import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOSProTactSanityCases {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    @DataProvider(name="tactUserInfo")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");
//        Appium.startServer("0.0.0.0","1234","2345");
        Appium.restartAppium();
    }

    //onboarding
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    , "noReset:false"    //continue the testing. false, reinstall the app; false, continue use the app
                   , "fullReset:true"  //restart the iPhone/simulator and install the app
//                    "fullReset:false"
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Cucumber Feature - onboarding", dataProvider = "tactUserInfo", priority = 0)
    private void TactOnboardingFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dependsOnMethods = "TactOnboardingFeature")
    void TactACreateSimpleOpptyFeature() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dependsOnMethods = "TactOnboardingFeature")
    void TactCalendarFeature() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Contact object", dependsOnMethods = "TactOnboardingFeature")
    void TactContactsFeature() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Contact object", dependsOnMethods = "TactOnboardingFeature")
    void TactContactLinkedInFeature() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Contact object", dependsOnMethods = "TactOnboardingFeature")
    void TactLeadFeature() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Add emails in Tacts", dataProvider = "tactUserInfo", dependsOnMethods = "TactOnboardingFeature")
    void TactEmailAddedFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "After add emails, then re_auth exchange", dataProvider = "tactUserInfo", dependsOnMethods = "TactEmailAddedFeature")
    void TactReauthExchangeFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Send emails and verify in Tact", dataProvider = "tactUserInfo", dependsOnMethods = "TactEmailAddedFeature")
    void TactSendEmailFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact", description = "Get Tact Version", dependsOnMethods = "TactReauthExchangeFeature")//"TactOnboardingFeature")
    void TactGetAppVersion() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dependsOnMethods = "TactGetAppVersion")
    void TactEditOpptyFeature() throws InterruptedException {
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
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact", description = "Runs Tact - delete Account", alwaysRun = true, dependsOnGroups = "Tact-Sanity")
    void TactDeleteAccount() throws InterruptedException {
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
                    , "noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    , "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(description="Runs Cucumber Feature - onboarding", dependsOnMethods = "TactDeleteAccount")
    private void TactLoginTactAIAccount() throws InterruptedException {
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
                    , "noReset:true"    //continue the testing. false, reinstall the app; false, continue use the app
                    , "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(description="Runs Cucumber Feature - onboarding", dependsOnMethods = "TactLoginTactAIAccount")
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
}


