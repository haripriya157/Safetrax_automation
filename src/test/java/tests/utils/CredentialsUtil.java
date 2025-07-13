package tests.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class CredentialsUtil {
    public static JSONObject getCredentials(String type) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/test/resources/credentials.json")));
            JSONObject json = new JSONObject(content);
            return json.getJSONObject(type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read credentials", e);
        }
    }
}


