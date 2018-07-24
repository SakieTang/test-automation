package ai.tact.qa.automation.runner;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkUtils {

    private static NetworkUtils instance;

    private static NetworkService networkService;

    public static NetworkUtils getInstance() {
        if (instance == null) {
            instance = new NetworkUtils();
        }

        return instance;
    }

    public NetworkService getService() {

        if (networkService == null) {
            OkHttpClient okHttpClient = new OkHttpClient();

            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("")
//                    .baseUrl("https://data.sfgov.org/resource/")
                    .baseUrl("https://na50.salesforce.com/services/data/v40.0/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            networkService = retrofit.create(NetworkService.class);
        }

        return networkService;
    }
}