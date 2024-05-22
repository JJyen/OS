import java.util.ArrayList;

public class Process implements Comparable<Process> {
    private int pid;
    private int arrivalTime;
    private ArrayList<Integer> dispatchTime = new ArrayList<>();
    private ArrayList<Integer> endTime = new ArrayList<>();
    private int burstTime;
    private int priority; 
    private int turnaroundTime;
    private int waitingTime;
    private int responseTime;
    
    
    public Process(int processId, int arrivalTime, int burstTime, int priority) {
        this.pid = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    //프로세서 아이디
    public int getPID() {
        return pid;
    }

    //도착 시간
    public int getArrivalTime() {
        return arrivalTime;
    }
    
    //실행 시간
    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getBurstTime() {
        return burstTime;
    }
    
    //디스패치
    public void setDispatchTime(int dispatchTime) {
    	this.dispatchTime.add(dispatchTime);
    } 
    
    public int getDispatchTime() {
    	return dispatchTime.get(dispatchTime.size()-1);
    }
    
    //엔드
    public void setEndTime(int endTime) {
    	this.endTime.add(endTime);
    }
    
    public int getEndTime() {
    	endTime.add(dispatchTime.get(dispatchTime.size()-1) + burstTime);
    	return endTime.get(endTime.size()-1);
    }
    
    //우선순위
    public int getPriority() {
    	return priority;
    } 
    
    //반환시간
    public int  getTurnaroundTime() {
        turnaroundTime = endTime.get(endTime.size() - 1) - arrivalTime;
        return turnaroundTime;
     }
    
    //대기시간
     public int getWaitingTime() {
        waitingTime = dispatchTime.get(0) - arrivalTime;
        for(int i = 0; i < dispatchTime.size() - 1; i++) {
           waitingTime += dispatchTime.get(i + 1) - endTime.get(i);
        }
        
        return waitingTime;
     }
     
     //응답시간
     public int getResponseTime() {
        responseTime = dispatchTime.get(0) - arrivalTime;
        return responseTime;
     }
  
    
    @Override
    public int compareTo(Process process) {
        if (this.arrivalTime > process.getArrivalTime())
            return 1;
        else if (this.arrivalTime < process.getArrivalTime())
            return -1;
        else { // arrivalTime이 같은 경우
            if (this.burstTime > process.getBurstTime())
                return 1;
            else if (this.burstTime < process.getBurstTime())
                return -1;
            return 0; // arrivalTime과 burstTime이 모두 같은 경우
        }
    }

}