
package edu.umb.cs681.hw01;

import java.util.*;
import java.util.stream.Collectors;

public class Car {

    private String model, make;
    private int mileage, year;
    private Double price;
    private int dominationCount;

    static ArrayList<Car> carList = new ArrayList<>();


    public Car(String model, String make, int mileage, int year, Double price) {
        this.model = model;
        this.make = make;
        this.mileage = mileage;
        this.year = year;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getMileage() {
        return mileage;
    }

    public int getYear() {
        return year;
    }

    public Double getPrice() {
        return price;
    }

    public void setDominationCount(ArrayList<Car> carList) {
        int count = 0;
        for (Car car : carList) {
            if (!car.equals(this)) {
                if ((this.getYear() >= car.getYear()) && (this.getMileage() <= car.getMileage()) && (this.getPrice() <= car.getPrice())) {
                    if ((this.getYear() > car.getYear()) || (this.getMileage() < car.getMileage()) || (this.getPrice() < car.getPrice())) {
                        count++;
                    }
                }
            }
        }
        this.dominationCount = count;
    }

    public int getDominationCount() {
        return this.dominationCount;
    }

    static void setUpCarList(){
        carList.add(new Car("ABC","AAA",17,2019,25900.7));
        carList.add(new Car("DEF","BBB",12,2003,20000.9));
        carList.add(new Car("GHI","CCC",23,2021,23000.5));
        carList.add(new Car("JKL","DDD",19,2022,31000.2));
        carList.add(new Car("MNO","EEE",18,2004,15700.0));
        carList.add(new Car("PQR","FFF",13,2008,19600.5));
        carList.add(new Car("STU","GGG",19,2009,17300.0));
        carList.add(new Car("VWX","HHH",21,2021,29400.3));
        carList.add(new Car("YZ","III",15,2007,20500.2));


    }


    public static void main(String[] args){

        //Setting up the list of cars
        setUpCarList();
        //Sorting based on Price Comparator
        List<Car> sortedCars_Price =
                carList.stream()
                        .sorted( Comparator.comparing((Car car)-> car.getPrice()) )
                        .collect( Collectors.toList() );


        //Sorting based on Mileage Comparator
        List<Car> sortedCars_Mileage =
                carList.stream()
                        .sorted( Comparator.comparing((Car car)-> car.getMileage()) )
                        .collect( Collectors.toList() );

        //Sorting based on Year Comparator
        List<Car> sortedCars_Year =
                carList.stream()
                        .sorted( Comparator.comparing((Car car)-> car.getYear()) )
                        .collect( Collectors.toList() );

        //Sorting based on Pareto(Domination) Comparator
        List<Car> sortedCars_Domination =
                carList.stream()
                        .peek(car -> car.setDominationCount(carList))
                        .sorted( Comparator.comparing((Car car)-> car.getDominationCount()) )
                        .collect( Collectors.toList() );

        // ------------------------ START OF PRICE COMPARATOR-------------------------
        //Separate Cars to the “HIGH” and “LOW” groups with a certain price threshold.
        Double priceThreshold = 20000.0;
        Map<Boolean, List<Car>> priceGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getPrice() >= priceThreshold));
        List<Car> lowPriceCars = priceGroups.get(false);
        List<Car> highPriceCars = priceGroups.get(true);
        // calculate statistics for Low group
        DoubleSummaryStatistics lowPriceSummary = lowPriceCars.stream()
                .mapToDouble(Car::getPrice)
                .summaryStatistics();
        // calculate statistics for High group
        DoubleSummaryStatistics highPriceSummary = highPriceCars.stream()
                .mapToDouble(Car::getPrice)
                .summaryStatistics();

        // print information for the low-priced cars

