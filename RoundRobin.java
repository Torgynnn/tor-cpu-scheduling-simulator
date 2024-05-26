import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class RoundRobin {
    private List<Process> processes;
    private int quantum; // Time slice given to each process

    public RoundRobin(List<Process> processes, int quantum) {
        this.processes = new ArrayList<>(processes);
        this.quantum = quantum;
    }

    public void schedule() {
        List<Process> readyQueue = new LinkedList<>(processes);
        int currentTime = 0;

        while (!readyQueue.isEmpty()) {
            Iterator<Process> iterator = readyQueue.iterator();
            while (iterator.hasNext()) {
                Process process = iterator.next();
                if (process.arrivalTime <= currentTime) {
                    if (process.remainingTime == 0) {
                        process.remainingTime = process.burstTime;
                    }
                    int timeSlice = Math.min(quantum, process.remainingTime);
                    process.startTime = (process.startTime == -1) ? currentTime : process.startTime;
                    currentTime += timeSlice;
                    process.remainingTime -= timeSlice;

                    if (process.remainingTime <= 0) {
                        process.finishTime = currentTime;
                        iterator.remove();
                    }
                }
            }
        }
    }

    public List<Process> getProcesses() {
        return processes;
    }
}

