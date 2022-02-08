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

    /**
     * get the item id
     * @return the item's id
     */
    public int getId() {
        return id;
    }

    /**
     * set the item id
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get the user id of the owner of the item
     * @return the user id of the owner of the item
     */
    public int getOwner() {
        return owner;
    }

    /**
     * set the owner of the item
     * @param owner the new owner's id
     */
    public void setOwner(int owner) {
        this.owner = owner;
    }

    /**
     * get the description of the item
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * set the description of the item
     * @param description the new description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get the currency of the item
     * @return the currency of the item
     */
    public int getCurrency() {
        return currency;
    }

    /**
     * set the currency of the item
     * @param currency the new currency of the item IModel.Currency ENUM should be used
     */
    public void setCurrency(int currency) {
        this.currency = currency;
    }

    /**
     * get the amount of the item
     * @return - the amount of the item
     */
    public double getCost() {
        return cost;
    }

    /**
     * set the amount of the item
     * @param cost - set the amount of the item
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * get the date of the item
     * @return the date of the item
     */
    public Date getDate() {
        return date;
    }

    /**
     * set the date of the item
     * @param date the new date of the item
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * get the category of the item
     * @return the category of the item
     */
    public Category getCategory() {
        return category;
    }

    /**
     * set the category of the item
     * @param category the new category of the item
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}
