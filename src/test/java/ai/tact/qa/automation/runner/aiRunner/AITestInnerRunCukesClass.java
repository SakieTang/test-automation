package ai.tact.qa.automation.runner.aiRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import org.testng.annotations.Test;

public class AITestInnerRunCukesClass {

    //login
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/AssistantAI.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@login"}
            , format = {
            "pretty",
            "json:target/report/AI/tactLogin.json"}
    )
    public class TactLogin extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println(">>>>>>@Test login RunCukesTest<<<<<<<");
        }
    }

    //logout
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@logout"}
            , format = {
            "pretty",
            "json:target/report/AI/tactLogout.json"}
    )
    public class TactLogout extends AbstractTestNGCucumberTests {
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
            , format = {
            "pretty",
            "json:target/report/AI/tactiOSAI.json"}
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
            , format = {
            "pretty",
            "json:target/report/AI/threadAI.json"}
    )
    public class testThreadAI extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
    }

    //CiscoWebexAI
    @CucumberOptions(
            features = ("src/test/resources/Features/h5/CiscoWebexAI.feature")
            ,glue = ("ai.tact.qa.automation.steps.h5Steps")
            ,tags={"@P0" +
            "," +
            "@P1" +
            ""}
            , format = {
            "pretty",
            "json:target/report/AI/ciscoWebex.json"}
    )
    public class testCiscoWebexAI extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
    }

    //alexaAI
    @CucumberOptions(
            features = ("src/test/resources/Features/h5/AlexaAI.feature")
            ,glue = ("ai.tact.qa.automation.steps.h5Steps")
            ,tags={"@P0"}
            , format = {
            "pretty",
            "json:target/report/AI/alexaAI.json"}
    )
    public class testAlexaAILogin extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
    }

    //alexaAI
    @CucumberOptions(
            features = ("src/test/resources/Features/h5/AlexaAI.feature")
            ,glue = ("ai.tact.qa.automation.steps.h5Steps")
            ,tags={"@P1"}
            , format = {
            "pretty",
            "json:target/report/AI/alexaAI.json"}
    )
    public class testAlexaAI extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }
    }
}
