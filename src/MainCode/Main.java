package MainCode;


public class Main {
    // Hàm tìm ước chung lớn nhất (GCD)
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
    // Hàm tìm bội chung nhỏ nhất (LCM)
    public static int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }
    // Hàm tìm LCM của 3 số nguyên
    public static int lcmOfThree(int a, int b, int c) {
        return lcm(a, lcm(b, c));
    }

    public static void main(String[] args) {
        DeadlineMonotonicScheduler scheduler = new DeadlineMonotonicScheduler();

        Task task1 = new Task(1, 3, 20, 7);
        Task task2 = new Task(2, 2, 5, 4);
        Task task3 = new Task(3, 2, 10, 9);

        scheduler.addTask(task1);
        scheduler.addTask(task2);
        scheduler.addTask(task3);

        int hyperPeriod = lcmOfThree(task1.period, task2.period, task3.period); // Bội chung nhỏ nhất các period của các task
        scheduler.schedule(hyperPeriod);

    }
}



