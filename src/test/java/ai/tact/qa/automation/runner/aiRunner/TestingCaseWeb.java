package ai.tact.qa.automation.runner.aiRunner;

import ai.tact.qa.automation.utils.*;
import ai.tact.qa.automation.utils.dataobjects.AlexaResponseInfo;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
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
    private static final String DATA_PATH = "%s/%s";

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
    @Test(groups = "Web", description = "Runs Web AI UserInformation")//, dataProvider = "yamlWebUserInforDataProvider")//, dependsOnMethods = "testTactRun")
    public void testAASparkRun (){//WebUserInfor webUserInfor) {
//        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);
        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiCiscoSpark));
        System.out.println(CustomPicoContainer.getInstance().getUser().getSalesforceAccount());

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testSparkAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

    @WebTest
    @Test(groups = "Web", description = "Runs Web AI UserInformation")//, dataProvider = "yamlWebUserInforDataProvider")//, dependsOnMethods = "testTactRun")
    public void testAAThreadRun (){//WebUserInfor webUserInfor) {
//        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);
        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiThread));
        System.out.println(CustomPicoContainer.getInstance().getUser().getSalesforceAccount());

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testThreadAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

    @WebTest
    @Test(groups = "Web", description = "Runs Web AI UserInformation")//, dataProvider = "yamlAlexaResponseDataProvider")
    public void testAlexaAIRun() throws IOException {
        //Get web User infor
        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiAlexa));
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
        //stop Selenium Server
        Selenium.stopServer();

        //start Appium server
        Appium.startServer();
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
    @Test(description="Runs Cucumber Feature - onboarding"
            , alwaysRun = true
            , dependsOnMethods = "stopSelenium"
    )
    private void TactAIFeature() throws InterruptedException {
        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiTactiOS));
        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");
        TestNGCucumberRunner testNGCucumberRunner;
//        //onboarding
        testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.TactLogin.class);
        testNGCucumberRunner.runCukes();

        //Tact AI Testing
        testNGCucumberRunner=new TestNGCucumberRunner(AITestInnerRunCukesClass.testTactAI.class);
        testNGCucumberRunner.runCukes();

        //logout
        testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.TactLogout.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");

        Appium.stopServer();

        log.info("Finished running");
        log.info("testNGCucumberRunner.finish(); FINISHED");
    }

    private User getUserDataFromYaml(UserTestingChannel testingChannel) {
        String fileDir = "src/main/resources/testData/ArrayOfUser.yaml";
        String arrayOfUsers = String.format(DATA_PATH, System.getProperty("user.dir"), fileDir);

        FileSystemResource resource = new FileSystemResource(arrayOfUsers, User.class);
        SeLionDataProvider dataProvider =null;
        try {
            dataProvider=DataProviderFactory.getDataProvider(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Hashtable<String, Object> allUsers = dataProvider.getDataAsHashtable();
        return (User) allUsers.get(testingChannel.toString());
    }
}
