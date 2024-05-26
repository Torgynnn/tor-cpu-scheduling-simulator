import java.util.*;

class SJF {
    private List<Process> processes;

    public SJF(List<Process> processes) {
        this.processes = new ArrayList<>(processes);
    }

    public void schedule() {
        // Sort processes based on burst time
        processes.sort(Comparator.comparingInt(p -> p.burstTime));

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
