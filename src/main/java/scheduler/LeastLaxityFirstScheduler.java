package scheduler;

import item.Task;

import java.util.*;


public class LeastLaxityFirstScheduler {
    private int currentTime = 0;

    private int getLaxity(Task task) {
        return (task.getDeadline() - currentTime) - task.getRemainingTime();
    }

    private Set<Task> tasks;

    private List<Integer> results = new ArrayList<>();

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

        if (endTime > results.size()) {
            // Thêm các phần tử mới vào danh sách
            for (int i = results.size(); i < endTime; i++) {
                results.add(0); // Giá trị mặc định
            }
        } else if (endTime < results.size()) {
            // Cắt bớt các phần tử cuối cùng của danh sách
            results.subList(endTime, results.size()).clear();
        }

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
                results.set(currentTime, -1);
                currentTime++;
                continue;
            }

//          Lấy task có Laxity ít nhất
            Task nextTask = availableTasks.poll();
//          Set remainingTime task đó --
            nextTask.setRemainingTime(nextTask.getRemainingTime() - 1);

            results.set(currentTime, nextTask.getId());
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
    public List<Integer> getResults() {return results;}
    public void setResults(List<Integer> results) {this.results = results;}

}
