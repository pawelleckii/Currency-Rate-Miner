package currenda;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

/***
 * Main class for handling console input and output.
 * Downloads and processes historical currency rates from NBP API.
 * @author Pawel Lecki
 */
public class CurrencyRateMiner {

    public static void main(String[] args){
        DecimalFormat format = new DecimalFormat("#.####");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                System.out.println("\nEnter currency code (e.g. 'EUR'):");
                String currencyCode = scanner.nextLine();
                if(currencyCode.equals("")) break;

                System.out.println("Enter start date yyyy-mm-dd:");
                String startDate = scanner.nextLine();

                System.out.println("Enter end date yyyy-mm-dd:");
                String endDate = scanner.nextLine();

                CurrencyInfo currencyInfo = BankDAO.getCurrencyInfo(currencyCode, startDate, endDate);
                System.out.println("Average Buying Rate: " + format.format(currencyInfo.avgBuyingRate()));
                System.out.println("Standard Deviation of Selling Rates: " + format.format(currencyInfo.standardDeviationOfSellingRates()));
            } catch (IOException e) {
                System.out.println(e.getClass() + ": Could not get such entries from NBP API.");
            } catch (IllegalArgumentException e){
                System.out.println(e.getClass() + ": " + e.getMessage());
            }
        }
    }
}