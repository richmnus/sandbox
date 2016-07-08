package ch1;

import java.util.HashMap;
import java.util.Map;

class Q13 {

	boolean isPerm(String first, String second) {
		char[] firstLetters = first.toCharArray();
		char[] secondLetters = second.toCharArray();
		Map<Character, Integer> check = new HashMap<>();
		// Load the map with all letters from the first word
		for (char c : firstLetters) {
			int count = 0;
			if (check.containsKey(c)) {
				count = check.get(c);
				count++;
				check.put(c, count);
			} else {
				check.put(c, 1);
			}
		}
		for (char c : secondLetters) {
			if (!check.containsKey(c)) {
				// Key wasn't in the map
				return false;
			} else {
				int count = check.get(c);
				count--;
				if (count == 0) {
					check.remove(c);
				} else {
					check.put(c, count);
				}
			}
		}
		// All letters now removed, check if any left
		if (check.isEmpty()) {
			// It's a perm
			return true;
		}
		return false;
	}

}
