package uk.martinus.jcip.ch5;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Barrier {

	private int nThreads;
	private CyclicBarrier barrier;

	public Barrier(int nThreads) {
		this.nThreads = nThreads;
		barrier = new CyclicBarrier(nThreads);
	}

	public void go() {
		Runnable task = new Runnable() {

			@Override
			public void run() {
				long startTime = 0;
				long endTime = 0;
				String msg;
				msg = String.format("Task started in thread %s\n", Thread
						.currentThread().getName());
				print(msg);
				try {
					startTime = new Date().getTime();
					Random r = new Random(System.nanoTime());
					Thread.sleep(1000 + r.nextInt(1000));
					endTime = new Date().getTime();
					barrier.await();
					msg = String.format("Task ended in thread %s after %dms\n",
							Thread.currentThread().getName(), endTime
									- startTime);
					print(msg);
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}

		};

		for (int i = 0; i < nThreads; i++) {
			Thread thread = new Thread(task, "worker" + Integer.toString(i));
			thread.start();
		}
	}

	private synchronized void print(String msg) {
		System.out.printf("%s", msg);
	}

	public static void main(String args[]) {
		new Barrier(10).go();
	}

}
