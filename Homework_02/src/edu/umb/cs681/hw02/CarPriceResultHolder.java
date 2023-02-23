package edu.umb.cs681.hw02;

public class CarPriceResultHolder {
    private int numCarExamined;
    private double average;

    public CarPriceResultHolder() {
        numCarExamined = 0;
        average = 0.0;
    }

    public void addPrice(double price) {
        average = (average * numCarExamined + price) / (numCarExamined + 1);
        numCarExamined++;
    }

    public int getNumCarExamined() {
        return numCarExamined;
    }

    public double getAverage() {
        return average;
    }
}
