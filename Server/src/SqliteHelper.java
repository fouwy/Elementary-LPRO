import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHelper {
    private static Connection c = null;

    //Only have one connection for for everyone accessing
    //TODO: When creating more threads for server maybe have to change to synchronized method
    public static Connection getConnection() throws SQLException {
        if(c == null){
            System.out.println("Opening new connection to Database");
            c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/jdbc-test/accounts.db");
        }
        return c;
    }
}
