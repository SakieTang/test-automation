package ai.tact.qa.automation.steps.apiCall;

import ai.tact.qa.automation.utils.CustomPicoContainer;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SFAuthenticator {

    private static SFAuthenticator instance;

    private final String url = "https://login.salesforce.com/services/oauth2/token";
    private final String grant_type = "password";

    private final String client_id = CustomPicoContainer.getInstance().getUser().getApiClientId(); //"3MVG9CEn_O3jvv0ydnh2Gwu9Kq.SoCleHJwlIjZg_Q2mjI8gSaaJ6qw0Df_qtTrb7L__rAfPKne7zILZfeE32";
    private final String client_secret = CustomPicoContainer.getInstance().getUser().getApiClientSecret(); //"6884892550614520415";
    private final String username = CustomPicoContainer.getInstance().getUser().getApiUserName(); //"automation.tactsf.s@gmail.com";    //sf account name
    private final String password = CustomPicoContainer.getInstance().getUser().getApiPassword(); //"Tact20189ilpMZITdoDz6ABvYGrn8xHS";

    //iOS
//    private final String client_id = "3MVG9CEn_O3jvv0ydnh2Gwu9Kq.SoCleHJwlIjZg_Q2mjI8gSaaJ6qw0Df_qtTrb7L__rAfPKne7zILZfeE32";
//    private final String client_secret = "6884892550614520415";
//    private final String username = "automation.tactsf.s@gmail.com";    //sf account name
//    private final String password = "tact2018VJBga3RjC06Cyv3ZAU1SaVVC";
    //Android
//    private final String client_id = "3MVG9CEn_O3jvv0yiRj2fbfaQUhUaoUKiB8Os5x9BcWqzjOABcnt2LmRpqLT17SnV1ANFktY3tegiN_E2QHzP";
//    private final String client_secret = "6864499967095259178";
//    private final String username = "automation.tactAndrSF.s@gmail.com";    //sf account name
//    private final String password = "tact2018lPt4DznhMZxZkrR94uOflHBac";

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
