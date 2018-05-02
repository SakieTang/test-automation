package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.DriverUtils;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import org.testng.annotations.Test;

public class AndroidTestInnerRunCukesClass {

    //onboarding
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Onboarding.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@onboarding"}
            , format = {
            "pretty",
//            "html:target/report/cucumber-pretty-OnboardingRunCukesFullyReset",
            "json:target/report/android/OnboardingRunCukesFullyReset.json"}
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
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@logout"}
            , format = {
            "pretty",
            "json:target/report/android/TactLogoutRunCukesNoReset.json"}
    )
    public class TactLogoutRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test login RunCukesTest");
        }
    }

    //login
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@login"}
            , format = {
            "pretty",
            "json:target/report/android/TactLoginRunCukesNoReset.json"}
    )
    public class TactLoginRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println(">>>>>>@Test login RunCukesTest<<<<<<<");
        }
    }

    //reauthExchangeAccount
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@reauthExchange"}
            , format = {
            "pretty",
            "json:target/report/android/TactLogoutRunCukesNoReset.json"}
    )
    public class TactReauthExchangeRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test login RunCukesTest");
        }
    }

    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Contacts.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
//            ,tags={"@P1"}
            , tags={"" +
                "@P0," +
                "@P1"}
            , format = {
            "pretty",
            "json:target/report/android/TactContactsFeatureRunCukesNoReset.json"}
    )
    public class TactContactsFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }
    }

    //lead
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Lead.feature")
            ,glue = ("ai.tact.qa.automation.steps")
            ,tags={"" +
                "@createLead," +
                "@P1"}
            , format = {
            "pretty",
            "json:target/report/android/TactLeadFeatureRunCukesNoReset.json"}
    )
    public class TactLeadFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //CreateSimpleOpportunities
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Opportunities.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            , tags={"@createSimpleOpportunity"}
            , format = {
            "pretty",
            "json:target/report/android/TactCreateOpptyFeatureRunCukesNoReset.json"}
    )
    public class TactCreateSimpleOpptyNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Opportunities Feature RunCukesTest");
        }

    }

    //EditOpportunities
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Opportunities.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            , tags={"@editExistingOppty"}
            , format = {
            "pretty",
            "json:target/report/android/TactOpptyFeatureRunCukesNoReset.json"}
    )
    public class TactEditOpptyFeatureNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Opportunities Feature RunCukesTest");
        }

    }

    //appVersion
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@getAppVersion"}
    )
    public class TactVersionFeatureCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //Delete
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@deleteAccount"}
            , format = {
            "pretty",
            "json:target/report/android/TactDeleteAccountRunCukesNoReset.json"}
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
            features = ("src/test/resources/Features/mobile/Calendar.feature")
            ,glue = ("ai/tact/qa/automation/steps/mobileSteps")
            ,tags={"@Event"}
            , format = {
            "pretty",
            "json:target/report/android/TactCalendarFeatureRunCukesNoReset.json"}
    )
    public class TactCalendarFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact Calendar Feature RunCukesNoReset"); }

    }
}
