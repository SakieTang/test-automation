package ai.tact.qa.automation.runner;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

public class NetworkUtils {

    private static NetworkUtils instance;

    private static NetworkService networkService;

    private static String salesforceToken;

    final String HEADER_AUTHORIZATION = "Authorization";
    final String AUTHORIZATION_VALUE_WITH_TOKEN = "Bearer %s";

    public static NetworkUtils getInstance() {
        if (instance == null) {
            instance = new NetworkUtils();
        }

        return instance;
    }

    public void setSalesforceToken(String salesforceToken) {
        NetworkUtils.salesforceToken = salesforceToken;
    }

    public NetworkService getService() {

        if (networkService == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();

                            builder.addHeader(HEADER_AUTHORIZATION, String.format(AUTHORIZATION_VALUE_WITH_TOKEN, salesforceToken));

                            Response response = chain.proceed(builder.build());

                            return response;
                        }
                    });


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://na50.salesforce.com/services/data/v40.0/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();

            networkService = retrofit.create(NetworkService.class);
        }

        return networkService;
    }
}