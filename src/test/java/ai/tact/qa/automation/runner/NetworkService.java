package ai.tact.qa.automation.runner;

import com.google.gson.JsonArray;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

public interface NetworkService {

    @GET("bbb8-hzi6.json")
    Call<ResponseBody> getTest();

    //https://na50.salesforce.com/services/data/v40.0/query/?q=SELECT+Id%2C+FirstName%2C+LastName+FROM+Lead+WHERE+FirstName+LIKE+%27%25t%25%27
    //base_url: https://na50.salesforce.com/services/data/v40.0/
//    Bearer 00D6A000002IaPT!AQkAQGn_wJyNY13.qk8b1VTewA0D5kG4.RQzHna6GR0wmpA6zmqdHZTQ_QMJDMDqW42pftXuUvAPwx.Wec92R7Etq8ocfW9l
//    @Path("token") String token
    @Headers("Authorization: Bearer 00D6A000002IaPT!AQkAQGn_wJyNY13.qk8b1VTewA0D5kG4.RQzHna6GR0wmpA6zmqdHZTQ_QMJDMDqW42pftXuUvAPwx.Wec92R7Etq8ocfW9l")  //{token}");
    @GET("query/?q=SELECT+Id%2C+FirstName%2C+LastName+FROM+Lead+WHERE+FirstName+LIKE+%27%25t%25%27")
    Call<ResponseBody> getSoql();   //@Path("token") String token);
}
