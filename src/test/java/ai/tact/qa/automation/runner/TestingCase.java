package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.LogUtil;
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

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        log.info("TestRunner - BeforeClass - setUpClass");
//        Appium.startServer("0.0.0.0","1234","2345");
//        Appium.restartAppium();
    }

    @MobileTest(  //iOS
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true",
                    "noReset:true",    //continue the testing. false, reinstall the app; false, continue use the app
                    "fullReset:false"  //restart the iPhone/simulator and install the app
            }
    )
    //w/ data provider
    @Test(groups = "Tact-login", description = "Runs Tact - login", dataProvider = "yamlDataProvider")//, dependsOnMethods = "TactOnboardingFeature")
    void TactSanityTest(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);

        log.info("TestRunner - Test - feature");
        log.info("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(testCase.class);
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
    }

    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Lead.feature")
            ,glue = ("ai.tact.qa.automation.steps")
            ,tags={"" +
//            "@createLead" +
//            ", " +
            "@P1"}
    )
    public class testCase extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ log.info("@Test Contacts Feature RunCukesTest"); }

    }
}


