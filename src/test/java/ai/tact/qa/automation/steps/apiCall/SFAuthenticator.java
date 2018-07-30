package ai.tact.qa.automation.steps.apiCall;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SFAuthenticator {

    private static SFAuthenticator instance;

    final String url = "https://login.salesforce.com/services/oauth2/token";
    final String grant_type = "password";
    final String client_id = "3MVG9CEn_O3jvv0ydnh2Gwu9Kq.SoCleHJwlIjZg_Q2mjI8gSaaJ6qw0Df_qtTrb7L__rAfPKne7zILZfeE32";
    final String client_secret = "6884892550614520415";
    final String username = "automation.tactsf.s@gmail.com";    //sf account name
    final String password = "Tact20189ilpMZITdoDz6ABvYGrn8xHS";

    private static SFNetworkService SFNetworkService;

    private static String salesforceToken;
    private static String salesforceTokenType;
    private static String salesforceAuthorization;
    private static String salesforceInstanceUrl;

    public static SFAuthenticator getInstance() {
        if (instance == null) {
            instance = new SFAuthenticator();
        }

        return instance;
    }

    public String getSFAccessAuthorization() {

        if (salesforceAuthorization == null) {
            getSFAccessInfo();
            salesforceAuthorization = String.format("%s %s", getSFAccessTokenType(), getSFAccessToken());
        }

        return salesforceAuthorization;
    }

    public String getSFAccessInstanceUrl() {

        if (salesforceInstanceUrl == null) {
            getSFAccessInfo();
        }

        return salesforceInstanceUrl;
    }

    public String getSFAccessToken() {

        if (salesforceToken == null) {
            getSFAccessInfo();
        }
        return salesforceToken;
    }

    public String getSFAccessTokenType() {

        if (salesforceTokenType == null) {
            getSFAccessInfo();
        }
        return salesforceTokenType;
    }

    private RequestBody getRequestBody() {
        RequestBody requestBody = new FormBody.Builder()
                .add("grant_type", grant_type)
                .add("client_id", client_id)
                .add("client_secret", client_secret)
                .add("username", username)
                .add("password", password)
                .build();
        return  requestBody;
    }

    private Request getRequest() {
        return new Request.Builder()
                .url(url)
                .post(getRequestBody())
                .build();
    }

    private void getSFAccessInfo() {
        OkHttpClient client = new OkHttpClient();
        try {
            Response response=client.newCall(getRequest()).execute();
            String jsonData=response.body().string();
            JSONObject jsonObject=new JSONObject(jsonData);
            salesforceToken = jsonObject.getString("access_token");
            salesforceTokenType = jsonObject.getString("token_type");
            salesforceInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
