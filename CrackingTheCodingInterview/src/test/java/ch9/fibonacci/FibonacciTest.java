package ch9.fibonacci;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FibonacciTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testFibonacciGeneratorWithoutCache() {
        Fibonacci fibonacci = new Fibonacci();
        int[] expected = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 };
        int[] actual = new int[10];
        for (int i = 0; i < 10; i++) {
            actual[i] = fibonacci.fib(i);
        }
        assertArrayEquals("The first 10 Fibonacci numbers, no cache", expected,
                actual);
    }

    @Test
    public final void testFibonacciGeneratorWithCache() {
        Fibonacci fibonacci = new Fibonacci();
        int[] expected = { 0, 1, 1, 2, 3, 5, 8, 13, 21, 34 };
        int[] actual = new int[10];
        for (int i = 0; i < 10; i++) {
            actual[i] = fibonacci.fibCache(i);
        }
        assertArrayEquals("The first 10 Fibonacci numbers, with cache",
                expected, actual);
    }

    @Test
    public final void testTimingFibonacciCacheVsNoCache() {
        Fibonacci fibonacci = new Fibonacci();
        long start1 = new Date().getTime();
        for (int i = 0; i < 40; i++) {
            fibonacci.fib(i);
        }
        long finish1 = new Date().getTime();
        long start2 = new Date().getTime();
        for (int i = 0; i < 40; i++) {
            fibonacci.fibCache(i);
        }
        long finish2 = new Date().getTime();
        System.out.printf("Fibonacci sequence to 100, no cache, %d ms\n",
                finish1 - start1);
        System.out.printf("Fibonacci sequence to 100, with cache, %d ms\n",
                finish2 - start2);
    }

}
