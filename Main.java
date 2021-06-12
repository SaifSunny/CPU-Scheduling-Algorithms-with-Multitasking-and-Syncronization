class Main {

    public static void main(String[] args) throws InterruptedException {

        // Process_id of all processes
        int processes[] = { 1, 2, 3, 4, 5 };
        int n = processes.length;

        // Burst time of all processes
        int burst_time[] = { 6, 3, 8, 3, 4 };
        // Arrival time of all processes
        int arrival_time[] = { 2, 5, 1, 0, 4 };
        // priority of all process for priority algorithm
        int priority[] = { 2, 1, 5, 4, 3 };
        // Quantum slice for round robin algorithm
        int quantum = 3;

        // printing process, burst time, arrival time, priority
        System.out.println("\n\nProcesses\tBurst Time\tArrival Time\tPriority\n");

        for (int i = 0; i < n; i++) {
            System.out.println("    " + processes[i] + "\t\t\t" + burst_time[i] + "\t\t\t " + arrival_time[i]
                    + "\t\t\t\t" + priority[i]);
        }
        System.out.println("\nTime quantum: " + quantum);

        // object of the anonymous class
        print p = new print();

        // thread objects
        FCFS a1 = new FCFS("First Come First Serve Sceduling Algorithm", processes, burst_time, arrival_time, n, p);

        Preemptive_SJF a2 = new Preemptive_SJF("Preemptive Shortest Job First Sceduling Algorithm", processes,
                burst_time, arrival_time, n, p);

        Non_preemptiveSJF a3 = new Non_preemptiveSJF("Non-Preemptive Shortest Job First Sceduling Algorithm", processes,
                burst_time, arrival_time, n, p);

        RoundRobin a4 = new RoundRobin("Round Robin Sceduling Algorithm", processes, burst_time, arrival_time, quantum,
                n, p);

        Priority a5 = new Priority("Priority Sceduling Algorithm", processes, burst_time, priority, n, p);

        // start threads
        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a5.start();

        // waits for the privious thread to end
        a1.join();
        a2.join();
        a3.join();
        a4.join();
        a5.join();
    }
}
