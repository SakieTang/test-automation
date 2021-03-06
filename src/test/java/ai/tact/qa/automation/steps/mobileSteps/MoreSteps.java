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
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MoreSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public MoreSteps() {

        And("^More: I switch to \"([^\"]*)\" option in more page$", (String option) -> {
            log.info("^More: I switch to " + option + " option in more page$");
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();
            TactSettingsPage tactSettingsPage = new TactSettingsPage();

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
            TactWelcomePage tactWelcomePage = new TactWelcomePage();
            TactSettingsPage tactSettingsPage = new TactSettingsPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            if (DriverUtils.isAndroid()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getAccountButton());
                tactSettingsPage.getAccountButton().tap(tactSettingsPage.getDeleteAccountButton());

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
            TactWelcomePage tactWelcomePage = new TactWelcomePage();
            TactSettingsPage tactSettingsPage = new TactSettingsPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getDeleteAccountButton());
            tactSettingsPage.getDeleteAccountButton().tap(tactAlertsPopUpPage.getTactDeleteButton());
            tactAlertsPopUpPage.getTactDeleteButton().tap();
            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getWelcomeTactLabel());
            } else {
                DriverUtils.sleep(5);
                if (Grid.driver().findElementsByXPath("//android.widget.Button[@text='LOG OUT']").size() != 0) {
                    Grid.driver().findElementByXPath("//android.widget.Button[@text='LOG OUT']").click();
                }
            }
        });
        Then("^More: I get App Version and \"([^\"]*)\" save in file$", (String isSave) -> {
            log.info("^More: I get App Version and " + isSave + " save in file$");
            TactSettingsPage tactSettingsPage = new TactSettingsPage();

            if (DriverUtils.isIOS()) {
                WebDriverWaitUtils.waitUntilElementIsVisible(tactSettingsPage.getAccountButton());
                tactSettingsPage.getAccountButton().tap(tactSettingsPage.getDeleteAccountButton());
            }
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
            TactNavigateTabBarPage tactNavigateTabBarPage = new TactNavigateTabBarPage();
            TactSettingsPage tactSettingsPage = new TactSettingsPage();

            if (DriverUtils.isIOS()) {

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
