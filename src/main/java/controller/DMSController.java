package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.SwitchManager;
import show.Result;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DMSController extends BaseController implements Initializable {

    @FXML
    private TextField deadlineField;


    @FXML
    private Label errorLabel;


    @FXML
    private TextField executionTimeField;


    @FXML
    private TextField observationTimeField;


    @FXML
    private TextField periodField;


    @FXML
    private TableColumn<Result, String> processColumn;


    @FXML
    private TextField processCountField;


    @FXML
    private Button resetButton;


    @FXML
    private TableView<Result> resultTable;


    @FXML
    private Button returnButton;


    @FXML
    private TableColumn<Result, String> timeColumn;


    private ObservableList<Result> resultList = FXCollections.observableArrayList();


    @FXML
    private void handleSimulateButtonAction(ActionEvent event) {
        int processCount = Integer.parseInt(processCountField.getText());
        int observationTime = Integer.parseInt(observationTimeField.getText());
        String[] executionTimes = executionTimeField.getText().split(",");
        String[] periods = periodField.getText().split(",");
        String[] deadlines = deadlineField.getText().split(",");


        if (executionTimes.length != processCount || periods.length != processCount || deadlines.length != processCount) {
            errorLabel.setVisible(true);
            System.out.println("Number of execution times, periods, and deadlines must match the number of processes.");
            return;
        }


        int[] executionTime = new int[processCount];
        int[] period = new int[processCount];
        int[] deadline = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            executionTime[i] = Integer.parseInt(executionTimes[i]);
            period[i] = Integer.parseInt(periods[i]);
            deadline[i] = Integer.parseInt(deadlines[i]);
        }


        resultList.clear();
        resultTable.getColumns().clear();
        resultTable.getColumns().add(timeColumn);
        resultTable.getColumns().add(processColumn);

        //Kiểm tra điều kiện lập lịch
        //float utilization = 0;
        //for (int i = 0; i < processCount; i++) {
        //    utilization += (float) ((1.0 * executionTime[i]) / deadline[i]);
        //}

       // int n = processCount;
       // if (utilization > n * (Math.pow(2, 1.0 / n) - 1)) {
       //     errorLabel.setVisible(true);
       //     return;
       // } else {
       //     errorLabel.setVisible(false);
       // }

        simulateDMS(processCount, observationTime, executionTime, period, deadline);
    }


    private void simulateDMS(int processCount, int observationTime, int[] executionTime, int[] period, int[] deadline) {
        int[] remainTime = new int[processCount];
        int[] processList = new int[observationTime];
        System.arraycopy(executionTime, 0, remainTime, 0, processCount);


        for (int i = 0; i < observationTime; i++) {
            int nextProcess = -1;
            int minDeadline = Integer.MAX_VALUE;


            for (int j = 0; j < processCount; j++) {
                if (remainTime[j] > 0 && deadline[j] < minDeadline) {
                    minDeadline = deadline[j];
                    nextProcess = j;
                }
            }


            if (nextProcess != -1) {
                processList[i] = nextProcess + 1;
                remainTime[nextProcess]--;
            }


            for (int j = 0; j < processCount; j++) {
                if ((i + 1) % period[j] == 0) {
                    remainTime[j] = executionTime[j];
                }
            }
        }


        // Add process columns dynamically
        for (int p = 1; p <= processCount; p++) {
            TableColumn<Result, String> processColumn = new TableColumn<>("P[" + p + "]");
            processColumn.setCellValueFactory(new PropertyValueFactory<>("process" + p));
            processColumn.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setStyle("-fx-background-color: " + (item.equals("Running") ? "lightgreen" : "white"));
                    }
                }
            });
            resultTable.getColumns().add(processColumn);
        }


        for (int i = 0; i < observationTime; i++) {
            Result result = new Result(i);
            for (int p = 1; p <= processCount; p++) {
                result.setProcessStatus(p, processList[i] == p ? "Running" : "");
            }
            resultList.add(result);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        resultTable.setItems(resultList);
        returnButton.setOnAction(event -> {
            SwitchManager.goHomePage(this, event);
        });
        resetButton.setOnAction(event -> {
            try {
                SwitchManager.goDMSPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }



}

