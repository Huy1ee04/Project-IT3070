package Item;

public class Task implements Comparable<Task> {
    private int id;
    private int executionTime;
    private int period;
    private int deadline;
    private int remainingTime;
    private int nextPeriodStart;

    public Task(int id, int executionTime, int period, int deadline) {
        this.id = id;
        this.executionTime = executionTime;
        this.period = period;
        this.deadline = deadline;
        this.remainingTime = executionTime;
        this.nextPeriodStart = 0;
    }

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

    @Override
    public int compareTo(Task other) {
        return this.deadline - other.deadline;
    }
}
