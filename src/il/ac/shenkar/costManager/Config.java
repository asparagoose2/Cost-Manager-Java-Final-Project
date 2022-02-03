package il.ac.shenkar.costManager;

public class Config {
    static public final String DB_NAME = "CostManager";
    static public final String DB_USER = "<username>";
    static public final String DB_PASSWORD = "<password>";
    static public final String DB_ONLY_URL = "jdbc:mysql://<ip and port>/";
    static public final String DB_URL = DB_ONLY_URL + DB_NAME;
    static public final String DB_DRIVER = "com.mysql.jdbc.Driver";
}
