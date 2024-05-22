import java.util.ArrayList;

public class ShortestJobFirst {
    private ArrayList<Process> jobQueue;
    private ArrayList<Process> readyQueue;
    private ArrayList<Process> ended;

    public ShortestJobFirst(ArrayList<Process> jobQueue) {
        this.jobQueue = jobQueue;
        this.readyQueue = new ArrayList<>();
        this.ended = new ArrayList<>();
        
        if (!jobQueue.isEmpty()) {
            ended.add(jobQueue.remove(0));
            ended.get(0).setDispatchTime(0);
        }
        
        while (!jobQueue.isEmpty()) {
            // jobQueue에서 readyQueue로 프로세스 이동
            for (Process process : jobQueue) {
                if (!ended.isEmpty() || process.getArrivalTime() <= ended.get(ended.size() - 1).getEndTime()) {
                    readyQueue.add(process);
                }
            }
            
            
            int minIndex = 0;
            for (int i = 1; i < readyQueue.size(); i++) {
                if (readyQueue.get(i).getBurstTime() < readyQueue.get(minIndex).getBurstTime()) {
                    minIndex = i;
                }
            }
            readyQueue.get(minIndex).setDispatchTime(ended.get(ended.size() - 1).getEndTime());
            ended.add(readyQueue.get(minIndex));
            
            for(int i=0; i < jobQueue.size(); i++) {
            	if(readyQueue.get(minIndex).getPID() == jobQueue.get(i).getPID()) {
            		jobQueue.remove(i);
            		break;	
            	}
            }
            
            readyQueue.clear();
            
        }
        
    }

    public void show() {
        System.out.println("sjf 되라 얍");
        
        for (Process process : ended) {
            System.out.println("Process ID: " + process.getPID() + ", Arrival Time: " + process.getArrivalTime() + ", Burst Time: " + process.getBurstTime());
            System.out.println("Dispatch Time: " + process.getDispatchTime()+ ", End Time: " + process.getEndTime());
            System.out.println("Turnaround Time: " + process.getTurnaroundTime()+ ", Waiting Time: " + process.getWaitingTime() + ", Response Time: " + process.getResponseTime());
            System.out.println("================================================================");
        }
        
    }
}