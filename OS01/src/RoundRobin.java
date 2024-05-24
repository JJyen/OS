import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class RoundRobin {
	private ArrayList<Process> jobQ; // job queue
	private Queue<Process> readyQ = new LinkedList<>(); // ready queue
	private int time;
	private int timeSlice;
	private int totalTimeSpent = 0;
	
	public RoundRobin(ArrayList<Process> jobQ) {
		this.jobQ = jobQ;
		time = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("Time Slice 입력: ");
		timeSlice = sc.nextInt();
	}
	
	public void run() {
		StringBuilder pidBuilder = new StringBuilder();
		StringBuilder timeBuilder = new StringBuilder();
		//도착 시간으로 정렬
		for(String sortBy : Arrays.asList("arrival")) {
			for(Process p : jobQ) {
				p.setSortBy(sortBy);
			}
		}
		Collections.sort(jobQ);
		int jobQIndex = 0;
		readyQ.add(jobQ.get(jobQIndex));
		totalTimeSpent += jobQ.get(jobQIndex).getBurstTime();
		jobQIndex += 1;
		while(!readyQ.isEmpty()) {
			//
			if(readyQ.element().getArrivalTime() > time) {
				pidBuilder.append("| ");
				timeBuilder.append(time);
				if(time < 10) timeBuilder.append(" ");
			}
			
			while(readyQ.element().getArrivalTime() > time) time++;
			//
			pidBuilder.append("| p").append(readyQ.element().getPID());
			timeBuilder.append(time);
			if(time < 10) timeBuilder.append(" ");
			
			readyQ.element().setDispatchTime(time);
			if(readyQ.element().getBurstTime() <= timeSlice) {
				//
				for(int i = 0; i < readyQ.element().getBurstTime() - 2; i++) pidBuilder.append(" ");
				for(int i = 0; i < readyQ.element().getBurstTime(); i++) timeBuilder.append(" ");
				
				time += readyQ.element().getBurstTime();
				readyQ.element().setBurstTime(0);
				
				for(int i = jobQIndex; i < jobQ.size(); i++) { //jobQ 에서 readyQ로
					if(time >= jobQ.get(jobQIndex).getArrivalTime()) {
						readyQ.add(jobQ.get(jobQIndex));
						totalTimeSpent += jobQ.get(jobQIndex).getBurstTime();
						jobQIndex += 1;
					}
				}
			}
			else {
				//
				for(int i = 0; i < timeSlice - 2; i++) pidBuilder.append(" ");
				for(int i = 0; i < timeSlice; i++) timeBuilder.append(" ");
				
				time += timeSlice;
				readyQ.element().setBurstTime(readyQ.element().getBurstTime() - timeSlice);
				for(int i = jobQIndex; i < jobQ.size(); i++) { //jobQ 에서 readyQ로
					if(time >= jobQ.get(jobQIndex).getArrivalTime()) {
						readyQ.add(jobQ.get(jobQIndex));
						totalTimeSpent += jobQ.get(jobQIndex).getBurstTime();
						jobQIndex += 1;
					}
				}
				readyQ.add(readyQ.element());
			}
			readyQ.element().setEndTime(time);
			readyQ.remove();
		}
		pidBuilder.append("|");
		timeBuilder.append(time);
		System.out.println("[Gantt chart]");
		System.out.println(pidBuilder);
		System.out.println(timeBuilder);
		System.out.println("==============================================================");
		printResult();
	}
	
	public void printResult() {
		double avgW;
		double avgR;
		int sumW = 0;
		int sumR = 0;
		for(String sortBy : Arrays.asList("PID")) {
			for(Process p : jobQ) {
				p.setSortBy(sortBy);
			}
		}
		Collections.sort(jobQ);
		System.out.println("[result]");
		System.out.printf("| %-13s| %-13s| %-13s| %-13s|\n", "process ID", "Turnaround", "Waiting", "Response");
		for(Process p: jobQ) {
			sumW += p.getWaitingTime();
			sumR += p.getResponseTime();
			System.out.printf("| %-13d| %-13d| %-13d| %-13d|\n", p.getPID(), p.getTurnaroundTime(), p.getWaitingTime(), p.getResponseTime());
		}
		avgW = (double)sumW/jobQ.size();
		avgR = (double)sumR/jobQ.size();
		System.out.printf("%-20s %-6d\n", "Execution time", time);
		System.out.printf("%-20s %-6.2f\n", "Avg waiting time", avgW);
		System.out.printf("%-20s %-6.2f\n", "Avg response time",  avgR);
		System.out.printf("%-20s %-6.2f\n", "CPU utilization", (double)totalTimeSpent/time);
		System.out.println("==============================================================");
	}
}