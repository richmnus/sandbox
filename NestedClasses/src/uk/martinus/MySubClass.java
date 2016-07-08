package uk.martinus;

import java.util.ArrayList;
import java.util.List;

public class MySubClass {

    public MySubClass() {
        List<Integer> list = new ArrayList<>();
        Integer myInt = 5;
        printList(list, myInt);
    }

    public static void printList(List<?> list, Object object) {
        for (Object elem : list)
            System.out.println(elem + " ");
        System.out.println();
    }

}
