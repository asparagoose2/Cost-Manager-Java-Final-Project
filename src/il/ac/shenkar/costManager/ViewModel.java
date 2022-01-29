package il.ac.shenkar.costManager;

import com.sun.xml.internal.bind.annotation.XmlLocation;

import java.util.Date;

public class ViewModel implements IViewModel{

    private IView view;
    private IModel model;
    private User user;


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
        try {
            model.createItem(name,amount,category,user,description,currency,date);
        } catch (CostManagerException e) {
            view.displayError(e.getMessage(),false);
        }
    }

    @Override
    public void getItems() {
        try {
            this.view.setItems(this.model.getItems(user));
            this.view.displayData("Items");
        } catch (CostManagerException e) {
            view.displayError(e.getMessage(),true);
        }
    }

    @Override
    public void addCategory(String categoryName) {
        try {
            this.model.createCategory(categoryName,user);
            this.view.setCategories(this.model.getCategories(user));

        } catch (CostManagerException e) {
            view.displayError(e.getMessage(),false);
        }
    }

    @Override
    public void login(String email, String password) {
        try {
            this.setUser(this.model.login(email,password));
            this.view.displayMessage("Login Successful","Login");
            this.view.setCategories(this.model.getCategories(user));
            this.view.setItems(this.model.getItems(user));
            this.view.setIsLoggedIn(true);

        } catch (CostManagerException e) {
            view.displayError(e.getMessage(),false);
        }
    }

    @Override
    public void register(String firstName, String lastName, String email, String password) {
        try {
            this.setUser(this.model.register(firstName,lastName,email,password));
            this.view.displayMessage("Registration Successful","Registration");
            this.view.setCategories(this.model.getCategories(user));
            this.view.setItems(this.model.getItems(user));
            this.view.setIsLoggedIn(true);
        } catch (CostManagerException e) {
            view.displayError(e.getMessage(),false);
        }
    }

    @Override
    public void getCategories() {
        try {
            this.view.setCategories(this.model.getCategories(user));
        } catch (CostManagerException e) {
            view.displayError(e.getMessage(),true);
        }
    }

}
