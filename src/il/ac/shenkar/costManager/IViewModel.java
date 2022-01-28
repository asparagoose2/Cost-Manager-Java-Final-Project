package il.ac.shenkar.costManager;
import java.util.Date;

/**
 * The interface View model. This interface is used to communicate between the view and the model.
 *
 */
public interface IViewModel {
    /**
     * Sets the view in the MVVM pattern.
     * @param view the view to set
     */
    public void setView(IView view);

    /**
     * Sets the model in the MVVM pattern.
     * @param model the model to set
     */
    public void setModel(IModel model);

    /**
     * Adds a new expense to the model.
     * @param itemName the name of the item
     * @param itemValue the value of the item
     * @param itemDate the date of the item
     */
    public void addItem(String itemName, double itemValue, Date itemDate);

    /**
     * Adds a new expense to the model.
     * @param itemName the name of the item
     * @param itemValue the value of the item
     * @param itemDate the date of the item
     */
    public void addItem(String itemName, double itemValue, Date itemDate, Category itemCategory);

    /**
     * Adds a new item to the user items in the model.
     * @param name the name of the item
     * @param amount the amount of the item
     * @param category the category of the item
     * @param description the description of the item
     * @param currency the currency of the item - should be an ENUM
     * @param date the date of the item
     */
    public void addItem(String name, double amount, Category category, String description, int currency, java.sql.Date date);


    /**
     * Add a new expense to the model.
     * @param item the expense
     */
    public void addItem(Item item);

    /**
     * Removes an expense from the model.
     * @param item the expense to remove
     */
    public void removeCostItem(Item item);

    /**
     * Updates an expense in the model.
     * @param itemId the id of the expense to update
     * @param item the new expense
     */
    public void updateItem(int itemId, Item item);


    /**
     * Gets the expenses from the model.
     * Should be called after the model has been updated.
     * After the expenses are retrieved, the view should be notified.
     */
    public void getItems();

    /**
     * Adds a new category to the model.
     * @param category the category to add
     */
    public void addCategory(Category category);


    /**
     * Adds a new category to the model.
     * @param categoryName the name of the category
     */
    public void addCategory(String categoryName);

    /**
     * login the user to the system
     * @param email the email of the user
     * @param password the password of the user
     */
    public void login(String email, String password);

    /**
     * Creates a new user and logs him in the system.
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    public void register(String firstName, String lastName, String email, String password);



    /**
     * Removes a category from the model.
     * @param category the category to remove
     */
    public void removeCategory(Category category);

    /**
     * Updates a category in the model.
     * @param categoryId the id of the category to update
     * @param category the new category
     */
    public void updateCategory(int categoryId, Category category);

    /**
     * Gets the categories from the model.
     * Should be called after the model has been updated.
     * After the categories are retrieved, the view should be notified.
     */
    public void getCategories();

    void setUser(User user);
}
