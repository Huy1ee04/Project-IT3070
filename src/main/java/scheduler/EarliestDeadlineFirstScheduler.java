package scheduler;

import item.Task;
import java.util.ArrayList;
import java.util.Comparator;

public class EarliestDeadlineFirstScheduler {

    // Hàm lập lịch theo EDF và in ra chuỗi kết quả
    public static StringBuffer earliestDeadlineFirst(ArrayList<Task> tasks, int time) {
        StringBuffer res = new StringBuffer("");

        // Sắp xếp các nhiệm vụ theo deadline
        tasks.sort(Comparator.comparingInt(Task::getDeadline));

        // Mảng lưu lịch trình của các task
        int[] schedule = new int[time];
        for (int i = 0; i < time; i++) {
            schedule[i] = -1; // -1 biểu thị thời gian rảnh
        }

        // Lập lịch các task
        for (int t = 0; t < time; t++) {
            Task currentTask = null;

            // Tìm task có thời hạn gần nhất
            for (Task task : tasks) {
                if (task.getArrivalTime() <= t && task.getExecutionTime() > 0) {
                    if (currentTask == null || task.getDeadline() < currentTask.getDeadline()) {
                        currentTask = task;
                    }
                }
            }

            if (currentTask != null) {
                schedule[t] = currentTask.getId();
                currentTask.setExecutionTime(currentTask.getExecutionTime() - 1);
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
}
