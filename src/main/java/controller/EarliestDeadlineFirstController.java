package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import manager.SwitchManager;

import java.io.IOException;

public class EarliestDeadlineFirstController extends BaseController{
    @FXML
    Rectangle CPU;
    @FXML
    HBox processQueue;
    @FXML
    HBox nameProcessQueue;
    @FXML
    Button returnButton;
    @FXML
    Button resetButton;
    public void initialize(){
        returnButton.setOnAction(event -> {
            SwitchManager.goHomePage(this, event);
        });
        resetButton.setOnAction(event -> {
            try {
                SwitchManager.goWFQPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
