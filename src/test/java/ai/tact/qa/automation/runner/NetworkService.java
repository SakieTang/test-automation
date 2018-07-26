package ai.tact.qa.automation.runner;

import ai.tact.qa.automation.runner.response_model.SqlResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface NetworkService {

    @GET("query/")
    Call<SqlResponse> getSqlQuery(@Query("q") String sqlQuery);

}
