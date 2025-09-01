package dao;

import model.InventoryItem;
import org.json.JSONArray;
import org.json.JSONObject;
import util.DBUtil;
import controller.LoginController;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    public List<InventoryItem> getItems() throws Exception {
        List<InventoryItem> items = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DBUtil.getSupabaseUrl() + "/rest/v1/inventory?select=*"))
                .header("Content-Type", "application/json")
                .header("apikey", DBUtil.getSupabaseKey())
                .header("Authorization", "Bearer " + LoginController.getJwtToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            JSONArray jsonArray = new JSONArray(response.body());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                InventoryItem item = new InventoryItem();
                item.setPartNumber(json.getString("partnumber"));
                item.setTestNumber(json.getInt("testnumber"));
                item.setSerialNumber(json.getInt("serialnumber"));
                item.setQuantity(json.getInt("quantity"));
                item.setAllocation(json.getString("allocation"));
                item.setPurchaseOrder(json.getInt("purchaseorder"));
                item.setDescription(json.getString("description"));
                item.setLocation(json.getString("location"));
                items.add(item);
            }
        } else {
            throw new Exception("Failed to fetch items: " + response.body());
        }
        return items;
    }

    public void delete(String partNumber) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DBUtil.getSupabaseUrl() + "/rest/v1/inventory?partnumber=eq." + partNumber))
                .header("Content-Type", "application/json")
                .header("apikey", DBUtil.getSupabaseKey())
                .header("Authorization", "Bearer " + LoginController.getJwtToken())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 204) {
            throw new Exception("Failed to delete item: " + response.body());
        }
    }
}
