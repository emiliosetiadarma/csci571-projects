package com.example.androidstocksearchcsci571;

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
    private Integer bidSize, low, bidPrice, askSize, volume, lastSize, askPrice;
    private Double prevClose, last, high, mid, open, tngoLast;

    public Price(String timestamp, String lastSaleTimestamp, String quoteTimestamp, String ticker, Integer bidSize, Integer low, Integer bidPrice, Integer askSize, Integer volume, Integer lastSize, Integer askPrice, Double prevClose, Double last, Double high, Double mid, Double open, Double tngoLast) {
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

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Integer bidPrice) {
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

    public Integer getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Integer askPrice) {
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
