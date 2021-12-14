package il.ac.shenkar.costManager;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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