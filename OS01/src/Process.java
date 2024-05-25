import java.util.ArrayList;

public class Process implements Comparable<Process> {
    protected int pid;
    protected int arrivalTime;
    protected int burstTime;
    protected int priority;
    protected ArrayList<Integer> dispatchTime = new ArrayList<>();
    protected ArrayList<Integer> endTime = new ArrayList<>();
    
    protected int turnaroundTime;
    protected int waitingTime;
    protected int responseTime;
    
    //새로 추가한 부분!!!!!
    protected int timeSlice;
    protected String sortBy = "burstTime";
    
    public Process(int processId, int arrivalTime, int burstTime, int priority) {
        this.pid = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    //프로세서 아이디
    public int getPID() { return pid; }
    //도착 시간
    public int getArrivalTime() { return arrivalTime; }
    //실행 시간
    public void setBurstTime(int burstTime) { this.burstTime = burstTime; }
    public int getBurstTime() { return burstTime; }
    //우선순위
    public int getPriority() { return priority; } 
    
    //디스패치
    public void setDispatchTime(int dispatchTime) { this.dispatchTime.add(dispatchTime); }
    public int getDispatchTime() { return dispatchTime.get(dispatchTime.size()-1); }
    //엔드
    public void setEndTime(int endTime) { this.endTime.add(endTime); }
    public int getEndTime() {
       endTime.add(dispatchTime.get(dispatchTime.size()-1) + burstTime);
       return endTime.get(endTime.size()-1);
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
     
     //새로 추가한 부분!!!!!
     //타임 슬라이스
     public int getTimeSlice() { return timeSlice; }
     public void setTimeSlice(int time) { this.timeSlice = time; }
     public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
     }
     //변경한 부분!!!!!!!!!!!
    @Override
    public int compareTo(Process process) {
       switch(sortBy) {
          case "PID":
             if (this.pid > process.getPID())
                   return 1;
               else if (this.pid < process.getPID())
                   return -1;
               else return 0;
          case "arrival":
             if (this.arrivalTime > process.getArrivalTime())
                return 1;
             else if (this.arrivalTime < process.getArrivalTime())
                return -1;
             else 
                return 0;
          case "burst":
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
       return 0;
    }
}