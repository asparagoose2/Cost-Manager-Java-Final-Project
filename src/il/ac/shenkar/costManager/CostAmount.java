package il.ac.shenkar.costManager;

public class CostAmount {
    private int amount;
    private Currency currency;

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double convertAmoutTo(Currency curr) {

    }
}
