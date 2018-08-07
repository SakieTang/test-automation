package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLinkedIn.TactWebviewLinkedinPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.*;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

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

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public SettingsSteps() {

        When("^Settings: I switch to \"([^\"]*)\" option in settings page$", (String settingsOption) -> {
            log.info("^Setting: I switch to " + settingsOption + " option in settings page$");
            TactSettingsPage tactSettingsPage = new TactSettingsPage();

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
                        TactAIAsserts.verifyFalse(true, "Please give a correct String (Account|Data Sources|Notification Settings|Contact Us)");
                }
            }
        });
        Then("^Settings: I switch to \"([^\"]*)\" option in Sources settings page$", (String sourceOption) -> {
            log.info("^Settings: I switch to " + sourceOption + " option in Sources settings page$");
            TactSourcesPage tactSourcesPage = new TactSourcesPage();
            LinkedInPage linkedInPage = new LinkedInPage();

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
                    TactAIAsserts.verifyFalse(true,"Please give a correct String (Sync Date Now|Salesforce|Exchange|Gmail|LinkedIn)");
            }
        });
        And("^Settings: I disconnect the \"([^\"]*)\" account$", (String sourceOption) -> {
            log.info("^Settings: I disconnect the " + sourceOption + " account$");
            TactSourcesPage tactSourcesPage = new TactSourcesPage();
            ExchangePage exchangePage = new ExchangePage();
            GmailPage gmailPage = new GmailPage();
            LinkedInPage linkedInPage = new LinkedInPage();

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
                    TactAIAsserts.verifyFalse(true,"Please give a correct String " +
                            "(Exchange|Gmail|LinkedIn)");
            }
        });
        Then("^Settings: I back to Settings page from Sources page$", () -> {
            log.info("^Settings: I back to Settings page from Sources page$");
            TactSettingsPage tactSettingsPage = new TactSettingsPage();
            TactSourcesPage tactSourcesPage = new TactSourcesPage();

            tactSourcesPage.getBackToSettingsPageButton().tap(tactSettingsPage.getSettingsTitleLabel());
        });
        When("^Settings: I sign in the Exchange account$", () -> {
            log.info("^Settings: I sign in the Exchange account$");
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();
            ExchangePage exchangePage = new ExchangePage();

            String exchangeEmail = CustomPicoContainer.getInstance().getUser().getExchangeEmailAddress();
            String exchangePwd = CustomPicoContainer.getInstance().getUser().getExchangeEmailPwd();
            String exchangeServer = CustomPicoContainer.getInstance().getUser().getExchangeServer();

//            if (DriverUtils.isIOS())
//            {
//                exchangeEmail = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailAddress();
//                exchangePwd = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailPwd();
//            }
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

            //workaround for "no policy" error msg (bug5580)
            String noPolicyLoc;
            if (DriverUtils.isIOS()) {
                noPolicyLoc = "//XCUIElementTypeStaticText[@name=\"There is no policy for this client.\"]";
            } else {
                noPolicyLoc = "//android.widget.TextView[@text='There is no policy for this client.']";
            }
            if (Grid.driver().findElementsByXPath(noPolicyLoc).size() !=0) {
                exchangePage.getSubmitButton().tap();
                DriverUtils.sleep(2);
                tactAlertsPopUpPage.getAlertsAllowButton().tap();
            }
        });
        When("^Settings: I sign in the Gmail account$", () -> {
            log.info("^Settings: I sign in the Gmail account$");
            GmailPage gmailPage = new GmailPage();
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();

            String gmailEmail = CustomPicoContainer.getInstance().getUser().getGmailEmailAddress();
            String gmailPwd = CustomPicoContainer.getInstance().getUser().getGmailEmailPwd();

//            if (DriverUtils.isIOS())
//            {
//                gmailEmail = CustomPicoContainer.getInstance().getUserInfor().getGmailIOSEmailAddress();
//            }
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
            TactWebviewLinkedinPage tactWebviewLinkedinPage = new TactWebviewLinkedinPage();

            String linkedInEmail = "uma@tact.ai";   //null;
            String linkedInPwd = "qwerty12";        //CustomPicoContainer.getInstance().getUserInfor().getLinkedInPwd();

            /**
             * iFrame
             */
//            ((WebDriver)Grid.driver()).findElement(By.tagName("iframe"));
//            System.out.println("Find the iframe");
//            DriverUtils.sleep(10);
//            System.out.println("switch to iframe");
//            ((WebDriver)Grid.driver()).switchTo().frame(0);
////            ((WebDriver)Grid.driver()).switchTo().frame( ((WebDriver)Grid.driver()).findElement(By.tagName("iframe")) );
//            System.out.println("switch to iframe done");
//            DriverUtils.sleep(10);
//
//
//            System.out.println("before click the login");
//            ((WebDriver)Grid.driver()).findElement(By.className("//*[contains(text(),'Sign in')]")).click();
//            System.out.println("after click");
//            DriverUtils.sleep(30);

            DriverUtils.tapXY(51,346);
            System.out.println("after click sign in");
            DriverUtils.sleep(10);

            if (((AppiumDriver)Grid.driver()).findElementsByXPath(tactWebviewLinkedinPage.getEmailWebViewTextField().getLocator()).size() != 0){
                System.out.println("find the email and pwd page " + linkedInEmail + " ==> " + linkedInPwd);
                System.out.println("tactWebviewLinkedinPage.getEmailWebViewTextField().getLocator() ==> " + tactWebviewLinkedinPage.getEmailWebViewTextField().getLocator());
                System.out.println("tactWebviewLinkedinPage.getPwdWebViewTextField().getLocator()   ==> " + tactWebviewLinkedinPage.getPwdWebViewTextField().getLocator());
                System.out.println("tactWebviewLinkedinPage.getSignInWebViewAndAllowButton().getLocator() ==> " + tactWebviewLinkedinPage.getSignInWebViewAndAllowButton().getLocator());


                ((AppiumDriver)Grid.driver()).findElementByXPath(tactWebviewLinkedinPage.getEmailWebViewTextField().getLocator()).sendKeys(linkedInEmail);
                ((AppiumDriver)Grid.driver()).findElementByXPath(tactWebviewLinkedinPage.getPwdWebViewTextField().getLocator()).sendKeys(linkedInPwd);
                ((AppiumDriver)Grid.driver()).findElementByXPath(tactWebviewLinkedinPage.getSignInWebViewAndAllowButton().getLocator()).click();
                DriverUtils.sleep(10);
            }

            //refresh (350, 66)
            DriverUtils.tapXY(350,66);
            System.out.println("after refresh");
            DriverUtils.sleep(20);

            //old version
//            if (DriverUtils.isIOS())
//            {
//                linkedInEmail = CustomPicoContainer.getInstance().getUserInfor().getLinkedInIOSEmailAddress();
//                log.info("linkedIn : " + linkedInEmail + "/" + linkedInPwd);
//            }
//
//            //iOS - convert to webview driver and check whether need to reload the page
//            if (DriverUtils.isIOS())
//            {
//                DriverUtils.convertToWebviewDriver();
//                while (((AppiumDriver)Grid.driver()).findElementsByXPath(linkedInPage.getEmailWebViewTextField().getLocator()).size() ==0) {
//                    DriverUtils.convertToNativeAPPDriver();
//                    linkedInPage.getReloadLinkedInPageButton().tap();
//                    log.info("after reload LinkedIn Page");
//                    DriverUtils.sleep(1);
//                    DriverUtils.convertToWebviewDriver();
//                }
//            }
//
//            TextField emailTextField = new TextField(linkedInPage.getEmailWebViewTextField().getLocator());
//            TextField pwdTextField = new TextField(linkedInPage.getPwdWebViewTextField().getLocator());
//            Button signInButton = new Button(linkedInPage.getSignInWebViewAndAllowButton().getLocator());
//
//            emailTextField.type(linkedInEmail);
//            pwdTextField.type(linkedInPwd);
//            signInButton.click();
//
//            WebDriverWaitUtils.waitUntilElementIsInvisible(linkedInPage.getLinkedInTitleLabel());
//            log.info("linkedin login done");
//
//            DriverUtils.sleep(2);
//            DriverUtils.convertToNativeAPPDriver();
//            WebDriverWaitUtils.waitUntilElementIsVisible(tactSourcesPage.getSourcesTitleLabel());
        });
        And("^Settings: I match the user \"([^\"]*)\"$", (String userName) -> {
            log.info("^Settings: I match the user " + userName + "$");

            DriverUtils.tapXY(188,336);
            DriverUtils.sleep(10);
            DriverUtils.tapXY(33,66);
            
        });
    }
}
