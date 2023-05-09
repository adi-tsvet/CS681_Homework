package edu.umb.cs681.hw602;


import edu.umb.cs681.hw08.RunnablePrimeFactorizer;

import java.util.concurrent.locks.ReentrantLock;

public class RunnableCancellablePrimeFactorizer extends RunnablePrimeFactorizer {

    public boolean done = false;
    public ReentrantLock lock = new ReentrantLock();

    public RunnableCancellablePrimeFactorizer(long dividend, long from, long to) {
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
        long divisor = from;
            while (dividend != 1 && divisor <= to) {
                try {
                    lock.lock();
                    if(done){
                        System.out.println("Stopped generating prime Factors.");
                        this.factors.clear();
                        break;
                    }
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
                } finally {
                    lock.unlock();
                }
            }
    }

    public static void main(String[] args) {
        RunnableCancellablePrimeFactorizer factorizer = new RunnableCancellablePrimeFactorizer(200560490130L, 2, 100000);
        Thread thread = new Thread(factorizer);
        thread.start();
        factorizer.setDone();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Factors found: " + factorizer.getPrimeFactors());
    }
}
