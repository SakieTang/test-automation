package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.utils.DriverUtils;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import org.testng.annotations.Test;

public class AndroidTestInnerRunCukesClass {

    //onboarding
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Onboarding.feature")
            ,glue = ("ai/tact/qa/automation/steps")
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
            ,glue = ("ai/tact/qa/automation/steps")
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
            ,glue = ("ai/tact/qa/automation/steps")
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
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@reauthExchange"}
            , format = {
            "pretty",
            "json:target/report/android/TactReauthExchangeAccountRunCukesNoReset.json"}
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
//            ,tags={"@P1"}
            , tags={
                "@createContact,"
                        +
                "@SFNoteAndroid, "            //Android only - @SFNoteAndroid, @logAndroid,
                        +
                "@SFNoteCall,"                               //Calling Activity   -@SFNoteCall, @logCallSFTask, @LocTaskCall,
                        +
                "@Log,    @SFEvent"  //delete from SF
//                "@SFNote,   @Log,       @SFTask,    @SFEvent"  //delete from SF
//                "@LocNote,  @LocTask,   @LocEvent"    //delete from client
                }
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
                "@createLead,"
                    +
                "@logAndroid,"            //Android only - @SFNoteAndroid, @logAndroid,
                    +
                "@logCallSFTask,"                        //Calling Activity - @SFNoteCall,    @logCallSFTask, @LocTaskCall,
                      +
                "@LocNote,  @LocTask,     @LocEvent"    //delete from client
//                "@SFNote,   @Log,   @SFTask,    @SFEvent"  //delete from SF
                }
            , format = {
            "pretty",
            "json:target/report/android/TactLeadFeatureRunCukesNoReset.json"}
    )
    public class TactLeadFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //account
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Account.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={
            "@createAccount,"
                    +
            "@SFNoteAndroid,"            //Android only - @SFNoteAndroid, @logAndroid,
                    +
            "@LocTaskCall, "                           //Calling Activity - @SFNoteCall,    @logCallSFTask, @LocTaskCall,
                    +
            "@LocNote,  @LocEvent"           //delete from client
//            "@LocNote,  @LocTask, @LocEvent"           //delete from client
//            "@SFNote,   @Log,     @SFTask,  @SFEvent"  //delete from SF
    }
            , format = {
            "pretty",
            "json:target/report/android/TactAccountFeatureRunCukesNoReset.json"}
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
                    "@logAndroid,"            //Android only - @SFNoteAndroid, @logAndroid,
                            +
                    "@SFNote,   @Log,       @SFTask,    @SFEvent"  //delete from SF
//                    "@LocNote,  @LocTask,   @LocEvent"                     //delete from client
    }
            , format = {
            "pretty",
            "json:target/report/android/TactOpptyFeatureRunCukesNoReset.json"}
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
            ,glue = ("ai/tact/qa/automation/steps")
            , tags={"@editExistingOppty, @deleteExistingOppty"}
            , format = {
            "pretty",
            "json:target/report/android/TactOpptyFeatureRunCukesNoReset.json"}
    )
    public class TactEditAndDeleteOpptyFeatureNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){
            System.out.println("@Test Opportunities Feature RunCukesTest");
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
            ,tags={"@ConnectExchangeTabBar"}
            , format = {
            "pretty",
            "json:target/report/android/AddEmailFromTabFeatureRunCukesNoReset.json"}
    )
    public class AddExchangeEmailFromTabFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //ViewEmailFields
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/Email.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@ViewExchangeEmail"}
            , format = {
            "pretty",
            "json:target/report/android/ViewEmailFromTabFeatureRunCukesNoReset.json"}
    )
    public class ViewEmailFieldFromTabFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Test Contacts Feature RunCukesTest"); }

    }

    //Delete
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
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
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@Event"}
            , format = {
            "pretty",
            "json:target/report/android/TactCalendarFeatureRunCukesNoReset.json"}
    )
    public class TactCalendarFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact Calendar Feature RunCukesNoReset"); }

    }

    //LearnMore account
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@LearnMore"}
            , format = {
            "pretty",
            "json:target/report/android/TactLearnMoreLinkFeatureRunCukesNoReset.json"}
    )
    public class TactLearnMoreLinkFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact LearnMore Feature RunCukesNoReset"); }

    }

    //Sandbox account
    @CucumberOptions(
            features = ("src/test/resources/Features/mobile/TactUserAccount.feature")
            ,glue = ("ai/tact/qa/automation/steps")
            ,tags={"@login-Sandbox, @logout"}
            , format = {
            "pretty",
            "json:target/report/android/TactSandboxFeatureRunCukesNoReset.json"}
    )
    public class TactSandboxFeatureRunCukesNoReset extends AbstractTestNGCucumberTests {
        @Test
        private void test(){ System.out.println("@Tact Sandbox Feature RunCukesNoReset"); }

    }
}
