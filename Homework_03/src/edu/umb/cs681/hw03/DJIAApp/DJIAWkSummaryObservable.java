package edu.umb.cs681.hw03.DJIAApp;

import edu.umb.cs681.hw03.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DJIAWkSummaryObservable extends Observable<WkSummary> {

    private List<DSummary> dailySummaries = new ArrayList<>();
    private List<WkSummary> weeklySummaries = new ArrayList<>();
    private WkSummary weeklySummary;
    public void addSummary(DSummary dSummary) {
        dailySummaries.add(dSummary);
        if (dailySummaries.size() == 5) {
            // Calculate weekly summary
            double open = dailySummaries.get(0).getOpen();
            double close = dailySummaries.get(4).getClose();
            double high = dailySummaries.stream().mapToDouble(DSummary::getHigh).max().orElse(0.0);
            double low = dailySummaries.stream().mapToDouble(DSummary::getLow).min().orElse(0.0);
            weeklySummary = new WkSummary(open, close, high, low);
            notifyObservers(weeklySummary);
            //Clear the collection
            dailySummaries.clear();
            weeklySummaries.add(weeklySummary);
        }

    }

}
