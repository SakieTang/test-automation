package ai.tact.qa.automation.steps.apiCall;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

public class SFNetworkUtils {

    private static SFNetworkUtils instance;

    private static SFNetworkService SFNetworkService;

    private static String salesforceInstanceUrl;
    private static String salesforceAuthorization;

    final String HEADER_AUTHORIZATION = "Authorization";
    final String AUTHORIZATION_VALUE_WITH_TOKEN = "%s %s";

    public static SFNetworkUtils getInstance() {
        if (instance == null) {
            instance = new SFNetworkUtils();
        }

        return instance;
    }

    public void setUp() {
        SFAuthenticator sfAuthenticator = SFAuthenticator.getInstance();

        salesforceInstanceUrl = sfAuthenticator.getSFAccessInstanceUrl();
        salesforceAuthorization = sfAuthenticator.getSFAccessAuthorization();
    }

    public SFNetworkService getService() {

        if (SFNetworkService == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder builder = chain.request().newBuilder();

                            builder.addHeader(HEADER_AUTHORIZATION, salesforceAuthorization);

                            Response response = chain.proceed(builder.build());

                            return response;
                        }
                    });


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(String.format("%s/services/data/v40.0/", salesforceInstanceUrl)) //https://na50.salesforce.com/services/data/v40.0/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(clientBuilder.build())
                    .build();

            SFNetworkService= retrofit.create(SFNetworkService.class);
        }

        return SFNetworkService;
    }


}