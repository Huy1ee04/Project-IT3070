package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import manager.SwitchManager;

import java.io.IOException;

public class MainController extends BaseController{
    @FXML
    private Button WFQButton;

public void initialize(){
    WFQButton.setOnAction(event -> {
        try {
            SwitchManager.goWFQPage(this,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });
}
}
