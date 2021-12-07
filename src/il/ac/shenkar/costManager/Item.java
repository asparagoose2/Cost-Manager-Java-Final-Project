package il.ac.shenkar.costManager;

/*
    We can use currency as ENUM, save all amounts in USD and when printing make the conversion.
 */
public class Item {
    private String name;
    private double amount;
    Currency currency; // setter and getter
    private int id;
    private User owner;

    public Item(String name, double amount) {
        this.setName(name);
        this.setAmount(amount);
    }

    public Item(String name, double amount, int id) {
        this.setName(name);
        this.setAmount(amount);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.update();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        // should update the DB
        this.amount = amount;
        this.update();
    }

    public int getId() {
        return id;
    }

    public void delete() {
        //this.owner.deleteItem(this);
    }

    // a way to update the DB
    private void update(){
        //this.owner.updateItem(this);
    }
}
