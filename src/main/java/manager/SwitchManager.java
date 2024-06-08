package manager;

import controller.*;
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
    public static void goRMSPage(BaseController baseController, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/RMS.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void goLLFPage(BaseController baseController, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/LLF.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void goEDFPage(BaseController baseController, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/EDF.fxml"));
        root = loader.load();  // Load FXML trước khi lấy controller
        EDFController EDFControllerNew = loader.getController();  // Lấy controller từ loader sau khi load
        EDFControllerNew.initialize();  // Gọi phương thức initialize của controller
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void goDeadlineMonotonicPage(BaseController baseController, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(baseController.getClass().getResource("/view/DMS.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DMSController deadlineMonotonicController = loader.getController();
        DMSController.initialize();
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


