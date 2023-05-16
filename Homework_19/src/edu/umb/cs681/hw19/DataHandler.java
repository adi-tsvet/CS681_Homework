package edu.umb.cs681.hw19;

import edu.umb.cs681.hw19.StockApp.StockQuoteObservable;

import java.util.Random;

public class DataHandler implements Runnable {

    private final StockQuoteObservable stockObservable;
    private final String ticker;
    private volatile boolean done = false;

    public DataHandler(StockQuoteObservable stockObservable, String ticker) {
        this.stockObservable = stockObservable;
        this.ticker = ticker;
    }

    @Override
    public void run() {
        while (!done) {
            Random random = new Random();
            double quote = random.nextDouble() * 100;
            stockObservable.changeQuote(ticker, quote);
        }
        if(done){
            System.out.println("\nStopped Data Handler Thread#"+ Thread.currentThread().threadId());
        }
        try {
            Thread.sleep(1000);
        }catch(InterruptedException e) {
            System.out.println("Thread #"+Thread.currentThread().getId() + " Interrupted");
        }
    }

    public void setDone() {
        done = true;
    }
}