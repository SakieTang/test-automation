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
    Log, Task, Event,        //subject
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

        Then("^API: I check Object \"(Contact|Account|Lead|Opportunity)\" is \"(saved|deleted)\" in salesforce$", (String objName, String option) -> {
            log.info("^API: I check Object " + objName + " is " + option + " saved in salesforce$");

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
            //saved|deleted
            switch (option) {
                case "saved":
                    System.out.println("is existing? " + isRecordExisting);
                    break;
                case "deleted":
                    System.out.println("is existing? " + isRecordExisting);
                    break;
            }

            //back to previous page for Android Opportunity
            if (DriverUtils.isAndroid() && objName.equalsIgnoreCase("Opportunity")) {
                String backLoc = "//android.widget.ImageButton[@content-desc='Navigate up']";
                Grid.driver().findElementByXPath(backLoc).click();
            }
        });
        And("^API: I delete Object \"(Contact|Account|Lead|Opportunity)\" from salesforce and wait for \"(\\d+)\" sec$", (String objName, Integer waitingTime) -> {
            log.info("^API: I delete Object " + objName + " from salesforce and wait for " + waitingTime + " sec$");

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
            deleteRecord(sfObject, "name", nameValue);
//            if (DriverUtils.isAndroid()) {
//                DriverUtils.sleep(30);
//                System.out.println("after 30 sec waiting");
//                DriverUtils.sleep(20);
//                System.out.println("after 20 sec waiting");
//                DriverUtils.sleep(waitingTime - 30 - 20);
//            }

            DriverUtils.sleep(waitingTime);
        });
        Then("^API: I check activity \"(Note|Log|Task|Event)\" is \"(saved|deleted)\" in salesforce$", (String activityOption, String option) -> {
            log.info("^API: I check activity " + activityOption + " is " + option + " in salesforce$");

            //get name field value
            SFACTIVITY sfactivity;
            String fieldName;
            String fieldValue;
            //    Task, Event, Log    //subject
            //    Note,               //title
            switch (activityOption) {
                case "Task":
                    sfactivity = SFACTIVITY.Task;
                    fieldName = "subject";
                    fieldValue = TactPinSteps.taskSubject;
                    break;
                case "Log":
                    sfactivity = SFACTIVITY.Task;
                    fieldName = "subject";
                    fieldValue = TactPinSteps.logSubject;
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

            //saved|deleted
            switch (option) {
                case "saved":
                    System.out.println("is existing? " + isRecordExisting);
                    break;
                case "deleted":
                    System.out.println("is existing? " + isRecordExisting);
                    break;
            }
        });
        And("^API: I delete activity \"(Note|Log|Task|Event)\" from salesforce$", (String activityOption) -> {
            log.info("^API: I delete Object " + activityOption + " from salesforce$");

            //get name field value
            SFACTIVITY sfactivity;
            String fieldName;
            String fieldValue;
            switch (activityOption) {
                case "Task":
                    sfactivity = SFACTIVITY.Task;
                    fieldName = "subject";
                    fieldValue = TactPinSteps.taskSubject;
                    break;
                case "Log":
                    sfactivity = SFACTIVITY.Task;
                    fieldName = "subject";
                    fieldValue = TactPinSteps.logSubject;
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

            deleteRecord(sfactivity, fieldName, fieldValue);
            DriverUtils.sleep(10);
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
    public static String getRecordID (SFACTIVITY activityName, String fieldName, String fieldValue) {
        String recordId = null;
        int totalSize = 0;

        getSFNetworkService();

        String sqlQuery = String.format("select id from %s where %s = '%s'", activityName, fieldName, fieldValue);
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
    public static void deleteRecord (SFACTIVITY activityName, String fieldName, String fieldValue) {

        String activityType = activityName.toString();
        String objectId = getRecordID(activityName, fieldName, fieldValue);
        System.out.println("objectTypeAndId " + activityName + " " + objectId);

        try {
            System.out.println(sfNetworkService.deleteRecord(activityType, objectId).execute());   //{"totalSize":1,"done":true,"records":[{"attributes":{"type":"Contact","url":"/services/data/v40.0/sobjects/Contact/0036A00000bsrxAQAQ"},"Id":"0036A00000bsrxAQAQ"}]}
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isRecordExisting(SFOBJECT.Contact, "name", fieldValue)){
            System.out.println(fieldValue + " " + activityName + " is deleted");
        }
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

        String fieldValue = "Send Quote 14390814_test";
        String fieldName = "subject";
//        //    Task, Event, Log        //subject
//        //    Note,               //title
//
        System.out.println(isRecordExisting(SFACTIVITY.Task, fieldName, fieldValue));
//        String noteId = getRecordID(SFACTIVITY.Note, fieldName, fieldValue);
//        System.out.println("id " + noteId);
//        deleteRecord(SFACTIVITY.Note, fieldName, fieldValue);
//
////        deleteRecord(SFOBJECT.Lead, "name", fieldValue);
//        DriverUtils.sleep(30);
//        System.out.println("after 30 sec wait");
//        DriverUtils.sleep(15);
//        System.out.println("after 45 sec wait");
//        DriverUtils.sleep(5);
//        System.out.println("after 50 sec wait");    //everything will be gone after 50-60 sec
//        DriverUtils.sleep(10);
//        System.out.println("after 60 sec wait");

    }


}
