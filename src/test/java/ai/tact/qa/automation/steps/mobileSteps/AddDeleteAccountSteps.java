package ai.tact.qa.automation.steps.mobileSteps;

import ai.tact.qa.automation.testcomponents.mobile.TactCompany.TactAddNewCompanyPage;
import ai.tact.qa.automation.testcomponents.mobile.TactContact.TactContactsMainPage;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class AddDeleteAccountSteps implements En {

    TactContactsMainPage tactContactsMainPage;
    TactAddNewCompanyPage tactAddNewCompanyPage;

    public AddDeleteAccountSteps() {

        tactContactsMainPage = new TactContactsMainPage();
        tactAddNewCompanyPage = new TactAddNewCompanyPage();

        And("^AddAccount: I create \"([^\"]*)\" time a \"([^\"]*)\" and \"([^\"]*)\" send to Salesforce, with username \"([^\"]*)\" and \"([^\"]*)\"$", (String time, String userType, String sendToSF, String accountName, String isSave) -> {
            System.out.println("^AddAccount: I create " + time + " time a " +userType + " and " + sendToSF + " send to Salesforce, with username " + accountName + " and " + isSave + "$");

            tactContactsMainPage.getContactsPlusIconButton().tap();
            tactAddNewCompanyPage.getTactAddNewCompanyButton().tap(tactAddNewCompanyPage.getNewCompanyTitleLabel());
        });
    }
}
