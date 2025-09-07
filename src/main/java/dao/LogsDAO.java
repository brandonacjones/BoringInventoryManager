package dao;

import controller.LoginController;
import model.LogItem;
import org.json.JSONArray;
import org.json.JSONObject;
import util.DBUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LogsDAO {

    public List<LogItem> getItems() throws Exception {
        List<LogItem> logItems = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DBUtil.getSupabaseUrl() + "/rest/v1/logs?select=*"))
                .header("Content-Type", "application/json")
                .header("apikey", DBUtil.getSupabaseKey())
                .header("Authorization", "Bearer " + LoginController.getJwtToken())
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            JSONArray jsonArray = new JSONArray(response.body());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                LogItem logItem = new LogItem();
                logItem.setCreated_at(json.getString("created_at"));
                logItem.setType(json.getString("type"));
                logItem.setDescription(json.getString("username"));
                logItem.setDescription(json.getString("description"));
                logItems.add(logItem);
            }
        } else {
            throw new Exception("Failed to fetch items: " + response.body());
        }
        return logItems;
    }
}
