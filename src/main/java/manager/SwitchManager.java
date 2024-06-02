package manager;

import controller.BaseController;
import controller.DeadlineMonotonicController;
import controller.MainController;
import controller.WFQController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchManager {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    public static void goWFQPage(BaseController baseController, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/WFQ.fxml"));
        root = loader.load();
        WFQController WFQControllerNew = loader.getController();
        WFQControllerNew.initialize();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void goDeadlineMonotonicPage(BaseController baseController, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/DeadlineMonotonic.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DeadlineMonotonicController deadlineMonotonicController = loader.getController();
        DeadlineMonotonicController.initialize();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void goHomePage(BaseController baseController, Event event){
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/Main.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainController mainController = loader.getController();
        mainController.initialize();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


