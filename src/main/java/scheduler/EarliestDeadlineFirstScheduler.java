package scheduler;
import java.util.Scanner;

public class EarliestDeadlineFirstScheduler {

    static final int MAX_PROCESS = 10;
    static int num_of_process = 3;
    static int[] execution_time = new int[MAX_PROCESS];
    static int[] period = new int[MAX_PROCESS];
    static int[] deadline = new int[MAX_PROCESS];
    static int[] remain_deadline = new int[MAX_PROCESS];
    static int[] remain_time = new int[MAX_PROCESS];
    static int[] completion_time = new int[MAX_PROCESS];
    static int[] arrival_time = new int[MAX_PROCESS];

    // collecting details of processes
    static void getProcessInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter total number of processes(maximum " + MAX_PROCESS + "): ");
        num_of_process = scanner.nextInt();
        if (num_of_process < 1) {
            System.out.println("Do you really want to schedule " + num_of_process + " processes? -_-");
            System.exit(0);
        }

        for (int i = 0; i < num_of_process; i++) {
            System.out.println("\nProcess " + (i + 1) + ":-");
            System.out.print("==> Execution time: ");
            execution_time[i] = scanner.nextInt();
            remain_time[i] = execution_time[i];
            System.out.print("==> Arrival time: ");
            arrival_time[i] = scanner.nextInt();
            System.out.print("==> Deadline: ");
            deadline[i] = scanner.nextInt();
            remain_deadline[i] = deadline[i]; // initialize remaining deadline
        }
    }

    // print scheduling sequence
    static void printSchedule(int[] process_list, int cycles) {
        System.out.println("\nScheduling:-\n");
        System.out.print("Time: ");
        for (int i = 0; i < cycles; i++) {
            if (i < 10)
                System.out.print("| 0" + i + " ");
            else
                System.out.print("| " + i + " ");
        }
        System.out.println("|");

        for (int i = 0; i < num_of_process; i++) {
            System.out.print("P[" + (i + 1) + "]: ");
            for (int j = 0; j < cycles; j++) {
                if (process_list[j] == i + 1)
                    System.out.print("|####");
                else
                    System.out.print("|    ");
            }
            System.out.println("|");
        }
    }

    static void earliestDeadlineFirst(int time) {
        int[] process_list = new int[time];

        for (int t = 0; t < time; t++) {
            int current_process = -1;
            int min_deadline = Integer.MAX_VALUE;

            // Check which processes are ready to execute
            for (int i = 0; i < num_of_process; i++) {
                if (arrival_time[i] <= t && remain_time[i] > 0 && remain_deadline[i] < min_deadline) {
                    min_deadline = remain_deadline[i];
                    current_process = i;
                }
            }

            if (current_process != -1) {
                remain_time[current_process]--;
                process_list[t] = current_process + 1;

                // Update deadlines
                for (int i = 0; i < num_of_process; i++) {
                    if (i != current_process && remain_time[i] > 0 && arrival_time[i] <= t) {
                        remain_deadline[i]--;
                    }
                }

                // If a process is completed, reset its deadline
                if (remain_time[current_process] == 0) {
                    remain_deadline[current_process] = deadline[current_process];
                }
            } else {
                process_list[t] = -1; // idle time
            }
        }

        printSchedule(process_list, time);
    }

    public static void main(String[] args) {
        getProcessInfo(); // collecting processes detail
        earliestDeadlineFirst(10);
    }
}
