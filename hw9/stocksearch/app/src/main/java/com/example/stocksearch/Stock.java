package com.example.stocksearch;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;

public class Stock {
    public String ticker, name;
    public double price = 0, change = 0;
    public Double quantity;

    public Stock(@NonNull String ticker, @NonNull String name, @NonNull double price, @NonNull double change) {
        this.ticker = ticker;
        this.name = name;
        this.price = price;
        this.change = change;
        this.quantity = null;
    }

    public Stock(@NonNull String ticker, @NonNull String name, @NonNull double price, @NonNull double change, @NonNull double quantity) {
        this.ticker = ticker;
        this.name = name;
        this.price = price;
        this.change = change;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "Stock{" +
                "ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", change=" + change +
                ", quantity=" + df.format(quantity == null ? 0.00 : quantity).toString() +
                '}';
    }
}
