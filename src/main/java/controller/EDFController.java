package controller;

import item.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import manager.SwitchManager;
import scheduler.EarliestDeadlineFirstScheduler;
import show.Detail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class EDFController extends BaseController {
    @FXML
    private Rectangle CPU;
    @FXML
    private HBox processQueue;
    @FXML
    private Button returnButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button addProcessButton;
    @FXML
    private Button loadFromFileButton;
    @FXML
    private TextField filePathField;
    @FXML
    private TextField executionTimeField;
    @FXML
    private TextField arrivalTimeField;
    @FXML
    private TextField deadlineField;
    @FXML
    private Button runButton;
    @FXML
    private TextField periodTextField;
    @FXML
    private TextField timeField;
    @FXML
    private Button timeButton;
    @FXML
    private Button loadFilePathButton;
    @FXML
    private VBox showDetail;
    int currentTime = 0;
    private ArrayList<Color> colorList;
    private StringBuffer result;
    private ArrayList<Task> tasks = new ArrayList<>();
    private static int id = 0;
    private int timeOfScheduling = 0;
    private double weight = 750;

    public void initialize() {
        // Khởi tạo danh sách màu sắc
        colorList = new ArrayList<>();
        colorList.add(Color.web("#FFFFFF"));   // Trắng
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
                id = 0;
                SwitchManager.goEDFPage(this, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        addProcessButton.setOnAction(event -> {
            try {
                int executionTime = Integer.parseInt(executionTimeField.getText());
                int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
                int deadline = Integer.parseInt(deadlineField.getText());
                int period = Integer.parseInt(periodTextField.getText());
                id++;
                tasks.add(new Task(id, executionTime, deadline, arrivalTime, period, "P" + id));
                ensureColorCapacity(id);  // Ensure there is a color for each task
                clearFields();  // Xóa các trường sau khi thêm tiến trình
            } catch (NumberFormatException e) {
                // Xử lý lỗi khi người dùng nhập sai định dạng số
                showError("Please enter valid numbers in all fields.");
            }
        });
        runButton.setOnAction(event -> {
            if (!EarliestDeadlineFirstScheduler.isSchedulable(tasks)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Scheduling Warning");
                alert.setHeaderText(null);
                alert.setContentText("The task set is not schedulable. Please check the task parameters.");
                alert.showAndWait();
                return;
            }

            saveTasksToFile();  // Save tasks to file
            result = EarliestDeadlineFirstScheduler.earliestDeadlineFirst(tasks, timeOfScheduling);
            visualizeResult();
            for (Task task : tasks) {
                System.out.println(task.getId());
            }
        });
        loadFilePathButton.setOnAction(event -> {
            File file = new File(filePathField.getText());
            loadTasksFromFile(file);
        });
        timeButton.setOnAction(event -> {
            try {
                timeOfScheduling = Integer.parseInt(timeField.getText());
            } catch (NumberFormatException e) {
                // Xử lý lỗi khi người dùng nhập sai định dạng số
                showError("Please enter a valid number for time.");
            }
        });

        loadFromFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                filePathField.setText(file.getAbsolutePath());
                loadTasksFromFile(file);
            }
        });
    }

    public void loadTasksFromFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 4) {
                    int arrivalTime = Integer.parseInt(parts[0]);
                    int executionTime = Integer.parseInt(parts[1]);
                    int deadline = Integer.parseInt(parts[2]);
                    int period = Integer.parseInt(parts[3]);
                    id++;
                    tasks.add(new Task(id, executionTime, deadline, arrivalTime, period, "P" + id));
                    ensureColorCapacity(id);  // Ensure there is a color for each task
                }
            }
        } catch (IOException | NumberFormatException e) {
            showError("Error reading file or invalid file format.");
        }
    }

    public void visualizeResult() {
        String[] resultArray = result.toString().split(" ");
        Timeline timeline = new Timeline();
        for (int i = 0; i < resultArray.length; i++) {
            int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(index), event -> {
                int processId = Integer.parseInt(resultArray[index]);
                taskWork(processId, processQueue);
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    public void taskWork(int i, HBox processQueue) {
        Detail detail = new Detail();
        detail.showDetailDeadline(showDetail, currentTime);
        processQueue.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(); // Create a new VBox
        vbox.setAlignment(Pos.CENTER);
        Text name;
        Rectangle rectangle = new Rectangle(weight / timeOfScheduling, 60);
        rectangle.setFill(colorList.get(i % colorList.size()));
        CPU.setFill(colorList.get(i % colorList.size()));
        if (i != 0) {
            name = new Text("P" + i);
        } else {
            name = new Text("");
        }
        Text current;
        current= new Text(Integer.toString(currentTime));
        currentTime = currentTime + 1;
        // Kiểm tra nếu thời gian lớn hơn hoặc bằng 50
        if (timeOfScheduling >= 50) {
            name.setWrappingWidth(rectangle.getWidth());
            String newSize = "-fx-font-size: " + (8 - timeOfScheduling / 50) + ";";
            name.setStyle(newSize); // Điều chỉnh kích thước font cỡ
            name.setTextAlignment(TextAlignment.CENTER); // Căn giữa text
            current.setStyle("-fx-font-size: 8");
        }

        vbox.getChildren().addAll(current, rectangle, name); // Thêm Rectangle và Text vào VBox
        processQueue.getChildren().add(vbox); // Thêm VBox vào processQueue
    }

    public void saveTasksToFile() {
        File file = new File("D:\\2023.2\\it3070\\Project-IT3070\\src\\main\\resources\\data.txt"); // Use forward slash for better compatibility
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(task.getArrivalTime() + " " + task.getExecutionTime() + " " + task.getDeadline() + " " + task.getPeriod());
                writer.newLine();
            }
        } catch (IOException e) {
            showError("Error saving tasks to file: " + e.getMessage()); // Log the exception message
        }
    }


    public void clearFields() {
        executionTimeField.clear();
        arrivalTimeField.clear();
        deadlineField.clear();
        periodTextField.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
        System.out.println(message); // Thay thế bằng mã hiển thị thông báo thực tế
    }

    private void ensureColorCapacity(int id) {
        // Generate random colors and add to the list if needed
        while (colorList.size() < id) {
            colorList.add(generateRandomColor());
        }
    }

    private Color generateRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256) % 255, random.nextInt(256), random.nextInt(256));
    }
}
