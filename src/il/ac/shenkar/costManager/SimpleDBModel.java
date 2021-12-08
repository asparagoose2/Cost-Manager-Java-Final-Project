package il.ac.shenkar.costManager;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SimpleDBModel implements IModel {
    String driverFullQualifieldName = "com.mysql.jdbc.Driver";
    String connectionString = "jdbc:mysql://172.19.0.2:3306/CostManager";

    public SimpleDBModel() throws CostItemException {
        try {
            Class.forName(driverFullQualifieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostItemException("problem with registing driver to the driver manager", e);
        }
    }


    @Override
    public Collection<Item> getItems() throws CostItemException{
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            ResultSet rs = statement.executeQuery("SELECT  * FROM  items where ownerId=1");
            List<Item> items = new LinkedList<>();
            while (rs.next())
            {
                String temp = rs.getString("name");
                double amount = rs.getDouble("cost");
                //items.add(new Item(temp,amount));
                System.out.println("temp = " + temp + ' ' + amount);

            }
            return items;
        }catch (SQLException e){
            throw new CostItemException("getItems error!",e);

        }
    }

    @Override
    public void deleteItems(Item item) throws CostItemException {
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            int rs = statement.executeUpdate("DELETE FROM  items where id="+item.getId());

        }catch (SQLException e){
            throw new CostItemException("getItems error!",e);
        }
    }

    @Override
    public void updateItem(Item item) throws CostItemException {

    }

    @Override
    public void addCategory(Category category) throws CostItemException {

    }

    @Override
    public Collection<Category> getCategories(User user) throws CostItemException {
        return null;
    }

    @Override
    public void deleteCategory(Category category) throws CostItemException {

    }

    @Override
    public void updateCategory(Category category) throws CostItemException {

    }

    @Override
    public void addUser(User user) throws CostItemException {

    }

    @Override
    public User login(String email, String password) throws CostItemException {
        return null;
    }

    @Override
    public void addItem(Item item) throws CostItemException{
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            int rs = statement.executeUpdate("INSERT INTO  items (ownerId,name,cost) VALUES (1,"+'"'+"koral"+'"'+",14)");

        }catch (SQLException e){
            throw new CostItemException("getItems error!",e);
        }
    }


}