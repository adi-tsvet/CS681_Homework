
package edu.umb.cs681.hw01;

import java.util.*;
import java.util.stream.Collectors;

public class Car {

    private String model, make;
    private int mileage, year;
    private float price;
    private int dominationCount;

    static ArrayList<Car> carList = new ArrayList<>();


    public Car(String model, String make, int mileage, int year, float price) {
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

    public float getPrice() {
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
        carList.add(new Car("ABC","AAA",17,2019,25900.7f));
        carList.add(new Car("DEF","BBB",12,2003,20000.9f));
        carList.add(new Car("GHI","CCC",23,2021,23000.5f));
        carList.add(new Car("JKL","DDD",19,2022,31000.2f));
        carList.add(new Car("MNO","EEE",18,2004,15700.0f));
        carList.add(new Car("PQR","FFF",13,2008,19600.5f));
        carList.add(new Car("STU","GGG",19,2009,17300.0f));
        carList.add(new Car("VWX","HHH",21,2021,29400.3f));
        carList.add(new Car("YZ","III",15,2007,20500.2f));


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
        float priceThreshold = 20000.0f;
        Map<Boolean, List<Car>> priceGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getPrice() >= priceThreshold));
        List<Car> lowPriceCars = priceGroups.get(false);
        List<Car> highPriceCars = priceGroups.get(true);
        // calculate statistics for each group
        Optional<Float> lowMaxPrice = lowPriceCars.stream()
                .map(Car::getPrice)
                .max(Float::compare);

        Optional<Float> lowMinPrice = lowPriceCars.stream()
                .map(Car::getPrice)
                .min(Float::compare);
        OptionalDouble lowAvgPrice = lowPriceCars.stream()
                .mapToDouble(Car::getPrice)
                .average();
        Optional<Float> highMaxPrice = highPriceCars.stream()
                .map(Car::getPrice)
                .max(Float::compare);
        Optional<Float> highMinPrice = highPriceCars.stream()
                .map(Car::getPrice)
                .min(Float::compare);
        OptionalDouble highAvgPrice = highPriceCars.stream()
                .mapToDouble(Car::getPrice)
                .average();
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
        System.out.println("  Average price: " + lowAvgPrice.getAsDouble());
        System.out.println("  Highest price: " + lowMaxPrice.get());
        System.out.println("  Lowest price: " + lowMinPrice.get());
        System.out.println("High price group:");
        System.out.println("  Number of cars: " + highPriceCars.size());
        System.out.println("  Average price: " + highAvgPrice.getAsDouble());
        System.out.println("  Highest price: " + highMaxPrice.get());
        System.out.println("  Lowest price: " + highMinPrice.get());

        // ------------------------ END OF PRICE COMPARATOR----------------------------

