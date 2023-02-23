package edu.umb.cs681.hw03.DJIAApp;

public class Summary {

    Double open,close,low,high;

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Summary(Double open, Double close, Double low, Double high) {
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
    }
}
