package currenda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.*;

/***
 * Retreives data from NBP API and parses it to CurrencyInfo object.
 * @author Pawel Lecki
 */
public class BankDAO {

    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";
    private static final String URL_SUFIX = "/?format=json";

    /***
     * @param currencyCode Code of currency retreived from NBP API
     * @param startDate Starting date of entries retreived from NBP API
     * @param endDate End date of entries retreived from NBP API
     * @throws IOException in case of connection problems.
     * @throws IllegalArgumentException if entered dates are in wrong format.
     */
    public static CurrencyInfo getCurrencyInfo(String currencyCode, String startDate, String endDate) throws IOException, IllegalArgumentException{
        if(!checkDate(startDate) ){
            throw  new IllegalArgumentException("Wrong start date.");
        }
        if(!checkDate(endDate)){
            throw new IllegalArgumentException("Wrong end date.");
        }
        InputStream stream = null;
        try {
            String sURL = API_URL + currencyCode + "/" + startDate + "/" + endDate + URL_SUFIX;
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            stream = (InputStream) request.getContent();
            JsonElement root = jsonParser.parse(new InputStreamReader(stream));
            JsonObject rootObj = root.getAsJsonObject();
            stream.close();
            return new CurrencyInfo(rootObj);
        } finally {
            if(stream != null){
                stream.close();
            }
        }
    }

    private static boolean checkDate(String date){
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
            return f.parse(date) != null;
        } catch (ParseException ignore) {
        }
        return false;
    }

}
