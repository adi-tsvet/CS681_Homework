package edu.umb.cs681.hw03.observer;

import edu.umb.cs681.hw03.DJIAApp.MthSummary;
import edu.umb.cs681.hw03.Observable;
import edu.umb.cs681.hw03.Observer;

public class MthCandleStickObserver implements Observer<MthSummary> {
    @Override
    public void update(Observable<MthSummary> sender, MthSummary event) {
        System.out.println("******MONTHLY SUMMARY******");
        System.out.println("Open: " + (event).getOpen());
        System.out.println("Close: " + (event).getClose());
        System.out.println("High: " + (event).getHigh());
        System.out.println("Low: " + (event).getLow());
    }

}