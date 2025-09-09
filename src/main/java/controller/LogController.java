package controller;

import dao.LogsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.LogItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;


public class LogController {

    @FXML private CheckBox fx_type_check;
    @FXML private CheckBox fx_user_check;
    @FXML private CheckBox fx_description_check;
    @FXML private TableView<LogItem> fx_logtable;
    @FXML private TableColumn<LogItem, String> fx_timecol;
    @FXML private TableColumn<LogItem, String> fx_typecol;
    @FXML private TableColumn<LogItem, String> fx_usercol;
    @FXML private TableColumn<LogItem, String> fx_descriptioncol;
    @FXML private TextField fx_logfield;

    private LogsDAO logsDAO = new LogsDAO();
    private Scene scene;
    private List<LogItem> logItems = new ArrayList<>();

    public void setScene(Scene scene) {this.scene = scene;}


    @FXML
    public void initialize() throws Exception {
        logItems = logsDAO.getItems();

        fx_timecol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        fx_typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        fx_usercol.setCellValueFactory(new PropertyValueFactory<>("user"));
        fx_descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        fx_logtable.setItems(FXCollections.observableArrayList(logItems));
    }

    @FXML
    public void search() {
        ObservableList<LogItem> original = FXCollections.observableList(logItems);
        FilteredList<LogItem> filtered = new FilteredList<>(original, logItem -> true);

        String query = fx_logfield.getText().trim().toLowerCase();

        filtered.setPredicate(logItem ->
            query.isEmpty() ||
                    (logItem.getType().toLowerCase().contains(query) && fx_type_check.isSelected()) ||
                    (logItem.getDescription().toLowerCase().contains(query) && fx_description_check.isSelected()) ||
                    (logItem.getUser().contains(query) && fx_user_check.isSelected())
        );

        fx_logtable.setItems(filtered);
    }
}
