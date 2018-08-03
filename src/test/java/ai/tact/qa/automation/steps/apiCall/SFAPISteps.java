package ai.tact.qa.automation.steps.apiCall;

import ai.tact.qa.automation.runner.response_model.SqlResponse;
import ai.tact.qa.automation.steps.mobileSteps.AddDeleteAccountSteps;
import ai.tact.qa.automation.steps.mobileSteps.AddDeleteContactSteps;
import ai.tact.qa.automation.steps.mobileSteps.AddDeleteLeadSteps;
import ai.tact.qa.automation.steps.mobileSteps.AddEditDeleteOpptySteps;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

enum SFOBJECT {
    Task, Event,        //subject
    Note,               //title
    Contact, Lead,      //lastName, name
    Account,            //name
    Opportunity         //name
}

enum SFObjs {
    User,       OpportunityContactRole, UserRole,   Organization,   Profile,
    Period,     OpportunityLineItem,    Pricebook2, PricebookEntry, Product2
}

public class SFAPISteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static SFNetworkService sfNetworkService;

    public SFAPISteps() {

        Given("^API: I get SF access information$", () -> {
            log.info("^API: I get SF access information$");
        });

        When("^API: I check Object \"(Task|Event|Note|Contact|Account|Lead|Opportunity)\" with field \"([^\"]*)\" value \"([^\"]*)\"$", (SFOBJECT sfobject, String field, String value) -> {
            log.info("^API: I check Object " + sfobject.toString() + " with field " + field + " value " + value + "$");

            String lName = AddDeleteLeadSteps.leadName;
            System.out.println("lastName : " + lName );
//            boolean isRecordExisting = isRecordExisting(sfobject, field, value);
//            System.out.println("is existing ? " + isRecordExisting);
        });

        Then("^API: I check Object \"(Contact|Account|Lead|Opportunity)\" saved in salesforce$", (String objName) -> {
            log.info("^API: I check Object " + objName + " saved in salesforce$");

            //get name field value
            String nameValue;
            SFOBJECT sfObject;
            switch (objName) {
                case "Contact":
                    nameValue = AddDeleteContactSteps.contactName;
                    sfObject = SFOBJECT.Contact;
                    break;
                case "Account":
                    nameValue = AddDeleteAccountSteps.accountName;
                    sfObject = SFOBJECT.Account;
                    break;
                case "Lead":
                    nameValue = AddDeleteLeadSteps.leadName;
                    sfObject = SFOBJECT.Lead;
                    break;
                case "Opportunity":
                    nameValue = AddEditDeleteOpptySteps.opportunityName;
                    sfObject = SFOBJECT.Opportunity;
                    break;
                default:
                    nameValue = AddDeleteContactSteps.contactName;
                    sfObject = SFOBJECT.Contact;
            }

            boolean isRecordExisting = isRecordExisting(sfObject, "name", nameValue);
            System.out.println("is existing ? " + isRecordExisting);
        });
    }

    public static void getSFNetworkService () {
        SFNetworkUtils sfNetworkUtils= SFNetworkUtils.getInstance();
        sfNetworkUtils.setUp();
        sfNetworkService = sfNetworkUtils.getService();
    }

    public static boolean isRecordExisting (SFOBJECT objectName, String fieldName, String fieldValue) {
        boolean isExisting = false;
        int totalSize;

        getSFNetworkService();

        String sqlQuery = String.format("select count() from %s where %s = '%s'", objectName, fieldName, fieldValue);
        System.out.println("sqlQuery " + sqlQuery);

        try {
            System.out.println(sfNetworkService.getSqlQueryBody(sqlQuery).execute().body().string());   //{"totalSize":1,"done":true,"records":[]}
            totalSize = sfNetworkService.getSqlQuery(sqlQuery).execute().body().totalSize;
            if (totalSize == 1){
                isExisting = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isExisting;
    }

    public void test(String sqlQuery) {

        SFNetworkUtils sfNetworkUtils= SFNetworkUtils.getInstance();
        sfNetworkUtils.setUp();
        sfNetworkService = sfNetworkUtils.getService();

        String keyword = "t";
        sqlQuery = "SELECT Id, FirstName, LastName FROM Lead WHERE FirstName LIKE '%"+keyword+"%'";

        try {
            sfNetworkService.getSqlQuery(sqlQuery);

            SqlResponse sqlResponse = sfNetworkService.getSqlQuery(sqlQuery).execute().body();

            System.out.println(sfNetworkService.getSqlQueryBody(sqlQuery).execute().body().string());

            System.out.println(String.format("RESULT: TOTAL SIZE: %d,   Records Size: %d", sqlResponse.totalSize, sqlResponse.records.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SFAPISteps.getSFNetworkService();
        System.out.println(SFAPISteps.isRecordExisting(SFOBJECT.Lead, "firstName", "07311213"));
        System.out.println();
        System.out.println(SFAPISteps.isRecordExisting(SFOBJECT.Contact, "firstName", "07311213"));
    }
}
