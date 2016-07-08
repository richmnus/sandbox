package ch1;

public class Q15 {

    String compress(String input) {
        StringBuilder output = new StringBuilder();
        char[] chars = input.toCharArray();
        char current = ' '; // Input string can't contain a ' ' character
        int repeats = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != current) {
                if (i > 0) {
                    output.append(repeats + 1);
                }
                output.append(chars[i]);
                repeats = 0;
                current = chars[i];
            } else {
                repeats++;
            }
        }
        output.append(repeats + 1);
        if (output.length() > input.length()) {
            return input;
        }
        return output.toString();
    }

}
