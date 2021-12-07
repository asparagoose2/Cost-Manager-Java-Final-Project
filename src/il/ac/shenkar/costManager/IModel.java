package il.ac.shenkar.costManager;

import java.util.Collection;

public interface IModel {
    public void addItem(Item item) throws CostItemException;
    public Collection<Item> getItems() throws CostItemException;
    public void deleteItems(Item item) throws CostItemException;
}
