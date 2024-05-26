import java.util.*;

class Priority {
    private List<Process> processes;

    public Priority(List<Process> processes) {
        this.processes = new ArrayList<>(processes);
    }

    public void schedule() {
        // Sort processes based on priority and arrival time
        processes.sort(Comparator.comparingInt((Process p) -> p.priority)
                .thenComparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        for (Process process : processes) {
            if (process.arrivalTime > currentTime) {
                currentTime = process.arrivalTime;
            }
            process.startTime = currentTime;
            process.finishTime = currentTime + process.burstTime;
            currentTime += process.burstTime;
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }
}
