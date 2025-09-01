package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;
import util.DBUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {

    @FXML private TextField fx_usernamefield;
    @FXML private Text fx_errortext;
    @FXML private PasswordField fx_passwordfield;
    @FXML private Button fx_submitbutton;
    private Scene mainScene;
    private MainController mainController;
    private static String jwtToken;

    public void setScene(Scene scene) {
        this.mainScene = scene;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String token) {
        jwtToken = token;
    }

    @FXML
    private void submit() {
        String email = fx_usernamefield.getText().trim();
        String password = fx_passwordfield.getText();

        if (email.isEmpty() || password.isEmpty()) {
            fx_errortext.setVisible(true);
            fx_errortext.setText("Email and Password Required.");
            return;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject payload = new JSONObject();
            payload.put("email", email);
            payload.put("password", password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DBUtil.getSupabaseUrl() + "/auth/v1/token?grant_type=password"))
                    .header("Content-Type", "application/json")
                    .header("apikey", DBUtil.getSupabaseKey())
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject responseJson = new JSONObject(response.body());

            if (response.statusCode() == 200) {
                jwtToken = responseJson.getString("access_token");
                switchToMainScene(email);
            } else {
                fx_errortext.setVisible(true);
                fx_errortext.setText("Login failed: " + responseJson.optString("error_description", "Something Broke."));
            }
        } catch (Exception e) {
            fx_errortext.setVisible(true);
            fx_errortext.setText("Error: " + e.getMessage());
        }
    }

    private void switchToMainScene(String email) {
        Stage stage = (Stage) fx_submitbutton.getScene().getWindow();
        stage.setScene(mainScene);
        stage.setTitle("Boring Inventory Manager");
        stage.show();
        try {
            mainController.setEmail(email);
            mainController.refresh();
        } catch (Exception e) {
            fx_errortext.setVisible(true);
            fx_errortext.setText("Refresh failed: " + e.getMessage());
        }
    }
}
