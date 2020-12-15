package com.example.stocksearch;

import org.json.JSONException;
import org.json.JSONObject;

/*
    Sample:
        {
        "timestamp": "2020-11-20T21:00:00+00:00",
        "bidSize": null,
        "lastSaleTimestamp": "2020-11-20T21:00:00+00:00",
        "low": 210,
        "bidPrice": null,
        "prevClose": 212.42,
        "quoteTimestamp": "2020-11-20T21:00:00+00:00",
        "last": 210.39,
        "askSize": null,
        "volume": 22843119,
        "lastSize": null,
        "ticker": "MSFT",
        "high": 213.285,
        "mid": null,
        "askPrice": null,
        "open": 212.2,
        "tngoLast": 210.39
    }
*/
public class Price {
    private String timestamp, lastSaleTimestamp, quoteTimestamp, ticker;
    private Integer bidSize, askSize, volume, lastSize;
    private Double prevClose, last, high, mid, open, tngoLast, low, askPrice, bidPrice;

    public Price(String timestamp, String lastSaleTimestamp, String quoteTimestamp, String ticker, Integer bidSize, Double low, Double bidPrice, Integer askSize, Integer volume, Integer lastSize, Double askPrice, Double prevClose, Double last, Double high, Double mid, Double open, Double tngoLast) {
        this.timestamp = timestamp;
        this.lastSaleTimestamp = lastSaleTimestamp;
        this.quoteTimestamp = quoteTimestamp;
        this.ticker = ticker;
        this.bidSize = bidSize;
        this.low = low;
        this.bidPrice = bidPrice;
        this.askSize = askSize;
        this.volume = volume;
        this.lastSize = lastSize;
        this.askPrice = askPrice;
        this.prevClose = prevClose;
        this.last = last;
        this.high = high;
        this.mid = mid;
        this.open = open;
        this.tngoLast = tngoLast;
    }

    public Price(JSONObject obj) {
        try {
            this.timestamp = !obj.isNull("timestamp") ? obj.getString("timestamp") : "";
            this.bidPrice = !obj.isNull("bidPrice") ? obj.getDouble("bidPrice") : 0.00;
            this.low = !obj.isNull("low") ? obj.getDouble("low") : 0.0;
            this.bidSize = !obj.isNull("bidSize") ? obj.getInt("bidSize") : 0;
            this.prevClose = !obj.isNull("prevClose") ? obj.getDouble("prevClose") : 0.0;
            this.quoteTimestamp = !obj.isNull("quotaTimestamp") ? obj.getString("quoteTimestamp") : "";
            this.last = !obj.isNull("last") ? obj.getDouble("last") : 0.0;
            this.askSize = !obj.isNull("askSize") ? obj.getInt("askSize") : 0;
            this.volume = !obj.isNull("volume") ? obj.getInt("volume") : 0;
            this.lastSize = !obj.isNull("lastSize") ? obj.getInt("lastSize") : 0;
            this.ticker = !obj.isNull("ticker") ? obj.getString("ticker") : "";
            this.high = !obj.isNull("high") ? obj.getDouble("high") : 0.0;
            this.tngoLast = !obj.isNull("tngoLast") ? obj.getDouble("tngoLast") : 0.0;
            this.askPrice = !obj.isNull("askPrice") ? obj.getDouble("askPrice") : 0.0;
            this.open = !obj.isNull("open") ? obj.getDouble("open") : 0.0;
            this.lastSaleTimestamp = !obj.isNull("lastSaleTimestamp") ? obj.getString("lastSaleTimestamp") : "";
            this.mid = !obj.isNull("mid") ? obj.getDouble("mid") : 0.0;
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLastSaleTimestamp() {
        return lastSaleTimestamp;
    }

    public void setLastSaleTimestamp(String lastSaleTimestamp) {
        this.lastSaleTimestamp = lastSaleTimestamp;
    }

    public String getQuoteTimestamp() {
        return quoteTimestamp;
    }

    public void setQuoteTimestamp(String quoteTimestamp) {
        this.quoteTimestamp = quoteTimestamp;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getBidSize() {
        return bidSize;
    }

    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Integer getAskSize() {
        return askSize;
    }

    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getLastSize() {
        return lastSize;
    }

    public void setLastSize(Integer lastSize) {
        this.lastSize = lastSize;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public Double getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(Double prevClose) {
        this.prevClose = prevClose;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getMid() {
        return mid;
    }

    public void setMid(Double mid) {
        this.mid = mid;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getTngoLast() {
        return tngoLast;
    }

    public void setTngoLast(Double tngoLast) {
        this.tngoLast = tngoLast;
    }

    @Override
    public String toString() {
        return "Price{" +
                "timestamp='" + timestamp + '\'' +
                ", lastSaleTimestamp='" + lastSaleTimestamp + '\'' +
                ", quoteTimestamp='" + quoteTimestamp + '\'' +
                ", ticker='" + ticker + '\'' +
                ", bidSize=" + bidSize +
                ", low=" + low +
                ", bidPrice=" + bidPrice +
                ", askSize=" + askSize +
                ", volume=" + volume +
                ", lastSize=" + lastSize +
                ", askPrice=" + askPrice +
                ", prevClose=" + prevClose +
                ", last=" + last +
                ", high=" + high +
                ", mid=" + mid +
                ", open=" + open +
                ", tngoLast=" + tngoLast +
                '}';
    }
}
