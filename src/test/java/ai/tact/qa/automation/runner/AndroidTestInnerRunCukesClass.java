package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.DriverUtils;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import org.testng.annotations.Test;

public class AndroidTestInnerRunCukesClass {

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

            DriverUtils.rebootEmulator();
            System.out.println("@Tact Onboarding Run FullyReset");
        }
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

    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/Contacts.feature")
            ,glue = ("ai/tact/qa/automation/steps")
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
