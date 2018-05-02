package ai.tact.qa.automation.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import org.testng.annotations.Test;

public class IOSTestInncerRunCukesClass {

    //onboarding
    @CucumberOptions(
            features = ("src/test/resources/Features/Onboarding.feature"),
            glue = ("ai/tact/qa/automation/steps")
            ,tags={"@onboarding"}
            , format = {
                    "pretty",
                    "html:target/report/cucumber-pretty-OnboardingRunCukesFullyReset",
                    "json:target/report/OnboardingRunCukesFullyReset.json"}
    )
    public class OnboardingRunCukesFullyReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Tact Onboarding Run FullyReset");
        }
    }

    //appVersion
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@getAppVersion"}
    )
    public class TactVersionFeatureCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //Email
    @CucumberOptions(
            features = ("src/test/resources/Features/Email.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@P0"}
            , format = {
                    "pretty",
                    "html:target/report/cucumber-pretty-AddEmailFromTabFeatureRunCukesNoReset",
                    "json:target/report/AddEmailFromTabFeatureRunCukesNoReset.json"}
    )
    public class AddEmailFromTabFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //Email
    @CucumberOptions(
            features = ("src/test/resources/Features/Email.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@P1,@P2"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-SendEmailFeatureRunCukesNoReset",
                "json:target/report/SendEmailFeatureRunCukesNoReset.json"}
    )
    public class SendEmailFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //logout
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature"),
            glue = ("ai/tact/qa/automation/steps")
            ,tags={"@logout"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactLogoutRunCukesNoReset",
                "json:target/report/TactLogoutRunCukesNoReset.json"}
    )
    public class TactLogoutRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test login RunCukesTest");
        }
    }

    //login
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature"),
            glue = ("ai/tact/qa/automation/steps")
            ,tags={"@login"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactLoginRunCukesNoReset",
                "json:target/report/TactLoginRunCukesNoReset.json"}
    )
    public class TactLoginRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println(">>>>>>@Test login RunCukesTest<<<<<<<");
        }
    }

    //reauthExchangeAccount
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature"),
            glue = ("ai/tact/qa/automation/steps")
            ,tags={"@reauthExchange"}
            , format = {
            "pretty",
            "html:target/report/cucumber-pretty-TactLogoutRunCukesNoReset",
            "json:target/report/TactLogoutRunCukesNoReset.json"}
    )
    public class TactReauthExchangeRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test login RunCukesTest");
        }
    }

    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/Contacts.feature")
//        ,plugin = { "pretty:STDOUT","html:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-pretty",
//                    "json:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-json",
//                    "com.cucumber.listener.ExtentCucumberForatter:/Users/sakie/workspace/testingEnv/CucumberBasics/Reports/cucumber-extent/report.html"}
            ,glue = ("ai/tact/qa/automation/steps")
//        , tags={"@smoke,@P0"} "@note,@SFTask,@Event"
//        ,tags={"@Task,@Log,@Event"}
            ,tags={"@createContact,@P1"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactContactsFeatureRunCukesNoReset",
                "json:target/report/TactContactsFeatureRunCukesNoReset.json"}
    )
    public class TactContactsFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }

    //DataSources
    @CucumberOptions(
            features = ("src/test/resources/Features/DataSources.feature")
            ,glue = ("ai/tact/qa/automation/steps")
//        ,tags={"@P0"}
            ,tags = {"@addLinkedIn"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactDataSourcesFeatureRunCukesNoReset",
                "json:target/report/TactDataSourcesFeatureRunCukesNoReset.json"}
    )
    public class TactDataSourcesFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Tact DataSources Feature RunCukesNoReset");
        }

    }

    //Delete
    @CucumberOptions(
            features = ("src/test/resources/Features/TactUserAccount.feature"),
            glue = ("ai/tact/qa/automation/steps")
            ,tags={"@deleteAccount"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactDeleteAccountRunCukesNoReset",
                "json:target/report/TactDeleteAccountRunCukesNoReset.json"}
    )
    public class TactDeleteAccountRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println(">>>>>>@Test login RunCukesTest<<<<<<<");
        }
    }

    //Lead
    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/Contacts.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@createLead"}
            , format = {
                "pretty",
                "html:target/report/cucumber-pretty-TactLeadFeatureRunCukesNoReset",
                "json:target/report/TactLeadFeatureRunCukesNoReset.json"}
    )
    public class TactLeadFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }

    //Calendar
    //Event
    @CucumberOptions(
            features = ("src/test/resources/Features/Calendar.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@Event"}
            , format = {
            "pretty",
            "html:target/report/cucumber-pretty-TactCalendarFeatureRunCukesNoReset",
            "json:target/report/TactCalendarFeatureRunCukesNoReset.json"}
    )
    public class TactCalendarFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact Calendar Feature RunCukesNoReset"); }

    }
}
