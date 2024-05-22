import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Process> jobQ = new ArrayList<>();
		
		while(true) {
			System.out.print("프로세스 ID: ");
			int id = scanner.nextInt();
			if(id == 0)
				break;
			
			System.out.print("도착시간: ");
			int at = scanner.nextInt();
			
			System.out.print("실행시간: ");
			int bt = scanner.nextInt();
			
			System.out.print("우선순위: ");
			int priority = scanner.nextInt();
			
			
			jobQ.add(new Process(id, at, bt, priority));
		
		}
		Collections.sort(jobQ);
		
		
		System.out.print("스케쥴러 선택: ");
		int sch = scanner.nextInt(); //
		
		switch(sch) {
		case 1:
	        ShortestJobFirst sjf = new ShortestJobFirst(jobQ);
	        sjf.show();
	        break;
		case 2:
	        Priority priority = new Priority(jobQ);
	        priority.show();
	        break;
		case 3:
	        //RoundRobin rr = new RoundRobin(jobQ);
	        break;
		case 4:
	        FirstComeFirstServed fcfs = new FirstComeFirstServed(jobQ);
	        break;
		}
		scanner.close();
		
	}

}