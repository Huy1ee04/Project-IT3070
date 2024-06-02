package controller;

import Item.PackageItem;
import scheduler.WFQScheduler;
import container.Flow;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import manager.SwitchManager;
import manager.UpdateSizeManager;
import transmisstion.QueueOut;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class WFQController extends BaseController{

    @FXML
    private Button returnButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button createFirstFlow;

    @FXML
    private Button createSecondFlow;

    @FXML
    private Button createThirdFlow;

    @FXML
    private Button startButton;

    @FXML
    private HBox firstFlowHBox;

    @FXML
    private HBox secondFlowHBox;

    @FXML
    private HBox thirdFlowHBox;

    @FXML
    private HBox outputQueueHBox;
    @FXML
    private Text resourceAllocFlow1;
    @FXML
    private Text resourceAllocFlow2;
    @FXML
    private Text resourceAllocFlow3;
    @FXML
    private Text resourceReturnFlow1;
    @FXML
    private Text resourceReturnFlow2;
    @FXML
    private Text resourceReturnFlow3;

    public void initialize() {
        ArrayList<Flow> flows = new ArrayList<>();
        QueueOut queueOut = new QueueOut();
        queueOut.sethBox(outputQueueHBox);
        queueOut.setThroughput(120);
        Flow flow1 = new Flow(2);
        Flow flow2 = new Flow(1);
        Flow flow3 = new Flow(1);
        flow1.setNameFlow("First Flow");
        flow2.setNameFlow("Second Flow");
        flow3.setNameFlow("Third Flow");
        flow1.setHbox(firstFlowHBox);
        flow2.setHbox(secondFlowHBox);
        flow3.setHbox(thirdFlowHBox);
        flow1.setResourceAlloc(resourceAllocFlow1);
        flow2.setResourceAlloc(resourceAllocFlow2);
        flow3.setResourceAlloc(resourceAllocFlow3);
        flow1.setResourceReturn(resourceReturnFlow1);
        flow2.setResourceReturn(resourceReturnFlow2);
        flow3.setResourceReturn(resourceReturnFlow3);
        flows.add(flow1);
        flows.add(flow2);
        flows.add(flow3);
        createFirstFlow.setOnAction(event -> showInputDialogAndAddSquare(flow1,  "First Flow", Color.BLUE));
        createSecondFlow.setOnAction(event -> showInputDialogAndAddSquare(flow2,  "Second Flow", Color.GREEN));
        createThirdFlow.setOnAction(event -> showInputDialogAndAddSquare(flow3,  "Third Flow", Color.YELLOW));
        startButton.setOnAction(event -> {
            WFQScheduler.simulationWFQ(flows, queueOut);
        });
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
        queueOut.gethBox().setOnMouseClicked(mouseEvent -> {
            UpdateSizeManager.updateQueueOutputSize(queueOut);
        });
        for(Flow flow : flows) {
            flow.getHbox().setOnMouseClicked(mouseEvent -> {
                UpdateSizeManager.updateFlowSize(flow);
            });
        }
    }

    private void showInputDialogAndAddSquare(Flow flow, String flowName, Color color) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Package Size");
        dialog.setHeaderText("Enter the size of the package for " + flowName);
        dialog.setContentText("Size:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(size -> {
            if (!size.trim().isEmpty()) { // Kiểm tra nhập liệu không rỗng
                try {
                    int packageSize = Integer.parseInt(size);
                    PackageItem pkg = new PackageItem(packageSize);
                    pkg.setNode(addSquareToHBox(flow.getHbox(), color));
                    pkg.getNode().addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText(""+pkg.getSizePackage());
                        alert.showAndWait();
                        event.consume(); // Ngăn chặn sự kiện nổi bật lên các phần tử cha
                    });
                    flow.addPacket(pkg);
                } catch (NumberFormatException e) {
                    // Handle the case where input is not a valid integer
                    System.err.println("Invalid size entered: " + size);
                }
            } else {
                System.err.println("Empty size entered.");
            }
        });
    }

    private Node addSquareToHBox(HBox hbox, Color color) {
        Rectangle square = new Rectangle(20, 20);
        square.setFill(color);

        // Thiết lập sự kiện chuột Capturing Event

        hbox.getChildren().addFirst(square);
        return square;
    }

}
