package il.ac.shenkar.costManager;

import java.util.Hashtable;

public class Currency {

    public static Hashtable<String, Double> currencyTable;

    static {
        currencyTable = new Hashtable<>();
        currencyTable.put("USD", 1.0);
        currencyTable.put("NIS", 3.16);
        currencyTable.put("EUR", 0.89);
    }
}
