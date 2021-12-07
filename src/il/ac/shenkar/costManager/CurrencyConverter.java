package il.ac.shenkar.costManager;

import java.util.Hashtable;

public class CurrencyConverter {

    public static Hashtable<Currency, Double> currencyTable;

    static {
        currencyTable = new Hashtable<>();
        currencyTable.put(Currency.USD, 1.0);
        currencyTable.put(Currency.NIS, 3.16);
        currencyTable.put(Currency.EUR, 0.89);
    }
    double convert(double amountInUSD, Currency currency) {
        return amountInUSD;
    }
}
