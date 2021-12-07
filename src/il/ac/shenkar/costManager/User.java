package il.ac.shenkar.costManager;

import java.util.ArrayList;
import java.util.Collection;

public class User implements IModel {
    private String name;
    private int userId;
    private ArrayList<Item> items;

    @Override
    public void addItem(Item item) throws CostItemException {
        this.items.add(item);
    }

    @Override
    public Collection<Item> getItems() throws CostItemException {
        return this.items;
    }

    @Override
    public void deleteItems(Item item) throws CostItemException {
        this.items.remove(item);
    }
}
