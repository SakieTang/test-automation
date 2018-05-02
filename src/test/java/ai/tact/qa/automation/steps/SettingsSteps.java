package ai.tact.qa.automation.steps;

import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.*;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.html.Button;
import com.paypal.selion.platform.html.Label;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import com.paypal.selion.platform.html.TextField;
import cucumber.api.java8.En;

import io.appium.java_client.AppiumDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingsSteps implements En {

    private TactAlertsPopUpPage tactAlertsPopUpPage;
    private TactSettingsPage tactSettingsPage;
    private TactSourcesPage tactSourcesPage;
    private ExchangePage exchangePage;
    private GmailPage gmailPage;
    private LinkedInPage linkedInPage;
    private TactNavigateTabBarPage tactNavigateTabBarPage;
    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public SettingsSteps() {

        tactAlertsPopUpPage = new TactAlertsPopUpPage();
        tactSettingsPage = new TactSettingsPage();
        tactSourcesPage = new TactSourcesPage();
        exchangePage = new ExchangePage();
        gmailPage = new GmailPage();
        linkedInPage = new LinkedInPage();
        tactNavigateTabBarPage = new TactNavigateTabBarPage();

        When("^Settings: I switch to \"([^\"]*)\" option in settings page$", (String settingsOption) -> {
            log.info("^Setting: I switch to " + settingsOption + " option in settings page$");

            if (DriverUtils.isIOS())
            {
                switch (settingsOption) {
                    case "Account":
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getIosAccountButton());
                        tactSettingsPage.getIosAccountButton().tap(tactSettingsPage.getDeleteAccountButton());
                        break;
                    case "Data Sources":
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getIosDataSourcesButton());
                        tactSettingsPage.getIosDataSourcesButton().tap();
                        break;
                    case "Notification Settings":
//                    WebDriverWaitUtils.waitUntilElementIsVisible();
                        break;
                    case "Contact Us":
//                    WebDriverWaitUtils.waitUntilElementIsVisible();
                        break;
                    default:
                        SeLionAsserts.verifyFalse(true, "Please give a correct String (Account|Data Sources|Notification Settings|Contact Us)");
                }
            }
        });
        Then("^Settings: I switch to \"([^\"]*)\" option in Sources settings page$", (String sourceOption) -> {
            log.info("^Settings: I switch to " + sourceOption + " option in Sources settings page$");


            switch (sourceOption) {
                case "Sync Date Now":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.getSyncDataNowButton());
                    tactSourcesPage.getSyncDataNowButton().tap();
                    break;
                case "Salesforce":
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.);
//                    tactSourcesPage.().tap();
                    break;
                case "Exchange":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.getExchangeButton());
                    tactSourcesPage.getExchangeButton().tap();
                    break;
                case "Gmail":
                    log.info("local : " + tactSourcesPage.getGmailButton().getLocator());
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.getGmailButton());
                    tactSourcesPage.getGmailButton().tap();
                    break;
                case "LinkedIn":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.getLinkedInButton());
                    tactSourcesPage.getLinkedInButton().tap(linkedInPage.getLinkedInTitleLabel());
                    break;
                default:
                    SeLionAsserts.verifyFalse(true,"Please give a correct String (Sync Date Now|Salesforce|Exchange|Gmail|LinkedIn)");
            }
        });
        And("^Settings: I disconnect the \"([^\"]*)\" account$", (String sourceOption) -> {
            log.info("^Settings: I disconnect the " + sourceOption + " account$");

            switch (sourceOption) {
                case "Exchange":
                    exchangePage.getExchangeDisconnectButton().tap(exchangePage.getExchangeDeleteAllDataButton());
                    exchangePage.getExchangeDeleteAllDataButton().tap();
                    log.info("there is a bug for this 5172");
                    break;
                case "Gmail":
                    gmailPage.getGmailDisconnectButton().tap(gmailPage.getGmailDeleteAllDataButton());
                    gmailPage.getGmailDeleteAllDataButton().tap(tactSourcesPage.getSourcesTitleLabel());
                    break;
                case "LinkedIn":
                    linkedInPage.getLinkedInDisconnectButton().tap(linkedInPage.getLinkedInDeleteAllDataButton());
                    linkedInPage.getLinkedInDeleteAllDataButton().tap(tactSourcesPage.getSourcesTitleLabel());
                    break;
                default:
                    SeLionAsserts.verifyFalse(true,"Please give a correct String " +
                            "(Exchange|Gmail|LinkedIn)");
            }
        });
        Then("^Settings: I back to Settings page from Sources page$", () -> {
            log.info("^Settings: I back to Settings page from Sources page$");

            tactSourcesPage.getBackToSettingsPageButton().tap(tactSettingsPage.getSettingsTitleLabel());
        });
        When("^Settings: I sign in the Exchange account$", () -> {
            log.info("^Settings: I sign in the Exchange account$");

            String exchangeEmail = null;
            String exchangePwd = null;
            String exchangeServer = CustomPicoContainer.getInstance().getUserInfor().getExchangeServer();

            if (DriverUtils.isIOS())
            {
                exchangeEmail = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailAddress();
                exchangePwd = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailPwd();
            }
            log.info("exchange : " + exchangeEmail + "/" + exchangePwd );
            log.info("exchange server : " + exchangeServer);

            exchangePage.getExchangeEmailTextField().sendKeys(exchangeEmail);
            exchangePage.getExchangePwdTextField().sendKeys(exchangePwd);
            exchangePage.getSubmitButton().tap();
            DriverUtils.sleep(2);

            if (DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getAlertsAllowButton().getLocator()).size() != 0) {
                log.info("inside the pup up");
                tactAlertsPopUpPage.getAlertsAllowButton().tap();
            }

            if (DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(exchangePage.getExchangeUnableToConnectLabel().getLocator()).size() !=0) {
                log.info("inside server");
                exchangePage.getExchangeServerTextField().sendKeys(exchangeServer);
                exchangePage.getExchangeUsernamaTextField().sendKeys(exchangeEmail);
                exchangePage.getSubmitButton().tap();
            }
            DriverUtils.sleep(5);
        });
        When("^Settings: I sign in the Gmail account$", () -> {
            log.info("^Settings: I sign in the Gmail account$");

            String gmailEmail = null;
            String gmailPwd = CustomPicoContainer.getInstance().getUserInfor().getGmailPwd();

            if (DriverUtils.isIOS())
            {
                gmailEmail = CustomPicoContainer.getInstance().getUserInfor().getGmailIOSEmailAddress();
            }
            log.info("gmail : " + gmailEmail + "/" + gmailPwd);

            Label gmailTitleLabel = new Label(gmailPage.getGmailHeadingTitleWebViewLabel().getLocator());
            log.info("new label " + gmailTitleLabel.getText()) ;
            Button useAnotherAccountButton = new Button(gmailPage.getGmailUseAnotherAccountWebViewButton().getLocator());
            if (gmailTitleLabel.getText().equalsIgnoreCase("Choose an account"))
            {
                log.info("click create another account");
                useAnotherAccountButton.click();
                DriverUtils.sleep(2);
            }

            TextField gmailOrPhoneTextField = new TextField(gmailPage.getGmailEmailOrPhoneWebViewTextField().getLocator());
            Button gmailNextButton = new Button(gmailPage.getGmailEmailNextWebViewButton().getLocator());

            gmailOrPhoneTextField.type(gmailEmail);
            gmailNextButton.click();
            DriverUtils.sleep(1);

            TextField pwdTextField = new TextField(gmailPage.getGmailPwdWebViewTextField().getLocator());
            Button pwdNextButton = new Button(gmailPage.getGmailPwdNextWebViewButton().getLocator());

            pwdTextField.type(gmailPwd);
            pwdNextButton.click();
            DriverUtils.sleep(2);

            Button gmailAllowAccessButton = new Button(gmailPage.getGmailAllowAccessAccountWebViewButton().getLocator());
            WebDriverWaitUtils.waitUntilElementIsVisible(gmailAllowAccessButton.getLocator());
            gmailAllowAccessButton.click();

            DriverUtils.sleep(2);
            DriverUtils.convertToNativeAPPDriver();
//            WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.getSourcesTitleLabel());
            WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactEmailButton());
            DriverUtils.sleep(5);
        });
        When("^Settings: I sign in the LinkedIn account$", () -> {
            log.info("^Settings: I sign in the linkedIn account$");

            String linkedInEmail = null;
            String linkedInPwd = CustomPicoContainer.getInstance().getUserInfor().getLinkedInPwd();

            if (DriverUtils.isIOS())
            {
                linkedInEmail = CustomPicoContainer.getInstance().getUserInfor().getLinkedInIOSEmailAddress();
                log.info("linkedIn : " + linkedInEmail + "/" + linkedInPwd);
            }

            //iOS - convert to webview driver and check whether need to reload the page
            if (DriverUtils.isIOS())
            {
                DriverUtils.convertToWebviewDriver();
                while (((AppiumDriver)Grid.driver()).findElementsByXPath(linkedInPage.getEmailWebViewTextField().getLocator()).size() ==0) {
                    DriverUtils.convertToNativeAPPDriver();
                    linkedInPage.getReloadLinkedInPageButton().tap();
                    log.info("after reload LinkedIn Page");
                    DriverUtils.sleep(1);
                    DriverUtils.convertToWebviewDriver();
                }
            }

            TextField emailTextField = new TextField(linkedInPage.getEmailWebViewTextField().getLocator());
            TextField pwdTextField = new TextField(linkedInPage.getPwdWebViewTextField().getLocator());
            Button signInButton = new Button(linkedInPage.getSignInWebViewAndAllowButton().getLocator());

            emailTextField.type(linkedInEmail);
            pwdTextField.type(linkedInPwd);
            signInButton.click();

            WebDriverWaitUtils.waitUntilElementIsInvisible(linkedInPage.getLinkedInTitleLabel());
            log.info("linkedin login done");

            DriverUtils.sleep(2);
            DriverUtils.convertToNativeAPPDriver();
            WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.getSourcesTitleLabel());
        });
    }
}
