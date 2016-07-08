package ch2;

public class Node {
	public Node next = null;
	public int data;

	public Node(int item) {
		this.data = item;
	}

	public void appendToTail(int d) {
		Node end = new Node(d);
		Node n = this;
		while (n.next != null) {
			n = n.next;
		}
		n.next = end;
	}

	public Node deleteNode(Node head, int d) {
		Node n = head;
		if (n.data == d) {
			return head.next;
		}
		while (n.next != null) {
			if (n.next.data == d) {
				n.next = n.next.next;
				return head;
			}
			n = n.next;
		}
		return head;
	}

	void deleteNode() {
		this.data = this.next.data;
		this.next = this.next.next;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Node n = this;
		builder.append(n.data);
		while (n.next != null) {
			builder.append(n.next.data);
			n = n.next;
		}
		return builder.toString();
	}

}
