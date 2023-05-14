package edu.umb.cs681.hw15.StockApp;


public record StockEvent(String ticker, Double quote) {

    public String getTicker() {
        return ticker;
    }

    public Double getQuote() {
        return quote;
    }
}

