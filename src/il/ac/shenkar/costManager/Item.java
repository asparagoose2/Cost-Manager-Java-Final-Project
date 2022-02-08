package il.ac.shenkar.costManager;

import java.util.Date;

/**
 * Item class represents an item in the cost manager.
 *
 * @see SimpleDBModel
 */
public class Item {
    private int id;
    private int owner;
    private String description;
    private int currency;
    private double cost;
    private Date date;
    private Category category;

    public Item(int id, int owner, String description, int currency, double cost, Date date, Category category) {
        setId(id);
        setOwner(owner);
        setDescription(description);
        setCurrency(currency);
        setCost(cost);
        setDate(date);
        setCategory(category);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", owner=" + owner +
                ", description='" + description + '\'' +
                ", currency=" + currency +
                ", cost=" + cost +
                ", date=" + date +
                ", category=" + category +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
