package edu.umb.cs681.hw03.DJIAApp;

import edu.umb.cs681.hw03.Observable;

import java.util.ArrayList;
import java.util.List;

public class DJIAMthSummaryObservable extends Observable<MthSummary> {

    private List<DSummary> dailySummaries = new ArrayList<>();
    private List<WkSummary> weeklySummaries = new ArrayList<>();
    private List<MthSummary> monthlySummaries = new ArrayList<>();
    private WkSummary weeklySummary;
    private MthSummary monthlySummary;
    public void addSummary(DSummary dSummary) {
        dailySummaries.add(dSummary);
        if (dailySummaries.size() == 5) {
            // Calculate weekly summary
            double open = dailySummaries.get(0).getOpen();
            double close = dailySummaries.get(4).getClose();
            double high = dailySummaries.stream().mapToDouble(DSummary::getHigh).max().orElse(0.0);
            double low = dailySummaries.stream().mapToDouble(DSummary::getLow).min().orElse(0.0);
            weeklySummary = new WkSummary(open, close, high, low);
            //Clear the collection
            dailySummaries.clear();
            weeklySummaries.add(weeklySummary);
        }

        // Calculate monthly summary
        if (!weeklySummaries.isEmpty() && weeklySummaries.size() == 4) {
            double open = weeklySummaries.get(0).getOpen();
            double close = weeklySummaries.get(weeklySummaries.size() - 1).getClose();
            double high = weeklySummaries.stream().mapToDouble(WkSummary::getHigh).max().orElse(0.0);
            double low = weeklySummaries.stream().mapToDouble(WkSummary::getLow).min().orElse(0.0);
            monthlySummary = new MthSummary(open, close, high, low);
            monthlySummaries.add(monthlySummary);
            notifyObservers(monthlySummary);
            // Clear the collection
            weeklySummaries.clear();
        }


    }

}