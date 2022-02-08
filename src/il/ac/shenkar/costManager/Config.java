package il.ac.shenkar.costManager;

/**
 * Config class is a class that holds all the application's configuration
 */
public class Config {
    static public final String DB_NAME = "CostManager";
    static public final String DB_USER = "<db-username>";
    static public final String DB_PASSWORD = "<db-password";
    static public final String DB_ONLY_URL = "jdbc:mysql://<db-ip>:<pd-port>/";
    static public final String DB_URL = DB_ONLY_URL + DB_NAME;
    static public final String DB_DRIVER = "com.mysql.jdbc.Driver";
}
