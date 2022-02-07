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


    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

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

    @Override
    public void showCurrentMonth() {
        service.submit(() -> {
            try {
                LinkedList<Item> allItems = (LinkedList<Item>) model.getItems(user);
                LinkedList<Item> filteredItems = new LinkedList<>();
                Iterator<Item> iterator = allItems.iterator();
                Calendar cal = Calendar.getInstance();

                while (iterator.hasNext()) {
                    Item item = iterator.next();
                    cal.setTime(item.getDate());
                    boolean m = cal.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH);
                    boolean y = cal.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR);
                    if(m && y) {
                        filteredItems.add(item);
                    }
                }

                view.setItems(filteredItems);
                view.displayData("Items");
            } catch (CostManagerException e) {
                view.displayError(e.getMessage(),false);
            }
        });
    }
}
