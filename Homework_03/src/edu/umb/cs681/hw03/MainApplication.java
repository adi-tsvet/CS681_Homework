package edu.umb.cs681.hw03;
import edu.umb.cs681.hw03.DJIAApp.*;
import edu.umb.cs681.hw03.observer.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainApplication {

    static Path path = Paths.get("./HistoricalPrices.csv");
    static WkCandleStickObserver wkCandleObserver = new WkCandleStickObserver();
    static MthCandleStickObserver MthCandleObserver = new MthCandleStickObserver();
    static DJIAWkSummaryObservable weeklyObservable = new DJIAWkSummaryObservable();
    static DJIAMthSummaryObservable monthlyObservable = new DJIAMthSummaryObservable();

    private static List<List<Double>> parseCSVFile(Path path) throws IOException {
        List<List<Double>> data = new ArrayList<>();
        try (Stream<String> lines = Files.lines(path)) {
            data = lines
                    .skip(1)
                    .map(line -> Arrays.asList(line.split(","))
                            .stream()
                            .skip(1)
                            .map(Double::parseDouble)
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
        }
        return data;
    }

    public static void main(String[] args) {

        weeklyObservable.addObserver(wkCandleObserver);
        monthlyObservable.addObserver(MthCandleObserver);
        try {
            List<List<Double>> csv = parseCSVFile(path);
            for (List<Double> row : csv) {
                double open = row.get(0);
                double high = row.get(1);
                double low = row.get(2);
                double close = row.get(3);
                DSummary dSummary = new DSummary(open, high, low, close);
                weeklyObservable.addSummary(dSummary);
                monthlyObservable.addSummary(dSummary);
            }

        } catch (IOException ex) {
            System.err.println("Error reading file: " + ex.getMessage());
        }
    }
}
