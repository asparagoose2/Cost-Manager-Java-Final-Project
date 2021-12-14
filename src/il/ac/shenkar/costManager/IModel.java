package il.ac.shenkar.costManager;

import java.util.Collection;

public interface IModel {
    public void addItem(Item item) throws CostItemException;
    public Collection<Item> getItems(User user) throws CostItemException;
    public void deleteItems(Item item) throws CostItemException;
    public void updateItem(Item item) throws  CostItemException;

    public void addCategory(Category category) throws CostItemException;
    public Collection<Category> getCategories(User user) throws CostItemException;
    public void deleteCategory(Category category) throws CostItemException;
    public void updateCategory(Category category) throws CostItemException;

    // will be included in the future
    //public void addUser(User user) throws CostItemException;

    // will be included in the future
     public User login(String email, String password) throws CostItemException;

}
