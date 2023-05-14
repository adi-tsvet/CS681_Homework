package edu.umb.cs681.hw15;

import edu.umb.cs681.hw15.StockApp.StockQuoteObservable;

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
            System.out.println("\nTerminated Data Handler Thread#"+ Thread.currentThread().threadId());
        }
    }

    public void setDone() {
        done = true;
    }
}