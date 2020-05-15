package client;

import spark.Request;
import spark.Response;

public class WebsiteMapping {
    public static String index(Request req, Response res) {
        return FileRenderer.renderContent("/static/index.html");
    }
}
