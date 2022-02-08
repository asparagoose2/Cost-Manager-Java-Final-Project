package il.ac.shenkar.costManager;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DBModelTest {
    static private IModel model;
    static String driverFullQualifieldName = "com.mysql.jdbc.Driver";
    static String connectionString = "jdbc:mysql://172.19.0.2:3306/CostManager";
    static final String USER_NAME = "root";
    static final String PASSWORD = "1";
    static User user;
    static Category category;
    static Item item;


    @BeforeAll
    public static void init() throws CostManagerException {
        model = new SimpleDBModel();
    }


    @Test
    @Order(1)
    void testAddUser() throws CostManagerException {
        user = model.register("test_first_name", "test_last_name", "test_email", "test_password");
        assert (user != null);
        assert (user.getUserId() != 0);
        assert (user.getName().equals("test_first_name test_last_name"));

    }

    @Test
    @Order(4)
    void testLogin() throws CostManagerException {
        user = model.login("test_email", "test_password");
        assert (user.getUserId() != 0);
        assert (user.getName().equals("test_first_name test_last_name"));

    }

    @Test
    @Order(3)
    void testFailedLoginWrongPassword() {
        CostManagerException thrown = assertThrows(CostManagerException.class, () -> model.login("test_email", "wrong_password"));
        Assertions.assertEquals("Wrong password", thrown.getMessage());
    }

    @Test
    @Order(2)
    void testFailedLoginWrongEmail() {
        CostManagerException thrown = assertThrows(CostManagerException.class, () -> model.login("wrong_email", "test_password"));
        Assertions.assertEquals("User not found", thrown.getMessage());
    }

    @Test
    @Order(5)
    void createCategory() throws CostManagerException {
        category = model.createCategory("test_category", user);
        assert (category.getId() != 0);
        assert (category.getName().equals("test_category"));
        assert (category.getOwner().getUserId() == user.getUserId());
    }

    @Test
    @Order(6)
    void createCategoryWithSameName() {
        CostManagerException thrown = assertThrows(CostManagerException.class, () -> model.createCategory("test_category", user));
        Assertions.assertEquals("Category already exists", thrown.getMessage());
    }

    @Test
    @Order(7)
    void createCategoryWithSameNameAndDifferentOwner() throws CostManagerException, SQLException {
        User user2 = null;
        AtomicReference<Category> category2 = new AtomicReference<>();
        try {
            user2 = model.register("test_first_name2", "test_last_name2", "test_email2", "test_password2");
            User user3 = user2;
            Assertions.assertDoesNotThrow(() -> category2.set(model.createCategory("test_category", user3)));
        } finally {
            if (user2 != null) {
                Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
                if (category2.get() != null) {
                    connection.createStatement().execute("DELETE FROM categories WHERE category_id = " + category2.get().getId());
                }
                for (Category c : model.getCategories(user2)) {
                    connection.createStatement().execute("DELETE FROM categories WHERE category_id = " + c.getId());
                }
                connection.createStatement().execute("DELETE FROM users WHERE user_id = " + user2.getUserId());
                connection.close();
            }
        }

    }

    @Test
    @Order(8)
    void createItem() throws CostManagerException {
        item = model.createItem("test_item", 1234, category, user, 1, new Date());
        assert (item.getId() != 0);
        assert (item.getDescription().equals("test_item"));
        assert (item.getCategory().getId() == category.getId());
    }

    @Test
    @Order(9)
    void testGetItems() throws CostManagerException {
        List<Item> items = (List<Item>) model.getItems(user);
        assert (items.size() == 1);
        assert (items.get(0).getDescription().equals("test_item"));
        assert (items.get(0).getCategory().getId() == category.getId());
    }

    @AfterAll
    public static void close() throws SQLException, CostManagerException {
        // sql query to delete user with email test_email
        Connection connection = DriverManager.getConnection(connectionString, USER_NAME, PASSWORD);
        if (item != null) {
            connection.createStatement().executeUpdate("DELETE FROM items WHERE id = " + item.getId());
        }
        if (user != null) {
            for (Category c : model.getCategories(user)) {
                connection.createStatement().execute("DELETE FROM categories WHERE category_id = " + c.getId());
            }
        }
        if (category != null) {
            connection.createStatement().executeUpdate("DELETE FROM categories WHERE category_name = 'test_category'");
        }
        connection.createStatement().executeUpdate("DELETE FROM users WHERE email = 'test_email'");
        connection.close();
    }

}
