import atlantafx.base.theme.PrimerLight;
import controller.LoginController;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.awt.Dimension;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        Dimension userDisplayDimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int USER_DISPLAY_WIDTH = userDisplayDimension.width;
        final int USER_DISPLAY_HEIGHT = userDisplayDimension.height;

        // Load main scene
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        Scene mainScene = new Scene(mainLoader.load(), USER_DISPLAY_WIDTH * 0.5, USER_DISPLAY_HEIGHT * 0.5);
        MainController mainController = mainLoader.getController();
        mainController.setScene(mainScene);

        // Load Login Scene
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 400, 250);
        LoginController controller = loginLoader.getController();
        controller.setScene(mainScene);
        controller.setMainController(mainController);


        stage.setScene(loginScene);
        stage.setTitle("Boring Inventory Manager");
        stage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
