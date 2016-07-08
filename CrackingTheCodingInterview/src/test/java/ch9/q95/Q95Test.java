package ch9.q95;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class Q95Test {

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
    public final void testZeroCharacters() {
        Q95 q95 = new Q95();
        List<String> results = q95.permute("");
        assertTrue("Perms list is empty", results.size() == 0);
    }

    @Test
    public final void testOneCharacter() {
        Q95 q95 = new Q95();
        List<String> results = q95.permute("d");
        assertTrue("Perms list has one string", results.size() == 1);
    }

    @Test
    public final void testTwoCharacters() {
        Q95 q95 = new Q95();
        List<String> results = q95.permute("do");
        assertTrue("Perms list has two strings", results.size() == 2);
    }

    @Test
    public final void testThreeCharacters() {
        Q95 q95 = new Q95();
        List<String> results = q95.permute("dog");
        assertTrue("Perms list has six strings", results.size() == 6);
    }

    @Test
    public final void testManyCharacters() {
        Q95 q95 = new Q95();
        List<String> results = q95.permute("quick");
        for (String s : results) {
            System.out.println(s);
        }
        assertTrue("Perms list has six strings", results.size() == 120);
    }

    @Test
    public final void testMockito() {
    	Q95 q95Mock = mock(Q95.class);
        List<String> results = q95Mock.permute("dog");
        assertTrue("Results should be empty", results.size()==0);
    	verify(q95Mock).permute("dog");
    	when(q95Mock.permute("dog")).thenReturn(new ArrayList<String>());
    }

}
