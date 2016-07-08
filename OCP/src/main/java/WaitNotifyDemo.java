public class WaitNotifyDemo {

	public static void main(String[] args) {
		ThreadB b = new WaitNotifyDemo().new ThreadB();
		b.start();
		synchronized (b) {
			try {
				b.wait();
				System.out.println("Thread a: " + b.total);
			} catch (InterruptedException e) {
				System.out.println(b.total);
			}
		}
	}

	class ThreadB extends Thread {
		int total;

		public void run() {
			synchronized (this) {
				for (int i = 0; i < 100; i++) {
					total += i;
				}
				System.out.println("Thread b: " + total);
				notify();
			}
		}
	}

}
