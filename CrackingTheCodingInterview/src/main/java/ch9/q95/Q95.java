package ch9.q95;

import java.util.ArrayList;
import java.util.List;

public class Q95 {

    Q95() {
    }

    List<String> permute(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }
        if (input.length() == 0) {
            // Return an empty list
            return new ArrayList<String>();
        }
        if (input.length() == 1) {
            List<String> perms = new ArrayList<>();
            // Return the input string
            perms.add(input);
            return perms;
        }
        List<String> perms = new ArrayList<>();
        // Split the input string into a main portion and a remainder portion
        // (the last char)
        String main = input.substring(0, input.length() - 1);
        char remainder = input.charAt(input.length() - 1);
        // Call this method recursively to calculate all permutations for the
        // main portion (which is of length one less than the input string)
        List<String> mainPerms = permute(main);
        // For each of the permutation strings returned by the recursive call,
        // insert the remainder character into each index position in the string
        // (and after the last character also) and save each one as a new
        // permutation
        for (String s : mainPerms) {
            // Add a space to the end so that the remainder character can also
            // be 'appended' to the end
            s += " ";
            for (int i = 0; i < s.length(); i++) {
                StringBuilder sb = new StringBuilder(s);
                sb.insert(i, remainder);
                perms.add(sb.toString().trim());
            }
        }
        return perms;
    }
}
