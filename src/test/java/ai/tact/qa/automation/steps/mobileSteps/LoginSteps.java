package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.*;
import ai.tact.qa.automation.testcomponents.mobile.TactCalendar.TactCalendarMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.ExchangePage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.html.Button;
import com.paypal.selion.platform.html.CheckBox;
import com.paypal.selion.platform.html.TextField;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;
import cucumber.api.DataTable;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public LoginSteps() {

        Given("^Login: I click connect with SF button$", () -> {
            log.info("^Login: I click connect with SF button$");
            TactWelcomePage tactWelcomePage = new TactWelcomePage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());
            tactWelcomePage.getConnectWithSFButton().tap();
        });
        And("^Login-Webview: I \"([^\"]*)\" send usage to google chrome and \"([^\"]*)\" sign in Chrome$", (String isSend, String isSignIn) -> {
            log.info("^Login-Webview: I " + isSend + " send usage to google chrome and " + isSignIn + " sign in Chrome$");
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();

            if (Grid.driver().findElementsById(sfLoginWebviewPage.getChromeSendReportCheckboxButton().getLocator()).size() != 0)
            {
                if (!isSend.equalsIgnoreCase("do"))
                {
                    sfLoginWebviewPage.getChromeSendReportCheckboxButton().tap();
                }
                sfLoginWebviewPage.getChromeAcceptContinueButton().tap(sfLoginWebviewPage.getChromeSignInNoThanksButton());

                if (!isSignIn.equalsIgnoreCase("do"))
                {
                    sfLoginWebviewPage.getChromeSignInNoThanksButton().tap(sfLoginWebviewPage.getSfLogoImage());
                }
            }
        });
        And("^Login-Webview: I enter the user email address with dataTable$", (DataTable userData) -> {
            log.info("^Login: I enter the user email address with dataTable$");
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();

//            //get Data from UserDetails using raw, and print the out
            List<List<String>> data = userData.raw();
            for (int i=0; i<data.size(); i++)
            {
                log.info( i + " " + data.get(i).toString());
            }

            String sfAccountName = data.get(0).get(0).toString();
            String sfPWD = data.get(0).get(1).toString();

            System.out.println("account " + sfAccountName);
            System.out.println("pwd " + sfPWD);

            if (DriverUtils.isIOS()) {

                WebDriverWaitUtils.waitUntilElementIsVisible( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField userNameSFTextField = new TextField( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField pwdSFTextField = new TextField( sfLoginWebviewPage.getPwdTextField().getLocator() );

                userNameSFTextField.type(sfAccountName);
                pwdSFTextField.type(sfPWD);
            }
            else {
                sfAccountName = CustomPicoContainer.getInstance().getUserInfor().getSalesforceAndroidAccountName();

                WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getSfLogoImage());
                sfLoginWebviewPage.getUserEmailTextField().setText(sfAccountName);
                sfLoginWebviewPage.getPwdTextField().sendKeys(sfPWD);
            }

            log.info("salesforce UserName : " + sfAccountName);
        });
        And("^Login-Webview: I enter the user email address$", () -> {
            log.info("^Login: I enter the user email address$");
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();

            String sfAccountName = CustomPicoContainer.getInstance().getUser().getSalesforceAccount();
            String sfPWD = CustomPicoContainer.getInstance().getUser().getSalesforceAccountPwd();

            //iOS
            if (DriverUtils.isIOS()) {
                log.info("IOS");

//                sfAccountName = CustomPicoContainer.getInstance().getUserInfor().getSalesforceIOSAccountName();
//                sfPWD = CustomPicoContainer.getInstance().getUserInfor().getSaleforceIOSPwd();

                WebDriverWaitUtils.waitUntilElementIsVisible( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField userNameSFTextField = new TextField( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField pwdSFTextField = new TextField( sfLoginWebviewPage.getPwdTextField().getLocator() );

                userNameSFTextField.type(sfAccountName);
                pwdSFTextField.type(sfPWD);

            //Android
            }
            else {
                log.info("Android");
//                sfAccountName = CustomPicoContainer.getInstance().getUserInfor().getSalesforceAndroidAccountName();
//                sfPWD = CustomPicoContainer.getInstance().getUserInfor().getSalesforceAndroidPwd();

                WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getSfLogoImage());
                sfLoginWebviewPage.getUserEmailTextField().setText(sfAccountName);
                sfLoginWebviewPage.getPwdTextField().sendKeys(sfPWD);
            }

            log.info("salesforce UserName : " + sfAccountName);
        });
        And("^Login-Webview: I \"([^\"]*)\" check remember me$", (String isCheck) -> {
            log.info("^Login-Webview: I " + isCheck + " check remember me$");
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();

            if (isCheck.equalsIgnoreCase("do"))
            {
                if (DriverUtils.isIOS()) {
                    CheckBox rememberMeCheckBox = new CheckBox( sfLoginWebviewPage.getRememberMeCheckBoxButton().getLocator() );
                    rememberMeCheckBox.check();
                }
                else {
                    sfLoginWebviewPage.getRememberMeCheckBoxButton().tap();
                }
            }
        });
        And("^Login-Webview: I click login button in \"([^\"]*)\" process$", (String processOption) -> {
            log.info("^Login: I click login button in " + processOption + " process$");
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();

            Button loginButton = new Button( sfLoginWebviewPage.getLoginButton().getLocator() );
            loginButton.click();
        });
        When("^Login-Webview: Login with existing user$", () -> {
            log.info("^Login: Login with existing user$");
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();

            //Webview - DriverUtils.convertToWebviewDriver();

            WebDriverWaitUtils.waitUntilElementIsVisible( sfLoginWebviewPage.getTactLoginWithExistingUserButton() );
            log.info("before get the button");
            Button loginWithExistingUserButton = new Button( sfLoginWebviewPage.getTactLoginWithExistingUserButton().getLocator() );

            log.info("local : " + loginWithExistingUserButton.getLocator());

            loginWithExistingUserButton.click();
            log.info("After click the existing button");
            DriverUtils.sleep(5);
        });
        Then("^Login: Allow Tact to access salesforce user data$", () -> {
            log.info("^Login: Allow Tact to access salesforce user data$");
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            DriverUtils.scrollToBottom();
            if (DriverUtils.isIOS() && Grid.driver().findElementsById(sfLoginWebviewPage.getTactAccessAllowButton().getLocator()).size() !=0)
            {
                log.info("This is the 1st time to access SF from Tact App");
                WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getTactAccessAllowButton());
                sfLoginWebviewPage.getTactAccessAllowButton().tap();
            }
        });
        Then("^Login: After SF connected, then Add Contacts to Tact$", () -> {
            log.info("^Login: After SF connected, then Add Contacts to Tact$");
            TactAccessSFPage tactAccessSFPage = new TactAccessSFPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            WebDriverWaitUtils.waitUntilElementIsVisible( tactAccessSFPage.getAddContactToTactTitleLabel() );
            tactAccessSFPage.getAddContactsButton().tap( );
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible( tactAlertsPopUpPage.getTactAccessContactsMsgLabel());
                tactAlertsPopUpPage.getAlertsOKButton().tap();
            }
            else {
                DriverUtils.sleep(4);
                if (Grid.driver().findElementsById(tactAlertsPopUpPage.getAlertsAllowButton().getLocator()).size()!=0) {
                    log.info("find the allow button");
//                DriverUtils.sleep(120);
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
                    tactAlertsPopUpPage.getAlertsAllowButton().tap();
                }
                else {
                    System.out.println("not find the elements");
                }
            }
//            WebDriverWaitUtils.waitUntilElementIsVisible(tactAccessSFPage.getTactSyncingLabel());
        });
        And("^Login: Waiting for Syncing finished in \"([^\"]*)\" process$", (String processOption) -> {
            log.info("^Login: Waiting for Syncing finished in " + processOption + " process$");
            TactAccessSFPage tactAccessSFPage = new TactAccessSFPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();
            ExchangePage exchangePage = new ExchangePage();

            //processOption: onboarding | login
            if (processOption.equalsIgnoreCase("onboarding") ||
                    Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingLabel().getLocator()).size() != 0 ) {
                //login processing begin
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAccessSFPage.getTactSyncingLabel());
                log.info("Start waiting for sync...");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingLabel());
                log.info("sync finished");
                DriverUtils.sleep(2);
            }

            if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login"))
            {
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
            }
            log.info("Tact Syncing is done");

            //Syncing data to your phone.
            if (DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel().getLocator()).size()!=0) {
                log.info("Start waiting for \"Syncing data to your phone.\"");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingItemsLabel());
                log.info("\"Syncing data to your phone.\" finished");
            }
            else {
                log.info("Tact Syncing Data to Phone is done");
            }
            if (processOption.equalsIgnoreCase("login"))
            {
                System.out.println("now in login process, and waiting for 15 sec for Android");
                if(DriverUtils.isAndroid()){
                    DriverUtils.sleep(15);
                }
                DriverUtils.sleep(15);
            }
            //exchangeSync
