package ai.tact.qa.automation.runner.aiRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

public class AITestInnerRunCukesClass {

    //login
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/AssistantAI.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@login"}
    )
    public class TactLogin extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println(">>>>>>@Test login RunCukesTest<<<<<<<");
        }
    }

    //Tact-iOS-AI
    @CucumberOptions(
            features=("src/test/resources/Features/mobile/AssistantAI.feature")
            , glue=("ai.tact.qa.automation.steps.mobileSteps")
            , tags={"@P1"}
//            , tags={"@P0,@P1"}
    )
    public class testTactAI extends AbstractTestNGCucumberTests {
        @Test
        private void test() {
            System.out.println("@Test Contacts Feature RunCukesTest");
        }
    }

    //threadAI
    @CucumberOptions(
            features = ("src/test/resources/Features/h5/ThreadAI.feature")
            ,glue = ("ai.tact.qa.automation.steps.h5Steps")
            ,tags={"@P0,@P1"}
    )
    public class testThreadAI extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
    }

    //sparkAI
    @CucumberOptions(
            features = ("src/test/resources/Features/h5/CiscoSparkAI.feature")
            ,glue = ("ai.tact.qa.automation.steps.h5Steps")
            ,tags={"@P0,@P1"}
    )
    public class testSparkAI extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
    }

    //alexaAI
    @CucumberOptions(
            features = ("src/test/resources/Features/h5/AlexaAI.feature")
            ,glue = ("ai.tact.qa.automation.steps.h5Steps")
            ,tags={"@P0,@P1"}
    )
    public class testAlexaAI extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
    }
}
