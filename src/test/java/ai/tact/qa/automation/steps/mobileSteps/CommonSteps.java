package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.*;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactEmail.TactMailBoxesPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSearch.TactSearchContactsPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.ExchangePage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.TactSettingsPage;
import ai.tact.qa.automation.utils.CustomPicoContainer;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommonSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public CommonSteps() {

        And("^Common: I switch to \"([^\"]*)\" driver$", (String driverContext) -> {
            log.info("^Common: I switch to " + driverContext + " driver$");

            if (driverContext.equalsIgnoreCase("Webview") && DriverUtils.isIOS()) {
                DriverUtils.sleep(10);
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
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();
            TactAccessSFPage tactAccessSFPage = new TactAccessSFPage();
            TactSettingsPage tactSettingsPage = new TactSettingsPage();

            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactMailBoxesPage tactMailBoxesPage = new TactMailBoxesPage();
            ExchangePage exchangePage = new ExchangePage();

            DriverUtils.sleep(2);

            //noReset --> For Android only, when it set to true. It will clear data.
//            if (DriverUtils.isAndroid() &&
//                    Grid.driver().findElementsByXPath(tactWelcomePage.getConnectWithSFButton().getLocator()).size() != 0 ){
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());
//                log.info("need to click the 'connect with salesforce' button");
//                tactWelcomePage.getConnectWithSFButton().tap();
//            }

            //Back to tab bar page
            if (DriverUtils.isIOS() && Grid.driver().findElementsByXPath(tactSettingsPage.getSettingsTitleLabel().getLocator()).size()!=0){
                System.out.println("now in Setting Page");
                tactSettingsPage.getIosBackToMoreButton().tap();
            }

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
            if (tabBarOption.equalsIgnoreCase("More") ||
                 tabBarOption.equalsIgnoreCase("Notebook") ||
                 tabBarOption.equalsIgnoreCase("Notifications") ||
                 tabBarOption.equalsIgnoreCase("Settings")) {
//                if (DriverUtils.isIOS()) {
                    //Android - old moreTitleLable://android.widget.RelativeLayout/android.view.ViewGroup/android.widget.TextView[@text='More']
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreButton());
                    tactNavigateTabBarPage.getTactMoreButton().tap(tactNavigateTabBarPage.getTactMoreTitleLabel());
                    log.info("after click more button in iOS");
//                }
//                else {    //old android version
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton());
//                    tactNavigateTabBarPage.getTactAndroidOldVMoreOptionsButton().tap();
//                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidOldVSettingsButton());
//                    tactNavigateTabBarPage.getTactAndroidOldVSettingsButton().tap();
//                    log.info("after click Setting button in Android");
//                }

                if (resyncPopUp())
                {
                    log.info("after resync pop up done");
                    DriverUtils.sleep(2);
                    tactNavigateTabBarPage.getTactMoreButton().tap(tactNavigateTabBarPage.getTactMoreTitleLabel());
                }
            }
//            else if (DriverUtils.isAndroid()) { //old android version   Need to modify this part
//                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAndroidNavigateButton());
//                tactNavigateTabBarPage.getTactAndroidNavigateButton().tap();
//
//                //old version ==> //android.widget.CheckedTextView[@text='Contacts']
//                //new version ==> //android.widget.TextView[@text='Contacts']
//            }

            switch (tabBarOption) {
                case "Assistant":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactAssistantButton());
                    tactNavigateTabBarPage.getTactAssistantButton().tap();
                    resyncPopUp();
                    break;
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
                    //Android old build - element loc: //android.widget.CheckedTextView[@text='Calendar']
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
//                    if (DriverUtils.isIOS())
//                    {
                        WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getSettingsButton());
                        log.info("finding the setting button");
                        tactNavigateTabBarPage.getSettingsButton().tap();
