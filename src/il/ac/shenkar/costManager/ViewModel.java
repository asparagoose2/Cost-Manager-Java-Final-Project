package il.ac.shenkar.costManager;

import javax.swing.*;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewModel implements IViewModel{

    private IView view;
    private IModel model;
    private User user;
    private final ExecutorService service;

    ViewModel() {
        this.service = Executors.newFixedThreadPool(3);
    }


    /**
     * sets the user
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * sets the view
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * sets the model
     * @param model the model to set
     */
    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * adds an item to the model and updates the view
     * @param name the name of the item
     * @param amount the amount of the item
     * @param category the category of the item
     * @param description the description of the item
     * @param currency the currency of the item - should be an ENUM
     * @param date the date of the item
     */
    @Override
    public void addItem(String name, double amount, Category category, String description, int currency, java.sql.Date date) {
        service.submit(() -> {
            try {
                model.createItem(name,amount,category,user,description,currency,date);
                LinkedList<Item> items = (LinkedList<Item>) model.getItems(user);
                SwingUtilities.invokeLater(() -> {
                    view.setItems(items);
                    view.displayData("Items");
                });
            } catch (CostManagerException e) {
                SwingUtilities.invokeLater(() -> view.displayError(e.getMessage(),false));
            }
        });
    }

    /**
     * gets the items from the model and updates the view
     */
    @Override
    public void getItems() {
        service.submit(() -> {
            try {
                LinkedList<Item> items = (LinkedList<Item>) model.getItems(user);
                SwingUtilities.invokeLater(() -> view.setItems(items));
                SwingUtilities.invokeLater(() -> view.displayData("Items"));
            } catch (CostManagerException e) {
                SwingUtilities.invokeLater(() -> view.displayError(e.getMessage(),true));
            }
        });
    }

    /**
     * get only the items of the month and year that the user chose and updates the view
     * @param month the month to get the expenses from
     * @param year the year to get the expenses from
     */
    @Override
    public void getItems(Month month, int year) {
        service.submit(() -> {
            try {
                LinkedList<Item> items = (LinkedList<Item>) model.getItems(user);
                Iterator<Item> iterator = items.iterator();
                while (iterator.hasNext()) {
                    Item item = iterator.next();
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(item.getDate());
                    if (calendar.get(Calendar.MONTH) + 1 !=  month.getValue() || calendar.get(Calendar.YEAR) != year) {
                        iterator.remove();
                    }
                }
                SwingUtilities.invokeLater(() -> view.setItems(items));
                SwingUtilities.invokeLater(() -> view.displayData("Items"));
            } catch (CostManagerException e) {
                SwingUtilities.invokeLater(() -> view.displayError(e.getMessage(),true));
            }
        });
    }

    /**
     * adds a category to the model and updates the view
     * @param categoryName the name of the category
     */
    @Override
    public void addCategory(String categoryName) {
        service.submit(() -> {
            try {
                model.createCategory(categoryName, user);
                LinkedList<Category> categories = (LinkedList<Category>) model.getCategories(user);
                SwingUtilities.invokeLater(() -> view.setCategories(categories));
            } catch (CostManagerException e) {
                SwingUtilities.invokeLater(() -> view.displayError(e.getMessage(),false));
            }
        });
    }

    /**
     * login a user to the system
     * @param email the email of the user
     * @param password the password of the user
     */
    @Override
    public void login(String email, String password) {
        service.submit(() -> {
            try {
                setUser(model.login(email,password));
                LinkedList<Category> categories = (LinkedList<Category>) model.getCategories(user);
                LinkedList<Item> items = (LinkedList<Item>) model.getItems(user);
                SwingUtilities.invokeLater(() -> view.setCategories(categories));
                SwingUtilities.invokeLater(() -> view.setItems(items));
                SwingUtilities.invokeLater(() -> view.setUser(this.user));
                SwingUtilities.invokeLater(() -> view.setIsLoggedIn(true));
            } catch (CostManagerException e) {
                SwingUtilities.invokeLater(() -> view.displayError(e.getMessage(),false));
            }
        });
    }

    /**
     * register a new user and login the user after registration
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    @Override
    public void register(String firstName, String lastName, String email, String password) {
        service.submit(() -> {
            try {
                setUser(model.register(firstName,lastName,email,password));
                LinkedList<Category> categories = (LinkedList<Category>) model.getCategories(user);
                LinkedList<Item> items = (LinkedList<Item>) model.getItems(user);
                SwingUtilities.invokeLater(() -> {
                    view.displayMessage("Registration Successful","Registration");
                    view.setCategories(categories);
                    view.setItems(items);
                    view.setUser(this.user);
                    view.setIsLoggedIn(true);
                });
            } catch (CostManagerException e) {
                SwingUtilities.invokeLater(() -> view.displayError(e.getMessage(),false));
            }
        });

    }

    /**
     * Sets categories in the view
     */
    @Override
    public void getCategories() {
        service.submit(() -> {
            try {
                LinkedList<Category> categories = (LinkedList<Category>) model.getCategories(user);
                SwingUtilities.invokeLater(() -> view.setCategories(categories));
            } catch (CostManagerException e) {
                SwingUtilities.invokeLater(() -> view.displayError(e.getMessage(),true));
            }
        });
    }
}
