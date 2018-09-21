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

    //contacts - LinkedIn
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

    //contacts
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Contacts.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={
            "@createContact,"
                +
            "@noteCall, @logCallSFTask, "//@TaskCall, "             //Calling Activity
                +
            "@Task,     @Event"                     //delete from client
//            "@note,     @Log,   @SFTask,    @SFEvent" //delete from SF
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

    //Lead
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Lead.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"" +
            "@createLead,"
                +
            "@Task,     @Event"                     //delete from client
//            "@note,     @Log,   @SFTask, @SFEvent"  //delete from SF
            }
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

    //account
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Account.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={
            "@createAccount,"
                    +
            "@note, @Log,   @SFTask, @SFEvent"  //delete from SF
//            "@Task, @Event"                     //delete from client
    }
            , format = {
            "pretty",
            "json:target/report/ios/TactAccountFeatureRunCukesNoReset.json"}
    )
    public class TactAccountFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Account Feature RunCukesTest");
        }
    }

    //Opportunity
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Opportunities.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            , tags={
                    "@createSimpleOpportunity,"
                        +
                    "@editExistingOppty, @deleteExistingOppty,"
                        +
                    "@note, @Log,   @SFTask, @SFEvent"  //delete from SF
//                  "@Task, @Event"                     //delete from client

    }
            , format = {
            "pretty",
            "json:target/report/ios/TactOpptyFeatureRunCukesNoReset.json"}
    )
    public class TactOpptyNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Opportunities Feature RunCukesTest");
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
            , tags={"@editExistingOppty, @deleteExistingOppty"}
            , format = {
            "pretty",
            "json:target/report/ios/TactOpptyFeatureRunCukesNoReset.json"}
    )
    public class TactEditAndDeleteOpptyFeatureNoReset extends AbstractTestNGCucumberTests {
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

    //LearnMore account
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags = {"@LearnMore"}
            , format = {
            "pretty",
            "json:target/report/ios/TactLearnMoreLinkFeatureRunCukesNoReset.json"}
    )
    public class TactLearnMoreLinkFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact LearnMore Feature RunCukesNoReset"); }

    }

    //Sandbox account
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags = {"@login-Sandbox, @logout"}
            , format = {
            "pretty",
            "json:target/report/ios/TactSandboxFeatureRunCukesNoReset.json"}
    )
    public class TactSandboxFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact Sandbox Feature RunCukesNoReset"); }

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
