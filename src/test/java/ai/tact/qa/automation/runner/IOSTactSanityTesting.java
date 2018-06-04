package ai.tact.qa.automation.runner;


import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
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

public class IOSTactSanityTesting {

    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");
        Appium.startServer("0.0.0.0","1234","2345");
        Appium.restartAppium();
    }

    //onboarding
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:false",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:true"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Cucumber Feature - onboarding", dataProvider = "yamlDataProvider", priority = 0)
    private void TactOnboardingFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //onboarding
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.OnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();

    }

//    //logout-login
//    @MobileTest(    //iOS
//            locale = "US",
//            additionalCapabilities = {
//                    "unicodeKeyboard:true","resetKeyboard:true",
//                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
//                    "fullReset:false"  //restart the iPhone/simulator and install the app
//            }
//    )
//    @Test(groups = "Tact-Sanity", description = "After add emails, then re_auth exchange", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
//    void TactLogoutLoginFeature(UserInfor userInfor) throws InterruptedException {
//        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
//        log.info("TestRunner - Test - feature");
//        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");
//
//        //logout
//        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLogoutRunCukesNoReset.class);
//        testNGCucumberRunner.runCukes();
//        //login
//        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLoginRunCukesNoReset.class);
//        testNGCucumberRunner.runCukes();
//    }

    //AddEmail
    @MobileTest(    //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    @Test(groups = "Tact-Sanity", description = "Add emails in Tacts", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")//, dependsOnMethods = "TactGetAppVersion")
    void TactAddEmailFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //AddEmailAccountFromTab
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.AddEmailFromTabFeatureRunCukesNoReset.class);
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
    @Test(groups = "Tact-Sanity", description = "After add emails, then re_auth exchange", dataProvider = "yamlDataProvider", dependsOnMethods = "TactAddEmailFeature")
    void TactBeReauthExchangeFeature(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //reauth exchange account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactReauthExchangeRunCukesNoReset.class);
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
    @Test(groups = "Tact-Sanity", description = "Send emails and verify in Tact", dataProvider = "yamlDataProvider", dependsOnMethods = "TactBeReauthExchangeFeature")
    void TactSendEmailFeature(UserInfor userInfor) throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //SendEmail
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.SendEmailFeatureRunCukesNoReset.class);
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
    @Test(groups = "Tact-Sanity", description = "Contact object", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
    void TactContactsFeature(UserInfor userInfor) throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Contact
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactContactsFeatureRunCukesNoReset.class);
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
    @Test(groups = "Tact-Sanity", description = "Calendar Actions", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
    void TactCalendarFeature(UserInfor userInfor) throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Calendar
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactCalendarFeatureRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();
    }

//    //DataSources
//    @MobileTest(  //iOS
//            locale = "US",
//            additionalCapabilities = {
//                    "unicodeKeyboard:true","resetKeyboard:true",
//                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
//                    "fullReset:false"  //restart the iPhone/simulator and install the app
//            }
//    )
//    @Test(groups = "Tact-Sanity", description = "TactDataSourcesTest", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
//    void TactDataSourcesTest(UserInfor userInfor) throws InterruptedException {
//        CustomPicoContainer.getInstance().setUserInfor(userInfor);//userInfor = userInfor;
//        log.info("TestRunner - Test - feature");
//        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");
//
//        //DataSources
//        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactDataSourcesFeatureRunCukesNoReset.class);
//        testNGCucumberRunner.runCukes();
//    }

    //getAppVersion
    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact", description = "Get Tact Version", dataProvider = "yamlDataProvider", dependsOnMethods = "TactOnboardingFeature")
    void TactGetAppVersion(UserInfor userInfor) throws InterruptedException {
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //Email
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactVersionFeatureCukesNoReset.class);
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
    //w/ data provider
    @Test(groups = "Tact", description = "Runs Tact - delete Account", dataProvider = "yamlDataProvider", alwaysRun = true, dependsOnGroups = "Tact-Sanity")
    void TactDeleteAccount(UserInfor userInfor) throws InterruptedException {

        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //delete account
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactDeleteAccountRunCukesNoReset.class);
        testNGCucumberRunner.runCukes();

    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");

        Appium.stopServer();
        if ( !Appium.checkIfServerIsRunnning("4723") ) {
            log.info("Appium does not run");
        } else {
            log.info("Appium does run, and stop again");
            Appium.stopServer();
        }

        //report
        log.info("Finished running");
        GenerateReport.getReport(WebDriverPlatform.IOS);
        GenerateReport.deleteAllJsonReport();

        log.info("testNGCucumberRunner.finish(); FINISHED");
    }
}


