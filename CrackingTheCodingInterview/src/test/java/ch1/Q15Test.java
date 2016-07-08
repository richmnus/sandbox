package ch1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Q15Test {

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
    public final void testStringIsCompressed() {
        assertEquals("String is compressed", "a2b1c5a3",
                new Q15().compress("aabcccccaaa"));
    }

    @Test
    public final void testStringIsNotCompressed() {
        assertEquals("String is compressed", "abcd", new Q15().compress("abcd"));
    }

}
