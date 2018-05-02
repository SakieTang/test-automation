package ai.tact.qa.automation.steps;

import ai.tact.qa.automation.testcomponents.mobile.TactAccessSFPage;
import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.TactMailBoxesPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.ExchangePage;
import ai.tact.qa.automation.testcomponents.mobile.TactWelcomePage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonSteps implements En {

    private TactWelcomePage tactWelcomePage;
    private TactNavigateTabBarPage tactNavigateTabBarPage;
    private TactContactsMainPage tactContactsMainPage;
    private TactContactObjPage tactContactObjPage;
    private TactMailBoxesPage tactMailBoxesPage;
    private TactAlertsPopUpPage tactAlertsPopUpPage;
    private TactAccessSFPage tactAccessSFPage;
    private ExchangePage exchangePage;
    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public CommonSteps() {

        tactWelcomePage = new TactWelcomePage();
        tactNavigateTabBarPage = new TactNavigateTabBarPage();
        tactAlertsPopUpPage = new TactAlertsPopUpPage();
        tactAccessSFPage = new TactAccessSFPage();

        tactContactObjPage = new TactContactObjPage();
        tactContactsMainPage = new TactContactsMainPage();
        tactMailBoxesPage = new TactMailBoxesPage();
        exchangePage = new ExchangePage();

        And("^I going check the new step cucumber-java(\\d+)$", (Integer arg0) -> {
            log.info("^I going check the new step cucumber-java(\\d+)$");
        });
        And("^I get the data from dataprovider$", () -> {
            log.info("^I get the data from dataprovider$");
            DriverUtils.sleep(60);
        });
        And("^I click login button$", () -> {
            log.info("^I click login button$");
        });
        Then("^I should see the userform page$", () -> {
            log.info("^I should see the userform page$");
        });
        And("^Common: I switch to \"([^\"]*)\" driver$", (String driverContext) -> {
            log.info("^Common: I switch to " + driverContext + " driver$");

            if (driverContext.equalsIgnoreCase("Webview") && DriverUtils.isIOS()) {
                log.info("-> convert To Webview driver <-");
                DriverUtils.convertToWebviewDriver();
            }
            else if (driverContext.equalsIgnoreCase("Native_APP") && DriverUtils.isIOS()) {
                log.info("-> convert To NativeAPP driver <-");
                DriverUtils.convertToNativeAPPDriver();
            }
        });
        When("^Common: I switch to \"([^\"]*)\" page from tab bar$", (String tabBarOption) -> {
            log.info("^Common: I switch to " + tabBarOption + " page from tab bar$");

            DriverUtils.sleep(2);

            //noReset --> For Android only, when it set to true. It will clear data.
//            if (DriverUtils.isAndroid() &&
//                    Grid.driver().findElementsByXPath(tactWelcomePage.getConnectWithSFButton().getLocator()).size() != 0 ){
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());
//                log.info("need to click the 'connect with salesforce' button");
//                tactWelcomePage.getConnectWithSFButton().tap();
//            }

            if (Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingLabel().getLocator()).size()!=0) {
                log.info("Start waiting for \"Syncing ...\"");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingLabel());
                log.info("\"Syncing...\" finished");
                DriverUtils.sleep(2);
            }
            else {
                log.info("No \"Syncing...\"");
            }

            //Syncing data to your phone Page - iOS only
            if (DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(tactNavigateTabBarPage.getTactMoreButton().getLocator()).size() == 0 &&
                    Grid.driver().findElementsByXPath(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel().getLocator()).size()!=0) {
                log.info("Start waiting for \"Syncing data to your phone.\"");
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
                log.info("\"Syncing data to your phone.\" finished");
                DriverUtils.sleep(2);
            }
            else {
                log.info("No \"Syncing data to your phone.\"");
            }
            //exchangeSync
            if (DriverUtils.isIOS() &&
                    Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactExchangeSyncErrorMsgTitleLabel().getLocator()).size() != 0) {
                log.info("Start waiting for \"exchange Reauth Sync\"");
                tactAlertsPopUpPage.getReauthorizeButton().tap(exchangePage.getExchangeTitleLabel());

                String exchangePwdText = CustomPicoContainer.getInstance().getUserInfor().getExchangeIOSEmailPwd();
                exchangePage.getExchangePwdTextField().sendKeys(exchangePwdText);
                exchangePage.getSubmitButton().tap();
                log.info("\"exchange Reauth Sync\" finished");
                DriverUtils.sleep(2);
            }
            else {
                log.info("No \"exchange Reauth Sync\"");
            }
            resyncPopUp();

            //More : Notebook, Notifications, Settings
            if ( tabBarOption.equalsIgnoreCase("More") ||
                 tabBarOption.equalsIgnoreCase("Notebook") ||
                 tabBarOption.equalsIgnoreCase("Notifications") ||
                 tabBarOption.equalsIgnoreCase("Settings")) {
                if (DriverUtils.isIOS()) {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
                    tactNavigateTabBarPage.getTactMoreButton().tap(tactNavigateTabBarPage.getTactMoreTitleLabel());
                    log.info("after click more button in iOS");
                }
                else {    //old android version
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton());
                    tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton().tap(tactNavigateTabBarPage.getTactAndroidOldVSettingsButton());
                    tactNavigateTabBarPage.getTactAndroidOldVSettingsButton().tap();
                    log.info("after click Setting button in Android");
                }

                if (resyncPopUp())
                {
                    log.info("after resync pop up done");
                    DriverUtils.sleep(2);
                    tactNavigateTabBarPage.getTactMoreButton().tap(tactNavigateTabBarPage.getTactMoreTitleLabel());
                }
            }
            else if (DriverUtils.isAndroid()) { //old android version   Need to modify this part
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidNavigateButton());
                tactNavigateTabBarPage.getTactAndroidNavigateButton().tap();

                //old version ==> //android.widget.CheckedTextView[@text='Contacts']
                //new version ==> //android.widget.TextView[@text='Contacts']
            }

            switch (tabBarOption) {
                case "Email":   //Android not support
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactEmailButton());
                    tactNavigateTabBarPage.getTactEmailButton().tap();
                    resyncPopUp();
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactEmailButton());
                    if (Grid.driver().findElementsByXPath(tactMailBoxesPage.getMailBoxesTitleLabel().getLocator()).size() == 0)
                    {
                        tactMailBoxesPage.getBackToMailBoxesButton().tap();
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactMailBoxesPage.getMailBoxesTitleLabel());
                    }
                    resyncPopUp();
                    break;
                case "Calendar":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactCalendarButton());
                    tactNavigateTabBarPage.getTactCalendarButton().tap();
                    resyncPopUp();
                    break;
                case "Contacts":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactContactsButton());
                    tactNavigateTabBarPage.getTactContactsButton().tap(tactContactsMainPage.getTactContactsTitleLabel());
                    resyncPopUp();
                    break;
                case "Opportunities":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactOpportunitiesButton());
                    tactNavigateTabBarPage.getTactOpportunitiesButton().tap();
                    resyncPopUp();
                    break;
                case "Notebook":
                    if (DriverUtils.isAndroid() ||
                        //iOS - notebook button is in navigation bar
                        Grid.driver().findElementsByXPath(tactNavigateTabBarPage.getNotebookButton().getLocator()).size() != 0 ) {
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotebookButton());
                        tactNavigateTabBarPage.getNotebookButton().tap();
                        resyncPopUp();
                    }
                    else {
                        //iOS - notebook is in more navigation bar
                        tactNavigateTabBarPage.getNotebookMoreButton().tap();
                    }
                    break;
                case "Notifications":   //Android not support
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotificationsButton());
                    tactNavigateTabBarPage.getNotificationsButton().tap();
                    break;
                case "Settings":
                    log.info("Goes to Settings Page");
                    if (DriverUtils.isIOS())
                    {
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getSettingsButton());
                        log.info("finding the setting button");
                        tactNavigateTabBarPage.getSettingsButton().tap();
                    }
                    log.info("after click setting button");
                    break;
                default:
                    if (DriverUtils.isIOS()) {
                        SeLionAsserts.verifyFalse(true,
                                "Please give a correct String (Email|Calendar|Contacts|Opportunities|More|Notebook|Notifications|Settings)");
                    }
                    else {
                        SeLionAsserts.verifyFalse(true,
                                "Please give a correct String (Calendar|Contacts|Notebook|Opportunities|Settings)");
                    }
            }

            log.info("swtich to " + tabBarOption);

