package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import manager.SwitchManager;

import java.io.IOException;

public class MainController extends BaseController{
    @FXML
    private Button WFQButton;
    @FXML
    private Button RMSButton;
    @FXML
    private Button EDFButton;
    @FXML
    private Button LLFButton;
    @FXML
    private Button DMSButton;
    public void initialize(){

    WFQButton.setOnAction(event -> {
        try {
            SwitchManager.goWFQPage(this,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });
    LLFButton.setOnAction(event -> {
        try {
            SwitchManager.goLLFPage(this,event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    });
        RMSButton.setOnAction(event -> {
            try {
                SwitchManager.goRMSPage(this,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        EDFButton.setOnAction(event -> {
            try {
                SwitchManager.goEDFPage(this,event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        DMSButton.setOnAction(event -> {
            try{
                SwitchManager.goDMSPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
}
}
