import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHelper {
    private static Connection c = null;
    public static Connection getConnection() throws SQLException {
        if(c == null){
            c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/jdbc-test/accounts.db");
        }
        return c;
    }
}
