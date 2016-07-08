package ch14;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class ForkJoinDemo {

	static class InitialiseArrayAction extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		private int[] data;
		private int start;
		private int end;

		InitialiseArrayAction(int[] data, int start, int end) {
			this.data = data;
			this.start = start;
			this.end = end;
		}

		@Override
		protected void compute() {
			if (end - start < 80_000_000) {
				// Less than threshold, just do the work
				for (int i = start; i < end; i++) {
					data[i] = ThreadLocalRandom.current().nextInt(0, 10);
				}
			} else {
				// Split the work
				InitialiseArrayAction a1 = new InitialiseArrayAction(data, 0,
						data.length / 2);
				InitialiseArrayAction a2 = new InitialiseArrayAction(data,
						data.length / 2, data.length);
				a1.fork();
				a2.compute();
				a1.join();
			}
		}
	}

	public static void main(String[] args) {
		int[] someArray = new int[100_000_000];
		ForkJoinDemo demo = new ForkJoinDemo();
		ForkJoinPool fjPool = new ForkJoinPool();
		RecursiveAction action = new ForkJoinDemo.InitialiseArrayAction(
				someArray, 0, someArray.length);

		long start = System.currentTimeMillis();
		fjPool.invoke(action);
		long end = System.currentTimeMillis();
		System.out.println("Time taken = " + (end - start) + "ms");

	}

}
