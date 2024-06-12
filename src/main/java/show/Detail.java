package show;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Detail {
    public void showDetailDeadline(VBox showDetail, int statusId) {
        String filePath = "D:\\2023.2\\it3070\\Project-IT3070\\src\\main\\resources\\data\\result.txt";
        String line = readSpecificLineFromFile(filePath, statusId);
        showDetail.getChildren().clear();
        if (line != null) {
            String[] parts = line.split(" ");
            int order = 1;
            for (String part : parts) {
                try {
                    int processId = Integer.parseInt(part);
                    Text text = new Text("P[" + order + "] :" + processId);
                    showDetail.getChildren().add(text);
                    order++;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid process ID found in the file: " + part);
                }
            }
        } else {
            System.err.println("No data found for status ID: " + statusId);

        }
    }

    private String readSpecificLineFromFile(String filePath, int lineIndex) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLine == lineIndex) {
                    return line;
                }
                currentLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
