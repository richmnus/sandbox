package ch2;

import java.util.ArrayList;
import java.util.List;

public class Q22 {

    Node kthFromLast(Node head, int k) {
        List<Node> nodes = new ArrayList<>();
        Node n = head;
        while (n.next != null) {
            nodes.add(n);
            n = n.next;
        }
        // Add the last node
        nodes.add(n);
        Node kthNode = nodes.get(nodes.size() - k -1);
        return kthNode;
    }

}
