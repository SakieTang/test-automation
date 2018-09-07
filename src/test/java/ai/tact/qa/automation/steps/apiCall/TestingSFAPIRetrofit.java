package ai.tact.qa.automation.steps.apiCall;

import ai.tact.qa.automation.utils.dataobjects.response_model.SqlResponse;
import ai.tact.qa.automation.utils.dataobjects.User;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TestingSFAPIRetrofit {

    public static String getSFAccessInfo (User user, String info) {
        OkHttpClient client = new OkHttpClient();
        String value = null;

        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", "password")
                .add("client_id", user.getApiClientId())//"3MVG9CEn_O3jvv0ydnh2Gwu9Kq.SoCleHJwlIjZg_Q2mjI8gSaaJ6qw0Df_qtTrb7L__rAfPKne7zILZfeE32")
                .add("client_secret", user.getApiClientSecret())//"6884892550614520415")
                .add("username", user.getApiUserName())//"automation.tactsf.s@gmail.com")
                .add("password", user.getApiPassword())//"Tact20189ilpMZITdoDz6ABvYGrn8xHS")
                .build();
        Request request = new Request.Builder()
                .url("https://login.salesforce.com/services/oauth2/token")
                .post(requestBody)
                .build();

        Response response;

        try {
            response = client.newCall(request).execute();
            String jsonData = response.body().string();
//            System.out.println("body ==>" + jsonData);
            JSONObject jsonObject = new JSONObject(jsonData);
            String token = jsonObject.getString("access_token");
            String token_type = jsonObject.getString("token_type");
            String instance_url = jsonObject.getString("instance_url");
            value = jsonObject.getString(info);
//                System.out.println("token ==>" + token);
//                System.out.println("token_type ==>" + token_type);
//                System.out.println("instance_url ==>" + instance_url);
            System.out.println(info + " ==>" + value);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static void main(String[] args) {
//        String token = TestingSFAPIRetrofit.getSFAccessInfo("access_token");
//        SFAuthenticator sfAuthenticator = SFAuthenticator.getInstance();

        SFNetworkUtils sfNetworkUtils= SFNetworkUtils.getInstance();
        sfNetworkUtils.setUp();

        try {
            String keyword = "t";
            String sqlQuery = "SELECT Id, FirstName, LastName FROM Lead WHERE FirstName LIKE '%"+keyword+"%'";

            SFNetworkService sfNetworkService = sfNetworkUtils.getService();
            sfNetworkService.getSqlQuery(sqlQuery);

            SqlResponse sqlResponse = sfNetworkService.getSqlQuery(sqlQuery).execute().body();

            System.out.println(sfNetworkService.getSqlQueryBody(sqlQuery).execute().body().string());

            System.out.println(String.format("RESULT: TOTAL SIZE: %d,   Records Size: %d", sqlResponse.totalSize, sqlResponse.records.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
