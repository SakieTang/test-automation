package ai.tact.qa.automation.steps;

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

    private TactWelcomePage tactWelcomePage;
    private SFLoginWebviewPage sfLoginWebviewPage;
    private TactAccessSFPage tactAccessSFPage;
    private TactAlertsPopUpPage tactAlertsPopUpPage;
    private TactNavigateTabBarPage tactNavigateTabBarPage;
    private TactCalendarMainPage tactCalendarMainPage;
    private ExchangePage exchangePage;
    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public LoginSteps() {

        tactWelcomePage = new TactWelcomePage();
        sfLoginWebviewPage = new SFLoginWebviewPage();
        tactAccessSFPage = new TactAccessSFPage();
        tactAlertsPopUpPage = new TactAlertsPopUpPage();
        tactNavigateTabBarPage = new TactNavigateTabBarPage();
        tactCalendarMainPage = new TactCalendarMainPage();
        exchangePage = new ExchangePage();

        Given("^Login: I click connect with SF button$", () -> {
            log.info("^Login: I click connect with SF button$");

            WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());
            tactWelcomePage.getConnectWithSFButton().tap();
        });
        And("^Login-Webview: I \"([^\"]*)\" send usage to google chrome and \"([^\"]*)\" sign in Chrome$", (String isSend, String isSignIn) -> {
            log.info("^Login-Webview: I " + isSend + " send usage to google chrome and " + isSignIn + " sign in Chrome$");

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

//            //get Data from UserDetails using raw, and print the out
            List<List<String>> data = userData.raw();
            for (int i=0; i<data.size(); i++)
            {
                log.info(data.get(i).toString());
            }

            String sfAccountName = data.get(0).get(1).toString();
            String sfPWD = data.get(1).get(1).toString();

            if (DriverUtils.isIOS()) {
                //Webview - DriverUtils.convertToWebviewDriver();
                sfAccountName = CustomPicoContainer.getInstance().getUserInfor().getSalesforceIOSAccountName();

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

            String sfAccountName = null;
            String sfPWD = CustomPicoContainer.getInstance().getUserInfor().getSalesforcePwd();

            //iOS
            if (DriverUtils.isIOS()) {
                log.info("IOS");
                sfAccountName = CustomPicoContainer.getInstance().getUserInfor().getSalesforceIOSAccountName();

                WebDriverWaitUtils.waitUntilElementIsVisible( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField userNameSFTextField = new TextField( sfLoginWebviewPage.getUserEmailTextField().getLocator() );
                TextField pwdSFTextField = new TextField( sfLoginWebviewPage.getPwdTextField().getLocator() );

                userNameSFTextField.type(sfAccountName);
                pwdSFTextField.type(sfPWD);

            //Android
            }
            else {
                log.info("Android");
                sfAccountName = CustomPicoContainer.getInstance().getUserInfor().getSalesforceAndroidAccountName();

                WebDriverWaitUtils.waitUntilElementIsVisible(sfLoginWebviewPage.getSfLogoImage());
                sfLoginWebviewPage.getUserEmailTextField().setText(sfAccountName);
                sfLoginWebviewPage.getPwdTextField().sendKeys(sfPWD);
            }

            log.info("salesforce UserName : " + sfAccountName);
        });
        And("^Login-Webview: I \"([^\"]*)\" check remember me$", (String isCheck) -> {
            log.info("^Login-Webview: I " + isCheck + " check remember me$");

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

            Button loginButton = new Button( sfLoginWebviewPage.getLoginButton().getLocator() );
            loginButton.click();


//            if (DriverUtils.isAndroid() ){// && processOption.equals("login")) {
//                DriverUtils.sleep(15);
//                log.info("to check whether syncing display or not");
//                if ( Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingLabel().getLocator()).size() == 0){
//                    DriverUtils.tapAndroidHardwareHomeBtn();
//                    log.info("click home btn");
//                    DriverUtils.sleep(5);
//                    DriverUtils.relaunchApp();
//                    log.info("relaunch app");
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());
//                    tactWelcomePage.getConnectWithSFButton().tap();
//
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactAccessSFPage.getTactSyncingLabel());
//                }
//            }

        });
        When("^Login-Webview: Login with existing user$", () -> {
            log.info("^Login: Login with existing user$");

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

            WebDriverWaitUtils.waitUntilElementIsVisible( tactAccessSFPage.getAddContactToTactTitleLabel() );
            tactAccessSFPage.getAddContactsButton().tap( );
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible( tactAlertsPopUpPage.getTactAccessContactsMsgLabel());
                tactAlertsPopUpPage.getAlertsOKButton().tap();
            }
            else if (Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getAlertsAllowButton().getLocator()).size()!=0) {
//                DriverUtils.sleep(120);
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
                tactAlertsPopUpPage.getAlertsAllowButton().tap();
            }
