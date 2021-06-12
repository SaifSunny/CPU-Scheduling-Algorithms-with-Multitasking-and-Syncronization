class RoundRobin extends Thread {

    public int processes[];
    public int burst_time[];
    public int arrival_time[];
    public int quantum;
    public int n;
    String name;
    print p;

    public RoundRobin(String name, int[] processes, int[] burst_time, int[] arrival_time, int quantum, int n, print p) {
        this.name = name;
        this.processes = processes;
        this.burst_time = burst_time;
        this.arrival_time = arrival_time;
        this.quantum = quantum;
        this.n = n;
        this.p = p;
    }

    static void findWaitingTime(int processes[], int n, int burst_time[], int wating_time[], int quantum) {

        int remaning_time[] = new int[n];

        // Copy the burst time into remaning_time[]
        for (int i = 0; i < n; i++)
            remaning_time[i] = burst_time[i];

        int exicution_time = 0; // Current time

        // Traversing processes until all of them are not done.
        while (true) {
            boolean done = true;

            for (int i = 0; i < n; i++) {

                // If burst time of a process is greater than 0 then the process is pending
                if (remaning_time[i] > 0) {
                    done = false;

                    if (remaning_time[i] > quantum) {
                        // Increase the exicution_time of the process
                        exicution_time += quantum;

                        // Decrease the burst_time of current process by quantum
                        remaning_time[i] = remaning_time[i] - quantum;
                    }

                    // If burst time is smaller than or equal to quantum. Last cycle of process
                    else {
                        // Increase the exicution_time
                        exicution_time = exicution_time + remaning_time[i];

                        // Waiting time = exicution time - burst time
                        wating_time[i] = exicution_time - burst_time[i];

                        // As the process gets fully executed, remaining burst time = 0
                        remaning_time[i] = 0;
                    }
                }
            }

            // If all processes are done
            if (done == true)
                break;
        }
    }

    // Method to calculate average time
    public void findavgTime(int processes[], int burst_time[], int quantum, int n) {

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
        findWaitingTime(sort_process, n, sort_burst_time, wating_time, quantum);

        // calculating turnaround time
        for (int i = 0; i < n; i++)
            turn_around_time[i] = burst_time[i] + wating_time[i];

        // scronizing thereds with the object of print Class
        synchronized (p) {
            p.printresult(name, sort_process, sort_burst_time, sort_arrival_time, n, wating_time, turn_around_time);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            findavgTime(processes, burst_time, quantum, n);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}