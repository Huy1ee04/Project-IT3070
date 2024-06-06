package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RMSController implements Initializable {
    @FXML
    private TextField processCountField;
    @FXML
    private TextField observationTimeField;
    @FXML
    private TextField executionTimeField;
    @FXML
    private TextField periodField;
    @FXML
    private TableView<Result> resultTable;
    @FXML
    private TableColumn<Result, Integer> timeColumn;
    @FXML
    private TableColumn<Result, String> processColumn;

    private ObservableList<Result> resultList = FXCollections.observableArrayList();

    @FXML
    private void handleSimulateButtonAction(ActionEvent event) {
        int processCount = Integer.parseInt(processCountField.getText());
        int observationTime = Integer.parseInt(observationTimeField.getText());
        String[] executionTimes = executionTimeField.getText().split(",");
        String[] periods = periodField.getText().split(",");

        if (executionTimes.length != processCount || periods.length != processCount) {
            System.out.println("Number of execution times and periods must match the number of processes.");
            return;
        }

        int[] executionTime = new int[processCount];
        int[] period = new int[processCount];
        for (int i = 0; i < processCount; i++) {
            executionTime[i] = Integer.parseInt(executionTimes[i]);
            period[i] = Integer.parseInt(periods[i]);
        }

        resultList.clear();
        simulateRMS(processCount, observationTime, executionTime, period);
    }

    private void simulateRMS(int processCount, int observationTime, int[] executionTime, int[] period) {
        int[] remainTime = new int[processCount];
        int[] processList = new int[observationTime];
        for (int i = 0; i < processCount; i++) {
            remainTime[i] = executionTime[i];
        }

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

        for (int i = 0; i < observationTime; i++) {
            resultList.add(new Result(i, processList[i] == 0 ? "" : "P[" + processList[i] + "]"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        processColumn.setCellValueFactory(new PropertyValueFactory<>("process"));
        resultTable.setItems(resultList);
    }

    public static class Result {
        private final int time;
        private final String process;

        public Result(int time, String process) {
            this.time = time;
            this.process = process;
        }

        public int getTime() {
            return time;
        }

        public String getProcess() {
            return process;
        }
    }
}
