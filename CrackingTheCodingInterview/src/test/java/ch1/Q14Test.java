package ch1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Q14Test {

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
    public final void stringIsShiftedRightOnePosition() {
        assertEquals(
                "The shifted output is 'Hello  World'",
                "Hello  World",
                new String(new Q14().shiftRight("Hello World ".toCharArray(),
                        6, 10)));
        assertEquals(
                "The shifted output is ' Hello World'",
                " Hello World",
                new String(new Q14().shiftRight("Hello World ".toCharArray(),
                        0, 10)));
    }

    @Test
    public final void returnsIndexOfLastCharacter() {
        assertEquals("The last character index is 10", 10,
                new Q14().endOf("Hello World ".toCharArray()));
        assertEquals("The last character index is 11", 11,
                new Q14().endOf(" Hello World".toCharArray()));
    }

    @Test
    public final void theStringIsEncodedCorrectly() {
        assertEquals("The encoded output is 'Mr%20John%20Smith'",
                "Mr%20John%20Smith", new Q14().encode("Mr John Smith    "));
    }

}
