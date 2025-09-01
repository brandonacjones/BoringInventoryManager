package util;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Form Submission Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
