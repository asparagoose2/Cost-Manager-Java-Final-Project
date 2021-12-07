package il.ac.shenkar.costManager;

public interface IModel {
    public void addItem(Item item) throws CostItemException;
    public Item[] getItems() throws CostItemException;
    public void deleteItems(Item item) throws CostItemException;
}