        System.out.println("Price-based sorting:");
        sortedCars_Price.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Price: " + car.getPrice());
        });
        System.out.println("Low-priced cars:");
        lowPriceCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Price: " + car.getPrice());
        });
        System.out.println("High-priced cars:");
        highPriceCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Price: " + car.getPrice());
        });
        // print statistics for Price group
        System.out.println("Low price group:");
        System.out.println("  Number of cars: " + lowPriceCars.size());
        System.out.println("  Average price: " + lowPriceSummary.getAverage());
        System.out.println("  Highest price: " + lowPriceSummary.getMax());
        System.out.println("  Lowest price: " + lowPriceSummary.getMin());
        System.out.println("High price group:");
        System.out.println("  Number of cars: " + highPriceCars.size());
        System.out.println("  Average price: " + highPriceSummary.getAverage());
        System.out.println("  Highest price: " + highPriceSummary.getMax());
        System.out.println("  Lowest price: " + highPriceSummary.getMin());

        // ------------------------ END OF PRICE COMPARATOR----------------------------

        // ------------------------ START OF MILEAGE COMPARATOR-------------------------
        //Separate Cars to the “HIGH” and “LOW” groups with a certain Mileage threshold.
        int mileageThreshold = 15;
        Map<Boolean, List<Car>> mileageGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getMileage() >= mileageThreshold));
        List<Car> lowMileageCars = mileageGroups.get(false);
        List<Car> highMileageCars = mileageGroups.get(true);

        // calculate statistics for Low group
        IntSummaryStatistics lowMileageSummary = lowMileageCars.stream()
                .mapToInt(Car::getMileage)
                .summaryStatistics();
        // calculate statistics for High group
        IntSummaryStatistics highMileageSummary = highMileageCars.stream()
                .mapToInt(Car::getMileage)
                .summaryStatistics();

        //Print Mileage sorting
        System.out.println("Mileage-based sorting:");
        sortedCars_Mileage.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Mileage: " + car.getMileage());
        });

        // print statistics for Price group

        System.out.println("Low-Mileage cars:");
        lowMileageCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Mileage: " + car.getMileage());
        });
        // print information for the low-Mileage cars
        System.out.println("Low-Mileage group:");
        System.out.println("  Number of cars: " + lowMileageCars.size());
        System.out.println("  Average Mileage: " + lowMileageSummary.getAverage());
        System.out.println("  Highest Mileage: " + lowMileageSummary.getMax());
        System.out.println("  Lowest Mileage: " + lowMileageSummary.getMin());

        System.out.println("High-Mileage cars:");
        highMileageCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Mileage: " + car.getMileage());
        });
        // print information for the High-Mileage cars
        System.out.println("High Mileage group:");
        System.out.println("  Number of cars: " + highMileageCars.size());
        System.out.println("  Average Mileage: " + highMileageSummary.getAverage());
        System.out.println("  Highest Mileage: " + highMileageSummary.getMax());
        System.out.println("  Lowest Mileage: " + highMileageSummary.getMin());


        // ------------------------ END OF MILEAGE COMPARATOR----------------------------

        // ------------------------ START OF YEAR COMPARATOR-------------------------
        //Separate Cars to the “HIGH” and “LOW” groups with a certain Mileage threshold.
        int yearThreshold = 2009;
        Map<Boolean, List<Car>> yearGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getYear() >= yearThreshold));
        List<Car> lowYearCars = yearGroups.get(false);
        List<Car> highYearCars = yearGroups.get(true);
        // calculate statistics for Low group
        IntSummaryStatistics lowYearSummary = lowYearCars.stream()
                .mapToInt(Car::getYear)
                .summaryStatistics();
        // calculate statistics for High group
        IntSummaryStatistics highYearSummary = highYearCars.stream()
                .mapToInt(Car::getYear)
                .summaryStatistics();

        // Print Year based sorting
        System.out.println("Year-based sorting:");
        sortedCars_Year.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Year: " + car.getYear());
        });

        // print statistics for Price group

        System.out.println("Old cars:");
        lowYearCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Year: " + car.getYear());
        });
        // print information for the low-Year cars
        System.out.println("Old years group:");
        System.out.println("  Number of cars: " + lowYearCars.size());
        System.out.println("  Average Year: " + lowYearSummary.getAverage());
        System.out.println("  Highest Year: " + lowYearSummary.getMax());
        System.out.println("  Lowest Year: " + lowYearSummary.getMin());

        System.out.println("New cars:");
        highYearCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Year: " + car.getYear());
        });
        // print information for the High-Year cars
        System.out.println("New Year group:");
        System.out.println("  Number of cars: " + highYearCars.size());
        System.out.println("  Average Year: " + highYearSummary.getAverage());
        System.out.println("  Highest Year: " + highYearSummary.getMax());
        System.out.println("  Lowest Year: " + highYearSummary.getMin());

        // ------------------------ END OF YEAR COMPARATOR----------------------------

        // ------------------------ START OF DOMINATION COMPARATOR-------------------------
        //Separate Cars to the “HIGH” and “LOW” groups with a certain Mileage threshold.
        int dominationThreshold = 1;
        Map<Boolean, List<Car>> DGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getDominationCount() >= dominationThreshold));
        List<Car> lowDCars = DGroups.get(false);
        List<Car> highDCars = DGroups.get(true);
        // calculate statistics for Low group
        IntSummaryStatistics lowDSummary = lowDCars.stream()
                .mapToInt(Car::getDominationCount)
                .summaryStatistics();
        // calculate statistics for High group
        IntSummaryStatistics highDSummary = highDCars.stream()
                .mapToInt(Car::getDominationCount)
                .summaryStatistics();

        // Print Year based sorting
        System.out.println("Domination-Count-based sorting:");
        sortedCars_Domination.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Domination-Count: " + car.getDominationCount());
        });

        // print statistics for Domination-Count group

        System.out.println("Low Domination-Count cars:");
        lowDCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Domination-Count: " + car.getDominationCount());
        });
        // print information for the low-Domination-Count cars
        System.out.println("Low Domination-Count group:");
        System.out.println("  Number of cars: " + lowDCars.size());
        System.out.println("  Average Domination Count: " + lowDSummary.getAverage());
        System.out.println("  Highest Domination Count: " + lowDSummary.getMax());
        System.out.println("  Lowest Domination Count: " + lowDSummary.getMin());

        System.out.println("High Domination-Count cars:");
        highDCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Domination-Count: " + car.getDominationCount());
        });
        // print information for the High-Domination-Count cars
        System.out.println("High Domination-Count group:");
        System.out.println("  Number of cars: " + highDCars.size());
        System.out.println("  Average Domination Count: " + highDSummary.getAverage());
        System.out.println("  Highest Domination Count: " + highDSummary.getMax());
        System.out.println("  Lowest Domination Count: " + highDSummary.getMin());

        // ------------------------ END OF DOMINATION COMPARATOR-------------------------

    }

}




