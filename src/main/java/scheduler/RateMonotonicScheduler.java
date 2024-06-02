package Scheduler;

import java.util.Scanner;

public class RateMonotonicScheduler {
    private static final int MAX_PROCESS = 10;
    private static int num_of_process = 3;
    private static int[] execution_time = new int[MAX_PROCESS];
    private static int[] period = new int[MAX_PROCESS];
    private static int[] remain_time = new int[MAX_PROCESS];
    private static int[] completion_time = new int[MAX_PROCESS];
    private static int[] arrival_time = new int[MAX_PROCESS];

    // Collecting details of processes
    private static void getProcessInfo() {
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
            System.out.print("==> Period: ");
            period[i] = scanner.nextInt();
        }

        scanner.close();
    }

    // Print scheduling sequence
    private static void printSchedule(int[] process_list, int cycles) {
        System.out.println("\nScheduling:-\n");

        System.out.print("Time: ");
        for (int i = 0; i < cycles; i++) {
            if (i < 10)
                System.out.print("| 0" + i + " ");
            else
                System.out.print("| " + i + " ");
        }
        System.out.println("|");

        for (int i = num_of_process - 1; i >= 0; i--) {
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

    private static void rateMonotonic(int time) {
        float utilization = 0;
        for (int i = 0; i < num_of_process; i++) {
            utilization += (float) ((1.0 * execution_time[i]) / period[i]);
        }

        int n = num_of_process;
        if (utilization > n * (Math.pow(2, 1.0 / n) - 1)) {
            System.out.println("\nGiven problem is not schedulable under said scheduling algorithm.");
            System.exit(0);
        }
        System.out.println(utilization);
        System.out.println(n * (Math.pow(2, 1.0 / n) - 1));

        int[] process_list = new int[time];
        int min = 999, next_process = 0;
        for (int i = 0; i < time; i++) {
            process_list[i] = -1;
            min = 1000;
            for (int j = 0; j < num_of_process; j++) {
                if (remain_time[j] > 0) {
                    if (min > period[j]) {
                        min = period[j];
                        next_process = j;
                    }
                }
            }
            if (remain_time[next_process] > 0) {
                process_list[i] = next_process + 1; // +1 for catering 0 array index.
                remain_time[next_process] -= 1;
            }
            for (int k = 0; k < num_of_process; k++) {
                if ((i + 1) % period[k] == 0) {
                    remain_time[k] = execution_time[k];
                    next_process = k;
                }
            }
        }
        printSchedule(process_list, time);
    }

    public static void main(String[] args) {
        getProcessInfo(); // Collecting processes detail
        rateMonotonic(20);
    }
}