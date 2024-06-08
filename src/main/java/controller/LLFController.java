package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import manager.SwitchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LLFController extends BaseController implements Initializable {
    public Button returnButton;
    public Button resetButton;
    public Label errorLabel;
    @FXML
    private TextField processCountField;
    @FXML
    private TextField observationTimeField;
    @FXML
    private TextField executionTimeField;
    @FXML
    private TextField periodField;
    @FXML
    private TextField arrivalTimeField;
    @FXML
    private TextField deadlineField;
    @FXML
    private TableView<Result> resultTable;
    @FXML
    private TableColumn<Result, Integer> timeColumn;


    private ObservableList<Result> resultList = FXCollections.observableArrayList();

    @FXML
    private void handleSimulateButtonAction(ActionEvent event) {
        int processCount = Integer.parseInt(processCountField.getText());
        int observationTime = Integer.parseInt(observationTimeField.getText());
        String[] executionTimes = executionTimeField.getText().split(",");
        String[] periods = periodField.getText().split(",");
        String[] arrivalTimes = arrivalTimeField.getText().split(",");
        String[] deadlines = deadlineField.getText().split(",");

        if (executionTimes.length != processCount || periods.length != processCount || arrivalTimes.length != processCount || deadlines.length != processCount) {
            errorLabel.setVisible(true);
            System.out.println("Number of execution times and periods must match the number of processes.");
            return;
        }

        int[] executionTime = new int[processCount];
        int[] period = new int[processCount];
        int[] arrivalTime = new int[processCount];
        int[] deadline = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            executionTime[i] = Integer.parseInt(executionTimes[i]);
            period[i] = Integer.parseInt(periods[i]);
            arrivalTime[i] = Integer.parseInt(periods[i]);
            deadline[i] = Integer.parseInt(deadlines[i]);
        }


        resultList.clear();
        resultTable.getColumns().clear();
        resultTable.getColumns().add(timeColumn);


//        kiểm tra điê kiện sử dụng cpu
        float utilization = 0;
        for (int i = 0; i < processCount; i++) {
            utilization += (float) ((1.0 * executionTime[i]) / period[i]);
        }

        int n = processCount;
        if (utilization > n * (Math.pow(2, 1.0 / n) - 1)) {
            errorLabel.setVisible(true);
            return;
        } else {
            errorLabel.setVisible(false);
        }

        simulateLLF(processCount, observationTime, executionTime, period);
    }

    private void simulateLLF(int processCount, int observationTime, int[] executionTime, int[] period) {
        int[] remainTime = new int[processCount];
        int[] processList = new int[observationTime];
        System.arraycopy(executionTime, 0, remainTime, 0, processCount);

        for (int i = 0; i < observationTime; i++) {
            int nextProcess = -1;
            int min = Integer.MAX_VALUE;

            for (int j = 0; j < processCount; j++) {
                if (remainTime[j] > 0 && period[j] < min) {
                    min = period[j];
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
                SwitchManager.goRMSPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static class Result {
        private final int time;
        private final String[] processStatus;

        public Result(int time) {
            this.time = time;
            this.processStatus = new String[10]; // Assume max 10 processes for simplicity
        }

        public int getTime() {
            return time;
        }

        public String getProcess1() {
            return processStatus[0];
        }

        public String getProcess2() {
            return processStatus[1];
        }

        public String getProcess3() {
            return processStatus[2];
        }

        public String getProcess4() {
            return processStatus[3];
        }

        public String getProcess5() {
            return processStatus[4];
        }

        public String getProcess6() {
            return processStatus[5];
        }

        public String getProcess7() {
            return processStatus[6];
        }

        public String getProcess8() {
            return processStatus[7];
        }

        public String getProcess9() {
            return processStatus[8];
        }

        public String getProcess10() {
            return processStatus[9];
        }

        public void setProcessStatus(int process, String status) {
            if (process >= 1 && process <= 10) {
                processStatus[process - 1] = status;
            }
        }
    }
}