        // ------------------------ START OF MILEAGE COMPARATOR-------------------------
        //Separate Cars to the “HIGH” and “LOW” groups with a certain Mileage threshold.
        int mileageThreshold = 15;
        Map<Boolean, List<Car>> mileageGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getMileage() >= mileageThreshold));
        List<Car> lowMileageCars = mileageGroups.get(false);
        List<Car> highMileageCars = mileageGroups.get(true);
        // calculate statistics for each group
        OptionalInt lowMaxMileage = lowMileageCars.stream()
                .mapToInt(Car::getMileage)
                .max();

        OptionalInt lowMinMileage = lowMileageCars.stream()
                .mapToInt(Car::getMileage)
                .min();
        OptionalDouble lowAvgMileage = lowMileageCars.stream()
                .mapToDouble(Car::getMileage)
                .average();
        OptionalInt highMaxMileage = highMileageCars.stream()
                .mapToInt(Car::getMileage)
                .max();
        OptionalInt highMinMileage = highMileageCars.stream()
                .mapToInt(Car::getMileage)
                .min();
        OptionalDouble highAvgMileage = highMileageCars.stream()
                .mapToDouble(Car::getMileage)
                .average();


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
        System.out.println("  Average Mileage: " + lowAvgMileage.getAsDouble());
        System.out.println("  Highest Mileage: " + lowMaxMileage.getAsInt());
        System.out.println("  Lowest Mileage: " + lowMinMileage.getAsInt());

        System.out.println("High-Mileage cars:");
        highMileageCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Mileage: " + car.getMileage());
        });
        // print information for the High-Mileage cars
        System.out.println("High Mileage group:");
        System.out.println("  Number of cars: " + highMileageCars.size());
        System.out.println("  Average Mileage: " + highAvgMileage.getAsDouble());
        System.out.println("  Highest Mileage: " + highMaxMileage.getAsInt());
        System.out.println("  Lowest Mileage: " + highMinMileage.getAsInt());


        // ------------------------ END OF MILEAGE COMPARATOR----------------------------

        // ------------------------ START OF YEAR COMPARATOR-------------------------
        //Separate Cars to the “HIGH” and “LOW” groups with a certain Mileage threshold.
        int yearThreshold = 2009;
        Map<Boolean, List<Car>> yearGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getYear() >= yearThreshold));
        List<Car> lowYearCars = yearGroups.get(false);
        List<Car> highYearCars = yearGroups.get(true);
        // calculate statistics for each group
        OptionalInt lowMaxYear = lowYearCars.stream()
                .mapToInt(Car::getYear)
                .max();

        OptionalInt lowMinYear = lowYearCars.stream()
                .mapToInt(Car::getYear)
                .min();
        OptionalDouble lowAvgYear = lowYearCars.stream()
                .mapToDouble(Car::getYear)
                .average();
        OptionalInt highMaxYear = highYearCars.stream()
                .mapToInt(Car::getYear)
                .max();
        OptionalInt highMinYear = highYearCars.stream()
                .mapToInt(Car::getYear)
                .min();
        OptionalDouble highAvgYear = highYearCars.stream()
                .mapToDouble(Car::getYear)
                .average();


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
        System.out.println("  Average Year: " + lowAvgYear.getAsDouble());
        System.out.println("  Highest Year: " + lowMaxYear.getAsInt());
        System.out.println("  Lowest Year: " + lowMinYear.getAsInt());

        System.out.println("New cars:");
        highYearCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Year: " + car.getYear());
        });
        // print information for the High-Year cars
        System.out.println("High Year group:");
        System.out.println("  Number of cars: " + highYearCars.size());
        System.out.println("  Average Year: " + highAvgYear.getAsDouble());
        System.out.println("  Highest Year: " + highMaxYear.getAsInt());
        System.out.println("  Lowest Year: " + highMinYear.getAsInt());

        // ------------------------ END OF YEAR COMPARATOR----------------------------

        // ------------------------ START OF DOMINATION COMPARATOR-------------------------
        //Separate Cars to the “HIGH” and “LOW” groups with a certain Mileage threshold.
        int dominationThreshold = 1;
        Map<Boolean, List<Car>> DGroups = Car.carList.stream()
                .collect(Collectors.partitioningBy(c -> c.getDominationCount() >= dominationThreshold));
        List<Car> lowDCars = DGroups.get(false);
        List<Car> highDCars = DGroups.get(true);
        // calculate statistics for each group
        OptionalInt lowMaxD = lowDCars.stream()
                .mapToInt(Car::getDominationCount)
                .max();

        OptionalInt lowMinD = lowDCars.stream()
                .mapToInt(Car::getDominationCount)
                .min();
        OptionalDouble lowAvgD = lowDCars.stream()
                .mapToDouble(Car::getDominationCount)
                .average();
        OptionalInt highMaxD = highDCars.stream()
                .mapToInt(Car::getDominationCount)
                .max();
        OptionalInt highMinD = highDCars.stream()
                .mapToInt(Car::getDominationCount)
                .min();
        OptionalDouble highAvgD = highDCars.stream()
                .mapToDouble(Car::getDominationCount)
                .average();


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
        System.out.println("  Average Year: " + lowAvgD.getAsDouble());
        System.out.println("  Highest Year: " + lowMaxD.getAsInt());
        System.out.println("  Lowest Year: " + lowMinD.getAsInt());

        System.out.println("High Domination-Count cars:");
        highDCars.forEach(car -> {
            System.out.println("Make: " + car.getMake() + " || Domination-Count: " + car.getDominationCount());
        });
        // print information for the High-Domination-Count cars
        System.out.println("High Domination-Count group:");
        System.out.println("  Number of cars: " + highDCars.size());
        System.out.println("  Average Year: " + highAvgD.getAsDouble());
        System.out.println("  Highest Year: " + highMaxD.getAsInt());
        System.out.println("  Lowest Year: " + highMinD.getAsInt());






    }

    }




