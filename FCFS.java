import java.util.*;

class FCFS {
    private List<Process> processes;

    public FCFS(List<Process> processes) {
        this.processes = processes;
    }

    public void schedule() {
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        for (Process process : processes) {
            process.startTime = Math.max(currentTime, process.arrivalTime);
            process.finishTime = process.startTime + process.burstTime;
            currentTime = process.finishTime;
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }
}
