package ai.tact.qa.automation.steps.apiCall;

import ai.tact.qa.automation.utils.dataobjects.response_model.SqlResponse;
import ai.tact.qa.automation.steps.mobileSteps.*;
import ai.tact.qa.automation.utils.DriverUtils;
import ai.tact.qa.automation.utils.LogUtil;
import com.paypal.selion.platform.asserts.SeLionAsserts;
import com.paypal.selion.platform.grid.Grid;
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

        Then("^API: I check Object \"(Contact|Account|Lead|Opportunity)\" is \"(saved|deleted)\" in salesforce$", (String objName, String option) -> {
            log.info("^API: I check Object " + objName + " is " + option + " saved in salesforce$");

            //get name field value
            String nameValue;
            SFOBJECT sfObject;
            long startTime = 0;
            long endTime = 0;
            long waitingTime = 0;
            boolean isRecordExisting = false;

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

            //saved|deleted
            switch (option) {
                case "saved":

                    //waiting for 2 mins for Object upload to SF
                    startTime = System.currentTimeMillis();
                    isRecordExisting = false;
                    while (!isRecordExisting && waitingTime < 120) {
                        //before checking time
                        endTime = System.currentTimeMillis();
                        isRecordExisting = isRecordExisting(sfObject, "name", nameValue);

                        waitingTime = (endTime - startTime)/1000;
                        if (isRecordExisting || waitingTime > 120) {
                            System.out.println("waiting time " + waitingTime);
                            break;
                        }
                        DriverUtils.sleep(30);
                    }

                    System.out.println("is existing? " + isRecordExisting);
                    break;
                case "deleted":

                    //waiting for 2 mins for Object deleted to SF
                    startTime = System.currentTimeMillis();
                    isRecordExisting = true;
                    while (!isRecordExisting && waitingTime < 120) {
                        //before checking time
                        endTime = System.currentTimeMillis();
                        isRecordExisting = isRecordExisting(sfObject, "name", nameValue);

                        waitingTime = (endTime - startTime)/1000;
                        if (!isRecordExisting || waitingTime > 120) {
                            System.out.println("waiting time " + waitingTime);
                            break;
                        }
                        DriverUtils.sleep(30);
                    }
                    System.out.println("is deleted? " + !isRecordExisting);
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

            DriverUtils.sleep(waitingTime);
        });
        Then("^API: I verify activity \"(Note|Log|Task|Event)\" is \"(saved|deleted)\" in salesforce$", (String activityOption, String option) -> {
            log.info("^API: I check activity " + activityOption + " is " + option + " in salesforce$");

            //get name field value
            SFACTIVITY sfactivity;
            String fieldName;
            String fieldValue;
            boolean isRecordExisting;
            long startTime = 0;
            long endTime = 0;
            long waitingTime = 0;
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

            //saved|deleted
            switch (option) {
                case "saved":

                    //waiting for 2 mins for Activity upload to SF
                    startTime = System.currentTimeMillis();
                    isRecordExisting = false;
                    while (!isRecordExisting && waitingTime < 120) {
                        //before checking time
                        endTime = System.currentTimeMillis();
                        isRecordExisting = isRecordExisting(sfactivity, fieldName, fieldValue);

                        waitingTime = (endTime - startTime)/1000;
                        if (isRecordExisting || waitingTime > 120) {
                            System.out.println("waiting time " + waitingTime);
                            break;
                        }
                        DriverUtils.sleep(30);
                    }

                    System.out.println("is existing? " + isRecordExisting);
                    break;
                case "deleted":

                    //waiting for 2 mins for Activity deleted to SF
                    startTime = System.currentTimeMillis();
                    isRecordExisting = true;
                    while (isRecordExisting && waitingTime < 120) {
                        //before checking time
                        endTime = System.currentTimeMillis();
                        isRecordExisting = isRecordExisting(sfactivity, fieldName, fieldValue);

                        waitingTime = (endTime - startTime)/1000;
                        if ( !isRecordExisting || waitingTime > 120) {
                            System.out.println("waiting time " + waitingTime);
                            break;
                        }
                        DriverUtils.sleep(30);
                    }

                    System.out.println("is deleted? " + !isRecordExisting);
                    SeLionAsserts.verifyFalse(isRecordExisting, "The record was deleted and not timeout(" + waitingTime + ").");
                    break;
            }
        });
        And("^API: I delete activity \"(Note|Log|Task|Event)\" from salesforce$", (String activityOption) -> {
            log.info("^API: I delete activity " + activityOption + " from salesforce$");

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

        String fieldValue = "11400919oppty_edit_Tact";
        String fieldName = "subject";
//        //    Task, , Log        //subject
//        //    Note,               //title

        //Event
        boolean s = isRecordExisting(SFOBJECT.Opportunity, "name", fieldValue);
        System.out.println(s);
//
//        System.out.println(isRecordExisting(SFACTIVITY.Event, fieldName, fieldValue));
//        String noteId = getRecordID(SFACTIVITY.Event, fieldName, fieldValue);
//        System.out.println("id " + noteId);
    }


}
