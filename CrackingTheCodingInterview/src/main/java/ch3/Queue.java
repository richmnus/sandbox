package ch3;

import ch2.Node;

public class Queue {

	Node head;
	Node tail;

	void enqueue(int item) {
		Node n = new Node(item);
		if (head == null) {
			// Means queue is empty
			// Always add a new item to the tail of the queue
			tail = n;
			// Only one item so both point to the same node
			head = tail;
		} else {
			// Queue is not empty
			tail.next = n;
			tail = n;
		}
	}

	int dequeue() {
		if (head != null) {
			// Always remove a node from the head of the queue
			Node n = head;
			head = head.next;
			return n.data;
		}
		return -1;
	}

	boolean isEmpty() {
		return (head == null);
	}

	int min() {

		return 0;
	}

}
