package MainCode;

import java.util.PriorityQueue;

class DeadlineMonotonicScheduler {
    PriorityQueue<Task> taskQueue = new PriorityQueue<>(); // Đã sắp xếp theo deadline ở class Task
    public void addTask(Task task) {
        taskQueue.add(task);
    }

    public void schedule(int hyperPeriod) {
        for (int currentTime = 0; currentTime < hyperPeriod; currentTime++) {
            PriorityQueue<Task> tempQueue = new PriorityQueue<>();

            // Tạo hàng đợi các nhiệm vụ sẵn sàng tại thời điểm hiện tại
            for (Task task : taskQueue) {
                if (currentTime > task.nextPeriodStart || currentTime == task.nextPeriodStart ) {
                    tempQueue.add(task);
                }
                // Kiểm tra và reset các nhiệm vụ tại thời điểm bắt đầu chu kỳ mới của chúng
                if (currentTime == task.nextPeriodStart && task.remainingTime == 0) {
                    task.remainingTime = task.executionTime;
                }
            }

            Task currentTask = tempQueue.poll();

            if(currentTask == null) {
                System.out.println("CPU trống tại thời điểm " + currentTime + " đến " + (currentTime + 1)); ;
            }

            if (currentTask != null && currentTask.remainingTime > 0) {
                currentTask.remainingTime--;
                System.out.println("Time " + currentTime + " to " + (currentTime + 1) + ": Task " + currentTask.id + " is executing.");

                if (currentTask.remainingTime == 0) {
                    System.out.println("Task " + currentTask.id + " completed at time " + (currentTime + 1) );
                    currentTask.nextPeriodStart += currentTask.period; // Cập nhật thời gian bắt đầu chu kỳ tiếp theo
                }

            }

        }
    }
}


