package ai.tact.qa.automation.steps.apiCall;

import ai.tact.qa.automation.runner.response_model.SqlResponse;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface SFNetworkService {

    @GET("query/")
    Call<SqlResponse> getSqlQuery(@Query("q") String sqlQuery);

    @GET("query/")
    Call<ResponseBody> getSqlQueryBody(@Query("q") String sqlQuery);

    @DELETE("sobjects/{objectType}/{objectId}")
    Call<ResponseBody> deleteRecord(@Path("objectType") String objectType, @Path("objectId") String objectId );
}
