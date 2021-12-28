package il.ac.shenkar.costManager;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements the IModel interface.
 * This class is responsible for connecting to the database and performing all the operations on the database.
 * This class uses MySQL JDBC driver.
 * @see IModel
 */
public class SimpleDBModel implements IModel {
    String driverFullQualifieldName = "com.mysql.jdbc.Driver";
    String connectionString = "jdbc:mysql://172.19.0.3:3306/CostManager";
    final String USER_NAME = "root";
    final String PASSWORD = "1";

    public SimpleDBModel() throws CostItemException {
        try {
            Class.forName(driverFullQualifieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostItemException("problem with registing driver to the driver manager", e);
        }
    }


    /**
     * This method is responsible for getting all the items from the database.
     * @param user the user whose items will be returned
     * @return
     * @throws CostItemException
     */
    @Override
    public Collection<Item> getItems(User user) throws CostItemException{
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            ResultSet rs = statement.executeQuery("SELECT  * FROM  items i inner join categories c on i.category = c.category_id where i.ownerId="+user.getUserId());
            List<Item> items = new LinkedList<>();
            while (rs.next())
            {
                String name = rs.getString("name");
                String description = rs.getString("description");
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                int ownerId = rs.getInt("ownerId");
                int id = rs.getInt("id");
                double cost = rs.getDouble("cost");
                int currency = rs.getInt("currency");
                Date date = rs.getDate("date");
                items.add(new Item(id,ownerId,name,description,currency,cost,date,new Category(user,categoryName,categoryId)));

            }
            return items;
        }catch (SQLException e){
            throw new CostItemException("getItems error!",e);

        }
    }

    /**
     * This method is responsible for deleting an item from the database.
     * @param item the item to be deleted from the user's list of items
     * @throws CostItemException
     */
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

    /**
     * This method is responsible for updating an item to the database.
     * @param item the item to be updated in the user's list of items
     * @throws CostItemException
     */
    @Override
    public void updateItem(Item item) throws CostItemException {
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            PreparedStatement addItemStatment = connection.prepareStatement("UPDATE items WHERE id = ? values (name, cost, category, currency, description, date) VALUES (?,?,?,?,?,?)");
            addItemStatment.setInt(1,item.getId());
            addItemStatment.setString(2, item.getName());
            addItemStatment.setDouble(3,item.getCost());
            addItemStatment.setInt(4,item.getCategory().getId());
            addItemStatment.setInt(5,item.getCurrency());
            addItemStatment.setString(6,item.getDescription());
            addItemStatment.setDate(7, new java.sql.Date(item.getDate().getTime()));
            addItemStatment.executeUpdate();

        }catch (SQLException e){
            throw new CostItemException("updateItem error!",e);
        }
    }

    /**
     * This method is responsible for adding a category to the database.
     * @param category
     * @throws CostItemException
     */
    @Override
    public void addCategory(Category category) throws CostItemException {
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            int rs = statement.executeUpdate("INSERT INTO  categories (ownerId,category_name) VALUES (" + category.getOwner().getUserId() +", " +'"' + category.getName() +'"' + ")");

        }catch (SQLException e){
            throw new CostItemException("add category error!",e);
        }
    }

    /**
     * This method is responsible for getting a list of categories from the database.
     * @param user the user whose categories will be returned
     * @return
     * @throws CostItemException
     */
    @Override
    public Collection<Category> getCategories(User user) throws CostItemException {
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            ResultSet rs = statement.executeQuery("SELECT  * FROM  categories where ownerId="+user.getUserId());
            List<Category> categories = new LinkedList<>();
            while (rs.next())
            {
                String temp = rs.getString("category_name");
                int id = rs.getInt("category_id");
                categories.add(new Category(user,temp,id));
            }
            return categories;
        }catch (SQLException e){
            throw new CostItemException("getCategories error!",e);

        }
    }

    /**
     * This method is responsible for deleting a category from the database.
     * @param category the category to be deleted from the user's list of categories
     * @throws CostItemException
     */
    @Override
    public void deleteCategory(Category category) throws CostItemException {
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            int rs = statement.executeUpdate("DELETE from categories where category_id="+ category.getId());

        }catch (SQLException e){
            throw new CostItemException("delete category error!",e);
        }
    }

    /**
     * This method is responsible for updating a category in the database.
     * @param category the category to be updated in the user's list of categories
     * @throws CostItemException
     */
    @Override
    public void updateCategory(Category category) throws CostItemException {
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            int rs = statement.executeUpdate("UPDATE categories SET ownerId=" + category.getOwner() +", category_name=" + category.getName() + "where category_id="+category.getId());

        }catch (SQLException e){
            throw new CostItemException("update category error!",e);
        }

    }

    /**
     * This method is responsible for logging in a user. It checks if the user exists in the database and if the password is correct.
     *
     * @param email the email of the user to be logged in
     * @param password the password of the user to be logged in
     * @return
     * @throws CostItemException
     */
    @Override
    public User login(String email, String password) throws CostItemException {
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            // simple query
            ResultSet rs = statement.executeQuery("SELECT  * FROM  users where email="+'"'+email+'"'+"and password="+'"'+password+'"');
            if(rs.next()) {
                return new User(rs.getString("firstName") + " " + rs.getString("lastName"), rs.getInt("id"));
            }
            return null;
        }catch (SQLException e){
            throw new CostItemException("login error!",e);

        }
    }

    /**
     * This method is responsible for adding an item to the database.
     *
     * @param item the Item to be added to the database
     * @throws CostItemException
     */
    @Override
    public void addItem(Item item) throws CostItemException{
        Connection connection = null ;
        try {
            //creating a connection object
            connection = DriverManager.getConnection(connectionString, "root", "1");

            // getting a statment object
            Statement statement = null;
            statement = connection.createStatement();


            PreparedStatement addItemStatment = connection.prepareStatement("INSERT INTO items (ownerId, name, cost, category, currency, description, date) VALUES (?,?,?,?,?,?,?)");
            addItemStatment.setInt(1,item.getOwner());
            addItemStatment.setString(2, item.getName());
            addItemStatment.setDouble(3,item.getCost());
            addItemStatment.setInt(4,item.getCategory().getId());
            addItemStatment.setInt(5,item.getCurrency());
            addItemStatment.setString(6,item.getDescription());
            addItemStatment.setDate(7, new java.sql.Date(item.getDate().getTime()));
            addItemStatment.executeUpdate();

        }catch (SQLException e){
            throw new CostItemException("getItems error!",e);
        }
    }


}