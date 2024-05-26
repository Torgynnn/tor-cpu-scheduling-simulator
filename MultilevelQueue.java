import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class MultilevelQueue {
    private List<Process> highPriorityProcesses;
    private List<Process> lowPriorityProcesses;
    private int quantum; // Quantum time for Round Robin in high priority queue

    public MultilevelQueue(List<Process> processes, int quantum) {
        this.quantum = quantum;
        this.highPriorityProcesses = new ArrayList<>();
        this.lowPriorityProcesses = new ArrayList<>();
        for (Process p : processes) {
            if (p.priority <= 5) { // Assume priorities are from 1 to 10; 1-5 are high priority
                highPriorityProcesses.add(p);
            } else {
                lowPriorityProcesses.add(p);
            }
        }
    }

    public void schedule() {
        // Schedule high priority queue with Round Robin
        RoundRobin rr = new RoundRobin(highPriorityProcesses, quantum);
        rr.schedule();

        // Schedule low priority queue with FCFS
        FCFS fcfs = new FCFS(lowPriorityProcesses);
        fcfs.schedule();
    }

    public List<Process> getProcesses() {
        List<Process> allProcesses = new ArrayList<>();
        allProcesses.addAll(highPriorityProcesses);
        allProcesses.addAll(lowPriorityProcesses);
        return allProcesses;
    }
}
