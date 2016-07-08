package ch9.fibonacci;

public class Fibonacci {

    int[] cache;

    Fibonacci() {
        cache = new int[100];
    }

    int fib(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        return fib(i - 1) + fib(i - 2);
    }

    int fibCache(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (cache[i] != 0) {
            return cache[i];
        }
        cache[i] = fibCache(i - 1) + fibCache(i - 2);
        return cache[i];
    }

}
