import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHelper {
    private static Connection connection = null;
    private static String pathToDB = "C:/sqlite3/jdbc-test/accounts.db";

    //Only have one connection for for everyone accessing
    public static synchronized Connection getConnection() throws SQLException {
        if(connection == null){
            System.out.println("Opening new connection to Database");
            connection = DriverManager.getConnection("jdbc:sqlite:"+ pathToDB);
        }
        return connection;
    }

    public static void setDatabasePath(String pathToDB) {
        SqliteHelper.pathToDB = pathToDB;
    }
}
