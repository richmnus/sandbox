package ch1;

class Q11 {

	boolean hasDuplicate(String input) {
		char[] letters = input.toCharArray();
		for (int i=0; i < letters.length; i++) {
			for (int j=0; j < letters.length; j++) {
				if (i == j) {
					continue;
				}
				if (letters[i] == letters[j]) {
					return true;
				}
			}
		}
		return false;
	}
}
