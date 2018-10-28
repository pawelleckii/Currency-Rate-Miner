package currenda;

import com.google.gson.JsonObject;

/***
 * Model of single historical entry in NBP API.
 * @author Pawel Lecki
 */
public class CurrencyRate {
    private String effectiveDate;
    private double buyingRate;  //"bid"
    private double sellingRate; //"ask"

    public CurrencyRate(JsonObject jsonRate) {
        this.effectiveDate = jsonRate.get("effectiveDate").getAsString();
        this.buyingRate = jsonRate.get("bid").getAsDouble();
        this.sellingRate = jsonRate.get("ask").getAsDouble();
    }

    public Double getBuyingRate() {
        return buyingRate;
    }

    public Double getSellingRate() {
        return sellingRate;
    }

    @Override
    public String toString() {
        return "currenda.CurrencyRate{" +
                "effectiveDate='" + effectiveDate + '\'' +
                ", buyingRate=" + buyingRate +
                ", sellingRate=" + sellingRate +
                '}';
    }
}
