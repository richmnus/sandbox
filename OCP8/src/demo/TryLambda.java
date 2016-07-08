package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TryLambda {

    List<Person> people = new ArrayList<>();

    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

    }

    // interface CheckPerson {
    // boolean test(Person p);
    // }

    // static class CheckPersonByAge implements CheckPerson {
    // @Override
    // public boolean test(Person p) {
    // if (p.age > 21) {
    // return true;
    // }
    // return false;
    // }
    // }

    void printPerson(List<Person> people, Predicate<Person> predicate) {
        for (Person p : people) {
            if (predicate.test(p)) {
                System.out.println(p.name);
            }
        }
    }

    public static void main(String[] args) {
        TryLambda tryLambda = new TryLambda();
        TryLambda.Person person1 = new TryLambda.Person("John", 21);
        TryLambda.Person person2 = new TryLambda.Person("Jane", 25);
        TryLambda.Person person3 = new TryLambda.Person("Bob", 31);
        tryLambda.people.add(person1);
        tryLambda.people.add(person2);
        tryLambda.people.add(person3);
        // tryLambda.printPerson(tryLambda.people,
        // new TryLambda.CheckPersonByAge());
        tryLambda.printPerson(tryLambda.people, p -> p.age > 21);
        tryLambda.printPerson(tryLambda.people, p -> {
            return p.age >= 21;
        });
    }
}
