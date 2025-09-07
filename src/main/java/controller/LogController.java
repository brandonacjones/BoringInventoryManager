package controller;

import dao.LogsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.LogItem;

import java.util.ArrayList;
import java.util.List;


public class LogController {

    @FXML private TableView<LogItem> fx_logtable;
    @FXML private TableColumn<LogItem, String> fx_timecol;
    @FXML private TableColumn<LogItem, String> fx_typecol;
    @FXML private TableColumn<LogItem, String> fx_usercol;
    @FXML private TableColumn<LogItem, String> fx_descriptioncol;

    private LogsDAO logsDAO = new LogsDAO();
    private Scene scene;
    private List<LogItem> logItems = new ArrayList<>();

    public void setScene(Scene scene) {this.scene = scene;}


    @FXML
    private void initialize() {
        fx_timecol.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        fx_typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        fx_usercol.setCellValueFactory(new PropertyValueFactory<>("user"));
        fx_descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        fx_logtable.setItems(FXCollections.observableArrayList(logItems));
    }
}
