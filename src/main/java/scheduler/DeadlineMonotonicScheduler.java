package scheduler;

import item.Task;
import java.util.PriorityQueue;

public class DeadlineMonotonicScheduler {
    private PriorityQueue<Task> taskQueue = new PriorityQueue<>(); // Already sorted by deadline in the Task class

    public void addTask(Task task) {
        taskQueue.add(task);
    }

    public void schedule(int hyperPeriod) {
        for (int currentTime = 0; currentTime < hyperPeriod; currentTime++) {
            PriorityQueue<Task> tempQueue = new PriorityQueue<>();

            // Create a queue of tasks ready at the current time
            for (Task task : taskQueue) {
                if (currentTime >= task.getNextPeriodStart()) {
                    tempQueue.add(task);
                }
                // Check and reset tasks at the start of their new period
                if (currentTime == task.getNextPeriodStart() && task.getRemainingTime() == 0) {
                    task.setRemainingTime(task.getExecutionTime());
                }
            }

            Task currentTask = tempQueue.poll();

            if (currentTask == null) {
                System.out.println("CPU idle from time " + currentTime + " to " + (currentTime + 1));
            }

            if (currentTask != null && currentTask.getRemainingTime() > 0) {
                currentTask.setRemainingTime(currentTask.getRemainingTime() - 1);
                System.out.println("Time " + currentTime + " to " + (currentTime + 1) + ": Task " + currentTask.getId() + " is executing.");

                if (currentTask.getRemainingTime() == 0) {
                    System.out.println("Task " + currentTask.getId() + " completed at time " + (currentTime + 1));
                    currentTask.setNextPeriodStart(currentTask.getNextPeriodStart() + currentTask.getPeriod()); // Update the start time of the next period
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadlineMonotonicScheduler scheduler = new DeadlineMonotonicScheduler();

        // Add some example tasks
        scheduler.addTask(new Task(1, 2, 5, 5));
        scheduler.addTask(new Task(2, 1, 3, 3));
        scheduler.addTask(new Task(3, 1, 4, 4));

        // Start scheduling for a hypothetical hyper-period
        int hyperPeriod = 15; // Example hyper-period is 15
        scheduler.schedule(hyperPeriod);
    }
}
