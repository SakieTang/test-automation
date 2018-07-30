package ai.tact.qa.automation.steps.apiCall;

import ai.tact.qa.automation.runner.response_model.SqlResponse;
import ai.tact.qa.automation.utils.LogUtil;
import cucumber.api.PendingException;
import cucumber.api.java8.En;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

enum SFOBJECT {
    Account, Lead, Contact, Opportunity, Note, Task, Event
}

public class SFAPISteps implements En {

    private static final Logger log = LogUtil.setLoggerHandler(Level.ALL);
    private static SFNetworkService sfNetworkService;

    public SFAPISteps() {

        Given("^API: I get SF access information$", () -> {
            log.info("^API: I get SF access information$");



        });
    }

    public static void getSFNetworkService () {
        SFNetworkUtils sfNetworkUtils= SFNetworkUtils.getInstance();
        sfNetworkUtils.setUp();
        sfNetworkService = sfNetworkUtils.getService();
    }

    public static boolean isObjectExisting (SFOBJECT objectName, String fieldName, String fieldValue) {
        boolean isExisting = false;

        System.out.println(objectName + " = " + objectName.toString() + " = " + objectName.getClass());

        String sqlQuery = String.format("select count() from %s where %s = '%s'", objectName, fieldName, fieldValue);

        try {
            System.out.println(sfNetworkService.getSqlQueryBody(sqlQuery).execute().body().string());   //{"totalSize":1,"done":true,"records":[]}
            System.out.println(sfNetworkService.getSqlQuery(sqlQuery).execute().body().totalSize);
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
        System.out.println(SFAPISteps.isObjectExisting(SFOBJECT.Lead, "firstName", "07311213"));
        System.out.println();
        System.out.println(SFAPISteps.isObjectExisting(SFOBJECT.Contact, "firstName", "07311213"));
    }
}
