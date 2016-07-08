package uk.martinus.algo;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    private long inversions;

    /**
     * Merge Sort algorithm has complexity of O(nlogn), other popular sort
     * algorithms (e.g. bubble sort) have n**2; counting inversions can be done
     * within Merge Sort for free - it's useful in Collaborative Filtering
     * 
     * @param input
     *            Input list of any length
     * @return
     */
    public List<Integer> mergeSort(List<Integer> input) {
        List<Integer> output = new ArrayList<Integer>();
        int inputSize = input.size();

        // Handle special cases
        switch (inputSize) {
        case 0:
            // No elements to process!
            return output;
        case 1:
            // Base case with 1 element
            output.add(input.get(0));
            return output;
        case 2:
            // Base case with 2 elements
            if (input.get(0) <= input.get(1)) {
                output.add(input.get(0));
                output.add(input.get(1));
            } else {
                output.add(input.get(1));
                output.add(input.get(0));
                inversions++;
            }
            return output;
        default:
        }

        // Split the input list into two sub-lists
        List<Integer> sublistLeft = null;
        List<Integer> sublistRight = null;
        int split = inputSize % 2 == 0 ? inputSize / 2 : inputSize / 2 + 1;
        sublistLeft = input.subList(0, split);
        sublistRight = input.subList(split, inputSize);

        // Recursively sort each of the smaller lists
        List<Integer> sortedLeft = mergeSort(sublistLeft);
        List<Integer> sortedRight = mergeSort(sublistRight);

        // Merge the results of the sub-lists into the output list - i and j are
        // the indexes for the left and right sub-lists respectively
        for (int n = 0, i = 0, j = 0; n < inputSize; n++) {
            if (i == sortedLeft.size()) {
                output.add(sortedRight.get(j));
                j++;
                continue;
            }
            if (j == sortedRight.size()) {
                output.add(sortedLeft.get(i));
                i++;
                continue;
            }
            if (sortedLeft.get(i) <= sortedRight.get(j)) {
                output.add(sortedLeft.get(i));
                i++;
            } else {
                output.add(sortedRight.get(j));
                j++;
                inversions += sortedLeft.size() - i;
            }
        }

        // Return the sorted list
        return output;
    }

    public long getInversions() {
        return inversions;
    }

}
