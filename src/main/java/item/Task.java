package item;

public class Task implements Comparable<Task> {
    private int id;
    private int executionTime;
    private int period;
    private int deadline;
    private int remainingTime;
    private int nextPeriodStart;
    private int arrivalTime;
    private String name;

    public static int countTasks = 0;

    // Constructors
    public Task(int id, int executionTime, int period, int deadline) {
        this.id = id;
        this.executionTime = executionTime;
        this.period = period;
        this.deadline = deadline;
        this.remainingTime = executionTime;
        this.nextPeriodStart = 0;
        this.arrivalTime = 0;
        countTasks++;
    }

    public Task(int id, int executionTime, int period, int deadline, int arrivalTime) {
        this.id = id;
        this.executionTime = executionTime;
        this.period = period;
        this.deadline = deadline;
        this.remainingTime = executionTime;
        this.nextPeriodStart = arrivalTime + period;
        this.arrivalTime = arrivalTime;
        countTasks++;
    }

    public Task(int id, int executionTime, int deadline, int arrivalTime, String name) {
        this.id = id;
        this.executionTime = executionTime;
        this.deadline = deadline;
        this.remainingTime = executionTime;
        this.arrivalTime = arrivalTime;
        this.name = name;
        countTasks++;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getNextPeriodStart() {
        return nextPeriodStart;
    }

    public void setNextPeriodStart(int nextPeriodStart) {
        this.nextPeriodStart = nextPeriodStart;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getName() {return name;};


    // Reset task for the next period
    public void resetTask() {
        this.remainingTime = this.executionTime;
        this.arrivalTime += this.period;
        this.deadline += this.period;
    }

    // Compare tasks based on their deadlines
    @Override
    public int compareTo(Task other) {
        return this.deadline - other.deadline;
    }
}
