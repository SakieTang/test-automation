package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.Appium;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import com.paypal.selion.annotations.MobileTest;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestingCaseAndroid {

    static final String appPackage = "appPackage:com.tactile.tact.alpha";
    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        System.out.println("TestRunner - BeforeClass - setUpClass");
//        DriverUtils.clearChromeData();
        Appium.restartAppium();
    }

    //onboarding
    @MobileTest(  //Android
            locale = "US",
            additionalCapabilities = {
                    "unicodeKeyboard:true","resetKeyboard:true"
                    , "noReset:true"
                    , "fullReset:false"
                    , appPackage,
                    "appActivity:com.tactile.tact.onboarding.SignInActivity"
            }
    )
    //w/ data provider
    @Test(groups = "Tact-login", description = "Runs Tact - login", dataProvider = "yamlDataProvider", priority = 0)
    void TactSanityTest(UserInfor userInfor) throws InterruptedException {
        CustomPicoContainer.getInstance().setUserInfor(userInfor);


        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        //contacts
        TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(AndroidTestInnerRunCukesClass.OnboardingRunCukesFullyReset.class);
        testNGCucumberRunner.runCukes();


    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        System.out.println("TestRunner - AfterClass - tearDownClass");

//        DriverUtils.clearChromeData();
        System.out.println("clear chrome data");

        Appium.stopServer();
        if ( !Appium.checkIfServerIsRunnning("4723") ) {
            System.out.println("Appium does not run");
        } else {
            System.out.println("Appium does run, and stop again");
            Appium.stopServer();
        }
    }

    @CucumberOptions(
            features = ("src/test/resources/Features/Calendar.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@Event"}
    )
    public class test extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

}


