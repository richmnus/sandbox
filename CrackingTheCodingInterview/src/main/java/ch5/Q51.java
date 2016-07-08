package ch5;

public class Q51 {

	static int insertInto(int m, int n, int i, int j) {
		// n is the receiver, so clear a space in n
		// n is 1101 1110 0011, m is 11001, i=2, j=6
		// Result is 1101 1110 0111
		// Need mask 1111 1000 0011
		// Start with 0000 0111 1100 and invert
		int mask = 0;
		for (int k = j; k >= i; k--) {
			mask |= 1 << k;
		}
		int maskedN = n & (~mask);
		int modifiedM = m << i;
		int result = modifiedM | maskedN;
		return result;
	}

}
