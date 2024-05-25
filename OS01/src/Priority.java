import java.util.ArrayList;

public class Priority {
    private ArrayList<PriorityProcess> jobQ;
    private ArrayList<PriorityProcess> readyQ;
    private ArrayList<PriorityProcess> ended;

    public Priority(ArrayList<PriorityProcess> jobQ) {
        this.jobQ = jobQ;
        this.readyQ = new ArrayList<>();
        this.ended = new ArrayList<>();
        
        if (!jobQ.isEmpty()) {
            ended.add(jobQ.remove(0));
            ended.get(0).setDispatchTime(0);
        }
        
        while (!jobQ.isEmpty()) {
            // jobQueue에서 readyQueue로 프로세스 이동
            for (PriorityProcess process : jobQ) {
                if (!ended.isEmpty() || process.getArrivalTime() <= ended.get(ended.size() - 1).getEndTime()) {
                    readyQ.add(process);
                }
            }
            
            
            int minIndex = 0;
            for (int i = 1; i < readyQ.size(); i++) {
                if (readyQ.get(i).getPriority() > readyQ.get(minIndex).getPriority()) {
                    minIndex = i;
                }
            }
            readyQ.get(minIndex).setDispatchTime(ended.get(ended.size() - 1).getEndTime());
            ended.add(readyQ.get(minIndex));
            
            for(int i=0; i < jobQ.size(); i++) {
            	if(readyQ.get(minIndex).getPID() == jobQ.get(i).getPID()) {
            		jobQ.remove(i);
            		break;	
            	}
            }
            
            readyQ.clear();
            
        }
        
    }

    public void show() {
        System.out.println("Priority 되라 얍");
        for (PriorityProcess process : ended) {
            System.out.println("Process ID: " + process.getPID() + ", Arrival Time: " + process.getArrivalTime() + ", Burst Time: " + process.getBurstTime());
            System.out.println("Dispatch Time: " + process.getDispatchTime()+ ", End Time: " + process.getEndTime());
            System.out.println("Turnaround Time: " + process.getTurnaroundTime()+ ", Waiting Time: " + process.getWaitingTime() + ", Response Time: " + process.getResponseTime());
            System.out.println("================================================================");
        }
        
    }
}