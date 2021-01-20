package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for communication with
 * the database.
 */
public class Database {
    Connection connection;

    /**
     * Creates a connection to the database.
     * @throws SQLException if it cannot connect to
     * the database.
     */
    public Database() throws SQLException {
        connection = SqliteHelper.getConnection();
    }

    /**
     * Attempts to add a new user to the database.
     * The username must be unique or a {@code SQLException} will be thrown.
     * @param accountInfo the username, password and email of the new user.
     * @throws SQLException if it could not register add the user.
     */
    public void registerNewUser(String[] accountInfo) throws SQLException {
        String username = accountInfo[1];
        String password = accountInfo[2];
        String email = accountInfo[3];
        String query = prepareQuery(email);

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        if (email != null)
            statement.setString(3, email);

        statement.executeUpdate();
    }

    /**
     * Checks if the username and password specified match
     * the entry in the database.
     * @param accountInfo the username and password.
     * @return true if username exists and password is correct,
     * false otherwise.
     * @throws SQLException a database connection error occurs.
     */
    public boolean canLogin(String[] accountInfo) throws SQLException{
        String username = accountInfo[1];
        String password = accountInfo[2];
        ResultSet rs;

        PreparedStatement statement = connection.prepareStatement("select password from member "+ "where username=?;");

        //Is username exists verify password
        if(isUsernameTaken(username)){
            statement.setString(1, username);
            rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getString(1).equals(password);
            }
        }
        return false;
    }

    /**
     * Queries the database for all the members in it.
     * @return a {@code List} of all usernames in the database.
     */
    public List<String> getAllMembers() {
        List<String>members = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("select username from member;");
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                members.add(rs.getString(1));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return members;
    }

    /**
     * Attempts to delete a member from the database
     * with the specified username.
     * @param accountInfo the username.
     * @return true if the user was deleted,
     * false if could not delete the user.
     */
    public boolean deleteUser(String [] accountInfo){
        String username = accountInfo[1];
        try {
            PreparedStatement statement = connection.prepareStatement("delete from member " +
                    "where username= ?;");
            statement.setString(1,username);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Checks if the specified username is allowed
     * to register.
     * @param accountInfo the username.
     * @return true if register is allowed,
     * false if not.
     * @throws SQLException a database connection error occurs.
     */
    public boolean isRegisterAllowed(String[] accountInfo) throws SQLException{
        return !isUsernameTaken(accountInfo[1]);
    }

    /**
     * Checks if the username specified is taken.
     * The database is queried to check if there is
     * a member whose username is the same as the specified.
     * @param username the username of the member.
     * @return true if the username is taken,
     * false if it is not.
     * @throws SQLException a database connection error occurs.
     */
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

    /**
     * Turns two members into friends.
     * Inserts the other user as a friend to each member specified
     * in the database.
     * @param accountInfo the usernames of the members to be added as friends.
     * @throws SQLException a database connection error occurs.
     */
    public void addFriend(String[] accountInfo) throws SQLException{
        String username = accountInfo[1];
        String friendUsername = accountInfo[2];
        PreparedStatement statement = connection.prepareStatement("insert into friends (username, friend_name) values (?, ?);");
        PreparedStatement statement2 = connection.prepareStatement("insert into friends (username, friend_name) values (?, ?);");

        if(canAddFriend(accountInfo)) {
            statement.setString(1, username);
            statement.setString(2, friendUsername);
            statement.executeUpdate();
            statement2.setString(1, friendUsername);
            statement2.setString(2, username);
            statement2.executeUpdate();
        }
    }

    /**
     * Checks if the specified member can be added as a friend.
     * @param accountInfo the username of the friend
     *                    to add.
     * @return true if friend can be added,
     * false if they cannot be added.
     * @throws SQLException a database connection error occurs.
     */
    public boolean canAddFriend(String[] accountInfo) throws SQLException{
        String friendUsername = accountInfo[2];

        if(isUsernameTaken(friendUsername)){
            return true;
        }
        return false;
    }

    /**
     * Checks if the specified member exists and
     * therefore can be removed from the friends
     * of this user.
     * @param accountInfo the friend's username.
     * @return true if friend can be removed,
     * false if they cannot be removed.
     * @throws SQLException a database connection error occurs.
     */
    public boolean canRemoveFriend(String[] accountInfo) throws SQLException{
        String friendUsername = accountInfo[2];
        PreparedStatement statement = connection.prepareStatement("select friend_name from friends "+ "where friend_name=?;");
        statement.setString(1, friendUsername);
        ResultSet rs = statement.executeQuery();
        //Returns true if there is one row or false if there are no rows in database
        return rs.next();
    }

    /**
     * Removes a friend from the specified user.
     * Deletes the specified friend from the friend entries of
     * this specified user in the database.
     * @param accountInfo usernames of user and friend.
     * @return true if removed friend succesfully,
     * false if could not remove friend.
     * @throws SQLException a database connection error occurs.
     */
    public boolean removeFriend(String[] accountInfo) throws SQLException{
        String username = accountInfo[1];
        String friendUsername = accountInfo[2];
        PreparedStatement statement = connection.prepareStatement("DELETE FROM friends WHERE username = ? AND friend_name=?");
        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM friends WHERE username = ? AND friend_name=?");

        try{
            statement.setString(1,friendUsername);
            statement.setString(2,username);
            statement.executeUpdate();
            statement2.setString(1,username);
            statement2.setString(2,friendUsername);
            statement2.executeUpdate();
            return true;
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Changes the password of a member.
     * Updates the database with the new password of the specified
     * member.
     * @param accountInfo username and new password of the member.
     * @return true if was succesfull, false if not.
     * @throws SQLException a database connection error occurs.
     */
    public boolean ChangePassword(String[] accountInfo) throws SQLException{
        String username = accountInfo[1];
        String password = accountInfo[2];
        PreparedStatement statement = connection.prepareStatement("UPDATE member SET password=? WHERE username=?" );
        try{
            statement.setString(1, password);
            statement.setString(2, username);
            int i = statement.executeUpdate();
            System.out.println(username);
            System.out.println(password);
            System.out.println(i+" passwords updated");
            return true;
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Queries the database for the friends of the
     * specified member.
     * @param accountInfo the username of the member.
     * @return a {@code List} of all the friends of this
     * member.
     */
    public List<String> getFriends(String[] accountInfo) {
        List<String>friendsList = new ArrayList<>();
        String username = accountInfo[1];

        try {
            PreparedStatement statement = connection.prepareStatement("select friend_name from member join friends using(username) where username=?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
                friendsList.add(rs.getString(1));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return friendsList;
    }
}


