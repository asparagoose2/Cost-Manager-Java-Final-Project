package il.ac.shenkar.costManager;

public class CostAmount {
    private double amount;
    private CurrencyConverter currencyConverter;

    public CostAmount(double amount) {
        this.amount = amount;
        this.currencyConverter = null;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CurrencyConverter getCurrency() {
        return currencyConverter;
    }

    public void setCurrency(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public double convertAmoutTo(CurrencyConverter curr) {
        return 2.0;
    }
}
