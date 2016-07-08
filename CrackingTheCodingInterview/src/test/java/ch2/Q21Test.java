package ch2;

import static org.junit.Assert.*;

import org.junit.Test;

public class Q21Test {

	@Test
	public void test() {
		Q21 q21 = new Q21();
		Node a = new Node(1);
		Node b = new Node(2);
		Node c = new Node(2);
		Node d = new Node(3);
		Node e = new Node(4);
		a.next = b;
		b.next = c;
		c.next = d;
		d.next = e;
		assertEquals("Duplicate node is removed", "1234",
				q21.removeDuplicates(a).toString());
	}

}
