import api.Example;
import client.WebsiteMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class Main {
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        staticFiles.location("/static");
        port(getEnvPort());

        path("/", () -> {
            get("/", WebsiteMapping::index);
            get("/index", WebsiteMapping::index);
        });

        path("/api", () -> {
            before("/*", (req, res) -> logger.info("Received api call: " + req.uri()));

            get("/example", Example::example);  // Example REST API call
        });
    }

    private static int getEnvPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; // Return default port
    }
}
