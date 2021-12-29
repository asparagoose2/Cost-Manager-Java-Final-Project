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

}
