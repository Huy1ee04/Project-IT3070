package os;

import java.io.IOException;
import java.util.Objects;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // load the FXML resource
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
            Image icon = new Image("file:D:/2023.2/it3070/Project-IT3070/src/main/resources/img/app_icon.png");


            // Set the icon for the stage
            primaryStage.getIcons().add(icon);
            // create a scene
            primaryStage.setScene(new Scene(root, 800, 600));

            // prevent resizing
            primaryStage.setResizable(false);

            // show the GUI
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
