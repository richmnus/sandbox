package ch14;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()
                        + " was interrupted");
                return;
            }
            System.out.println(Thread.currentThread().getName()
                    + " printed this");
        }
    }

}

public class ConcDemo {

    public static void main(String[] args) {
        Runnable[] tasks = new Runnable[100];
        ExecutorService ex = Executors.newFixedThreadPool(tasks.length);
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new Task();
            ex.execute(tasks[i]);
        }
        System.out.println("Started all workers");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ex.shutdownNow();
        System.out.println(Thread.currentThread().getName() + " exiting");
    }
}
