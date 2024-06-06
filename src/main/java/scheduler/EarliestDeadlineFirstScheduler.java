package scheduler;

import item.Task;

import java.util.ArrayList;

public class EarliestDeadlineFirstScheduler {

    // Print scheduling sequence
    public static StringBuffer earliestDeadlineFirst(ArrayList<Task> tasks, int time) {
        StringBuffer res = new StringBuffer("");

        // In ra trạng thái ban đầu của các nhiệm vụ
        System.out.println("Initial task states:");
        for (Task task : tasks) {
            System.out.println(task);
        }

        for (int t = 0; t < time; t++) {
            Task currentTask = null;
            int minDeadline = Integer.MAX_VALUE;

            // Tìm quá trình sẵn sàng để thực thi
            for (Task task : tasks) {
                if (task.getArrivalTime() <= t && task.getRemainingTime() > 0 && task.getDeadline() < minDeadline) {
                    minDeadline = task.getDeadline();
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

            // In ra trạng thái của các nhiệm vụ tại mỗi thời điểm
            System.out.println("Time " + t + ":");
            for (Task task : tasks) {
                System.out.println(task);
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
