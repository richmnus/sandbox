package ch2;

import java.util.ArrayList;
import java.util.List;

public class Q21 {

	Node removeDuplicates(Node head) {
		List<Integer> check = new ArrayList<>();
		Node n = head;
		while (n.next != null) {
			if (check.contains(n.next.data)) {
				n.next = n.next.next;
			} else {
				check.add(n.next.data);
			}
			n = n.next;
		}
		return head;
	}

}
