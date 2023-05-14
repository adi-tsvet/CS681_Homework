package edu.umb.cs681.hw15.observer;


import edu.umb.cs681.hw15.Observable;
import edu.umb.cs681.hw15.Observer;
import edu.umb.cs681.hw15.StockApp.StockEvent;

public class LineChartObserver implements Observer<StockEvent> {

    @Override
    public void update(Observable<StockEvent> sender, StockEvent event) {
        System.out.print("\nLine Chart Update Function");
        System.out.print("\nModified Quote : " + event.getQuote());
        System.out.print("\nModified Ticker : " + event.getTicker());

    }
}
