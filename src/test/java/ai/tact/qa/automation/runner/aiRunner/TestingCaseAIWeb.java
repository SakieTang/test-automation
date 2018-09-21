package ai.tact.qa.automation.runner.aiRunner;

import ai.tact.qa.automation.utils.*;
import ai.tact.qa.automation.utils.dataobjects.AlexaResponseInfo;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import ai.tact.qa.automation.utils.dataobjects.WebUserInfor;
import ai.tact.qa.automation.utils.report.GenerateReport;
import com.paypal.selion.annotations.WebTest;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;

import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestingCaseAIWeb {

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

        //delete file
        GenerateReport.deleteAIReport();
    }


    @WebTest
    @Test(groups = "Web", description = "Runs Web AI UserInformation")//, dataProvider = "yamlWebUserInforDataProvider")//, dependsOnMethods = "testTactRun")
    public void testAASparkRun (){//WebUserInfor webUserInfor) {
//        CustomPicoContainer.getInstance().setWebUserInfor(webUserInfor);
        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiCiscoWebex));
        System.out.println(CustomPicoContainer.getInstance().getUser().getSalesforceAccount());


        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testCiscoWebexAI.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(2);
    }

//    @WebTest
//    @Test(groups = "Web", description = "Runs Web AI UserInformation")//, dataProvider = "yamlWebUserInforDataProvider")//, dependsOnMethods = "testTactRun")
//    public void testAAThreadRun (){//WebUserInfor webUserInfor) {
//        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiThread));
//        System.out.println(CustomPicoContainer.getInstance().getUser().getSalesforceAccount());
//
//        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testThreadAI.class);
//        testNGCucumberRunner.runCukes();
//
//        DriverUtils.sleep(2);
//    }
//
//    @WebTest
//    @Test(groups = "Web", description = "Runs Web AI UserInformation")//, dataProvider = "yamlAlexaResponseDataProvider")
//    public void testAlexaAIRun() throws IOException {
//        //Get web User infor
//        CustomPicoContainer.getInstance().setUser(getUserDataFromYaml(UserTestingChannel.aiAlexa));
//        //Get Alexa Response data
//        String alexaResponseFile = "src/main/resources/testData/ListOfAlexaResponse.yaml";
//        FileSystemResource resource = new FileSystemResource(alexaResponseFile, AlexaResponseInfo.class);
//        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
//
//        Hashtable<String, Object> allAlexaResponse = dataProvider.getDataAsHashtable();
//        CustomPicoContainer.getInstance().setAlexaResponseInfos(allAlexaResponse);
//
//        TestNGCucumberRunner testAlexaLogin = new TestNGCucumberRunner(AITestInnerRunCukesClass.testAlexaAILogin.class);
//        testAlexaLogin.runCukes();
//
//        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AITestInnerRunCukesClass.testAlexaAI.class);
//        testNGCucumberRunner.runCukes();
//
//        DriverUtils.sleep(2);
//    }

    @WebTest
    @Test(description = "stop selenium, start appium", alwaysRun = true, dependsOnGroups = "Web")
    public void stopSelenium () {
        //stop Selenium Server
        Selenium.stopServer();
    }

//    @Test(description =  "start appium", alwaysRun = true, dependsOnMethods = "stopSelenium")
//    public void startAppium() {
//        //start Appium Server
//        Appium.startServer();
//    }

//    @Test(description = "start running Tact Assistant from iOS", alwaysRun = true, dependsOnMethods = "startAppium")
//    public void TactAIFeature() {
//        System.out.println(System.getProperty("user.dir"));
//        String currentDir = String.format("%s/%s", System.getProperty("user.dir"), "testNGAIMobile.xml");
//        String command = String.format("mvn test -DsuiteXmlFile=%s", currentDir);
//        System.out.println("currentDir " + currentDir + "'\n command " + command);
//
//        DriverUtils.runCommand(new String[]{"bash", "-c", command});
//    }

//    @AfterClass(alwaysRun = true)
//    public void tearDownClass() throws Exception {
//        log.info("TestRunner - AfterClass - tearDownClass");
//
//        Appium.stopServer();
//
//        log.info("Finished running");
//        log.info("testNGCucumberRunner.finish(); FINISHED");
//
//        GenerateReport.generateAIHtml();
//        GenerateReport.uploadAIReport(false);
//    }

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
        System.out.println("testingchannel : " + testingChannel.toString());
        return (User) allUsers.get(testingChannel.toString());
    }
}
