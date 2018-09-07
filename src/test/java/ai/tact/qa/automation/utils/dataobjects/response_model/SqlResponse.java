package ai.tact.qa.automation.utils.dataobjects.response_model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SqlResponse {
    @SerializedName("totalSize")
    public Integer totalSize;

    @SerializedName("records")
    public ArrayList<JsonObject> records;
}