//                    }
                    log.info("after click setting button");
                    break;
                default:
                    if (DriverUtils.isIOS()) {
                        SeLionAsserts.verifyFalse(true,
                                "Please give a correct String ((Assistant)|Email|Calendar|Contacts|Opportunities|More|Notebook|Notifications|Settings)");
                    }
                    else {
                        SeLionAsserts.verifyFalse(true,
                                "Please give a correct String (Calendar|Contacts|Notebook|Opportunities|Settings)");
                    }
            }

            log.info("swtich to " + tabBarOption);
//
//            DriverUtils.sleep(60);
        });
        Then("^Common: I click back icon$", () -> {
            log.info("^Common: I click back icon$");
            TactSearchContactsPage tactSearchContactsPage = new TactSearchContactsPage();
            TactContactObjPage tactContactObjPage = new TactContactObjPage();
            String backLoc;

            if (DriverUtils.isIOS()) {
                backLoc = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]";
            } else {
                backLoc = "//android.widget.ImageButton[@content-desc='Navigate up']";
            }

            if (Grid.driver().findElementsByXPath(backLoc).size() != 0) {
                Grid.driver().findElementByXPath(backLoc).click();
            }
            //oppty back
            // < //XCUIElementTypeButton[@name="Back"]
            //contact
            // < //XCUIElementTypeNavigationBar/XCUIElementTypeButton[1]
//            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactObjPage.getGoBackToContactsMainPageButton());
//            tactContactObjPage.getGoBackToContactsMainPageButton().tap();
//
//            if (DriverUtils.isAndroid() &&
//                    Grid.driver().findElementsById(tactSearchContactsPage.getSearchAllContactsTextField().getLocator()).size() != 0) {
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap();
//            }
            System.out.println("should back to contact main page");
        });
        Then("^Common: I turn (on|off) wifi$", (String isOn) -> {
            log.info("^Common: I turn " + isOn + " wifi$");

            if (isOn.equalsIgnoreCase("on")){
                DriverUtils.turnOnWifi();
            } else {
                DriverUtils.turnOffWifi();
            }
        });
        When("^Common: I am waiting for syncing done$", () -> {
            log.info("^Common: I am waiting for syncing done$");
            TactSyncPage tactSyncPage = new TactSyncPage();

            if (DriverUtils.isIOS()) {
                DriverUtils.scrollToBottom();
                WebDriverWaitUtils.waitUntilElementIsInvisible(tactSyncPage.getPendingSyncToSFLabel().getLocator());
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSyncPage.getSyncedWithSFLabel().getLocator());
            } else {
                System.out.println("in Android");
                DriverUtils.sleep(25);
                System.out.println("after wait for 30 sec");
            }
        });
    }

    protected void whatever() {
        TactContactObjPage tactContactObjPage = new TactContactObjPage();
        TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();

        log.info("^Common: I click back icon$");
        tactContactObjPage = new TactContactObjPage();
        tactContactsMainPage = new TactContactsMainPage();

        tactContactObjPage.getGoBackToContactsMainPageButton().tap();
        while (Grid.driver().findElementsByXPath(tactContactsMainPage.getContactsPlusIconButton().getLocator()).size() != 0
                && DriverUtils.isAndroid()) {
            log.info("From Contact Obj to Contacts list");
            tactContactObjPage.getGoBackToContactsMainPageButton().tap();
        }
//            if (DriverUtils.isIOS()){
//                tactContactObjPage.getGoBackToContactsMainPageButton().tap(tactContactsMainPage.getTactContactsTitleLabel());
//            }

    }

    protected boolean resyncPopUp(){
        TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();
        TactAccessSFPage tactAccessSFPage = new TactAccessSFPage();
        TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();

        //Tact needs to resync pop_up
        if (DriverUtils.isIOS() &&
                Grid.driver().findElementsByXPath(tactAlertsPopUpPage.getTactNeedsToResyncTitleLabel().getLocator()).size()!=0) {
            log.info("Start waiting for \"Tact need to resync\"");
            tactAlertsPopUpPage.getResyncButton().tap();
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
