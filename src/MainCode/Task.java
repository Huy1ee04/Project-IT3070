package MainCode;

class Task implements Comparable<Task> {
    int id;
    int executionTime;
    int period;
    int deadline;
    int remainingTime;
    int nextPeriodStart;

    public Task(int id, int executionTime, int period, int deadline) {
        this.id = id;
        this.executionTime = executionTime;
        this.period = period;
        this.deadline = deadline;
        this.remainingTime = executionTime;
        this.nextPeriodStart = 0;
    }

    @Override
    public int compareTo(Task other) {
        return this.deadline - other.deadline;
    }
}
