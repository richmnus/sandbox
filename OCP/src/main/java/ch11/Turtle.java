package ch11;

import java.util.LinkedHashSet;

public class Turtle {

    int size;

    public Turtle(int s) {
        size = s;
    }

    @Override
    public boolean equals(Object o) {
        return (this.size == ((Turtle) o).size);
    }

    @Override
    public int hashCode() {
        return size / 5;
    }

    public static void main(String[] args) {
        LinkedHashSet<Turtle> t = new LinkedHashSet<Turtle>();
        t.add(new Turtle(1));
        t.add(new Turtle(2));
        t.add(new Turtle(1));
        System.out.println(t.size());
    }

}
