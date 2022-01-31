package il.ac.shenkar.costManager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
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
                view.setItems(model.getItems(user));
                view.displayData("Items");
            } catch (CostManagerException e) {
                view.displayError(e.getMessage(),false);
            }
        });
    }

    @Override
    public void getItems() {
        service.submit(() -> {
            try {
                view.setItems(model.getItems(user));
                view.displayData("Items");
            } catch (CostManagerException e) {
                view.displayError(e.getMessage(),false);
            }
        });
    }

    @Override
    public void addCategory(String categoryName) {
        service.submit(() -> {
            try {
                model.createCategory(categoryName, user);
                view.setCategories(model.getCategories(user));
            } catch (CostManagerException e) {
                view.displayError(e.getMessage(), false);
            }
        });
    }

    @Override
    public void login(String email, String password) {
        service.submit(() -> {
            try {
                System.out.println("login");
                System.out.println(email);
                System.out.println(password);
                setUser(model.login(email,password));
                view.displayMessage("Login Successful","Login");
                view.setCategories(model.getCategories(user));
                view.setItems(model.getItems(user));
                view.setIsLoggedIn(true);

            } catch (CostManagerException e) {
                view.displayError(e.getMessage(),false);
            }
        });
    }

    @Override
    public void register(String firstName, String lastName, String email, String password) {
        service.submit(() -> {
            try {
                setUser(model.register(firstName,lastName,email,password));
                view.displayMessage("Registration Successful","Registration");
                view.setCategories(model.getCategories(user));
                view.setItems(model.getItems(user));
                view.setIsLoggedIn(true);
            } catch (CostManagerException e) {
                view.displayError(e.getMessage(),false);
            }
        });

    }

    @Override
    public void getCategories() {
        service.submit(() -> {
            try {
                view.setCategories(model.getCategories(user));
            } catch (CostManagerException e) {
                view.displayError(e.getMessage(),true);
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
