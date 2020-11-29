import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    Connection connection;

    public Database() throws SQLException {
        connection = SqliteHelper.getConnection();
    }

    public void registerNewUser(String[] accountInfo) throws SQLException {
        String email = accountInfo[0];
        String username = accountInfo[1];
        String password = accountInfo[2];
        String query = prepareQuery(email);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        if (email != null)
            statement.setString(3, email);

        statement.executeUpdate();
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

    public boolean isRegisterAllowed(String[] accountInfo) throws SQLException{
        return !isUsernameTaken(accountInfo[1]);
    }

    public boolean isUsernameTaken(String username) throws SQLException{   //change to private

        PreparedStatement statement = connection.prepareStatement("select username from member "+
                                                                    "where username=?;");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        //Returns true if there is one row or false if there are no rows in database
        return rs.next();
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
