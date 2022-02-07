package il.ac.shenkar.costManager;
import java.time.Month;
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
     * Gets the expenses from the model.
     * Should be called after the model has been updated.
     * After the expenses are retrieved, the view should be notified.
     *
     */
    public void getItems();


    /**
     * Gets the expenses from the model.
     * Should be called after the model has been updated.
     * After the expenses are retrieved, the view should be notified.
     * @param month the month to get the expenses from
     * @param year the year to get the expenses from
     *
     */
    public void getItems(Month month, int year);

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
     * Gets the categories from the model.
     * Should be called after the model has been updated.
     * After the categories are retrieved, the view should be notified.
     */
    public void getCategories();

    void setUser(User user);

}
