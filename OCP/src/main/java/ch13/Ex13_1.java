package ch13;

class Worker implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (i % 10 == 0) {
				System.out.print(".");
			}
		}
		System.out.println("Finished");
	}

}

public class Ex13_1 {

	void go() {
		Thread t1 = new Thread(new Worker());
		t1.start();
	}

	public static void main(String[] args) {
		new Ex13_1().go();
	}

}
