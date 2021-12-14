package il.ac.shenkar.costManager;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    private String name;
    private int userId;
    private ArrayList<Item> items;

    public User(String name, int userId) {
        this.name = name;
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
