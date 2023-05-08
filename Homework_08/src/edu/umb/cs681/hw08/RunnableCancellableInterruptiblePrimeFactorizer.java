package edu.umb.cs681.hw08;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellableInterruptiblePrimeFactorizer extends RunnableCancellablePrimeFactorizer {

    private volatile boolean done = false;
    private ReentrantLock lock = new ReentrantLock();

    public RunnableCancellableInterruptiblePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public void setDone() {
        lock.lock();
        try {
            done = true;
        } finally {
            lock.unlock();
        }
    }

    public void generatePrimeFactors() {
        Thread currentThread = Thread.currentThread();
        while (!done) {
            lock.lock();
            try {
                if (currentThread.isInterrupted()) {
                    System.out.println("Thread interrupted.");
                    return;
                }
                long divisor = from;
                while (dividend != 1 && divisor <= to && !done) {
                    if (divisor > 2 && isEven(divisor)) {
                        divisor++;
                        continue;
                    }
                    if (dividend % divisor == 0) {
                        factors.add(divisor);
                        dividend /= divisor;
                    } else {
                        if (divisor == 2) {
                            divisor++;
                        } else {
                            divisor += 2;
                        }
                    }
                }
            } finally {
                lock.unlock();
            }
        }
        System.out.println("Task completed.");
    }

    public static void main(String[] args) {
        RunnableCancellableInterruptiblePrimeFactorizer factorizer = new RunnableCancellableInterruptiblePrimeFactorizer(200560490130L, 2, 100000);
        Thread thread = new Thread(factorizer);
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        factorizer.setDone();
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Factors found: " + factorizer.getPrimeFactors());
    }
}
