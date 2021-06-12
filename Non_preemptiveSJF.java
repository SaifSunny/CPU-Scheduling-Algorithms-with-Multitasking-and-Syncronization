class Non_preemptiveSJF extends Thread {

    public int processes[];
    public int burst_time[];
    public int arrival_time[];
    public int n;
    String name;
    print p;

    public Non_preemptiveSJF(String name, int[] processes, int[] burst_time, int[] arrival_time, int n, print p) {
        this.name = name;
        this.processes = processes;
        this.burst_time = burst_time;
        this.arrival_time = arrival_time;
        this.n = n;
        this.p = p;
    }

    // Method to calculate average time
    public void findavgTime(int processes[], int burst_time[], int arrival_time[], int n) {

        int sort_process[] = new int[n];
        int sort_burst_time[] = new int[n];
        int sort_arrival_time[] = new int[n];
        int temp, execution_time = 0; // exicution time = last process ending time
        int min;

        // copying data into local arrays
        for (int i = 0; i < n; i++) {
            sort_process[i] = processes[i];
            sort_burst_time[i] = burst_time[i];
            sort_arrival_time[i] = arrival_time[i];
        }

        // Sorting based on arrval time
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (sort_arrival_time[i] > sort_arrival_time[j]) {

                    temp = sort_burst_time[i];
                    sort_burst_time[i] = sort_burst_time[j];
                    sort_burst_time[j] = temp;

                    temp = sort_process[i];
                    sort_process[i] = sort_process[j];
                    sort_process[j] = temp;

                    temp = sort_arrival_time[i];
                    sort_arrival_time[i] = sort_arrival_time[j];
                    sort_arrival_time[j] = temp;

                }
            }
        }

        // sorting based on burst time
        int k = 1;
        for (int j = 0; j < n - 1; j++) {

            execution_time = execution_time + sort_burst_time[j];
            min = sort_burst_time[k];

            for (int i = k; i < n; i++) {
                // condition to comcare burst_time of the next arrived processes
                if (execution_time >= sort_arrival_time[i] && sort_burst_time[i] < min) {
                    temp = sort_process[k];
                    sort_process[k] = sort_process[i];
                    sort_process[i] = temp;

                    temp = sort_arrival_time[k];
                    sort_arrival_time[k] = sort_arrival_time[i];
                    sort_arrival_time[i] = temp;

                    temp = sort_burst_time[k];
                    sort_burst_time[k] = sort_burst_time[i];
                    sort_burst_time[i] = temp;
                }
            }
            k++;
        }

        int wating_time[] = new int[n];
        int turn_around_time[] = new int[n];

        // waiting time for first process is 0
        wating_time[0] = 0;

        int start_time = 0; // start time of processes

        for (int i = 1; i < n; i++) {
            // start time = end time of privious peocesses
            start_time = start_time + sort_burst_time[i - 1];
            // calculating waiting time = start time - arrival time
            wating_time[i] = start_time - sort_arrival_time[i];
        }

        // calculating Turn around time = current burst time + current wating time

        for (int i = 0; i < n; i++)
            turn_around_time[i] = sort_burst_time[i] + wating_time[i];

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