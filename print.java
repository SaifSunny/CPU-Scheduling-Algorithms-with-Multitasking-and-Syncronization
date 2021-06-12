public class print {

    public void printresult(String name, int[] processes, int[] burst_time, int[] arrival_time, int n,
            int[] wating_time, int[] turn_around_time) {

        System.out.println("\n_______________________________________________________________________\n");
        System.out.println("\n\nInitializing " + name + " . . .\n");
        // printing process,burst time,arrivaltime,watingtime,turnaround time
        System.out.println("Processes\tBurst time\tArrival Time\tWaiting time\tTurn around time\n");

        int total_wt = 0;
        int total_tat = 0;

        // calculatimg Total turn_around_time and wating_time
        for (int i = 0; i < n; i++) {

            total_wt = total_wt + wating_time[i];
            total_tat = total_tat + turn_around_time[i];

            System.out.println("    " + processes[i] + "\t\t\t" + burst_time[i] + "\t\t\t\t" + arrival_time[i]
                    + "\t\t\t " + wating_time[i] + "\t\t\t\t\t" + turn_around_time[i]);
        }

        // calculatimg avarage turn_around_time and wating_time
        System.out.println("\n" + name + " :\n");
        System.out.println("Average turn around time = " + (float) total_tat / (float) n);
        System.out.println("Average waiting time = " + (float) total_wt / (float) n);

        System.out.println("\n\nExiting " + name + " . . .\n");

    }
}