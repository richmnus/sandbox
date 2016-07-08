package demo;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		new Main().new Inner().doStuff();
		System.out.printf("At the end of Main\n");
	}

	private class Inner {

		public void doStuff() {
			try {
				System.out.printf("In the try block\n");
				int i = 1;
				if (i > 0) {
					throw new IOException();
				}
				throw new RuntimeException();
			} catch (IOException e) {
				System.out.printf("In the catch block\n");
			} finally {
				System.out.printf("In the finally block\n");
			}
			// The code at the bottom
			System.out.printf("This is the code at the bottom\n");
		}
	}

}
