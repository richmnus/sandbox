package uk.martinus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyConcreteClass extends MyAbstractClass {

    public MyConcreteClass() {
        System.out.println("At beginning of constructor");
    }

    // Static initializer block, Java guarantees these are executed in the order
    // they appear in the source code
    static {
        int i = 0;
        System.out.println("In static initializer block 1");
        List<String> list = new ArrayList<>();
    }

    // Initializer block, is automatically run at the BEGINNING of every
    // constructor
    {
        int i = 0;
        System.out.println("In initializer block 2");
    }

    public static <K, V> Map<K, V> newInstance() {
        return new HashMap<K, V>();
    }

    @Override
    public void protectedMethodB() {

    }

    public static void main(String args[]) {
        System.out.println("Beginning of main()");
        new MyConcreteClass();
    }

}
