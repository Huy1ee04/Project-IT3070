package controller;

import item.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import manager.SwitchManager;
import scheduler.EarliestDeadlineFirstScheduler;

import java.io.IOException;
import java.util.ArrayList;

public class EDFController extends BaseController {
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
    @FXML
    Button addProcessButton;
    @FXML
    TextField executionTimeField;
    @FXML
    TextField arrivalTimeField;
    @FXML
    TextField deadlineField;
    @FXML
    Button runButton;
    @FXML
    TextField timeField;
    @FXML
    Button timeButton;

    ArrayList<Color> colorList;
    StringBuffer result;
    ArrayList<Task> tasks = new ArrayList<>();
    private static int id = 0;
    int timeOfScheduling = 0;

    public void initialize() {
        // Khởi tạo danh sách màu sắc
        colorList = new ArrayList<>();
        colorList.add(Color.web("#000000"));   // Đen
        colorList.add(Color.web("#FF0000"));   // Đỏ
        colorList.add(Color.web("#00FF00"));   // Xanh lá cây
        colorList.add(Color.web("#0000FF"));   // Xanh da trời
        colorList.add(Color.web("#FFFF00"));   // Vàng
        colorList.add(Color.web("#FF00FF"));   // Hồng
        colorList.add(Color.web("#00FFFF"));   // Cyan
        colorList.add(Color.web("#FFA500"));   // Cam
        colorList.add(Color.web("#7FFF00"));   // Xanh lá mạ
        colorList.add(Color.web("#40E0D0"));   // Xanh ngọc
        colorList.add(Color.web("#800080"));   // Tím

        // Thiết lập hành động cho các nút
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
        addProcessButton.setOnAction(event -> {
            try {
                int executionTime = Integer.parseInt(executionTimeField.getText());
                int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
                int deadline = Integer.parseInt(deadlineField.getText());
                id++;
                tasks.add(new Task(id, executionTime, deadline, arrivalTime, "P" + id));
                clearFields();  // Xóa các trường sau khi thêm tiến trình
            } catch (NumberFormatException e) {
                // Xử lý lỗi khi người dùng nhập sai định dạng số
                showError("Please enter valid numbers in all fields.");
            }
        });
        runButton.setOnAction(event -> {
            result = EarliestDeadlineFirstScheduler.earliestDeadlineFirst(tasks, timeOfScheduling);
            visualizeResult();
        });
        timeButton.setOnAction(event -> {
            try {
                timeOfScheduling = Integer.parseInt(timeField.getText());
            } catch (NumberFormatException e) {
                // Xử lý lỗi khi người dùng nhập sai định dạng số
                showError("Please enter a valid number for time.");
            }
        });
    }

    private void visualizeResult() {
        String[] resultArray = result.toString().split(" ");
        Timeline timeline = new Timeline();
        for (int i = 0; i < resultArray.length; i++) {
            int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(2 * index), event -> {
                int processId = Integer.parseInt(resultArray[index]);
                if (processId != 0) {
                    taskWork(processId, processQueue, nameProcessQueue);
                }
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    public void taskWork(int i, HBox processQueue, HBox nameProcessQueue) {
        Rectangle rectangle = new Rectangle(60, 20);
        rectangle.setFill(colorList.get(i % colorList.size()));
        Text name = new Text("P" + i);
        processQueue.getChildren().add(rectangle);
        nameProcessQueue.getChildren().add(name);
    }

    private void clearFields() {
        executionTimeField.clear();
        arrivalTimeField.clear();
        deadlineField.clear();
    }

    private void showError(String message) {
        // Hiển thị thông báo lỗi (có thể sử dụng Alert hoặc Text khác)
        System.out.println(message); // Thay thế bằng mã hiển thị thông báo thực tế
    }
}
