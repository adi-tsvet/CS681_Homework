package edu.umb.cs681.hw04;

/*
 CRIM     per capita crime rate by town
 ZN       proportion of residential land zoned for lots over 25,000 sq.ft.
 INDUS    proportion of non-retail business acres per town
 CHAS     Charles River dummy variable (= 1 if tract bounds river; 0 otherwise)
 NOX      nitric oxides concentration (parts per 10 million)
 RM       average number of rooms per dwelling
 AGE      proportion of owner-occupied units built prior to 1940
 DIS      weighted distances to five Boston employment centres
 RAD      index of accessibility to radial highways
 TAX      full-value property-tax rate per $10,000
 PTRATIO  pupil-teacher ratio by town
 B        1000(Bk - 0.63)^2 where Bk is the proportion of blacks by town
 LSTAT    % lower status of the population
 MEDV     Median value of owner-occupied homes in $1000's*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BostonData {

    private double crim;
    private double zn;
    private double indus;
    private Boolean chas;
    private double nox;
    private double rm;
    private double age;
    private double dis;
    private int rad;
    private int tax;
    private double ptratio;
    private double b;
    private double lstat;
    private double medv;

    public BostonData(double crim, double zn, double indus, Boolean chas, double nox, double rm, double age, double dis, int rad, int tax, double ptratio, double b, double lstat, double medv) {
        this.crim = crim;
        this.zn = zn;
        this.indus = indus;
        this.chas = chas;
        this.nox = nox;
        this.rm = rm;
        this.age = age;
        this.dis = dis;
        this.rad = rad;
        this.tax = tax;
        this.ptratio = ptratio;
        this.b = b;
        this.lstat = lstat;
        this.medv = medv;
    }

    public double getCrim() {
        return crim;
    }

    public double getZn() {
        return zn;
    }

    public double getIndus() {
        return indus;
    }

    public double getNox() {
        return nox;
    }

    public double getRm() {
        return rm;
    }

    public double getAge() {
        return age;
    }

    public double getDis() {
        return dis;
    }

    public int getRad() {
        return rad;
    }

    public int getTax() {
        return tax;
    }

    public double getPtratio() {
        return ptratio;
    }

    public double getB() {
        return b;
    }

    public double getLstat() {
        return lstat;
    }

    public double getMedv() {
        return medv;
    }

    public boolean isChas(){
        return chas;
    }


}
