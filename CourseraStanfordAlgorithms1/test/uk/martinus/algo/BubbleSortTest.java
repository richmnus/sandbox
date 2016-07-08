package uk.martinus.algo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.martinus.algo.BubbleSort;

public class BubbleSortTest {

	private static final int[] input = { 5, 9, 2, 4, 8, 10, 7, 6, 3, 1 };
	private static final int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void numbersAreSortedAscendingOrder() {
		BubbleSort sort = new BubbleSort();
		sort.bubbleSort(input);
		print();
		Assert.assertArrayEquals("Bubble-sort sorted output", expected, input);
	}

	private void print() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length; i++) {
			builder.append(String.format("%d", input[i]));
			if (i < input.length - 1) {
				builder.append(", ");
			}
		}
		builder.append("\n");
		System.out.println(builder.toString());
	}

}
