package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactAlertsPopUpPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLead.TactAddNewLeadPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLead.TactLeadObjPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDeleteLeadSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    public static String leadName;

    public AddDeleteLeadSteps() {

        Then("^AddLead: I input a user name \"([^\"]*)\", company name \"([^\"]*)\" and \"([^\"]*)\"$", (String leadName, String companyName, String isSave) -> {
            log.info("^AddLead: I input a user name " + leadName + ", company name " + companyName + " and " + isSave + "$");
            TactAddNewLeadPage tactAddNewLeadPage = new TactAddNewLeadPage();
            TactLeadObjPage tactLeadObjPage = new TactLeadObjPage();
            TactAlertsPopUpPage tactAlertsPopUpPage = new TactAlertsPopUpPage();

            //set username Text
            String fName = DriverUtils.get1stNFromFullName(leadName, false);
            String lName = DriverUtils.getLastNFromFullName(leadName, true);

            if (fName.isEmpty()) {
                this.leadName = lName;
            } else {
                this.leadName=String.format("%s %s", fName, lName);
            }

            System.out.println(this.leadName);

            if (!fName.isEmpty()){
                tactAddNewLeadPage.getFirstNameTextField().setText(fName);
            }
            tactAddNewLeadPage.getLastNameTextField().setText(lName);

            //set company Text
            tactAddNewLeadPage.getCompanyTextField().setText(companyName);

            //save or not save Lead
            if (!DriverUtils.isTextEmpty(isSave))
            {
                tactAddNewLeadPage.getSaveNewLeadButton().tap();
                if (DriverUtils.isAndroid())
                {
                    WebDriverWaitUtils.waitUntilElementIsVisible(tactLeadObjPage.getLeadIconImage());
                    log.info("The lead icon displays");
                    tactLeadObjPage.getGoBackToContactsMainPageButton().tap();
                }
            }
            else {
                tactAddNewLeadPage.getCancelAddNewLeadButton().tap();
                if (DriverUtils.isAndroid())
                {
                    tactAlertsPopUpPage.getAndroidPopUpSureConfirmOKButton().tap();
                }
            }
        });
    }
}
