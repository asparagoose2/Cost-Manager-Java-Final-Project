package il.ac.shenkar.costManager;

import java.util.Collection;
import java.util.Date;

/**
 * The Interface IModel.
 * The IModel interface should be implemented by any class whose instances are intended to be executed as the model in the MVVM pattern in a Cost Manager application.
 */

public interface IModel {

    /**
     * Currency ENUM
     */
    enum CURRENCY {
        USD,
        EUR,
        NIS;

        /**
         * Gets the currency value as it is stored in the DB.
         * @return
         */
        public int getValue() {
            return this.ordinal() + 1;
        }

        /**
         * Gets the currency value as it is displayed in the UI.
         * @return
         */
        @Override
        public String toString() {
            return this.name();
        }
    }

    /**
     * Create new cost item from values.
     *
     * @param amount      - the amount of the item
     * @param category    - the category of the item
     * @param owner       - the owner of the item
     * @param description - the description of the item
     * @param currency    - the currency of the item
     * @param date        - the date of the item
     * @return the newly created item
     * @throws CostManagerException the cost item exception
     */
    public Item createItem(String description, double amount, Category category, User owner, int currency, Date date) throws CostManagerException;


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
     * Login.
     * The user will be logged in to the application.
     *
     * @param email    the email of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the user that was logged in
     * @throws CostManagerException the cost item exception
     */
    public User login(String email, String password) throws CostManagerException;

    /**
     * Create a new user.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param email     the email of the user
     * @param password  the password of the user
     * @return the newly created user
     * @throws CostManagerException the cost item exception
     */
    public User register(String firstName, String lastName, String email, String password) throws CostManagerException;

    /**
     * This method is used to create a new category.
     * The category will be created in the user's list.
     * If the category already exists, the method will throw an exception.
     * @param name - the name of the category to be created
     * @param owner - the owner of the new category
     * @return the newly created category
     * @throws CostManagerException the cost item exception
     */
    Category createCategory(String name, User owner) throws CostManagerException;
}
