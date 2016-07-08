package uk.martinus.jcip.ch5;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Semaphores {

    private Semaphore printPermits;
    int nThreads;

    public Semaphores(int nThreads) {
        this.nThreads = nThreads;
        this.printPermits = new Semaphore(1);
    }

    public void go() {

        Runnable task = new Runnable() {

            @Override
            public void run() {
                long startTime = 0;
                long endTime = 0;
                try {
                    printPermits.acquire();
                    System.out.printf("Task started in thread %s\n", Thread
                            .currentThread().getName());
                    printPermits.release();
                    startTime = new Date().getTime();
                    Random r = new Random(System.nanoTime());
                    Thread.sleep(1000 + r.nextInt(1000));
                    endTime = new Date().getTime();
                    printPermits.acquire();
                    System.out.printf("Task ended in thread %s after %dms\n",
                            Thread.currentThread().getName(), endTime
                                    - startTime);
                    printPermits.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread(task, "worker" + Integer.toString(i));
            thread.start();
        }
    }

    public static void main(String[] args) {
        new Semaphores(10).go();
    }
}
