package edu.umb.cs681.hw08;


public class RunnableCancellableInterruptiblePrimeFactorizer extends RunnableCancellablePrimeFactorizer {

    public RunnableCancellableInterruptiblePrimeFactorizer(long dividend, long from, long to) {
        super(dividend, from, to);
    }

    public void generatePrimeFactors() {
            long divisor = from;
            while (dividend != 1 && divisor <= to) {
                try {
                    lock.lock();
                    if(done){
                        System.out.println("Stopped. Thread Interrupted");
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
                }
                finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(1000);
                }catch(InterruptedException e) {
                    System.out.println(e.toString());
                    continue;
                }
            }
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
