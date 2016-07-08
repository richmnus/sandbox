package uk.martinus.algo;

import java.util.List;

public class BruteForceInversions {

    /**
     * Count inversions using brute force. This executes in n**2 time. Useful
     * for checking other algorithms. Max number of inversions is 'n choose 2',
     * or n(n-1)/2. So if int values are used for the input array, then a long
     * value should be used for counting inversions to prevent overflow.
     * 
     * @param input
     *            The input list
     * @return Number of inversions
     */
    public static long countInversions(List<Integer> input) {
        long inversions = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = i; j < input.size(); j++) {
                if (input.get(i) > input.get(j)) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

}
