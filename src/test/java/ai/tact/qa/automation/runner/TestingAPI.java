package ai.tact.qa.automation.runner;

import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.http.POST;


import java.io.IOException;

public class TestingAPI {

    public static String api () {
        OkHttpClient client = new OkHttpClient();
        String token = null;

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"grant_type\"\r\n\r\npassword\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"client_id\"\r\n\r\n3MVG9CEn_O3jvv0ydnh2Gwu9Kq.SoCleHJwlIjZg_Q2mjI8gSaaJ6qw0Df_qtTrb7L__rAfPKne7zILZfeE32\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"client_secret\"\r\n\r\n6884892550614520415\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"username\"\r\n\r\nautomation.tactsf.s@gmail.com\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\nTact20189ilpMZITdoDz6ABvYGrn8xHS\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
                .url("https://login.salesforce.com/services/oauth2/token")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "06c7d4de-db39-4da7-9e3a-53b497680783")
                .build();

        Response response;

        {
            try {
                System.out.println("before the call");
                response=client.newCall(request).execute();
                System.out.println("after the call");
                String r = response.toString();
                String sBody = response.body().string();
                token = sBody.split(":")[1].split(",")[0];
                System.out.println("response ==>" + r);
                System.out.println("body ==>" + sBody);
                System.out.println("token ==>" + token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    public static void main(String[] args) {
        String token = TestingAPI.api().replace("\"","");
        token = String.format("Bearer %s", token);
        System.out.println("token ==>" + token);

        NetworkUtils networkUtils = NetworkUtils.getInstance();

        try {
            System.out.println(networkUtils.getService().getSoql().execute().body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

//
//        networkUtils.getService().getTest().enqueue(new retrofit2.Callback<ResponseBody>() {
//            @Override
//            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//                try {
//                    System.out.println(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable throwable) {
//
//            }
//        });
    }

}
