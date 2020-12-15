package com.example.androidstocksearchcsci571;

/* Sample:
    {
        "name": "NVIDIA Corp",
        "ticker": "NVDA",
        "permaTicker": "US000000000079",
        "openFIGIComposite": "BBG000BBJQV0",
        "assetType": "Stock",
        "isActive": true,
        "countryCode": "US"
    }
 */
public class Autosuggest {
    private String name, ticker, permaTicker, openFIGIComposite, assetType, countryCode;
    private boolean isActive;

    public Autosuggest(String name, String ticker, String permaTicker, String openFIGIComposite, String assetType, String countryCode, boolean isActive) {
        this.name = name;
        this.ticker = ticker;
        this.permaTicker = permaTicker;
        this.openFIGIComposite = openFIGIComposite;
        this.assetType = assetType;
        this.countryCode = countryCode;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getPermaTicker() {
        return permaTicker;
    }

    public void setPermaTicker(String permaTicker) {
        this.permaTicker = permaTicker;
    }

    public String getOpenFIGIComposite() {
        return openFIGIComposite;
    }

    public void setOpenFIGIComposite(String openFIGIComposite) {
        this.openFIGIComposite = openFIGIComposite;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Autosuggest{" +
                "name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", permaTicker='" + permaTicker + '\'' +
                ", openFIGIComposite='" + openFIGIComposite + '\'' +
                ", assetType='" + assetType + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
