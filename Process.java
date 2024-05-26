class Process {
    int id;
    int burstTime;
    int arrivalTime;
    int startTime = -1;
    int finishTime;
    int remainingTime = 0;
    int priority; // Lower numbers indicate higher priority

    Process(int id, int burstTime, int arrivalTime, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }
}
