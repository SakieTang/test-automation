package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactCompany.TactAddNewCompanyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import ai.tact.qa.automation.testcomponents.mobile.TactLead.TactAddNewLeadPage;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import cucumber.api.java8.En;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDeleteAccountSteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);

    public AddDeleteAccountSteps() {

        And("^AddAccount: I create \"([^\"]*)\" time a \"([^\"]*)\" and \"([^\"]*)\" send to Salesforce, with username \"([^\"]*)\" and \"([^\"]*)\"$", (String time, String userType, String sendToSF, String accountName, String isSave) -> {
            log.info("^AddAccount: I create " + time + " time a " +userType + " and " + sendToSF + " send to Salesforce, with username " + accountName + " and " + isSave + "$");
            TactContactsMainPage tactContactsMainPage = new TactContactsMainPage();
            TactAddNewCompanyPage tactAddNewCompanyPage = new TactAddNewCompanyPage();

            tactContactsMainPage.getContactsPlusIconButton().tap();
            tactAddNewCompanyPage.getTactAddNewCompanyButton().tap(tactAddNewCompanyPage.getNewCompanyTitleLabel());
        });

        And("^AddAccount: I input a user name \"([^\"]*)\", company name \"([^\"]*)\" and \"([^\"]*)\"$", (String leadName, String companyName, String isSave) -> {
            log.info("^AddLead: I input a user name " + leadName + ", company name " + companyName + " and " + isSave + "$");
            TactAddNewLeadPage tactAddNewLeadPage = new TactAddNewLeadPage();

            //set username Text
            String fName = DriverUtils.get1stNFromFullName(leadName);
            String lName = DriverUtils.getLastNFromFullName(leadName);
            System.out.println(fName + " " + lName);

            if (!fName.isEmpty()){
                tactAddNewLeadPage.getFirstNameTextField().setText(fName);
            }
            tactAddNewLeadPage.getLastNameTextField().setText(lName);

            //set company Text
            tactAddNewLeadPage.getCompanyTextField().setText(companyName);

            //save or not save Lead
            if (!DriverUtils.isTextEmpty(isSave)) {
                tactAddNewLeadPage.getSaveNewLeadButton().tap();
            } else {
                tactAddNewLeadPage.getCancelAddNewLeadButton().tap();
                if (DriverUtils.isAndroid()) {

                }
            }

            System.out.println("finish");
            DriverUtils.sleep(10);

        });
    }
}
