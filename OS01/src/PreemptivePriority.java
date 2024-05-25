import java.util.ArrayList;


public class PreemptivePriority {
    private ArrayList<PriorityProcess> readyQ;
    private ArrayList<PriorityProcess> run;
 
	public PreemptivePriority(ArrayList<PriorityProcess> jobQueue) {
        this.readyQ = jobQueue;
        this.run = new ArrayList<>();
        
        if(!readyQ.isEmpty()) {
        	PriorityProcess firstP = readyQ.get(0);
        	run.add(firstP);
        	firstP.setDispatchTime(firstP.getArrivalTime());
        }
        
        while(!readyQ.isEmpty()) {
        	Integer noneFinishedP_index = null;
        	boolean isFinished = true;
        	
        	//run의 마지막 요소가 readyQ에도 존재하는 확인하여 작업이 끝난 P인지 확인한다. 끝나지 않은 P이면 readyQ에서의 인덱스 위치를 NFP에 저장한다.
        	for(PriorityProcess process : readyQ) {
        		if(run.get(run.size()-1).getPID() == process.getPID() ){
        			isFinished = false;
        			noneFinishedP_index = readyQ.indexOf(process);
        			break;
        		}
        	}
        	
        	
        	//int highestPriority = run.get(run.size()-1).getPriority(); //2
        	int highestPriorityIndex = 0;
        	
        	if(noneFinishedP_index != null)
        		highestPriorityIndex = noneFinishedP_index; //0
        	
        	if(isFinished) { //NFP = null
        		for(PriorityProcess p : readyQ) {
        			if(run.get(run.size()-1).getEndTime() >= p.getArrivalTime() && readyQ.get(highestPriorityIndex).getPriority() < p.getPriority())
        				highestPriorityIndex = readyQ.indexOf(p);
        		}
        		run.add(readyQ.get(highestPriorityIndex));
        		
        		boolean isRedispatched = false;
        		for(PriorityProcess p : run) {
        			if(run.get(run.size()-1).getPID() == p.getPID()) {
        				isRedispatched = true;
        				break;
        			}
        		}
        		
        		if(!isRedispatched)
        			run.get(run.size()-1).setDispatchTime(run.get(run.size()-1).getArrivalTime());
        		else if(isRedispatched)
        			run.get(run.size()-1).setDispatchTime(run.get(run.size()-2).getEndTime());
        		
        	} else if(!isFinished) { //NFP = 정수
        		int noneFinishedP_ET = readyQ.get(noneFinishedP_index).getDispatchTime() + readyQ.get(noneFinishedP_index).getBurstTime(); //선정 당하지 않았을 때의 endTime
        		for(PriorityProcess p : readyQ) {
        			if(noneFinishedP_ET >= p.getArrivalTime() && readyQ.get(highestPriorityIndex).getPriority() < p.getPriority()) {
        				highestPriorityIndex = readyQ.indexOf(p);
        			}
        		}
        				
        	}
        	
        	if(noneFinishedP_index != null) {
            	if(noneFinishedP_index == highestPriorityIndex) {
            		readyQ.remove((int) noneFinishedP_index);
            	} else if(noneFinishedP_index != highestPriorityIndex) {
            		run.get(run.size()-1).setEndTime(readyQ.get(highestPriorityIndex).getArrivalTime());
            		run.add(readyQ.get(highestPriorityIndex));
            		run.get(run.size()-1).setDispatchTime(run.get(run.size()-1).getArrivalTime());
            		
            		PriorityProcess noneFinishedP = run.get(run.size()-2);
            		//System.out.println("1: " + noneFinishedP.getBurstTime());
            		//System.out.println("2: " + run.get(run.size()-1).getDispatchTime());
            		//System.out.println("3: " + noneFinishedP.getDispatchTime());
            		noneFinishedP.setBurstTime(noneFinishedP.getBurstTime() - run.get(run.size()-1).getDispatchTime()); //- noneFinishedP.getDispatchTime()
            	}
        	}
        	        	
        }
        System.out.println("Current readyQ size: " + readyQ.size());
        System.out.println("Current run size: " + run.size());
        show();
        
        
	}
	
	public void show() {
		 	System.out.println("??");
	        for (PriorityProcess process : run) {
	            System.out.println("Process ID: " + process.getPID() + ", Arrival Time: " + process.getArrivalTime() + ", Burst Time: " + process.getBurstTime());
	            System.out.println("Dispatch Time: " + process.getDispatchTime()+ ", End Time: " + process.getEndTime());
	            System.out.println("Turnaround Time: " + process.getTurnaroundTime()+ ", Waiting Time: " + process.getWaitingTime() + ", Response Time: " + process.getResponseTime());
	            System.out.println("================================================================");
	        }
	        
	    }
}
