package il.ac.shenkar.costManager;

import java.util.Date;

/*
    We can use currency as ENUM, save all amounts in USD and when printing make the conversion.
 */
public class Item {
    private int id;
    private int owner;
    private String name;
    private String description;
    private int currency;
    private double cost;
    private Date date;
    private String category;

    public Item(int id, int owner, String name, double cost, String category) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.cost = cost;
        this.category = category;

        this.currency = 1;
        this.description = "";
        this.date = new Date();
    }

    public Item(int id, int owner, String name, String description, int currency, double cost, Date date, String category) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.currency = currency;
        this.cost = cost;
        this.date = date;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
