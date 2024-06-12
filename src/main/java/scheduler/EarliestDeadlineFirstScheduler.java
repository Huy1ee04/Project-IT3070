package scheduler;

import item.Task;
import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EarliestDeadlineFirstScheduler {
    public static boolean isSchedulable(ArrayList<Task> tasks) {
        double utilization = 0;

        for (Task task : tasks) {
            if (task.getPeriod() > 0) {
                utilization += (double) task.getExecutionTime() / task.getPeriod();
            }
        }

        return utilization <= 1;
    }

    // Hàm lập lịch theo EDF và in ra chuỗi kết quả
    public static StringBuffer earliestDeadlineFirst(ArrayList<Task> tasks, int time) {
        StringBuffer res = new StringBuffer("");
        // Mảng lưu lịch trình của các task
        int[] schedule = new int[time];
        for (int i = 0; i < time; i++) {
            schedule[i] = -1; // -1 biểu thị thời gian rảnh
        }
        clearFileContent("D:\\2023.2\\it3070\\Project-IT3070\\src\\main\\resources\\data\\result.txt");
        // Lập lịch các task
        for (int t = 0; t < time; t++) {
            Task currentTask = null;
            saveStatus(tasks);
            // Tìm task có thời hạn gần nhất và đã sẵn sàng để thực thi
            for (Task task : tasks) {

                if (task.getArrivalTime()>= 0 && task.getArrivalTime() <= t && task.getRemainingTime() > 0) {
                    if (currentTask == null || task.getDeadline() < currentTask.getDeadline()) {
                        currentTask = task;
                    }
                }
            }

            if (currentTask != null) {
                schedule[t] = currentTask.getId();
                currentTask.setRemainingTime(currentTask.getRemainingTime() - 1);

                // Kiểm tra xem tiến trình đã hoàn thành chưa và có chu kỳ không
                if (currentTask.getRemainingTime() == 0 && currentTask.getPeriod() > 0) {
                    // Cập nhật lại thời hạn cho tiến trình sau khi hoàn thành
                    currentTask.resetTask();
                }
            }
        }

        // Xây dựng chuỗi kết quả
        for (int t = 0; t < time; t++) {
            if (schedule[t] == -1) {
                res.append("0 ");
            } else {
                res.append(schedule[t]).append(" ");
            }
        }

        System.out.println(res);
        return res;
    }
    private static void saveStatus(ArrayList<Task> tasks) {
        File file = new File("D:\\2023.2\\it3070\\Project-IT3070\\src\\main\\resources\\data\\result.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (Task task : tasks) {
                writer.write(task.getDeadline() + " ");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void clearFileContent(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
