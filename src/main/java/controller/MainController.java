package controller;

import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import dao.InventoryDAO;
import model.InventoryItem;
import util.DBUtil;
import util.LogUtil;

import static util.AlertUtil.showError;
import static util.TimeUtil.getLocalTime;

public class MainController {
    @FXML private TableView<InventoryItem> fx_table;
    @FXML private TableColumn<InventoryItem, String> fx_partnumber;
    @FXML private TableColumn<InventoryItem, Integer> fx_testnumber;
    @FXML private TableColumn<InventoryItem, Integer> fx_serialnumber;
    @FXML private TableColumn<InventoryItem, Integer> fx_quantity;
    @FXML private TableColumn<InventoryItem, String> fx_allocation;
    @FXML private TableColumn<InventoryItem, Integer> fx_purchaseorder;
    @FXML private TableColumn<InventoryItem, String> fx_description;
    @FXML private TableColumn<InventoryItem, String> fx_location;
    @FXML private CheckMenuItem fx_view_lighttheme;
    @FXML private CheckMenuItem fx_view_darktheme;
    @FXML private MenuItem fx_file_activitylog;
    @FXML private Button fx_additem;
    @FXML private Button fx_signout;
    @FXML private Text fx_refreshtext;
    @FXML private TextField fx_searchfield;
    @FXML private Text fx_signedinas;

    private InventoryDAO inventoryDAO = new InventoryDAO();
    private Scene scene;
    private List<InventoryItem> items = new ArrayList<>();

    public static String username = "";

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setEmail(String email) {
        fx_signedinas.setText("Signed in as " + email);
        username = email;
    }

    @FXML
    public void initialize() {
        // Map columns to model properties
        fx_partnumber.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
        fx_testnumber.setCellValueFactory(new PropertyValueFactory<>("testNumber"));
        fx_serialnumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        fx_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        fx_allocation.setCellValueFactory(new PropertyValueFactory<>("allocation"));
        fx_purchaseorder.setCellValueFactory(new PropertyValueFactory<>("purchaseOrder"));
        fx_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        fx_location.setCellValueFactory(new PropertyValueFactory<>("location"));

        fx_table.setItems(FXCollections.observableArrayList(items));


        // Update Time
        fx_refreshtext.setText(getLocalTime());

        // Add Context Menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");
        contextMenu.getItems().addAll(editItem, deleteItem);

        editItem.setOnAction(event -> {
            InventoryItem selectedItem = fx_table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    editItem(selectedItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        deleteItem.setOnAction(event -> {
            InventoryItem selectedItem = fx_table.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                try {
                    deleteItem(selectedItem);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fx_table.setRowFactory(tv -> {
            TableRow<InventoryItem> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty() && row.isSelected()) {
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    @FXML
    private void setLightTheme() {
        scene.getStylesheets().clear();
        scene.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        fx_view_darktheme.setSelected(false);
        fx_view_lighttheme.setSelected(true);
    }

    @FXML
    private void setDarkTheme() {
        scene.getStylesheets().clear();
        scene.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        fx_view_lighttheme.setSelected(false);
        fx_view_darktheme.setSelected(true);
    }

    @FXML
    private void addItem() throws Exception {
        Stage newStage = new Stage();
        newStage.setTitle("Add Item");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/additem.fxml"));
        Scene newScene = new Scene(loader.load(), 400, 600);
        newStage.setScene(newScene);

        newScene.setUserAgentStylesheet(scene.getUserAgentStylesheet());

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initOwner(fx_table.getScene().getWindow());

        newStage.showAndWait();
    }

    @FXML
    public void refresh() {
        try {
            items.clear();
            items = inventoryDAO.getItems();
            fx_table.setItems(FXCollections.observableArrayList(items));
            fx_refreshtext.setText(getLocalTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void search() {
        String current = fx_searchfield.getText().trim().toUpperCase();

        ObservableList<InventoryItem> observableList = FXCollections.observableArrayList(items);
        FilteredList<InventoryItem> filteredList = new FilteredList<>(observableList, item -> true);

        filteredList.setPredicate(item ->
                current.isEmpty() || item.getPartNumber().toUpperCase().contains(current));
        fx_table.setItems(filteredList);
    }

    @FXML
    private void editItem(InventoryItem selectedItem) throws Exception{
        Stage editStage = new Stage();
        editStage.setTitle("Edit Item");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edititem.fxml"));
        Scene editScene = new Scene(loader.load(), 400, 600);
        editStage.setScene(editScene);

        EditController editController = loader.getController();
        editController.setItem(selectedItem);
        editScene.setUserAgentStylesheet(scene.getUserAgentStylesheet());

        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.initOwner(fx_table.getScene().getWindow());
        editStage.showAndWait();
    }

    private void deleteItem(InventoryItem item) throws Exception {

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Item");
        confirmAlert.setContentText("Are you sure you want to delete " + item.getDescription() + " " + item.getPartNumber() + "?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                inventoryDAO.delete(item.getPartNumber());
                LogUtil.logEvent(MainController.username, "DELETE", "Deleted PN <" + item.getPartNumber() + ">.");
                refresh();
            } catch (SQLException e) {
                showError(e.getMessage());
            }
        }

    }

    @FXML
    private void signOut() throws Exception {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DBUtil.getSupabaseUrl() + "/auth/v1/logout"))
                    .header("Content-Type", "application/json")
                    .header("apikey", DBUtil.getSupabaseKey())
                    .header("Authorization", "Bearer " + LoginController.getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString("{\"scope\":\"global\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204 && response.statusCode() != 200) {
                showError("Logout failed: " + response.body());
                return;
            }

            LoginController.setJwtToken(null);

            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Scene loginScene = new Scene(loginLoader.load(), 400, 250);
            LoginController controller = loginLoader.getController();
            controller.setScene(scene);
            controller.setMainController(this);

            Stage stage = (Stage) fx_signout.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Boring Inventory Manager");
            stage.show();
        } catch (Exception e) {
            showError("Logout Failed: " + e.getMessage());
        }
    }

    @FXML
    private void viewAllLogs() throws Exception {
        Stage logStage = new Stage();
        logStage.setTitle("Activity Logs");

        FXMLLoader logLoader = new FXMLLoader(getClass().getResource("/logs.fxml"));
        Scene logScene = new Scene(logLoader.load(), this.scene.getWidth(), this.scene.getHeight());
        logStage.setScene(logScene);

        logScene.setUserAgentStylesheet(scene.getUserAgentStylesheet());
        logStage.showAndWait();
    }
}