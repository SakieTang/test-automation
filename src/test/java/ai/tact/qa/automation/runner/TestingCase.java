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
import ai.tact.qa.automation.utils.dataobjects.UserInfor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestingCase  {

    private TestNGCucumberRunner  testNGCucumberRunner;

    @DataProvider(name="yamlDataProvider")
    public Object[][] getYamlDataProvider() throws IOException {
        FileSystemResource resource = new FileSystemResource("src/main/resources/testData/ListOfUser.yaml", UserInfor.class);
        SeLionDataProvider dataProvider = DataProviderFactory.getDataProvider(resource);
        return dataProvider.getAllData();
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        System.out.println("TestRunner - BeforeClass - setUpClass");
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

        System.out.println("TestRunner - Test - feature");
        System.out.println("Grid.driver().getCapabilities() ==> " + Grid.driver().getCapabilities() + "\n");

        testNGCucumberRunner = new TestNGCucumberRunner(testCase.class);
        testNGCucumberRunner.runCukes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        System.out.println("TestRunner - AfterClass - tearDownClass");

//        Appium.stopServer();
//        if ( !Appium.checkIfServerIsRunnning("4723") ) {
//            System.out.println("Appium does not run");
//        } else {
//            System.out.println("Appium does run, and stop again");
//            Appium.stopServer();
//        }
    }

    @CucumberOptions(
            features = ("src/test/resources/Features/Contacts.feature")
            ,glue = ("ai.tact.qa.automation.steps")
            ,tags={"@createDupContact"}
    )
    public class testCase extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

//    //Assistant
//    @CucumberOptions(
//            features = ("src/test/resources/Features/AssistantAI.feature")
//            ,glue = ("ai/tact/qa/automation/steps")
//            ,tags={"@Help"}
//    )
//    public class TactAssistantFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
//        @Test
//        private void test(){ System.out.println("@Tact Assistant Feature RunCukesNoReset"); }
//
//    }

//    @CucumberOptions(
//            features = ("src/test/resources/Features/TactUserAccount.feature")
//            ,glue = ("ai.tact.qa.automation.steps")
//            ,tags={"@getAppVersion"}
//    )
//    public class TactVersionFeatureCukesNoReset extends AbstractTestNGCucumberTests {
//        @Test
//        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
//
//    }



//        //Lead
//        testNGCucumberRunner = new TestNGCucumberRunner(IOSTestInncerRunCukesClass.TactLeadFeatureRunCukesNoReset.class);
//        testNGCucumberRunner.runCukes();
//        System.out.println("testNGCucumberRunner.finish(); FINISHED");
//        testNGCucumberRunner.finish();
}


