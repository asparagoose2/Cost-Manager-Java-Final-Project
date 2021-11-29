package il.ac.shenkar.costManager;

public class Item {
    private String name;
    private CostAmount amount;

    public Item(String name, double amount) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount.getAmount();
    }

    public void setAmount(int amount) {
        this.amount.setAmount(amount);
    }
}
