package currenda;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/***
 * Model of currency entry containing historical rates of given currency.
 * @author Pawel Lecki
 */
public class CurrencyInfo {
    private String currencyCode;
    private List<CurrencyRate> currencyRates;

    /***
     * @param rootJsonObj expected in form {"code" , "rates":[{"effectiveDate","bid","ask"}]}
     */
    public CurrencyInfo(JsonObject rootJsonObj) {
        this.currencyCode = rootJsonObj.get("code").getAsString();
        this.currencyRates = new ArrayList<>();

        JsonArray jsonRates = rootJsonObj.get("rates").getAsJsonArray();
        for(int i=0; i < jsonRates.size(); i++){
            currencyRates.add(new CurrencyRate(jsonRates.get(i).getAsJsonObject()));
        }
    }

    public double standardDeviationOfSellingRates(){
        double X2 = 0;
        double X = 0;
        int N = currencyRates.size();
        for (CurrencyRate rate : currencyRates) {
            double d = rate.getSellingRate();
            X2 += d*d;
            X += d;
        }
        return Math.sqrt(X2/N - X*X/N/N);
    }

    public double avgBuyingRate() {
        double sum = 0;
        for(CurrencyRate currencyRate : currencyRates){
             sum += currencyRate.getBuyingRate();
         }
         return sum/currencyRates.size();
    }

    @Override
    public String toString() {
        return "currenda.CurrencyInfo{" +
                "currencyCode='" + currencyCode + '\'' +
                ", currencyRates=" + currencyRates +
                '}';
    }
}
