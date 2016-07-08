package uk.martinus.algo;

public class BubbleSort {

    /**
     * Bubble sort, excecutes in place, complexity O(n**2)
     * 
     * @param input
     *            Array of ints to sort
     */
    public void bubbleSort(int[] input) {
        int swap;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length - 1; j++) {
                if (input[j + 1] < input[j]) {
                    swap = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = swap;
                }
            }
        }
    }

}
