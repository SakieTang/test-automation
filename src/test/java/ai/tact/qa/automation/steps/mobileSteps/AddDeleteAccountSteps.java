package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactCompany.TactAddNewCompanyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactCompany.TactCompanyObjPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDeleteAccountSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    public static String accountName;

    public AddDeleteAccountSteps() {
        And("^AddLead: I input a user name \"([^\"]*)\" and \"([^\"]*)\"$", (String accountNameText, String isSave) -> {
            log.info("^AddLead: I input a user name " + accountNameText + " and " + isSave + "$");
            TactAddNewCompanyPage tactAddNewCompanyPage = new TactAddNewCompanyPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();
            TactCompanyObjPage tactCompanyObjPage = new TactCompanyObjPage();
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();

            //set accountName Text
            accountName = DriverUtils.getNameWithStamp(accountNameText, true);

            tactAddNewCompanyPage.getNameTextField().sendKeys(accountName);

            //save or not save Account
            if (!DriverUtils.isTextEmpty(isSave)) {
                tactAddNewCompanyPage.getSaveNewCompanyButton().tap();

                if (DriverUtils.isAndroid())
                {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactCompanyObjPage.getGoBackToContactsMainPageButton());
                    tactCompanyObjPage.getGoBackToContactsMainPageButton().tap();
                }
            }
            else {
                log.info("w/o save");

                tactAddNewCompanyPage.getCancelAddNewCompanyButton().tap();
                if (DriverUtils.isAndroid())
                {
                    tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
                }
            }

            WebDriverWaitUtils.waitUntilElementIsVisible(tactContactsMainPage.getTactContactsTitleLabel());
            log.info("save ? " + isSave);
//            DriverUtils.sleep(10);
        });
    }
}
