package il.ac.shenkar.costManager;

import java.sql.*;
import java.util.ArrayList;
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
    String connectionString = "jdbc:mysql://172.19.0.2:3306/CostManager";
    final static String USER_NAME = "root";
    final static String PASSWORD = "1";

    public SimpleDBModel() throws CostManagerException {
        try {
            Class.forName(driverFullQualifieldName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostManagerException("problem with registering driver to the driver manager", e);
        }
    }


    /**
     * This method is responsible for getting all the items from the database.
     * @param user the user whose items will be returned
     * @return a list of items
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public Collection<Item> getItems(User user) throws CostManagerException {
//        Connection connection = null ;
        ResultSet rs = null;
        try (
                //creating a connection object
                Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
                // getting a statement object
                PreparedStatement getItemsStatement = connection.prepareStatement("SELECT  * FROM  items i inner join categories c on i.category = c.category_id where i.ownerId=?")

        ) {
            // getting a statement object
            getItemsStatement.setInt(1, user.getUserId());

            // simple query
            rs = getItemsStatement.executeQuery();
            List<Item> items = new LinkedList<>();
            // iterating over the result set and adding the items to the list of items
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
            throw new CostManagerException("getItems error!",e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("problem with closing the result set object" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is responsible for deleting an item from the database.
     * @param item the item to be deleted from the user's list of items
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public void deleteItems(Item item) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement deleteItemStatement = connection.prepareStatement("DELETE FROM items WHERE id=?")
        ) {
            // setting the parameters
            deleteItemStatement.setInt(1, item.getId());
            // executing the query
            int rs = deleteItemStatement.executeUpdate();
            // checking the result
            if (rs == 0) {
                throw new CostManagerException("Item not found");
            }
        }catch (SQLException e){
            throw new CostManagerException("deleteItems error!",e);
        }
    }

    /**
     * This method is responsible for updating an item to the database.
     * @param item the item to be updated in the user's list of items
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public void updateItem(Item item) throws CostManagerException {
        try ( Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
              PreparedStatement updateItemStatement = connection.prepareStatement("UPDATE items SET name=?, description=?, category=?, cost=?, currency=?, date=? WHERE id=?")
        ) {
            // setting the parameters
            updateItemStatement.setString(1, item.getName());
            updateItemStatement.setString(2, item.getDescription());
            updateItemStatement.setInt(3, item.getCategory().getId());
            updateItemStatement.setDouble(4, item.getCost());
            updateItemStatement.setInt(5, item.getCurrency());
            updateItemStatement.setDate(6, new java.sql.Date(item.getDate().getTime()));
            updateItemStatement.setInt(7, item.getId());
            // execute the update
            int rs = updateItemStatement.executeUpdate();
            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("Item not found");
            }

        }catch (SQLException e){
            throw new CostManagerException("updateItem error!",e);
        }
    }

    /**
     * This method is responsible for adding a category to the database.
     * @param category the category to be added to the database
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public void addCategory(Category category) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement addCategoryStatement = connection.prepareStatement("INSERT INTO categories (owner_id, category_name) VALUES (?,?)")
        ) {
            // setting the parameters
            addCategoryStatement.setInt(1, category.getOwner().getUserId());
            addCategoryStatement.setString(2, category.getName());

            // execute the update
            int rs = addCategoryStatement.executeUpdate();

            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("Category not added");
            }

        } catch (SQLException e){
            throw new CostManagerException("add category error!",e);
        }
    }

    /**
     * This method is responsible for getting a list of categories from the database.
     * @param user the user whose categories will be returned
     * @return a list of categories
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public Collection<Category> getCategories(User user) throws CostManagerException {
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement getCategoriesStatement = connection.prepareStatement("SELECT * FROM categories WHERE owner_id=?")
        ) {
            // setting the parameters
            getCategoriesStatement.setInt(1, user.getUserId());

            // execute the query
            rs = getCategoriesStatement.executeQuery();

            // create a list of categories
            Collection<Category> categories = new LinkedList<>( );
            while (rs.next()) {
                // create a category
                Category category = new Category(user, rs.getString("category_name"), rs.getInt("category_id"));
                // add the category to the list
                categories.add(category);
            }
            return categories;
        }catch (SQLException e){
            throw new CostManagerException("getCategories error!",e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Could not close ResultSet in getCategories " + e.getMessage());
                }
            }
        }
    }

    /**
     * This method is responsible for deleting a category from the database.
     * @param category the category to be deleted from the user's list of categories
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public void deleteCategory(Category category) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement deleteCategoryStatement = connection.prepareStatement("DELETE FROM categories WHERE category_id=?")
        ) {
            // setting the parameters
            deleteCategoryStatement.setInt(1, category.getId());

            // execute the update
            int rs = deleteCategoryStatement.executeUpdate();

            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("Category not deleted");
            }

        }catch (SQLException e){
            throw new CostManagerException("delete category error!",e);
        }
    }

    /**
     * This method is responsible for updating a category in the database.
     * @param category the category to be updated in the user's list of categories
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public void updateCategory(Category category) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement updateCategoryStatement = connection.prepareStatement("UPDATE categories SET category_name=? WHERE category_id=?")
        ) {
            // setting the parameters
            updateCategoryStatement.setString(1, category.getName());
            updateCategoryStatement.setInt(2, category.getId());

            // execute the update
            int rs = updateCategoryStatement.executeUpdate();

            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("Category not updated");
            }

        }catch (SQLException e){
            throw new CostManagerException("update category error!",e);
        }

    }

    /**
     * This method is responsible for logging in a user. It checks if the user exists in the database and if the password is correct.
     *
     * @param email the email of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the user if the login is successful, null otherwise
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public User login(String email, String password) throws CostManagerException {
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement loginStatement = connection.prepareStatement("SELECT * FROM users WHERE email=? AND password=?")
        ) {
            // setting the parameters
            loginStatement.setString(1, email);
            loginStatement.setString(2, password);

            // execute the query
            rs = loginStatement.executeQuery();

            // check if the user exists
            if (rs.next()) {
                // create the user
               return new User(rs.getString("first_name") + " " + rs.getString("last_name"), rs.getInt("user_id"));
            }
            return null;

        }catch (SQLException e){
            throw new CostManagerException("login error!",e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error closing result set " + e.getMessage());
                }
            }
        }
    }

    /**
     * This method is responsible for adding an item to the database.
     *
     * @param item the Item to be added to the database
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public void addItem(Item item) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement addItemStatement = connection.prepareStatement("INSERT INTO items (ownerId, name, cost, category, currency, description, date) VALUES (?,?,?,?,?,?,?)")
        ) {
            // setting the parameters
            addItemStatement.setInt(1,item.getOwner());
            addItemStatement.setString(2, item.getName());
            addItemStatement.setDouble(3,item.getCost());
            addItemStatement.setInt(4,item.getCategory().getId());
            addItemStatement.setInt(5,item.getCurrency());
            addItemStatement.setString(6,item.getDescription());
            addItemStatement.setDate(7, new Date(item.getDate().getTime()));

            // execute the query
            int rs = addItemStatement.executeUpdate();

            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("Item not added");
            }

        }catch (SQLException e){
            throw new CostManagerException("getItems error!",e);
        }
    }


}