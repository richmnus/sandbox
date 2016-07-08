package uk.martinus.algo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSort {

	private int comparisons = 0;

	public static void main(String[] args) {
		List<Integer> data = new ArrayList<Integer>();
		List<Integer> check = new ArrayList<Integer>();
		// data.add(3);
		// data.add(8);
		// data.add(2);
		// data.add(5);
		// data.add(1);
		// data.add(4);
		// data.add(7);
		// data.add(6);
		// data.add(9);
		// data.add(10);
		QuickSort qs = new QuickSort();
		qs.loadData("QuickSort.txt", data);
		qs.loadData("QuickSort.txt", check);
		qs.partition(data, 0, data.size() - 1);
		// System.out.println(data);
		Collections.sort(check);
		System.out.printf("Correct? - %b\n", qs.checkCorrectness(data, check));
		System.out.printf("Comparisons = %d\n", qs.getComparisons());
		List<Integer> motData = new ArrayList<Integer>();
		motData.add(30);
		motData.add(20);
		motData.add(30);
		motData.add(40);
		motData.add(100);
		motData.add(30);
		System.out.printf("Median-of-three index is %d\n",
				qs.medianOfThree(motData, 0, motData.size() - 1));
	}

	/**
	 * Quick Sort algorithm has average complexity of O(nlogn), dependent upon
	 * choice of pivot. This algorithm executes in place, the original list is
	 * passed down to each recursive call.
	 * 
	 * @param input
	 *            Input list
	 * @param left
	 *            beginning of sub-list to partition
	 * @param right
	 *            end of sub-list to partition (inclusive)
	 * @return
	 */
	public void partition(List<Integer> input, int left, int right) {
		int pivotIdx, swapValue;

		// Base case, 1 or less elements
		if (left >= right) {
			return;
		}

		// Calculate # comparisons for the exercise
		comparisons += (right - left);

		// Base case, 2 elements
		if ((right - left) == 1) {
			if (input.get(left) > input.get(right)) {
				swapValue = input.get(left);
				input.set(left, input.get(right));
				input.set(right, swapValue);
			}
			return;
		}

		// Choose a pivot - leftmost or rightmost
		// pivotIdx = left;
		// pivotIdx = right;
		pivotIdx = medianOfThree(input, left, right);

		// The partition stage assumes the pivot is in the first element of the
		// range, so swap the pivot with the first element (if it already is,
		// then this is just one redundant swap)
		swapValue = input.get(pivotIdx);
		input.set(pivotIdx, input.get(left));
		input.set(left, swapValue);

		// Partition (assumes pivot has been placed in the first element)
		int i = left + 1;
		for (int j = left + 1; j <= right; j++) {
			if (input.get(j) < input.get(left)) {
				swapValue = input.get(j);
				input.set(j, input.get(i));
				input.set(i, swapValue);
				i++;
			}
		}

		// Complete the partition - swap the pivot with the element just before
		// the i'th element
		swapValue = input.get(left);
		input.set(left, input.get(i - 1));
		input.set(i - 1, swapValue);

		// Recursively partition the left and right sub-lists either side of the
		// pivot (now placed in its correct position)
		partition(input, left, i - 2);
		partition(input, i, right);
	}

	private void loadData(String filename, List<Integer> data) {
		String value;
		BufferedReader input;
		try {
			FileInputStream fin = new FileInputStream(filename);
			input = new BufferedReader(new InputStreamReader(fin));
			while ((value = input.readLine()) != null) {
				data.add(Integer.parseInt(value));
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int medianOfThree(List<Integer> input, int left, int right) {
		int middle = (left + right) / 2;
		List<Integer> motIndex = new ArrayList<Integer>();

		// Min and max are the indices in the motIndex list that contain the
		// indices that correspond to the min and max values in the input list -
		// range is 0 to 2
		int min = 0;
		int max = 2;

		// This is the index in the motIndex list (that contains the index in
		// the input list) that is chosen to be the current pivot element, range
		// is 0 to 2
		int pivot = 0;

		// Initialise the motIndex list with the three indices from the input
		// list
		motIndex.add(left);
		motIndex.add(middle);
		motIndex.add(right);

		// For each of the three entries in the motIndex list...
		for (int i = 0; i < motIndex.size(); i++) {
			// Mark which of the three entries in the motIndex list contain the
			// corresponding min and max values in the input list
			if (input.get(motIndex.get(i)) > input.get(motIndex.get(max))) {
				max = i;
			}
			if (input.get(motIndex.get(i)) < input.get(motIndex.get(min))) {
				min = i;
			}
		}
			
		// For each of the three entries in the motIndex list...
		for (int i = 0; i < motIndex.size(); i++) {
			if (i != min && i != max) {
				pivot = i;
			}
		}
			
		// Return the index in the input list of the element that is chosen as
		// the current pivot
		return motIndex.get(pivot);
	}

	public int getComparisons() {
		return comparisons;
	}

	private boolean checkCorrectness(List<Integer> list1, List<Integer> list2) {
		boolean correct = true;
		for (int i = 0; i < list1.size(); i++) {
			if (list1.get(i).intValue() != list2.get(i).intValue()) {
				correct = false;
			}
		}
		return correct;
	}

}
