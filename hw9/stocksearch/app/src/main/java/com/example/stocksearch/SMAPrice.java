package com.example.stocksearch;

import org.json.JSONException;
import org.json.JSONObject;

/*
    Sample:
    {
        "date": "2018-11-21T00:00:00.000Z",
        "close": 144.71,
        "high": 155.3,
        "low": 143.61,
        "open": 154.62,
        "volume": 25620900,
        "adjClose": 143.8003279959,
        "adjHigh": 154.3237574305,
        "adjLow": 142.7072427856,
        "adjOpen": 153.6480320277,
        "adjVolume": 25620900,
        "divCash": 0,
        "splitFactor": 1
    }
*/
public class SMAPrice {
    private String date;
    private Double close, high, low, open, adjClose, adjHigh, adjLow, adjOpen;
    private Integer volume, adjvolume, divCash, splitFactor;

    public SMAPrice(String date, Double close, Double high, Double low, Double open, Double adjClose, Double adjHigh, Double adjLow, Double adjOpen, Integer volume, Integer adjvolume, Integer divCash, Integer splitFactor) {
        this.date = date;
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.adjClose = adjClose;
        this.adjHigh = adjHigh;
        this.adjLow = adjLow;
        this.adjOpen = adjOpen;
        this.volume = volume;
        this.adjvolume = adjvolume;
        this.divCash = divCash;
        this.splitFactor = splitFactor;
    }

    public SMAPrice(JSONObject obj) {
        try {
            this.date = !obj.isNull("date") ? obj.getString("date") : "";
            this.close = !obj.isNull("close") ? obj.getDouble("close") : 0.0;
            this.high = !obj.isNull("high") ? obj.getDouble("high") : 0.0;
            this.low = !obj.isNull("low") ? obj.getDouble("low") : 0.0;
            this.open = !obj.isNull("open") ? obj.getDouble("open") : 0.0;
            this.volume = !obj.isNull("volume") ? obj.getInt("volume") : 0;
            this.adjClose = !obj.isNull("adjClose") ? obj.getDouble("adjClose") : 0.0;
            this.adjHigh = !obj.isNull("adjHigh") ? obj.getDouble("adjHigh") : 0.0;
            this.adjLow = !obj.isNull("adjLow") ? obj.getDouble("adjLow") : 0.0;
            this.adjOpen = !obj.isNull("adjOpen") ? obj.getDouble("adjOpen") : 0.0;
            this.adjvolume = !obj.isNull("adjVolume") ? obj.getInt("adjVolume") : 0;
            this.divCash = !obj.isNull("divCash") ? obj.getInt("divCash") : 0;
            this.splitFactor = !obj.isNull("splitFactor") ? obj.getInt("splitFactor") : 0;
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(Double adjClose) {
        this.adjClose = adjClose;
    }

    public Double getAdjHigh() {
        return adjHigh;
    }

    public void setAdjHigh(Double adjHigh) {
        this.adjHigh = adjHigh;
    }

    public Double getAdjLow() {
        return adjLow;
    }

    public void setAdjLow(Double adjLow) {
        this.adjLow = adjLow;
    }

    public Double getAdjOpen() {
        return adjOpen;
    }

    public void setAdjOpen(Double adjOpen) {
        this.adjOpen = adjOpen;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getAdjvolume() {
        return adjvolume;
    }

    public void setAdjvolume(Integer adjvolume) {
        this.adjvolume = adjvolume;
    }

    public Integer getDivCash() {
        return divCash;
    }

    public void setDivCash(Integer divCash) {
        this.divCash = divCash;
    }

    public Integer getSplitFactor() {
        return splitFactor;
    }

    public void setSplitFactor(Integer splitFactor) {
        this.splitFactor = splitFactor;
    }

    @Override
    public String toString() {
        return "SMAPrice{" +
                "date='" + date + '\'' +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", adjClose=" + adjClose +
                ", adjHigh=" + adjHigh +
                ", adjLow=" + adjLow +
                ", adjOpen=" + adjOpen +
                ", volume=" + volume +
                ", adjvolume=" + adjvolume +
                ", divCash=" + divCash +
                ", splitFactor=" + splitFactor +
                '}';
    }
}