//            WebDriverWaitUtils.waitUntilElementIsVisible(tactAccessSFPage.getTactSyncingLabel());
        });
        And("^Login: Waiting for Syncing finished in \"([^\"]*)\" process$", (String processOption) -> {
            log.info("^Login: Waiting for Syncing finished in " + processOption + " process$");
            //processOption: onboarding | login

            //login processing begin
            if (Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingLabel().getLocator()).size()!=0)
            {
                log.info("Start waiting for sync...");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingLabel());
                log.info("sync finished");
                DriverUtils.sleep(2);
            }
            if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login"))
            {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
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
            if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login"))
            {
                DriverUtils.sleep(5);
            }
            //exchangeSync
            if (DriverUtils.isIOS() &&
                    processOption.equalsIgnoreCase("login") &&
                    Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactExchangeSyncErrorMsgTitleLabel().getLocator()).size() != 0)
            {
                log.info("Start waiting for \"exchange Reauth Sync\"");
                tactAlertsPopUpPage.getReauthorizeButton().tap(exchangePage.getExchangeTitleLabel());

                String exchangePwdText = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailPwd();
                log.info("exchange pwd " + exchangePwdText);
                exchangePage.getExchangePwdTextField().sendKeys(exchangePwdText);
                exchangePage.getSubmitButton().tap();
                log.info("\"exchange Reauth Sync\" finished");
                DriverUtils.sleep(2);
            }
            if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("onboarding")) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
//            } else if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login")) {
            }
            else if (DriverUtils.isIOS() && processOption.equalsIgnoreCase("login")) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
            }

            //for Android, diff version should waiting for different element during login
            if (DriverUtils.isAndroid())
            {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactCalendarMainPage.getCalendarAddPlusButton());
            }

            DriverUtils.sleep(2);
            log.info(">>>>>>>After login");
            //login processing finish
        });
        And("^Login: Allow all access for Tact$", () -> {
            log.info("^Login: Allow all access for Tact$");

            //notification
            if ( DriverUtils.isIOS() ) {
                log.info("Allow to Send Notifications");
                WebDriverWaitUtils.waitUntilElementIsVisible(tactAlertsPopUpPage.getAlertsAllowButton());
                tactAlertsPopUpPage.getAlertsAllowButton().tap(tactNavigateTabBarPage.getTactCalendarButton());

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
                tactCalendarMainPage.getConnectYourCalendarAndRemindersButton().tap(tactAlertsPopUpPage.getAlertsAllowButton());
                tactAlertsPopUpPage.getAlertsAllowButton().tap();
                DriverUtils.sleep(2);
                DriverUtils.tapAndroidHardwareBackBtn();
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton());
                DriverUtils.sleep(2);
            }
        });
        Then("^Login: workaround for Android app onboarding workflow only$", () -> {
            log.info("^Login: workaround for Android app onboarding workflow only$");

            if(DriverUtils.isAndroid())
            {
                log.info("start waiting 15");
                DriverUtils.sleep(15);
                log.info("after 15 sec waiting");
                String urlPath = "//android.widget.TextView[@text='https://c.na50.content.force.com']";

                if (Grid.driver().findElementsByXPath(urlPath).size() != 0) {
//                if (Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingLabel().getLocator()).size()==0) {
//                if (Grid.driver().findElementsByXPath(tactAccessSFPage.getAddContactsButton().getLocator()).size()==0) {
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