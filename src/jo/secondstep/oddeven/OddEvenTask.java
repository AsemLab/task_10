package jo.secondstep.oddeven;

import java.util.*;

public class OddEvenTask {

	private List<Integer> data;
	private List<Integer> oddNumbersData;
	private List<Integer> evenNumbersData;

	final private Key dataKey = new Key();
	final private Key oddDataKey = new Key();
	final private Key evenDataKey = new Key();

	private Thread t1;
	private Thread t2;
	private Thread t3;

	boolean active;

	public OddEvenTask(Integer... data) {
		this.data = new ArrayList<>();

		oddNumbersData = new ArrayList<Integer>();
		evenNumbersData = new ArrayList<Integer>();

		active = false;

		prepareThreads(data);
	}

	public void stopExecute() {
		active = false;
	}

	public void executeTask() {

		System.out.println(Thread.currentThread().getName() + " - started");

		active = true;

		t1.setName("T1");
		t2.setName("T2");
		t3.setName("T3");

		t1.start();
		t2.start();
		t3.start();

	}

	public void addNewData(Integer... input) {
		synchronized (dataKey) {
			if (data.isEmpty()) {
				data.addAll(Arrays.asList(input));
				dataKey.notify();
			}
		}
	}

	public void prepareThreads(Integer... input) {
		t1 = new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println(Thread.currentThread().getName() + " - started");
				data.addAll(Arrays.asList(input));

				while (active) {

					synchronized (dataKey) {

						if (data.isEmpty()) {
							try {
								System.out.println(Thread.currentThread().getName() + " - checked");
								dataKey.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} // if end

						else {

							for (Integer n : data) {
								if (n % 2 == 0) {
									synchronized (evenDataKey) {
										evenNumbersData.add(n);
										evenDataKey.notify();
									}
								} else {
									synchronized (oddDataKey) {
										oddNumbersData.add(n);
										oddDataKey.notify();
									}
								}
							} // for end

							data.clear();
						} // else end
					} // synchronized end
				} // while end

				System.out.println(Thread.currentThread().getName() + " - completed");
			}// run end
		});

		t2 = new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println(Thread.currentThread().getName() + " - started");

				while (active) {
					synchronized (evenDataKey) {

						if (evenNumbersData.isEmpty()) {
							try {
								System.out.println(Thread.currentThread().getName() + " - checked");
								evenDataKey.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} // if end
						else {
							for (Integer n : evenNumbersData) {
								for (int i = 0; i < n; i++)
									System.out.print("*");

								System.out.println();
							} // outer for end

							evenNumbersData.clear();
						} // else end
					} // synchronized end
				} // while end

				System.out.println(Thread.currentThread().getName() + " - completed");
			}// run end
		});

		t3 = new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println(Thread.currentThread().getName() + " - started");

				while (active) {
					synchronized (oddDataKey) {

						if (oddNumbersData.isEmpty()) {
							try {
								System.out.println(Thread.currentThread().getName() + " - checked");
								oddDataKey.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} // if end
						else {
							for (Integer n : oddNumbersData) {
								for (int i = 0; i < n; i++)
									System.out.print("*");

								System.out.println();
							} // outer for end

							oddNumbersData.clear();
						} // else end
					} // synchronized end
				} // while end

				System.out.println(Thread.currentThread().getName() + " - completed");
			}// run end
		});

	}
}
