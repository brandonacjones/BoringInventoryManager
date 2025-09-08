package controller;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.DBUtil;
import util.LogUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static util.AlertUtil.showError;
import static util.ValidationUtil.validateInteger;
import static util.ValidationUtil.validateString;

public class AddController {
    @FXML Button fx_submitbutton;
    @FXML Button fx_cancelbutton;
    @FXML TextField fx_partnumberfield;
    @FXML TextField fx_testnumberfield;
    @FXML TextField fx_serialnumberfield;
    @FXML TextField fx_quantityfield;
    @FXML TextField fx_allocationfield;
    @FXML TextField fx_purchaseorderfield;
    @FXML TextField fx_descriptionfield;
    @FXML TextField fx_locationfield;
    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    @FXML
    public void initialize() {

    }

    @FXML
    private void submit() throws Exception {

        String partNumber = validateString("Part Number", fx_partnumberfield.getText());
        if (partNumber == null) return;

        Integer testNumber = validateInteger("Test Number", fx_testnumberfield.getText());
        if (testNumber == null) return;

        Integer serialNumber = validateInteger("Serial Number", fx_serialnumberfield.getText());
        if (serialNumber == null) return;

        Integer quantity = validateInteger("Quantity", fx_quantityfield.getText());
        if (quantity == null) return;

        String allocation = validateString("Allocation", fx_allocationfield.getText());
        if (allocation == null) return;

        Integer purchaseOrder = validateInteger("Purchase Order", fx_purchaseorderfield.getText());
        if (purchaseOrder == null) return;

        String description = validateString("Description", fx_descriptionfield.getText());
        if (description == null) return;

        String location = validateString("Location", fx_locationfield.getText());
        if (location == null) return;

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject payload = new JSONObject();
            payload.put("partnumber", partNumber);
            payload.put("testnumber", testNumber);
            payload.put("serialnumber", serialNumber);
            payload.put("quantity", quantity);
            payload.put("allocation", allocation);
            payload.put("purchaseorder", purchaseOrder);
            payload.put("description", description);
            payload.put("location", location);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DBUtil.getSupabaseUrl() + "/rest/v1/inventory"))
                    .header("Content-Type", "application/json")
                    .header("apikey", DBUtil.getSupabaseKey())
                    .header("Authorization", "Bearer " + LoginController.getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {

                LogUtil.logEvent(MainController.username, "CREATE", "Created PN <" + partNumber + ">.");

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Submission Successful");
                success.setContentText("Form data submitted successfully.");
                success.showAndWait();

            } else {
                showError("Database Error: " + response.body());
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) fx_cancelbutton.getScene().getWindow();
        stage.close();
    }

}
