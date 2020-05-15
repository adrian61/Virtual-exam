package api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public abstract class Example {
    public static JsonObject example(Request req, Response res) {
        res.type("application/json");
        Gson gson = new Gson();
        return gson.fromJson("{\"id\":1, \"value\":\"example\"}", JsonObject.class);
    }
}
