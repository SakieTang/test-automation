package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.asserts.TactAIAsserts;
import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.TactSettingsPage;
import ai.tact.qa.automation.testcomponents.mobile.TactWelcomePage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.grid.Grid;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MoreSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public MoreSteps() {

        TactWelcomePage tactWelcomePage = new TactWelcomePage();
        TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();
        TactSettingsPage tactSettingsPage = new TactSettingsPage();
        TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

        And("^More: I switch to \"([^\"]*)\" option in more page$", (String option) -> {
            log.info("^More: I switch to " + option + " option in more page$");

            switch (option) {
                case "Notebook":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotebookButton());
                    tactNavigateTabBarPage.getNotebookButton().tap();
                    break;
                case "Notification":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getNotificationsButton());
                    tactNavigateTabBarPage.getNotificationsButton().tap();
                    break;
                case "Settings":
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getSettingsButton());
                    tactNavigateTabBarPage.getSettingsButton().tap(tactSettingsPage.getSettingsTitleLabel());
                    break;
                default:
                    TactAIAsserts.verifyFalse(true,"Please give a correct String (Notebook|Notifications|Settings)");
            }
        });
        And("^More: I log out from the app$", () -> {
            log.info("^More: I log out from the app$");

            if (DriverUtils.isAndroid()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getAndroidMoreOptionButton());
                tactSettingsPage.getAndroidMoreOptionButton().tap(tactSettingsPage.getLogOutButton());
                tactSettingsPage.getLogOutButton().tap(tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton());
                tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
            }
            else {
                tactSettingsPage.getLogOutButton().tap();
            }
            WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getConnectWithSFButton());

            //clear data from chrome (android Only)
            if (DriverUtils.isAndroid())
            {
                DriverUtils.clearChromeData();
            }
        });
        And("^More: I delete current account from the app$", () -> {
            log.info("^More: I delete current account from the app$");

            if (DriverUtils.isAndroid())
            {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getAndroidMoreOptionButton());
                tactSettingsPage.getAndroidMoreOptionButton().tap();

                if ((Grid.driver().findElementsByXPath(tactSettingsPage.getAndroidMoreOptionButton().getLocator())).size() != 0 ){
                    System.out.println("Did not click the more option button in the corner, need to re-click by x-y");
                    DriverUtils.tapXY(1342, 182);
                }
            }
            WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getDeleteAccountButton());
            tactSettingsPage.getDeleteAccountButton().tap(tactAlertsPopUpPage.getTactDeleteButton());
//            DriverUtils.sleep(60);
            tactAlertsPopUpPage.getTactDeleteButton().tap();
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getWelcomeTactLabel());
            } else {
                DriverUtils.sleep(60);
            }
        });
        Then("^More: I get App Version and \"([^\"]*)\" save in file$", (String isSave) -> {
            log.info("^More: I get App Version and " + isSave + " save in file$");

            WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getAppVersionLabel());
            String appVersion = tactSettingsPage.getAppVersionLabel().getValue();
            String appName = DriverUtils.getAppName();
            String appFrom = DriverUtils.getAppFrom();
            String fileDir = "target/iosVersion.txt";

            if (DriverUtils.isAndroid())
            {
                fileDir = "target/androidVersion.txt";
            }
            if (!DriverUtils.isTextEmpty(isSave))
            {
                DriverUtils.writeToFile(fileDir, DriverUtils.getCurrentMobileOSType() + " : " + appName + " - " + appVersion + " - " + appFrom, false);
            }
        });
        And("^More: I switch back to More page$", () -> {
            log.info("^More: I switch back to More page$");

            if ( DriverUtils.isIOS() ) {

                if (Grid.driver().findElementsByXPath(tactSettingsPage.getIosBackToSettingButton().getLocator()).size() != 0) {
                    System.out.println("now in Account Page");
                    tactSettingsPage.getIosBackToSettingButton().tap(tactSettingsPage.getIosBackToMoreButton());
                    tactSettingsPage.getIosBackToMoreButton().tap(tactNavigateTabBarPage.getTactMoreTitleLabel());
                } else if (Grid.driver().findElementsByXPath(tactSettingsPage.getIosBackToMoreButton().getLocator()).size() != 0) {
                    System.out.println("now in Settings Page");
                    tactSettingsPage.getIosBackToMoreButton().tap();
                }
                WebDriverWaitUtils.waitUntilElementIsVisible(tactNavigateTabBarPage.getTactMoreTitleLabel());
            }
        });
    }
}
