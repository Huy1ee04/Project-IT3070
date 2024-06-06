package scheduler;

import item.Task;

import java.util.ArrayList;
import java.util.Set;

public class EarliestDeadlineFirstScheduler {

    // Print scheduling sequence
    public static StringBuffer earliestDeadlineFirst(ArrayList<Task> tasks, int time) {
        StringBuffer res = new StringBuffer("");

        for (int t = 0; t < time; t++) {
            Task currentTask = null;
            int min_deadline = Integer.MAX_VALUE;

            // Tìm quá trình sẵn sàng để thực thi
            for (Task task : tasks) {
                if (task.getArrivalTime() <= t && task.getRemainingTime() > 0 && task.getDeadline() < min_deadline) {
                    min_deadline = task.getDeadline();
                    currentTask = task;
                }
            }

            if (currentTask != null) {
                currentTask.setRemainingTime(currentTask.getRemainingTime() - 1);
                res.append(currentTask.getId()).append(" ");

                // Nếu quá trình hoàn thành, đặt lại thời hạn
                if (currentTask.getRemainingTime() == 0) {
                    currentTask.resetTask();
                }
            } else {
                res.append("0 "); // Thời gian rảnh
            }

            // Cập nhật thời hạn của tất cả các task đang chờ
            for (Task task : tasks) {
                if (task != currentTask && task.getRemainingTime() > 0 && task.getArrivalTime() <= t) {
                    task.setDeadline(task.getDeadline() - 1);
                }
            }
        }
        System.out.println(res);
        return res;
    }
}