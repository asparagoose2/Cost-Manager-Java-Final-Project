package il.ac.shenkar.costManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Main {
    static IView view;
    static IModel model;
    static IViewModel viewModel;



    public static void main(String[] args) {
        try {
//            view = new ConsoleView();
            model = new SimpleDBModel();
            viewModel = new ViewModel();
            view = new GUI(viewModel);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }
        view.setViewModel(viewModel);
        viewModel.setModel(model);
        viewModel.setView(view);
//        User user;
//        try {
//            user = model.login("arthur.dent@email.com","12345");
//            viewModel.setUser(user);
//            viewModel.getCategories();
//        } catch (CostManagerException e) {
//            e.printStackTrace();
//        }
        SwingUtilities.invokeLater(() -> view.start());




        /*
        try {
            SimpleDBModel m = new SimpleDBModel();
            User user = m.login("arthur.dent@email.com","12345");
//            Properties properties = new Properties();

//            System.out.println(user.getName());
//            m.addCategory(new Category(user,"food",0));
//            LinkedList<Category> categories = (LinkedList<Category>) m.getCategories(user);
//            Category category = categories.pop();
//            System.out.println(category);
            m.addItem(new Item(0,user.getUserId(),"test item","okokok", 1, 27.3, new Date(),new Category(user,"food",2)));
            LinkedList<Item> items = (LinkedList<Item>) m.getItems(user);
            for (Item item : items) {
                System.out.println(item);
            }
            m.deleteItems(items.pop());
//            System.out.println(items);
        } catch (CostManagerException e) {
            e.printStackTrace();
        }

         */

    }
}
