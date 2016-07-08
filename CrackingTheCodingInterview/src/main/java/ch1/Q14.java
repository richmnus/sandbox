package ch1;

public class Q14 {

    String encode(String input) {
        char[] characters = input.toCharArray();
        int length = characters.length;
        for (int i = 0; i < length; i++) {
            if (characters[i] == ' ') {
                characters[i] = '0';
                shiftRight(characters, i, endOf(characters));
                characters[i] = '2';
                shiftRight(characters, i, endOf(characters));
                characters[i] = '%';
            }
        }
        return new String(characters);
    }

    char[] shiftRight(char[] input, int start, int end) {
        for (int i = end + 1; i > start; i--) {
            input[i] = input[i - 1];
        }
        input[start] = ' ';
        return input;
    }

    int endOf(char[] input) {
        for (int i = input.length - 1; i >= 0; i--) {
            if (input[i] != ' ') {
                return i;
            }
        }
        return -1;
    }
}
