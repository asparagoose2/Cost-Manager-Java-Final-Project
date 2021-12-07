package il.ac.shenkar.costManager;

import java.util.ArrayList;

public class User implements IModel {
    private String name;
    private int userId;
    private ArrayList<Item> items;


    @Override
    public void addItem(Item item) throws CostItemException {
        this.items.add(item);
    }

    @Override
    public Item[] getItems() throws CostItemException {
        Item[] toReturn = new Item[this.items.size()];
        for(int i = 0; i < this.items.size(); i++){
            toReturn[i] = this.items.get(i);
        }
        return toReturn;
    }

    @Override
    public void deleteItems(Item item) throws CostItemException {
        this.items.remove(item);
    }
}
