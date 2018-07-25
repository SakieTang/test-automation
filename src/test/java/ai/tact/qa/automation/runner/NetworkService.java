package ai.tact.qa.automation.runner;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkService {

    @GET("https://data.sfgov.org/resource/bbb8-hzi6.json ")
    Call<String> getTest();

}
