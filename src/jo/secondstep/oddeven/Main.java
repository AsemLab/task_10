package jo.secondstep.oddeven;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println(Thread.currentThread().getName()+" - started");
		
		OddEvenTask task = new OddEvenTask(1,2,3,4,5,6);
		task.executeTask();
		
		Thread.sleep(5000);
		task.addNewData(1,2,3,4,5);
		
		System.out.println(Thread.currentThread().getName()+" - completed");

	}

}
