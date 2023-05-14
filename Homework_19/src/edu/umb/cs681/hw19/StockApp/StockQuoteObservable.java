package edu.umb.cs681.hw19.StockApp;



import edu.umb.cs681.hw19.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class StockQuoteObservable extends Observable<StockEvent> {

    private Map<String, Double> stockList = new HashMap<>();
    private ReentrantLock lockTQ = new ReentrantLock();
    public void changeQuote(String ticker , Double quote){
        lockTQ.lock();
        try {
            stockList.put(ticker, quote);
        } finally {
            lockTQ.unlock();
        }
        notifyObservers(new StockEvent(ticker,quote));
    }
}
