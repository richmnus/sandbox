package test;

public class Main {

    public static void main(String[] args) {
        Main start = new Main();
    }

    public Main() {
        Node root = new Node("Root");
        Node child1 = new Node("Child 1");
        Node child2 = new Node("Child 2");
        Node grandchild1 = new Node("Grandchild 1");
        root.addChild(child1);
        root.addChild(child2);
        child2.addChild(grandchild1);
        System.out.println("Printing tree...");
        System.out.println(root);
    }
    

}
