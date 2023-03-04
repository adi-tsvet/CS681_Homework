package edu.umb.cs681.hw03.observer;


import edu.umb.cs681.hw03.DJIAApp.WkSummary;
import edu.umb.cs681.hw03.Observable;
import edu.umb.cs681.hw03.Observer;

public class WkCandleStickObserver implements Observer<WkSummary> {
    @Override
    public void update(Observable<WkSummary> sender, WkSummary event) {
        System.out.println("******WEEKLY SUMMARY******");
        System.out.println("Open: " + (event).getOpen());
        System.out.println("Close: " + (event).getClose());
        System.out.println("High: " + (event).getHigh());
        System.out.println("Low: " + (event).getLow());
    }

}
