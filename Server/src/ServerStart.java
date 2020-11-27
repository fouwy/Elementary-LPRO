import java.sql.SQLException;

public class ServerStart {
    public static void main(String[] args) {
        String pathToDatabase = "C:/sqlite3/jdbc-test/accounts.db";
        Database database;

        try {
            database = new Database(pathToDatabase);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return;
        }

        String[] accountInfo = {"pilantra2", "pass", null};
        database.registerNewUser(accountInfo);

        printNumberAndNameOfMembers(database);

        database.deleteUser("pilantra2");

        printNumberAndNameOfMembers(database);
    }

    private static void printNumberAndNameOfMembers(Database database) {
        System.out.println("Number of members: " + database.getAllMembers().size());
        System.out.println(database.getAllMembers().toString());
    }
}
