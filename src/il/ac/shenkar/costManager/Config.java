package il.ac.shenkar.costManager;

/**
 * Config class is a class that holds all the application's configuration
 */
public class Config {
    static public final String DB_NAME = "CMFinal";
    static public final String DB_USER = "root";
    static public final String DB_PASSWORD = "1";
    static public final String DB_ONLY_URL = "jdbc:mysql://172.19.0.2:3306/";
    static public final String DB_URL = DB_ONLY_URL + DB_NAME;
    static public final String DB_DRIVER = "com.mysql.jdbc.Driver";
}
