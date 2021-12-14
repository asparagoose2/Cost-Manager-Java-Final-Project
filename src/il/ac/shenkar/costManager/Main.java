package il.ac.shenkar.costManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        try {
            SimpleDBModel m = new SimpleDBModel();
            User user = m.login("arthur.dent@email.com","12345");
            Properties properties = new Properties();

//            System.out.println(user.getName());
//            m.addCategory(new Category(user,"food",0));
//            LinkedList<Category> categories = (LinkedList<Category>) m.getCategories(user);
//            Category category = categories.pop();
//            System.out.println(category);
//            m.addItem(new Item(0,user.getUserId(),"test item","bla bla bla bla", 1, 27.3, new Date(),category));
//            LinkedList<Item> items = (LinkedList<Item>) m.getItems(user);
//            m.deleteItems(items.pop());
//            System.out.println(items);
        } catch (CostItemException e) {
            e.printStackTrace();
        }

    }
}
