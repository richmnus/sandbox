package ch2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Q22Test {

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
    public final void testKthNodeFromTail() {
        Q22 q22 = new Q22();
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        assertEquals("1st node from end is 3", 3, q22.kthFromLast(a, 1).data);
        assertEquals("2nd node from end is 2", 2, q22.kthFromLast(a, 2).data);
        assertEquals("3rd node from end is 2", 2, q22.kthFromLast(a, 3).data);
        assertEquals("4th node from end is 1", 1, q22.kthFromLast(a, 4).data);
    }
}
