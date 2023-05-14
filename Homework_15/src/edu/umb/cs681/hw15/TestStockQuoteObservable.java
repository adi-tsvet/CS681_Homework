package edu.umb.cs681.hw15;


import edu.umb.cs681.hw15.StockApp.StockQuoteObservable;
import edu.umb.cs681.hw15.observer.D3Observer;
import edu.umb.cs681.hw15.observer.LineChartObserver;
import edu.umb.cs681.hw15.observer.TableObserver;


public class TestStockQuoteObservable {

    public static void main(String[] args) throws InterruptedException {
        StockQuoteObservable stockObservable = new StockQuoteObservable();
        D3Observer d3Observer = new D3Observer();
        LineChartObserver lcObserver = new LineChartObserver();
        TableObserver tObserver = new TableObserver();
        stockObservable.addObserver(tObserver);
        stockObservable.addObserver(d3Observer);
        stockObservable.addObserver(lcObserver);

        DataHandler[] handlers = new DataHandler[10];

        // Start data handler threads
        for (int i = 0; i < handlers.length; i++) {
            String ticker = "TICKER_" + i;
            handlers[i] = new DataHandler(stockObservable, ticker);
            new Thread(handlers[i]).start();
        }

        for (DataHandler handler : handlers) {
            handler.setDone();
        }


    }
}
