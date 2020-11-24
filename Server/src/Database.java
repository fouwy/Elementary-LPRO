import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection connection;

    public Database(String pathToDatabase) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + pathToDatabase);
    }

    public void registerNewUser(String username, String password, String email) {
        String query = prepareQuery(email);

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setQueryTimeout(30); //seconds

            statement.setString(1, username);
            statement.setString(2, password);
            if (email != null)
                statement.setString(3, email);

            statement.executeUpdate();

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    public List<String> getAllMembers() {
        List<String>members = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("select username from member");
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                members.add(rs.getString(1));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return members;
    }

    public void deleteUser(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from member " +
                    "where username= ?;");
            statement.setString(1,username);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private String prepareQuery(String email) {
        String query;
        if (email == null)
            query = "insert into member(username, password) values(?, ?)";
        else
            query = "insert into member(username, password, email) values(?, ?, ?)";
        return query;
    }
}
