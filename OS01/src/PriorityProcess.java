import java.util.*;

public class PriorityProcess extends Process {

    public PriorityProcess(int processId, int arrivalTime, int burstTime, int priority) {
        super(processId, arrivalTime, burstTime, priority);
    }

    @Override
    public int compareTo(Process process) {
        if (this.arrivalTime > process.getArrivalTime())
            return 1;
        else if (this.arrivalTime < process.getArrivalTime())
            return -1;
        else { // arrivalTime이 같은 경우
            if (this.priority < process.getPriority())
                return 1;
            else if (this.priority > process.getPriority())
                return -1;
            return 0; // arrivalTime과 burstTime이 모두 같은 경우
        }
    }

}
