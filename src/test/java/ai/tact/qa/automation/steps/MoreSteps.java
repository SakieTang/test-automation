package ai.tact.qa.automation.steps;

import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactNavigateTabBarPage;
import ai.tact.qa.automation.testcomponents.mobile.TactSetting.TactSettingsPage;
import ai.tact.qa.automation.testcomponents.mobile.TactWelcomePage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;

import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MoreSteps implements En {

    private TactWelcomePage tactWelcomePage;
    private TactNavigateTabBarPage tactNavigateTabBarPage;
    private TactSettingsPage tactSettingsPage;
    private TactAlertsPopUpPage tactAlertsPopUpPage;
    private Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public MoreSteps() {

        tactWelcomePage = new TactWelcomePage();
        tactNavigateTabBarPage = new TactNavigateTabBarPage();
        tactSettingsPage = new TactSettingsPage();
        tactAlertsPopUpPage = new TactAlertsPopUpPage();

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
                    SeLionAsserts.verifyFalse(true,"Please give a correct String (Notebook|Notifications|Settings)");
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
                tactSettingsPage.getAndroidMoreOptionButton().tap(tactSettingsPage.getDeleteAccountButton());
            }

            tactSettingsPage.getDeleteAccountButton().tap(tactAlertsPopUpPage.getTactDeleteButton());
            DriverUtils.sleep(60);
//            tactAlertsPopUpPage.getTactDeleteButton().tap();
//            WebDriverWaitUtils.waitUntilElementIsVisible(tactWelcomePage.getWelcomeTactLabel());
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
                DriverUtils.writeToFile(fileDir, DriverUtils.getCurrentMobileOSType() + " : " + appName + " - " + appVersion + " - " + appFrom);
            }
        });
    }
}
