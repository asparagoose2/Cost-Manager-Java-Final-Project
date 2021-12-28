package il.ac.shenkar.costManager;
import java.util.Collection;

/**
 * The Interface IModel.
 * The IModel interface should be implemented by any class whose instances are intended to be executed as the model in the MVVM pattern in a Cost Manager application.
 *
 */

public interface IModel {
    /**
     * Add an item to the model. The item will be added to the user's list of items.
     *
     * @throws CostItemException the cost item exception
     * @param item the Item to be added to the database
     */
    public void addItem(Item item) throws CostItemException;

    /**
     * Gets the items. The items will be returned in the form of a collection.
     * The collection will contain all the items in the user's list.
     *
     * @param user the user whose items will be returned
     * @return the items in the user's list in the form of a collection
     * @throws CostItemException the cost item exception
     */
    public Collection<Item> getItems(User user) throws CostItemException;

    /**
     * Delete item.
     * The item will be deleted from the user's list of items.
     * @param item the item to be deleted from the user's list of items
     * @throws CostItemException the cost item exception
     */
    public void deleteItems(Item item) throws CostItemException;

    /**
     * Update item.
     * The item will be updated in the user's list of items.
     *
     * @param item the item to be updated in the user's list of items
     * @throws CostItemException
     */
    public void updateItem(Item item) throws  CostItemException;

    public void addCategory(Category category) throws CostItemException;

    /**
     * Gets the categories.
     * The categories will be returned in the form of a collection.
     * The collection will contain all the categories in the user's list.
     *
     * @param user the user whose categories will be returned
     * @return Collection<Category> the categories in the user's list in the form of a collection
     * @throws CostItemException the cost item exception
     */
    public Collection<Category> getCategories(User user) throws CostItemException;

    /**
     * Delete category.
     * The category will be deleted from the user's list of categories.
     *
     * @param category the category to be deleted from the user's list of categories
     * @throws CostItemException the cost item exception
     */
    public void deleteCategory(Category category) throws CostItemException;

    /**
     * Update category.
     * The category will be updated in the user's list of categories.
     *
     * @param category the category to be updated in the user's list of categories
     * @throws CostItemException the cost item exception
     */
    public void updateCategory(Category category) throws CostItemException;

    // will be included in the future
    //public void addUser(User user) throws CostItemException;

    /**
     * Login.
     * The user will be logged in to the application.
     *
     * @param email the email of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the user that was logged in
     * @throws CostItemException the cost item exception
     */
     public User login(String email, String password) throws CostItemException;

}
