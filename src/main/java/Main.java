import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(getEnvPort());
        get("/hello", (req, res) -> "Hello World");
    }

    static int getEnvPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port
    }
}
