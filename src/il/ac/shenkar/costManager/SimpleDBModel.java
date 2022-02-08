package il.ac.shenkar.costManager;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements the IModel interface.
 * This class is responsible for connecting to the database and performing all the operations on the database.
 * This class uses MySQL JDBC driver.
 *
 * @see IModel
 */
public class SimpleDBModel implements IModel {
    String driverFullQualifiedName = Config.DB_DRIVER;
    String connectionString = Config.DB_URL;
    final static String USER_NAME = Config.DB_USER;
    final static String PASSWORD = Config.DB_PASSWORD;

    public SimpleDBModel() throws CostManagerException {
        try {
            Class.forName(driverFullQualifiedName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostManagerException("problem with registering driver to the driver manager", e);
        }
    }


    /**
     * This method is responsible for getting all the items from the database.
     *
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
            while (rs.next()) {
                String description = rs.getString("description");
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                int ownerId = rs.getInt("ownerId");
                int id = rs.getInt("id");
                double cost = rs.getDouble("cost");
                int currency = rs.getInt("currency");
                Date date = rs.getDate("date");
                items.add(new Item(id, ownerId, description, currency, cost, date, new Category(user, categoryName, categoryId)));
            }
            return items;
        } catch (SQLException e) {
            throw new CostManagerException("getItems error!", e);
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
     * This method is responsible for getting a list of categories from the database.
     *
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
            Collection<Category> categories = new LinkedList<>();
            while (rs.next()) {
                // create a category
                Category category = new Category(user, rs.getString("category_name"), rs.getInt("category_id"));
                // add the category to the list
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            throw new CostManagerException("getCategories error!", e);
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
     * This method is responsible for logging in a user. It checks if the user exists in the database and if the password is correct.
     *
     * @param email    the email of the user to be logged in
     * @param password the password of the user to be logged in
     * @return the user if the login is successful, null otherwise
     * @throws CostManagerException if there is a problem with the database. @link CostManagerException
     */
    @Override
    public User login(String email, String password) throws CostManagerException {
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement loginStatement = connection.prepareStatement("SELECT * FROM users WHERE email=?")
        ) {

            // setting the parameters
            loginStatement.setString(1, email);

            // execute the query
            rs = loginStatement.executeQuery();

            // check if the user exists
            if (rs.next()) {
                // check if the password is correct with hash
                if (BCrypt.checkpw(password, rs.getString("password"))) {
                    // create the user and return it
                    return new User(rs.getString("first_name") + " " + rs.getString("last_name"), rs.getInt("user_id"));
                } else {
                    throw new CostManagerException("Wrong password");
                }
            } else {
                throw new CostManagerException("User not found");
            }

        } catch (SQLException e) {
            throw new CostManagerException("login error!", e);
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
     * This method is responsible for registering a new user.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param email     the email of the user
     * @param password  the password of the user
     * @return
     * @throws CostManagerException
     */
    @Override
    public User register(String firstName, String lastName, String email, String password) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement registerStatement = connection.prepareStatement("INSERT INTO users(first_name, last_name, email, password) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            User newUser = null;
            // hash the password
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // setting the parameters
            registerStatement.setString(1, firstName);
            registerStatement.setString(2, lastName);
            registerStatement.setString(3, email);
            registerStatement.setString(4, hashedPassword);

            // execute the update
            int rs = registerStatement.executeUpdate();

            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("User not registered");
            }


            // get the generated user id
            ResultSet generatedKeys = registerStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newUser = new User(firstName + " " + lastName, generatedKeys.getInt(1));
            }

            if (newUser == null) {
                throw new CostManagerException("User not registered");
            }

            createCategory("Utilities", newUser);
            createCategory("Transport", newUser);
            createCategory("Entertainment", newUser);
            createCategory("Other", newUser);

            return newUser;

        } catch (SQLException e) {
            throw new CostManagerException("register error!", e);
        }
    }


    /**
     * This method is responsible for creating a new category.
     *
     * @param name  the name of the category
     * @param owner the owner of the category
     * @return the created category
     * @throws CostManagerException
     */
    @Override
    public Category createCategory(String name, User owner) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement addCategory = connection.prepareStatement("INSERT INTO categories (category_name, owner_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            // setting the parameters
            addCategory.setString(1, name);
            addCategory.setInt(2, owner.getUserId());

            // execute the query
            int rs = addCategory.executeUpdate();

            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("Category not added");
            }
            int categoryID;

            try (ResultSet generatedKeys = addCategory.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoryID = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }

            return new Category(owner, name, categoryID);

        } catch (SQLException e) {
            throw new CostManagerException("Category already exists", e);
        }
    }


    /**
     * This method is responsible for creating a new expense item.
     *
     * @param amount      - the amount of the item
     * @param category    - the category of the item
     * @param owner       - the owner of the item
     * @param description - the description of the item
     * @param currency    - the currency of the item
     * @param date        - the date of the item
     * @return the created expense item
     * @throws CostManagerException
     */
    @Override
    public Item createItem(String description, double amount, Category category, User owner, int currency, java.util.Date date) throws CostManagerException {
        try (Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
             PreparedStatement addItemStatement = connection.prepareStatement("INSERT INTO items (ownerId, description, cost, category, currency, date) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
        ) {
            // setting the parameters
            addItemStatement.setInt(1, owner.getUserId());
            addItemStatement.setString(2, description);
            addItemStatement.setDouble(3, amount);
            addItemStatement.setInt(4, category.getId());
            addItemStatement.setInt(5, currency);
            addItemStatement.setDate(6, new Date(date.getTime()));

            // execute the query
            int rs = addItemStatement.executeUpdate();

            // check if the update was successful
            if (rs == 0) {
                throw new CostManagerException("Item not added");
            }
            int userId;

            try (ResultSet generatedKeys = addItemStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return new Item(userId, owner.getUserId(), description, currency, amount, date, category);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CostManagerException("Create item error!", e);
        }
    }


}