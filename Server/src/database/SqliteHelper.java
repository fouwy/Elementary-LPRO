package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to manage the connection to the database.
 */
public class SqliteHelper {
    private static Connection connection = null;
    private static String pathToDB = "C:/sqlite3/jdbc-test/accounts_friends.db";

    /**
     * Opens a new connection to the database and stores it for when
     * it is requested again.<br>
     * This makes sure there is only one open connection to the database
     * at any time.
     * @return a connection to the database.
     * @throws SQLException a database connection error occurs.
     */
    public static synchronized Connection getConnection() throws SQLException {
        if(connection == null){
            System.out.println("Opening new connection to database.Database");
            connection = DriverManager.getConnection("jdbc:sqlite:"+ pathToDB);
        }
        return connection;
    }

    public static void setDatabasePath(String pathToDB) {
        SqliteHelper.pathToDB = pathToDB;
    }
}
