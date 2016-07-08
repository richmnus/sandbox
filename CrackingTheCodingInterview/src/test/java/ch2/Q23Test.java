package ch2;

import static org.junit.Assert.*;

import org.junit.Test;

public class Q23Test {

    @Test
    public final void testKthNodeFromTail() {
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(2);
        Node d = new Node(3);
        Node e = new Node(4);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;
        assertEquals("Node c is present, sequence is 12234", "12234", a.toString());
        c.deleteNode();
        assertEquals("Node c is deleted, sequence is 1234", "1234", a.toString());
    }

}
