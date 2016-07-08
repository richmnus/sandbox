package ch11;

import java.util.Collection;
import java.util.List;

public class GenericsDemo {

    class Animal {
    }

    class Dog extends Animal {
    }

    class Yorkie extends Dog {
    }

    class Cat extends Animal {
    }

    private List<Object> objects;
    private List<Animal> animals;
    private List<Dog> dogs;
    private List<Yorkie> Yorkies;
    private List<Cat> cats;

    GenericsDemo() {
        objects.add(new Object());
        animals.add(new Animal());
        animals.add(new Dog());
        animals.add(new Yorkie());
        animals.add(new Cat());
        dogs.add(new Dog());
        dogs.add(new Yorkie());
        cats.add(new Cat());
        Yorkies.add(new Yorkie());
//        addObject(animals);
    }

    private void add(List<? extends Animal> animals) {
    }

    private void addObject(List<Object> list) {
        list.add(new Dog());
    }

    public static void main(String[] args) {
        new GenericsDemo();
    }

}
