package ai.tact.qa.automation.runner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface NetworkService {

    @GET("query/${query}")
    Call<ResponseBody> getSqlQuery(@Path("query") String sqlQuery);

}
