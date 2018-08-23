package ai.tact.qa.automation.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import org.testng.annotations.Test;

public class IOSTestInnerRunCukesClass {

    //onboarding
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Onboarding.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@onboarding"}
            , format = {
                    "pretty",
//                    "html:target/report/cucumber-pretty-OnboardingRunCukesFullyReset",
                    "json:target/report/ios/OnboardingRunCukesFullyReset.json"}
    )
    public class OnboardingRunCukesFullyReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Tact Onboarding Run FullyReset");
        }
    }

    //appVersion
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@getAppVersion"}
    )
    public class TactVersionFeatureCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //AddEmail
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Email.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@ConnectGmailTabBar,@ConnectExchangeTabBar"}
            , format = {
                    "pretty",
                    "json:target/report/ios/AddEmailFromTabFeatureRunCukesNoReset.json"}
    )
    public class AddEmailFromTabFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //SendEmail
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Email.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@P1,@P2"}
//            ,tags={"@gmailDraft"}
            , format = {
                "pretty",
                "json:target/report/ios/SendEmailFeatureRunCukesNoReset.json"}
    )
    public class SendEmailFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //logout
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@logout"}
            , format = {
                "pretty",
                "json:target/report/ios/TactLogoutRunCukesNoReset.json"}
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
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@login"}
            , format = {
                "pretty",
                "json:target/report/ios/TactLoginRunCukesNoReset.json"}
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
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@reauthExchange"}
            , format = {
            "pretty",
            "json:target/report/ios/TactReauthExchangeAccountRunCukesNoReset.json"}
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
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={
            "@createContact,"
                +
            "@P1"
            }
            , format = {
                "pretty",
                "json:target/report/ios/TactContactsFeatureRunCukesNoReset.json"}
    )
    public class TactContactsFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }
    }

    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Contacts.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@addLinkedInSalesNavigator"}
            , format = {
            "pretty",
            "json:target/report/ios/TactContactLinkedInFeatureRunCukesNoReset.json"}
    )
    public class TactContactLinkedInFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }
    }

    //Lead
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Lead.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"" +
            "@createLead," +
            "@P1"}
            , format = {
            "pretty",
            "json:target/report/ios/TactLeadFeatureRunCukesNoReset.json"}
    )
    public class TactLeadsFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Contacts Feature RunCukesTest");
        }

    }

    //CreateSimpleOpportunities
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Opportunities.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            , tags={"@createSimpleOpportunity"}
            , format = {
            "pretty",
            "json:target/report/ios/TactCreateOpptyFeatureRunCukesNoReset.json"}
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
            ,glue = ("ai/tact/qa/automation/steps")
            , tags={"@editExistingOppty"}
            , format = {
            "pretty",
            "json:target/report/ios/TactOpptyFeatureRunCukesNoReset.json"}
    )
    public class TactEditOpptyFeatureNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Opportunities Feature RunCukesTest");
        }

    }

    //Delete
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@deleteAccount"}
            , format = {
                "pretty",
                "json:target/report/ios/TactDeleteAccountRunCukesNoReset.json"}
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
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@Event"}
            , format = {
            "pretty",
            "json:target/report/ios/TactCalendarFeatureRunCukesNoReset.json"}
    )
    public class TactCalendarFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact Calendar Feature RunCukesNoReset"); }

    }

    //Assistant
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/AssistantAI.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@Help"}
            , format = {
            "pretty",
            "json:target/report/ios/TactCalendarFeatureRunCukesNoReset.json"}
    )
    public class TactAssistantFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact Assistant Feature RunCukesNoReset"); }

    }

    /**
     * No longer support
     */
    //DataSources
    // from 3.8(1657)+ , can only add sales navigator from any contacts/lead/account from 'in' icon
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/DataSources.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags = {"@addLinkedIn"}
            , format = {
            "pretty",
            "json:target/report/ios/TactDataSourcesFeatureRunCukesNoReset.json"}
    )
    public class TactDataSourcesFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Tact DataSources Feature RunCukesNoReset");
        }

    }
}
