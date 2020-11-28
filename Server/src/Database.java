import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection connection;

    public Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite3/jdbc-test/accounts.db");
    }

    public void registerNewUser(String[] accountInfo) {
        String email = accountInfo[0];
        String username = accountInfo[1];
        String password = accountInfo[2];
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

    public boolean isRegisterAllowed(String[] accountInfo) {
        return !isUsernameTaken(accountInfo[1]);
    }

    public boolean isUsernameTaken(String username) {   //change to private
        try {
            PreparedStatement statement = connection.prepareStatement("select username from member "+
                                                                        "where username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            //Returns true if there is one row or false if there are no rows in database
            return rs.next();
        } catch (SQLException e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return true;
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
