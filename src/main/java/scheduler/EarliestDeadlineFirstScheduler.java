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
            }
        }

        // get maximum of three numbers
        static int max(int a, int b, int c) {
            if (a >= b && a >= c)
                return a;
            else if (b >= a && b >= c)
                return b;
            else
                return c;
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
            float utilization = 0;
            for (int i = 0; i < num_of_process; i++) {
                utilization += (1.0 * execution_time[i]) / deadline[i];
            }

            int[] process = new int[num_of_process];
            int max_deadline, current_process = 0, min_deadline, process_list[] = new int[time];
            boolean[] is_ready = new boolean[num_of_process];

            for (int i = 0; i < num_of_process; i++) {
                is_ready[i] = false;
                process[i] = i + 1;
            }

            max_deadline = deadline[0];
            for (int i = 1; i < num_of_process; i++) {
                if (deadline[i] > max_deadline)
                    max_deadline = deadline[i];
            }
            for (int i = 0; i < num_of_process; i++) {
                for (int j = i + 1; j < num_of_process; j++) {
                    if (deadline[j] < deadline[i]) {
                        int temp = execution_time[j];
                        execution_time[j] = execution_time[i];
                        execution_time[i] = temp;
                        temp = deadline[j];
                        deadline[j] = deadline[i];
                        deadline[i] = temp;
                        temp = process[j];
                        process[j] = process[i];
                        process[i] = temp;
                        temp = arrival_time[j];
                        arrival_time[j] = arrival_time[i];
                        arrival_time[i] = temp;
                    }
                }
            }

            for (int i = 0; i < num_of_process; i++) {
                remain_time[i] = execution_time[i];
                remain_deadline[i] = deadline[i];
            }

            for (int t = 0; t < time; t++) {
                for (int i = 0; i < num_of_process; i++) {
                    if (arrival_time[i] == t) {
                        is_ready[i] = true;
                    }
                }
                if (execution_time[current_process] > 0 && is_ready[current_process]) {
                    --execution_time[current_process];
                    process_list[t] = process[current_process];
                } else {
                    process_list[t] = -1;
                }
                min_deadline = max_deadline;
                for (int i = 0; i < num_of_process; i++) {
                    if ((deadline[i] <= min_deadline) && (execution_time[i] > 0) && arrival_time[i] <= t + 1) {
                        current_process = i;
                        min_deadline = deadline[i];
                    }
                }
            }
            printSchedule(process_list, time);
        }

        public static void main(String[] args) {
            getProcessInfo(); // collecting processes detail
            earliestDeadlineFirst(10);
        }
    }
