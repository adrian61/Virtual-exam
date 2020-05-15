package client;

import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;

public class FileRenderer {

    public static String renderContent(String htmlFile) {
        String html = null;
        try {
            html = IOUtils.toString(Spark.class.getResourceAsStream(htmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }
}
