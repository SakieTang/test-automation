package ai.tact.qa.automation.runner.h5Runner;

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
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
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

public class ThreadCases {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String DATA_PATH = "%s/%s";

//    @DataProvider(name = "yamlWebUserInforDataProvider")
//    public Object[][] getYamlDataProvider() throws IOException {
//        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfWebUser.yaml", WebUserInfor.class);
//        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
//        return dataProvider.getAllData();
//    }

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
    @Test(groups = "Web", description = "Runs Web Thread")
    public void testAAThreadRun (){
        System.out.println("inside the thread run");

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(testCase.class);
        testNGCucumberRunner.runCukes();

        DriverUtils.sleep(5);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");

        Appium.stopServer();

        log.info("Finished running");
        log.info("testNGCucumberRunner.finish(); FINISHED");
    }

//    private User getUserDataFromYaml(UserTestingChannel testingChannel) {
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

    @CucumberOptions(
            features = ("src/test/resources/Features/h5/Thread.feature")
            ,glue = ("ai.tact.qa.automation.steps.h5Steps")
            ,tags={
            "@ThreadDismissMention"
            }
    )
    public class testCase extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

}