//            if (DriverUtils.isIOS() &&
            if (    processOption.equalsIgnoreCase("login") &&
                    Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactExchangeSyncErrorMsgTitleLabel().getLocator()).size() != 0)
            {
                log.info("Start waiting for \"exchange Reauth Sync\"");
                tactAlertsPopUpPage.getReauthorizeButton().tap(exchangePage.getExchangeTitleLabel());

                String exchangePwdText = CustomPicoContainer.getInstance().getUser().getExchangeEmailPwd();

                log.info("exchange pwd " + exchangePwdText);
                exchangePage.getExchangePwdTextField().sendKeys(exchangePwdText);
                exchangePage.getSubmitButton().tap();
                log.info("\"exchange Reauth Sync\" finished");
                DriverUtils.sleep(10);


                //workaround for "no policy" error msg (bug5580)
                String noPolicyLoc;
                if (DriverUtils.isIOS()) {
                    noPolicyLoc = "//XCUIElementTypeStaticText[@name=\"There is no policy for this client.\"]";
                } else {
                    noPolicyLoc = "//android.widget.TextView[@text='There is no policy for this client.']";
                }
                if (Grid.driver().findElementsByXPath(noPolicyLoc).size() !=0) {
                    System.out.println("see the 'no policy'");
                    exchangePage.getSubmitButton().tap();
                    DriverUtils.sleep(2);
                    tactAlertsPopUpPage.getAlertsAllowButton().tap();
                } else {
                    System.out.println("not see the 'no policy'");
                }
                System.out.println("finish Connect the exchange account");
                DriverUtils.sleep(15);

            } else {
                log.info("Tact no reauth for exchange email");
            }

            //Allow "Tact" would like to send you notification in iOS
            if (DriverUtils.isIOS()) {
                if (Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getAlertsAllowButton().getLocator()).size() != 0) {
                    tactAlertsPopUpPage.getAlertsAllowButton().tap();
                }
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
            }
