package ai.tact.qa.automation.steps.apiCall;

import ai.tact.qa.automation.runner.response_model.SqlResponse;
import ai.tact.qa.automation.steps.mobileSteps.*;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import ai.tact.qa.automation.utils.dataobjects.User;
import ai.tact.qa.automation.utils.dataobjects.UserTestingChannel;
import com.paypal.selion.platform.dataprovider.DataProviderFactory;
import com.paypal.selion.platform.dataprovider.SeLionDataProvider;
import com.paypal.selion.platform.dataprovider.impl.FileSystemResource;
import com.paypal.selion.platform.grid.Grid;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

enum SFOBJECT {
    Contact, Lead,      //lastName, name
    Account,            //name
    Opportunity         //name
}
enum SFACTIVITY {
    Task, Event,        //subject
    Note                //title
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

            //back to previous page for Android Opportunity
            if (DriverUtils.isAndroid() && objName.equalsIgnoreCase("Opportunity")) {
                String backLoc = "//android.widget.ImageButton[@content-desc='Navigate up']";
                Grid.driver().findElementByXPath(backLoc).click();
            }
        });
        Then("^API: I check activity \"(Note|Event|Log|Task)\" saved in salesforce$", (String activityOption) -> {
            log.info("^API: I check activity " + activityOption + " saved in salesforce$");

            //get name field value
            SFACTIVITY sfactivity;
            String fieldName;
            String fieldValue;
            //    Task, Event,        //subject
            //    Note,               //title
            switch (activityOption) {
                case "Task":
                    sfactivity = SFACTIVITY.Task;
                    fieldName = "subject";
                    fieldValue = TactPinSteps.taskSubject;
                    break;
                case "Event":
                    sfactivity = SFACTIVITY.Event;
                    fieldName = "subject";
                    fieldValue = TactPinSteps.eventSubject;
                    break;
                case "Note":
                    sfactivity = SFACTIVITY.Note;
                    fieldName = "title";
                    fieldValue = TactPinSteps.noteTitle;
                    break;
                default:
                    log.info("it is log");
                    sfactivity = SFACTIVITY.Task;
                    fieldName = "subject";
                    fieldValue = TactPinSteps.logSubject;
            }

            boolean isRecordExisting = isRecordExisting(sfactivity, fieldName, fieldValue);
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

    public static String getRecordID (SFOBJECT objectName, String fieldName, String fieldValue) {
        String recordId = null;
        int totalSize = 0;

        getSFNetworkService();

        String sqlQuery = String.format("select id from %s where %s = '%s'", objectName, fieldName, fieldValue);
        System.out.println("sqlQuery " + sqlQuery);

        try {
            System.out.println(sfNetworkService.getSqlQueryBody(sqlQuery).execute().body().string());   //{"totalSize":1,"done":true,"records":[]}
            SqlResponse sqlResponse = sfNetworkService.getSqlQuery(sqlQuery).execute().body();
            totalSize = sqlResponse.totalSize;
            if (totalSize == 0){
                recordId = null;
            } else {
                recordId=sqlResponse.records.get(0).get("Id").toString().split("\"")[1];
            }
            System.out.println(recordId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordId;

    }
    public static void deleteRecord (SFOBJECT objectName, String fieldName, String fieldValue) {

        String objectType = objectName.toString();
        String objectId = getRecordID(objectName, fieldName, fieldValue);
        System.out.println("objectTypeAndId " + objectName + " " + objectId);

        try {
            System.out.println(sfNetworkService.deleteRecord(objectType, objectId).execute());   //{"totalSize":1,"done":true,"records":[{"attributes":{"type":"Contact","url":"/services/data/v40.0/sobjects/Contact/0036A00000bsrxAQAQ"},"Id":"0036A00000bsrxAQAQ"}]}
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isRecordExisting(SFOBJECT.Contact, "name", fieldValue)){
            System.out.println(fieldValue + " " + objectName + " is deleted");
        }
    }

    public static boolean isRecordExisting (SFACTIVITY activityName, String fieldName, String fieldValue) {
        boolean isExisting = false;
        int totalSize;

        getSFNetworkService();

        String sqlQuery = String.format("select count() from %s where %s = '%s'", activityName, fieldName, fieldValue);
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

//        String fieldValue = "Singh 14000807Umi";
//        int totalSize = 0;
//        String id = getRecordID(SFOBJECT.Contact, "name", fieldValue);
//
//        getSFNetworkService();
//
//        String objectType = SFOBJECT.Contact.toString();
//        String objectId = id;
//        System.out.println("objectTypeAndId " + objectType + " " + objectId);
//
//        try {
//
//            System.out.println(sfNetworkService.deleteRecord(objectType, objectId).execute());   //{"totalSize":1,"done":true,"records":[{"attributes":{"type":"Contact","url":"/services/data/v40.0/sobjects/Contact/0036A00000bsrxAQAQ"},"Id":"0036A00000bsrxAQAQ"}]}
//            System.out.println(isRecordExisting(SFOBJECT.Contact, "name", fieldValue));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
////        String id = getRecordID(SFOBJECT.Contact, "name", fieldValue);
////        System.out.println("id " + id); //0036A00000bsrxAQAQ
//
//        String a = "\"0036A00000bsrxAQAQ\"";
//        System.out.println(a);
//        System.out.println(a.split("\"")[1]);

        deleteRecord(SFOBJECT.Contact, "name", "Singh 14410808Umi");

    }


}
