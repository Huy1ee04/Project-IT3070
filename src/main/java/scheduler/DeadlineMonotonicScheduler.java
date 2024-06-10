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

        // Create some tasks
        Task task1 = new Task(1, 2, 5, 5); // Task 1 with execution time 2, period 5, deadline 5
        Task task2 = new Task(2, 1, 3, 3); // Task 2 with execution time 1, period 3, deadline 3
        Task task3 = new Task(3, 1, 4, 4); // Task 3 with execution time 1, period 4, deadline 4

        // Add tasks to the scheduler
        scheduler.addTask(task1);
        scheduler.addTask(task2);
        scheduler.addTask(task3);

        // Determine the hyper period (LCM of the periods)
        int hyperPeriod = lcm(task1.getPeriod(), lcm(task2.getPeriod(), task3.getPeriod()));

        // Schedule the tasks
        scheduler.schedule(hyperPeriod);
    }

    // Helper method to compute the LCM of two numbers
    private static int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }

    // Helper method to compute the GCD of two numbers
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
