package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.*;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestingCase  {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static final String DATA_PATH = "%s/%s";
    private static final UserTestingChannel testingChannel = UserTestingChannel.mobileIOS;

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

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
    }

    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the UserInformation. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(description = "Runs Tact - login", dataProvider = "tactUserInfo")//, dependsOnMethods = "TactOnboardingFeature")
    void TestingCase(User user) {
        CustomPicoContainer.getInstance().setUser(user);

        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

//        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInnerRunCukesClass.OnboardingRunCukesFullyReset.class);
//        testNGCucumberRunner.runCukes();

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(testCase.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        log.info("TestRunner - AfterClass - tearDownClass");
    }

    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Lead.feature")
            ,glue = ("ai.tact.qa.automation.steps")
            ,tags={"" +
                "@note"
//                + ", " +
//                "@createContact"
            }
    )
    public class testCase extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ log.info("@Test Contacts Feature RunCukesTest"); }

    }
}