//            if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("onboarding")) {
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
//            }
//            else if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login")) {
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
//            }

            //for Android, diff version should waiting for different element during login  - for old UI version
            if (DriverUtils.isAndroid())
            {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactCalendarButton());
            }

            DriverUtils.sleep(2);
            log.info(">>>>>>>After login");
            //login processing finish
        });
        And("^Login: Allow all access for Tact$", () -> {
            log.info("^Login: Allow all access for Tact$");
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();
            TactCalendarMainPage tactCalendarMainPage = new TactCalendarMainPage();

            //notification
            if (DriverUtils.isIOS()) {
//                log.info("Allow to Send Notifications");
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
//                tactAlertsPopUpPage.getAlertsAllowButton().tap(tactNavigateTabBarPage.getTactCalendarButton());

                //switch to Calendar tab
                tactNavigateTabBarPage.getTactCalendarButton().tap(tactCalendarMainPage.getConnectYourCalendarAndRemindersButton());

                //Connect Calendars and Reminders
                log.info("Connect Calendars and Reminders");
                tactCalendarMainPage.getConnectYourCalendarAndRemindersButton().tap(tactAlertsPopUpPage.getAlertsOKButton());
                tactAlertsPopUpPage.getAlertsOKButton().tap(tactCalendarMainPage.getConnectCalendarTitleLabel());
                //Connect Calendars
                log.info("Calenders");
                tactCalendarMainPage.getCalendarRemindersDoneButton().tap(tactAlertsPopUpPage.getAlertsOKButton());
                tactAlertsPopUpPage.getAlertsOKButton().tap(tactCalendarMainPage.getConnectRemindersTitleLabel());
                //Connect Reminders
                log.info("Reminders");
                tactCalendarMainPage.getCalendarRemindersDoneButton().tap(tactNavigateTabBarPage.getTactCalendarButton());
            }
            else {    //Android - Connect Calendars
                log.info("Connect Calendars and Reminders");

                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactCalendarButton());
                tactNavigateTabBarPage.getTactCalendarButton().tap(tactCalendarMainPage.getConnectYourCalendarAndRemindersButton());

                tactCalendarMainPage.getConnectYourCalendarAndRemindersButton().tap();
                DriverUtils.sleep(3);
                if (Grid.driver().findElementsById(tactAlertsPopUpPage.getAlertsAllowButton().getLocator()).size() != 0 ) {
                    tactAlertsPopUpPage.getAlertsAllowButton().tap();
                }
                DriverUtils.sleep(2);
                DriverUtils.tapAndroidHardwareBackBtn();
                DriverUtils.sleep(2);
            }
        });
        Then("^Login: workaround for Android app onboarding workflow only$", () -> {
            log.info("^Login: workaround for Android app onboarding workflow only$");
            TactWelcomePage tactWelcomePage = new TactWelcomePage();
            SFLoginWebviewPage sfLoginWebviewPage = new SFLoginWebviewPage();

            if(DriverUtils.isAndroid())
            {
                log.info("start waiting 15");
                DriverUtils.sleep(15);
                log.info("after 15 sec waiting");
                String urlPath = "//android.widget.TextView[@text='https://c.na50.content.force.com']";

                if (Grid.driver().findElementsByXPath(urlPath).size() != 0) {
                    log.info("workaround");
                    WebDriverWaitUtils.waitUntilElementIsInvisible(sfLoginWebviewPage.getSfLoginUrlLabel());
                    WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getSfLoginPageCloseXButton());

                    sfLoginWebviewPage.getSfLoginPageCloseXButton().tap(tactWelcomePage.getConnectWithSFButton());
                    tactWelcomePage.getConnectWithSFButton().tap();
                }
                else {
                    log.info("do not need to do workaround");
                }
            }
        });
    }

}
