package il.ac.shenkar.costManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is responsible for creating the database and tables.
 */
public class Setup {
    /**
     * This method initializes the database.
     * @param args
     * @throws CostManagerException
     */
    public static void main(String[] args) throws CostManagerException {
        try {
            Class.forName(Config.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CostManagerException("problem with registering driver to the driver manager", e);
        }
        createDatabase();
        createTables();
    }

    /**
     * This method creates the database.
     */
    public static void createDatabase() {
        try (
                Connection conn = DriverManager.getConnection(Config.DB_ONLY_URL, Config.DB_USER, Config.DB_PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE " + Config.DB_NAME;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates the tables.
     */
    public static void createTables() {
        try (
                Connection conn = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            String createCategoriesTable = "CREATE TABLE `categories` (\n" +
                    "  `category_id` int(11) NOT NULL,\n" +
                    "  `category_name` varchar(255) NOT NULL,\n" +
                    "  `owner_id` int(11) NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

            String alterCategoriesTable = "ALTER TABLE `categories`\n" +
                    "  ADD PRIMARY KEY (`category_id`),\n" +
                    "ADD UNIQUE KEY `category_name_unique` (`owner_id`,`category_name`),\n" +
                    "  ADD KEY `owner_id` (`owner_id`),\n" +
                    "  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=169;";

            String createUsersTable = "CREATE TABLE `users` (" +
                    "`user_id` int(11) NOT NULL," +
                    "  `first_name` varchar(255) NOT NULL," +
                    "  `last_name` varchar(255) NOT NULL," +
                    "  `email` varchar(255) NOT NULL," +
                    "  `password` varchar(255) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

            String alterUserTable = "ALTER TABLE `users`\n" +
                    "  ADD PRIMARY KEY (`user_id`),\n" +
                    "  ADD UNIQUE KEY `email` (`email`),\n" +
                    "  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=85;";

            String createItemTable = "CREATE TABLE `items` (\n" +
                    "  `id` int(11) NOT NULL,\n" +
                    "  `ownerId` int(11) NOT NULL,\n" +
                    "  `description` varchar(255) NOT NULL,\n" +
                    "  `cost` double NOT NULL,\n" +
                    "  `category` int(11) NOT NULL,\n" +
                    "  `currency` int(11) NOT NULL DEFAULT 1,\n" +
                    "  `date` date NOT NULL DEFAULT current_timestamp()\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

            String alterItemsTable = "ALTER TABLE `items`\n" +
                    "  ADD PRIMARY KEY (`id`),\n" +
                    "  ADD KEY `ownerId` (`ownerId`),\n" +
                    "  ADD KEY `category` (`category`),\n" +
                    "  ADD KEY `currency` (`currency`),\n"+
                    " MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;";

            stmt.executeUpdate(createUsersTable);
            stmt.executeUpdate(alterUserTable);
            System.out.println("Created Users table");
            stmt.executeUpdate(createCategoriesTable);
            stmt.executeUpdate(alterCategoriesTable);
            System.out.println("Created Categories table");
            stmt.executeUpdate(createItemTable);
            stmt.executeUpdate(alterItemsTable);
            System.out.println("Created Items table");

            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


