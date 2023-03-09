package edu.umb.cs681.hw05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BostonHousing {

    private List<BostonData> data;

    // Constructor to parse the CSV file
    public BostonHousing(String FILE_PATH) {
        this.data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double crim = Double.parseDouble(parts[0]);
                double zn = Double.parseDouble(parts[1]);
                double indus = Double.parseDouble(parts[2]);
                Boolean chas = parts[3].equals("\"1\"") ? true : false;
                double nox = Double.parseDouble(parts[4]);
                double rm = Double.parseDouble(parts[5]);
                double age = Double.parseDouble(parts[6]);
                double dis = Double.parseDouble(parts[7]);
                int rad = Integer.parseInt(parts[8]);
                int tax = Integer.parseInt(parts[9]);
                double ptratio = Double.parseDouble(parts[10]);
                double b = Double.parseDouble(parts[11]);
                double lstat = Double.parseDouble(parts[12]);
                double medv = Double.parseDouble(parts[13]);
                BostonData bostonData = new BostonData(crim, zn, indus, chas, nox, rm, age, dis, rad, tax, ptratio, b, lstat, medv);
                data.add(bostonData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    * Data processing #1
    * 1. Identify the areas/blocks next to Charles river.
    * 2. Compute the highest, lowest and average price of those houses.
    */
    public Runnable processData1() {
        return () -> {
            List<BostonData> charlesRiverBlocks = data.stream()
                    .filter(bostonData -> bostonData.isChas())
                    .collect(Collectors.toList());

            double highestPrice = charlesRiverBlocks.stream()
                    .mapToDouble(BostonData::getMedv)
                    .max()
                    .orElse(0);

            double lowestPrice = charlesRiverBlocks.stream()
                    .mapToDouble(BostonData::getMedv)
                    .min()
                    .orElse(0);

            double averagePrice = charlesRiverBlocks.stream()
                    .mapToDouble(BostonData::getMedv)
                    .average()
                    .orElse(0);

            System.out.println("Data Processing #1:");
            System.out.println("Areas/blocks next to Charles River: " + charlesRiverBlocks.size());
            System.out.println("Highest price: " + highestPrice);
            System.out.println("Lowest price: " + lowestPrice);
            System.out.println("Average price: " + averagePrice);
        };
    }

    /*
    * Data processing #2
    * Identify the areas/blocks within the top (lowest) 10% of “low” crime rate
    * the top (lowest) 10% of pupil-teacher ratio.
    * Compute the max, min and average of:
    *   • Price
    *   • NOX concentration
    *   • # of rooms
    */
    public Runnable processData2() {
        return () -> {
            // Find the top 10% of areas with low crime rate
            List<BostonData> lowCrimeAreas = data.stream()
                    .sorted(Comparator.comparing(BostonData::getCrim))
                    .limit(data.size() / 10)
                    .collect(Collectors.toList());

            // Find the top 10% of areas with low pupil-teacher ratio
            List<BostonData> lowPTRatioAreas = data.stream()
                    .sorted(Comparator.comparing(BostonData::getPtratio))
                    .limit(data.size() / 10)
                    .collect(Collectors.toList());

            // Find the areas that are common to both lists
            List<BostonData> commonAreas = lowCrimeAreas.stream()
                    .filter(lowPTRatioAreas::contains)
                    .collect(Collectors.toList());

            // Compute the max, min and average of price, NOX concentration and number of rooms
            double maxPrice = commonAreas.stream().mapToDouble(BostonData::getMedv).max().orElse(0);
            double minPrice = commonAreas.stream().mapToDouble(BostonData::getMedv).min().orElse(0);
            double avgPrice = commonAreas.stream().mapToDouble(BostonData::getMedv).average().orElse(0);

            double maxNOX = commonAreas.stream().mapToDouble(BostonData::getNox).max().orElse(0);
            double minNOX = commonAreas.stream().mapToDouble(BostonData::getNox).min().orElse(0);
            double avgNOX = commonAreas.stream().mapToDouble(BostonData::getNox).average().orElse(0);

            double maxRooms = commonAreas.stream().mapToDouble(BostonData::getRm).max().orElse(0);
            double minRooms = commonAreas.stream().mapToDouble(BostonData::getRm).min().orElse(0);
            double avgRooms = commonAreas.stream().mapToDouble(BostonData::getRm).average().orElse(0);

            System.out.println("\n Data Processing #2:");
            System.out.println("Areas/blocks with top (lowest) 10% of low crime rate and low pupil-teacher ratio: " + commonAreas.size());
            commonAreas.forEach(bostonData -> System.out.println("Block " + (data.indexOf(bostonData) + 1)
                    + ": Crime rate = " + bostonData.getCrim()
                    + " || PTRatio = " + bostonData.getPtratio()));
            System.out.println("Max price: " + maxPrice);
            System.out.println("Min price: " + minPrice);
            System.out.println("Average price: " + avgPrice);
            System.out.println("Max NOX concentration: " + maxNOX);
            System.out.println("Min NOX concentration: " + minNOX);
            System.out.println("Average NOX concentration: " + avgNOX);
            System.out.println("Max number of rooms: " + maxRooms);
            System.out.println("Min number of rooms: " + minRooms);
            System.out.println("Average number of rooms: " + avgRooms);
        };
    }

    /*
    * Data processing #3:
    * Average house prices by distance to employment centers
    * In this scenario, we want to explore including the average, maximum, and minimum house
    * prices for each distance range.
    * Defined distance ranges in miles as {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};*/
    public Runnable processData3() {
        return () -> {
            // Define distance ranges in miles from 1 to 10.
            int[] distanceRanges = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

            // Group data points by distance range and calculate average house price for each range
            Map<Integer, Double> avgHousePricesByDistance = new HashMap<>();
            Map<Integer, Double> maxHousePricesByDistance = new HashMap<>();
            Map<Integer, Double> minHousePricesByDistance = new HashMap<>();
            for (int range : distanceRanges) {
                final double maxDistance = range;
                final double minDistance = range - 1;

                double avgPrice = data.stream()
                        .filter(d -> d.getDis() >= minDistance && d.getDis() < maxDistance)
                        .mapToDouble(BostonData::getMedv)
                        .average()
                        .orElse(0.0);

                double maxPrice = data.stream()
                        .filter(d -> d.getDis() >= minDistance && d.getDis() < maxDistance)
                        .mapToDouble(BostonData::getMedv)
                        .max()
                        .orElse(0.0);

                double minPrice = data.stream()
                        .filter(d -> d.getDis() >= minDistance && d.getDis() < maxDistance)
                        .mapToDouble(BostonData::getMedv)
                        .min()
                        .orElse(0.0);

                avgHousePricesByDistance.put(range, avgPrice);
                maxHousePricesByDistance.put(range, maxPrice);
                minHousePricesByDistance.put(range, minPrice);
            }

            // Print results
            System.out.println("Data Processing #3:");
            System.out.println("Average house prices by distance to employment centers:");
            for (int range : distanceRanges) {
                System.out.println("Range : " + range);
                System.out.println("Average house price within " + range + " mile(s): " + avgHousePricesByDistance.get(range));
                System.out.println("Maximum house price within " + range + " mile(s): " + maxHousePricesByDistance.get(range));
                System.out.println("Minimum house price within " + range + " mile(s): " + minHousePricesByDistance.get(range));
            }
        };
    }

    /* Data Processing #4
    * Calculate the average age of houses for tax rate
    * Group data by age range and calculate average price and tax
    */
    public Runnable processData4(){
        return () -> {
            // Average age of houses for tax rate
            System.out.println("Average age of houses by tax rate:");
            data.stream()
                    .collect(Collectors.groupingBy(BostonData::getTax, Collectors.averagingDouble(BostonData::getAge)))
                    .forEach((tax, avgAge) -> System.out.printf("Tax Rate : %d || Average Age %.2f years\n",
                            tax, avgAge));


            // Group data by age range and calculate average Price and tax
            System.out.println("\n Average house price and tax by age range:");
            data.stream()
                    .collect(Collectors.groupingBy(house -> (int) (house.getAge() / 10)))
                    .forEach((ageRange, housesByAge) -> {
                        DoubleSummaryStatistics medvStats = housesByAge.stream()
                                .collect(Collectors.summarizingDouble(BostonData::getMedv));
                        DoubleSummaryStatistics taxStats = housesByAge.stream()
                                .collect(Collectors.summarizingDouble(BostonData::getTax));
                        System.out.printf("Age Range %d-%d years: Avg price: $%.2f || Avg tax: $%.2f\n",
                                ageRange * 10, ageRange * 10 + 9, medvStats.getAverage(), taxStats.getAverage());
                    });
        };
    }

    public static void main(String[] args) throws InterruptedException {
        BostonHousing bostonHousing = new BostonHousing("./bos-housing.csv");

        // Create four data processing processor 'Runnable functions'
        List<Runnable> processor = new ArrayList<>();
        processor.add(bostonHousing.processData1());
        System.out.println("Data Process 1 Added");
        processor.add(bostonHousing.processData2());
        System.out.println("Data Process 2 Added");
        processor.add(bostonHousing.processData3());
        System.out.println("Data Process 3 Added");
        processor.add(bostonHousing.processData4());
        System.out.println("Data Process 4 Added");

        // Create four threads and run the processor in parallel
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(processor.get(i));
            threads.add(thread);
            System.out.println("Thread #" + thread.threadId()+" for Data Process "+i+ " started !");
            thread.start();
        }

        for (int i = 0; i < 4; i++) {
            threads.get(i).join();
            System.out.println("Thread #" + threads.get(i).threadId()+" for Data Process "+i+ " finished !");
        }
    }
}

