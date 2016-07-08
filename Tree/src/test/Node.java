package test;

import java.util.ArrayList;
import java.util.List;

public class Node {

    // The node's parent is automatically set, the root's parent is null
    private Node parent = null;
    // Assumes that a child node isn't entered more than once in the list
    private List<Node> children;
    private String name;

    public Node(String name) {
        this.name = name;
        children = new ArrayList<Node>();
    }

    public void addChild(Node node) {
        children.add(node);
        node.setParent(this);
    }

    public boolean isRoot() {
        // A node with a null parent is a root
        return (parent==null)? true: false;
    }

    public String toString() {
        String result = String.format("%s{", this.name);
        for (Node n : children) {
            result += n.toString();
        }
        if (hasSiblings()) {
            result += String.format("}, ");
        } else {
            result += String.format("}");
        }
        return result;
    }

    public void setParent(Node node) {
        parent = node;
    }

    public Node getParent(Node node) {
        return parent;
    }
    
    public List<Node> getChildren() {
        return children;
    }
    
    public boolean hasSiblings() {
        // Is this the root node?
        if (isRoot()) {
            // Then it has no siblings
            return false;
        }
        // If this node's parent has only one child (i.e. this one)
        if (parent.getChildren().size()==1) {
            // Then this node has no siblings
            return false;
        }
        // Otherwise this node has siblings
        return true;
    }
    
    public boolean hasChildren() {
        return !children.isEmpty();
    }
    
}
