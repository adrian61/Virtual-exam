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

        path("/", () -> {   // TODO: Website mapping
            get("/", WebsiteMapping::index);
            get("/index", WebsiteMapping::index);

        });

        path("/api", () -> {
            path("/exam", () -> {
                post("/", null);                    // Creating a new exam
                get("/:examId", null);              // Getting data about an existing exam
                put("/:examId", null);              // Updating exam
                delete("/:examId", null);           // Deleting exam

                post("/:examId/student", null);     // Student sends his exam answers
                get("/:examId/student", null);      // Student gets exam data (not view or answers!)
            });
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
