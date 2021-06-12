class Priority extends Thread {
    public int processes[];
    public int burst_time[];
    public int priority[];
    public int n;
    String name;
    print p;

    public Priority(String name, int[] processes, int[] burst_time, int[] priority, int n, print p) {
        this.name = name;
        this.processes = processes;
        this.burst_time = burst_time;
        this.priority = priority;
        this.n = n;
        this.p = p;
    }

    // Method to calculate average time
    public void findavgTime(int processes[], int burst_time[], int priority[], int n) {

        int sort_process[] = new int[n];
        int sort_burst_time[] = new int[n];
        int sort_priority[] = new int[n];
        int temp;

        // copying data into local arrays
        for (int i = 0; i < n; i++) {
            sort_process[i] = processes[i];
            sort_burst_time[i] = burst_time[i];
            sort_priority[i] = priority[i];
        }

        // Sorting process,burst_time based on priority
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (sort_priority[i] > sort_priority[j]) {

                    temp = sort_priority[i];
                    sort_priority[i] = sort_priority[j];
                    sort_priority[j] = temp;

                    temp = sort_burst_time[i];
                    sort_burst_time[i] = sort_burst_time[j];
                    sort_burst_time[j] = temp;

                    temp = sort_process[i];
                    sort_process[i] = sort_process[j];
                    sort_process[j] = temp;
                }
            }
        }

        int wating_time[] = new int[n];
        int turn_around_time[] = new int[n];
        int arrival_time[] = new int[n];

        // all the process arived at same time thus arrival time 1
        for (int i = 0; i < n; i++) {
            arrival_time[i] = 1;
        }

        // waiting time for first process is 0
        wating_time[0] = 0;

        // calculating waiting time = privious burst_time + privious wating time
        for (int i = 1; i < n; i++) {
            wating_time[i] = sort_burst_time[i - 1] + wating_time[i - 1];
        }

        // calculating turnaround time = burst_time + wating_time
        for (int i = 0; i < n; i++)
            turn_around_time[i] = sort_burst_time[i] + wating_time[i];

        // scronizing thereds with the object of print Class
        synchronized (p) {
            p.printresult(name, sort_process, sort_burst_time, arrival_time, n, wating_time, turn_around_time);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            findavgTime(processes, burst_time, priority, n);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}