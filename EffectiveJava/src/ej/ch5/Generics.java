package ej.ch5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Generics {

    // Returning a List of type String - this is fine
    public static List<String> getListString() {
        List<String> myList = new ArrayList<>();
        return myList;
    }

    // But if you parameterize it, the compiler wants you to do as below
    // The <T> tells the compiler that 'T' is a parameterized type, otherwise
    // the compiler might think 'T' refers to a class or interface that is
    // missing
    public static <T> List<T> getListType(Set<T> input) {
        List<T> myList = new ArrayList<>();
        return myList;
    }

    // Similar to the case above, in order to use parameterized types 'T' and
    // 'S' they need to be declared before the return type
    public static <T, S> void setList(List<T> input1, Set<S> input2) {
        for (T t : input1) {
            // Do something
        }
    }

    // Here the parameterized type declaration before the return type also
    // includes a specification for the type parameter
    interface EventStore {
        <T extends List<?>> Iterator<T> load(String domainId, String aggregateId);
    }

}
