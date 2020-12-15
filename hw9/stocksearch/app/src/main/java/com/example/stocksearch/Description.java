package com.example.stocksearch;

/*
    Sample:
    {
        "exchangeCode": "NASDAQ",
        "description": "NVIDIA’s invention of the GPU in 1999 sparked the growth of the PC gaming market, redefined modern computer graphics and revolutionized parallel computing. More recently, GPU deep learning ignited modern AI ― the next era of computing ― with the GPU acting as the brain of computers, robots and self-driving cars that can perceive and understand the world.",
        "ticker": "NVDA",
        "startDate": "1999-01-22",
        "name": "NVIDIA Corp",
        "endDate": "2020-11-20"
    }
*/

import org.json.JSONException;
import org.json.JSONObject;

public class Description {
    private String exchangeCode, description, ticker, startDate, name, endDate;

    public Description(String exchangeCode, String description, String ticker, String startDate, String name, String endDate) {
        this.exchangeCode = exchangeCode;
        this.description = description;
        this.ticker = ticker;
        this.startDate = startDate;
        this.name = name;
        this.endDate = endDate;
    }

    public Description(JSONObject obj) {
        try {
            this.exchangeCode = obj.has("exchangeCode") ? obj.getString("exchangeCode") : "";
            this.description = obj.has("description") ? obj.getString("description") : "";
            this.ticker = obj.has("ticker") ? obj.getString("ticker") : "";
            this.startDate = obj.has("startDate") ? obj.getString("startDate") : "";
            this.name = obj.has("name") ? obj.getString("name") : "";
            this.endDate = obj.has("endDate") ? obj.getString("endDate") : "";
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Description{" +
                "exchangeCode='" + exchangeCode + '\'' +
                ", description='" + description + '\'' +
                ", ticker='" + ticker + '\'' +
                ", startDate='" + startDate + '\'' +
                ", name='" + name + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
