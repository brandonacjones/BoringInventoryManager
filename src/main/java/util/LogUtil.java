package util;

import controller.LoginController;
import javafx.scene.control.Alert;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class LogUtil {
    public static void logEvent(String username, String type, String description) throws Exception {
        LocalDateTime utc = LocalDateTime.now(ZoneId.of("Z"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss");
        String currentTimeUtc = utc.format(dtf);

        HttpClient client = HttpClient.newHttpClient();
        JSONObject payload = new JSONObject();
        payload.put("type", type);
        payload.put("user", username);
        payload.put("description", description);
        payload.put("created_at", currentTimeUtc);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DBUtil.getSupabaseUrl() + "/rest/v1/logs"))
                    .header("Content-Type", "application/json")
                    .header("apikey", DBUtil.getSupabaseKey())
                    .header("Authorization", "Bearer " + LoginController.getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Log success");
                success.setHeaderText("Log submitted successfully");
                success.setContentText("Log submitted with response body status code 201.");
                success.showAndWait();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Log Error");
                error.setHeaderText("Error submitting activity log");
                error.setContentText(response.body());
            }
        } catch (Exception e) {
            System.err.println("LogUtil Error: " + e.getMessage());
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Log Exception");
            error.setHeaderText("Exception in log submission");
            error.setContentText("Error: " + e.getMessage());
            error.showAndWait();
        }
    }
}
