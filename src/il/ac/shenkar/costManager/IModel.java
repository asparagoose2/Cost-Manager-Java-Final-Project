package il.ac.shenkar.costManager;
import java.util.Collection;
import java.util.Date;

/**
 * The Interface IModel.
 * The IModel interface should be implemented by any class whose instances are intended to be executed as the model in the MVVM pattern in a Cost Manager application.
 *
 */

public interface IModel {

    enum CURRENCY {
        USD,
        EUR,
        NIS;
        public int getValue() {
            return this.ordinal() + 1;
        }
    }

    /**
     * Add an item to the model. The item will be added to the user's list of items.
     *
     * @throws CostManagerException the cost item exception
     * @param item the Item to be added to the database
     */
    public void addItem(Item item) throws CostManagerException;

    /**
     * Create new cost item from values.
     *
     * @param name - the name of the item
     * @param amount - the amount of the item
     * @param category - the category of the item
     * @param owner - the owner of the item
     * @param description - the description of the item
     * @param currency - the currency of the item
     * @param date - the date of the item
     * @return the newly created item
     * @throws CostManagerException
     */
    public Item createItem(String name, double amount, Category category, User owner, String description, int currency, Date date ) throws CostManagerException;


    /**
     * Gets the items. The items will be returned in the form of a collection.
     * The collection will contain all the items in the user's list.
     *
     * @param user the user whose items will be returned
     * @return the items in the user's list in the form of a collection
     * @throws CostManagerException the cost item exception
     */
    public Collection<Item> getItems(User user) throws CostManagerException;

    /**
     * Delete item.
     * The item will be deleted from the user's list of items.
     * @param item the item to be deleted from the user's list of items
     * @throws CostManagerException the cost item exception
     */
    public void deleteItems(Item item) throws CostManagerException;

    /**
     * Update item.
     * The item will be updated in the user's list of items.
     *
     * @param item the item to be updated in the user's list of items
     * @throws CostManagerException the cost item exception @link{CostManagerException}
     */
    public void updateItem(Item item) throws CostManagerException;

    public void addCategory(Category category) throws CostManagerException;

    /**
     * Gets the categories.
     * The categories will be returned in the form of a collection.
     * The collection will contain all the categories in the user's list.
     *
     * @param user the user whose categories will be returned
     * @return Collection<Category> the categories in the user's list in the form of a collection
     * @throws CostManagerException the cost item exception
     */
    public Collection<Category> getCategories(User user) throws CostManagerException;

    /**
     * Delete category.
     * The category will be deleted from the user's list of categories.
     *
     * @param category the category to be deleted from the user's list of categories
     * @throws CostManagerException the cost item exception
     */
    public void deleteCategory(Category category) throws CostManagerException;

    /**
     * Update category.
     * The category will be updated in the user's list of categories.
     *
     * @param category the category to be updated in the user's list of categories
     * @throws CostManagerException the cost item exception
     */
    public void updateCategory(Category category) throws CostManagerException;

    // will be included in the future
    //public void addUser(User user) throws CostItemException;

    /**
     * Login.
     * The user will be logged in to the application.
     *
     * @param email the email of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the user that was logged in
     * @throws CostManagerException the cost item exception
     */
     public User login(String email, String password) throws CostManagerException;

    /**
     * Create a new user.
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param password the password of the user
     * @return the newly created user
     * @throws CostManagerException the cost item exception
     */
     public User register(String firstName, String lastName, String email, String password) throws CostManagerException;

    Item createItem(String name, double amount, Category category, User owner, String description, int currency, java.sql.Date date) throws CostManagerException;

    Category createCategory(String name, User owner) throws CostManagerException;
}
