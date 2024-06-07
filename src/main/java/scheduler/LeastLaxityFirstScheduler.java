package scheduler;

import item.Task;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Iterator;


public class LeastLaxityFirstScheduler {
    private int currentTime = 0;
    private int getLaxity(Task task) {
        return (task.getDeadline() - currentTime) - task.getRemainingTime();
    }
    private Set<Task> tasks;
    public LeastLaxityFirstScheduler() {
        Comparator<Task> byArrivalTimeThenId = (Task t1, Task t2) -> {
            if (t1.getArrivalTime() != t2.getArrivalTime()) {
                return Integer.compare(t1.getArrivalTime(), t2.getArrivalTime());
            } else {
                return Integer.compare(t1.getId(), t2.getId());
            }
        };
        this.tasks = new TreeSet<>(byArrivalTimeThenId);
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
            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (currentTime == task.getArrivalTime()){
                    availableTasks.offer(task);
                    iterator.remove();
                }
                else break;
            }


            if (availableTasks.isEmpty()) {
                System.out.println("Current Time: " + currentTime + " No tasks available");
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
        LeastLaxityFirstScheduler scheduler = new LeastLaxityFirstScheduler();
        Task task_1 = new Task(1, 3, 7, 12, 0);
        Task task_2 = new Task(2, 4, 10, 13, 0);
        Task task_3 = new Task(3, 5, 12, 14,  0);

        scheduler.addTask(task_1);
        scheduler.addTask(task_2);
        scheduler.addTask(task_3);


        scheduler.scheduleTasks(25);
    }
}
