
package edu.umb.cs681.hw02;

import java.util.*;

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

        double averagePrice = carList.stream()
                .map(car -> car.getPrice())
                .reduce(new CarPriceResultHolder(),
                        (result, price) -> {
                            result.addPrice(price);
                            return result;
                        },
                        (finalResult, intermediateResult) -> finalResult
                ).getAverage();

        System.out.println("The average price : " + averagePrice );






    }

    }




