public class Preemptive_SJF extends Thread {

    public int processes[];
    public int burst_time[];
    public int arrival_time[];
    public int n;
    String name;
    print p;

    public Preemptive_SJF(String name, int[] processes, int[] burst_time, int[] arrival_time, int n, print p) {
        this.name = name;
        this.processes = processes;
        this.burst_time = burst_time;
        this.arrival_time = arrival_time;
        this.n = n;
        this.p = p;
    }

    public void findWaitingTime(int processes[], int arrival_time[], int burst_time[], int n, int wating_time[]) {

        int remaning_time[] = new int[n];
        int complete = 0, min = Integer.MAX_VALUE;
        int shortest = 0, finish_time;
        int exicution_time = 0;
        boolean check = false;

        // Copy the burst time into remaning_time[]
        for (int i = 0; i < n; i++)
            remaning_time[i] = burst_time[i];

        // Process until all processes are completed
        while (complete != n) {

            // Finding the process with minimum remaining time
            for (int i = 0; i < n; i++) {
                if ((arrival_time[i] <= exicution_time) && (remaning_time[i] < min) && remaning_time[i] > 0) {
                    min = remaning_time[i];
                    shortest = i;
                    check = true;
                }
            }

            if (check == false) {
                exicution_time++;
                continue;
            }

            // Reduce remaining time by one
            remaning_time[shortest]--;

            // Update minimum
            min = remaning_time[shortest];
            if (min == 0)
                min = Integer.MAX_VALUE;

            // If a process gets completely executed
            if (remaning_time[shortest] == 0) {

                // Increment complete
                complete++;
                check = false;

                // Find finish time of current process
                finish_time = exicution_time + 1;

                // Calculate waiting time
                wating_time[shortest] = finish_time - burst_time[shortest] - arrival_time[shortest];

                if (wating_time[shortest] < 0) {
                    wating_time[shortest] = 0;
                }
            }
            // Increment time
            exicution_time++;

        }
    }

    // Method to calculate average time
    public void findavgTime(int processes[], int burst_time[], int arrival_time[], int n) {

        int sort_process[] = new int[n];
        int sort_burst_time[] = new int[n];
        int sort_arrival_time[] = new int[n];
        int temp;

        // copying data into local arrays
        for (int i = 0; i < n; i++) {
            sort_process[i] = processes[i];
            sort_burst_time[i] = burst_time[i];
            sort_arrival_time[i] = arrival_time[i];
        }

        // Sorting process,burst_time based on arrival_time
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (sort_arrival_time[i] > sort_arrival_time[j]) {

                    temp = sort_process[i];
                    sort_process[i] = sort_process[j];
                    sort_process[j] = temp;

                    temp = sort_burst_time[i];
                    sort_burst_time[i] = sort_burst_time[j];
                    sort_burst_time[j] = temp;

                    temp = sort_arrival_time[i];
                    sort_arrival_time[i] = sort_arrival_time[j];
                    sort_arrival_time[j] = temp;
                }
            }
        }

        int wating_time[] = new int[n];
        int turn_around_time[] = new int[n];

        // Function to find waiting time of all processes
        findWaitingTime(sort_process, sort_arrival_time, sort_burst_time, n, wating_time);

        // calculating turn around time for all processes
        for (int i = 0; i < n; i++) {
            turn_around_time[i] = sort_burst_time[i] + wating_time[i];
        }
        // scronizing thereds with the object of print Class
        synchronized (p) {
            p.printresult(name, sort_process, sort_burst_time, sort_arrival_time, n, wating_time, turn_around_time);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            findavgTime(processes, burst_time, arrival_time, n);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}