package scheduler;

import Item.Task;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;


public class LeastLaxityFirst {
    private int currentTime = 0;
    private int getLaxity(Task task) {
        return (task.getDeadline() - currentTime) - task.getRemainingTime();
    }
    private Set<Task> tasks;
    public LeastLaxityFirst() {
        Comparator<Task> byArrivalTime = Comparator.comparingInt(Task::getArrivalTime);
        this.tasks = new TreeSet<>(byArrivalTime);
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    public void scheduleTasks(int endTime) {
        PriorityQueue<Task> availableTasks = new PriorityQueue<>((task1, task2) -> {
            int laxity1 = getLaxity(task1);
            int laxity2 = getLaxity(task2);

            if (laxity1 != laxity2) {
                return Integer.compare(laxity1, laxity2);
            } else {
                return Integer.compare(task1.getDeadline(), task2.getDeadline());
            }
        });

        while (currentTime < endTime) {
            for (Task task : tasks) {
                if (currentTime == task.getArrivalTime()){
                    availableTasks.offer(task);
                    tasks.remove(task);
                }
                else break;
            }

            if (availableTasks.isEmpty()) {
                System.out.println("Current Time: " + currentTime + "No tasks available");
                currentTime++;
                continue;
            }

//          Lấy task có Laxity ít nhất
            Task nextTask = availableTasks.poll();
//          Set remainingTime task đó --
            nextTask.setRemainingTime(nextTask.getRemainingTime() - 1);

            System.out.println("Current Time: " + currentTime + ", Executing Task ID: " + nextTask.getId());
            currentTime++;

            if (nextTask.getRemainingTime() == 0) {
                nextTask.resetTask();
                tasks.add(nextTask);
            }

            else availableTasks.offer(nextTask);
        }
    }
    public Set<Task> getTasks() {return tasks;}
    public void setTasks(Set<Task> tasks){this.tasks = tasks;}

    public static void main(String[] args) {
        LeastLaxityFirst scheduler = new LeastLaxityFirst();
        Task task_1 = new Task(1, 2, 6, 6, 0);
        Task task_2 = new Task(2, 2, 8, 8, 0);
        Task task_3 = new Task(3, 3, 10, 10,  0);


      //  scheduler.addTask(task1);
        //scheduler.addTask(task2);
        //scheduler.addTask(task3);

        scheduler.scheduleTasks(20);
    }
}
