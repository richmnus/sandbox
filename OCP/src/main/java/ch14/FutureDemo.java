package ch14;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

class FutureTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int value = ThreadLocalRandom.current().nextInt(1, 11);
        return value;
    }

}

public class FutureDemo {

    public static void main(String[] args) {
        Callable<Integer>[] tasks = new FutureTask[10];
        ExecutorService ex = Executors.newFixedThreadPool(tasks.length);
        Future<Integer>[] futureResults = new Future[10];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new FutureTask();
            futureResults[i] = ex.submit(tasks[i]);
        }
        System.out.println("Started all workers");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Future<Integer> i : futureResults) {
            try {
                System.out.println(i.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " exiting");
        System.out.println(Thread.currentThread().getName() + " exiting again");
        // Some random comment
        // Another random comment
    }
}