//            DriverUtils.sleep(60);
        });
        Then("^Common: I click back icon$", () -> {
            log.info("^Common: I click back icon$");

            tactContactObjPage.getGoBackToContactsMainPageButton().tap();
            while (Grid.driver().findElementsByXPath(tactContactsMainPage.getContactsPlusIconButton().getLocator()).size() != 0
                    && DriverUtils.isAndroid()) {
                log.info("From Contact Obj to Contacts list");
                tactContactObjPage.getGoBackToContactsMainPageButton().tap();
            }
//            if ( DriverUtils.isIOS() ){
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap(tactContactsMainPage.getTactContactsTitleLabel());
//            }
        });
    }

    protected void whatever() {
        log.info("^Common: I click back icon$");

        tactContactObjPage.getGoBackToContactsMainPageButton().tap();
        while (Grid.driver().findElementsByXPath(tactContactsMainPage.getContactsPlusIconButton().getLocator()).size() != 0
                && DriverUtils.isAndroid()) {
            log.info("From Contact Obj to Contacts list");
            tactContactObjPage.getGoBackToContactsMainPageButton().tap();
        }
//            if ( DriverUtils.isIOS() ){
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap(tactContactsMainPage.getTactContactsTitleLabel());
//            }

    }

    public boolean resyncPopUp(){
        //Tact needs to resync pop_up
        if (DriverUtils.isIOS() &&
                Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactNeedsToResyncTitleLabel().getLocator()).size()!=0) {
            log.info("Start waiting for \"Tact need to resync\"");
            tactAlertsPopUpPage.getResyncButton().tap();
//            WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingDataToPhoneTitleLabel());
            WebDriverWaitUtils.waitUntilElementIsInvisible(tactAccessSFPage.getTactSyncingItemsLabel());
            log.info("\"Tact need to resync\" finished");
            WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
            return true;
        }
        else {
            log.info("No \"Tact need to resync\"");
            return false;
        }
    }
}
