package uk.martinus.jcip.ch5;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Latch {

    private CountDownLatch startGate;
    private CountDownLatch endGate;
    int nThreads;

    public Latch(int nThreads) {
        this.nThreads = nThreads;
        startGate = new CountDownLatch(1);
        endGate = new CountDownLatch(nThreads);
    }

    public void go() {

        Runnable task = new Runnable() {

            @Override
            public void run() {
                String msg = String.format("Task started in thread %s\n",
                        Thread.currentThread().getName());
                print(msg);
                long startTime = 0;
                long endTime = 0;
                try {
                    // Wait until the startGate latch has counted down to zero
                    startGate.await();
                    // Go!
                    try {
                        startTime = new Date().getTime();
                        Random r = new Random(System.nanoTime());
                        Thread.sleep(1000 + r.nextInt(1000));
                        endTime = new Date().getTime();
                    } finally {
                        // Decrement the endGate latch by one, once all threads
                        // have done this the latch will reach zero
                        endGate.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msg = String.format("Task ended in thread %s after %dms\n",
                        Thread.currentThread().getName(), endTime - startTime);
                print(msg);
            }

        };

        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread(task, "worker" + Integer.toString(i));
            thread.start();
        }
        // This has only been initialized to 1, so as soon as this is
        // decremented to zero, all waiting threads will go
        startGate.countDown();
        try {
            // Now wait until all threads have decremented the endGate to zero
            // indicating that all threads have finished
            endGate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("All workers now finished\n");
    }

    private synchronized void print(String msg) {
        System.out.printf("%s", msg);
    }

    public static void main(String[] args) {
        new Latch(10).go();
    }
}
