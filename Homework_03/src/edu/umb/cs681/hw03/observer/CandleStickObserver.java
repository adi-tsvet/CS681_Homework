package edu.umb.cs681.hw03.observer;


import edu.umb.cs681.hw03.DJIAApp.MthSummary;
import edu.umb.cs681.hw03.DJIAApp.WkSummary;
import edu.umb.cs681.hw03.Observable;
import edu.umb.cs681.hw03.Observer;

public class CandleStickObserver implements Observer<Object> {

    @Override
    public void update(Observable<Object> sender, Object event) {
        if (event instanceof WkSummary) {
            handleWeeklySummary((WkSummary) event);
        } else if (event instanceof MthSummary) {
            handleMonthlySummary((MthSummary) event);
        } else {
            throw new IllegalArgumentException("Invalid event type");
        }
    }

    private void handleWeeklySummary(WkSummary wkSummary) {
        System.out.println("******WEEKLY SUMMARY******");
        System.out.println("Open: " + wkSummary.getOpen());
        System.out.println("Close: " + wkSummary.getClose());
        System.out.println("High: " + wkSummary.getHigh());
        System.out.println("Low: " + wkSummary.getLow());
    }

    private void handleMonthlySummary(MthSummary mthSummary) {
        System.out.println("************MONTHLY SUMMARY************");
        System.out.println("Open: " + mthSummary.getOpen());
        System.out.println("Close: " + mthSummary.getClose());
        System.out.println("High: " + mthSummary.getHigh());
        System.out.println("Low: " + mthSummary.getLow());
    }
}

