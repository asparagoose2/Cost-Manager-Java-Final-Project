package il.ac.shenkar.costManager;

import java.util.ArrayList;

public class User {
    private String name;
    private int userId;
    private ArrayList<Item> items;

    public User(String name, int userId) {
        this.name = name;
        this.userId = userId;
        this.items = new ArrayList<Item>();
        this.pullItemsFromDB();
    }

    // should this be used?
    public User(String name, int userId, Item[] items) {
        this.name = name;
        this.userId = userId;
        this.items = new ArrayList<Item>();
        for(Item item : items) {
            this.addItem(item);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // the db should be updated
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }
    // user id is immutable, thus no setter is provided

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        // should be used?
        this.items = items;
    }

    public void addItem(Item item) {
        // this should push it to the DB
        this.items.add(item);
    }

    public void pullItemsFromDB() {
        // this should pull all the items from the DB to overwrite the ArrayList<Item>
    }

    public void deleteItem(Item item) {
        this.items.remove(item);
    }

    public void updateItem(Item item) {
        // update the DB
    }

}
