import java.util.ArrayList;

public class FirstComeFirstServed {
	//FCFS 개발 클래스입니다.
	private ArrayList<Process> jobQ; //= new ArrayList<>(); // jobQ
	private int time;
	public FirstComeFirstServed(ArrayList<Process> jobQ) {
		this.jobQ = jobQ;
		time = 0;
		//파일에서 정보 얻어와 리스트에 추가하기 (plist.add(new ProcessCom(n, m,, ,));
		/*
		jobQ.add(new Process(1, 10, 10, 1));
		jobQ.add(new Process(2, 5, 1, 1));
		jobQ.add(new Process(3, 20, 5, 1));
		jobQ.add(new Process(4, 2, 2, 1));
		*/
		//
		printp(); // process 정보 출력
		//Collections.sort(jobQ); // 도착시간 기준으로 정렬
		for(int i = 0; i < jobQ.size(); i++) { // 정렬된 순서대로 process 실행
			while(time < jobQ.get(i).getArrivalTime()) time++; // process 도착할 때까지 time 증가
			jobQ.get(i).setDispatchTime(time);
			time += jobQ.get(i).getBurstTime();
			jobQ.get(i).setEndTime(time);
		}
		print();
	}
	public void printp() {
		System.out.println("[Input]");
		System.out.printf("| %-13s| %-13s| %-13s|\n", "process ID", "Burst", "arrive");
		for(Process p: jobQ) {
			System.out.printf("| %-13d| %-13d| %-13d|\n", p.getPID(), p.getBurstTime(), p.getArrivalTime());
		}
	}
	public void print() {
		double avgW;
		double avgR;
		int sumW = 0;
		int sumR = 0;
		System.out.println("[Output]");
		System.out.printf("| %-13s| %-13s| %-13s| %-13s|\n", "process ID", "Turnaround", "Waiting", "Response");
		for(Process p: jobQ) {
			sumW += p.getWaitingTime();
			sumR += p.getResponseTime();
			System.out.printf("| %-13d| %-13d| %-13d| %-13d|\n", p.getPID(), p.getTurnaroundTime(), p.getWaitingTime(), p.getResponseTime());
		}
		avgW = (double)sumW/jobQ.size();
		avgR = (double)sumR/jobQ.size();
		System.out.printf("%-20s %3d\n", "Execution time", time);
		System.out.printf("%-20s %5.2f\n", "Avg waiting time", avgW);
		System.out.printf("%-20s %5.2f\n", "Avg response time",  avgR);
		System.out.println("==============================================================");
	}
}