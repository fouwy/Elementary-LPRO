import database.Database;
import database.SqliteHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseTest {
    private Database database;
    String[] accountInfo = {"Register", "testUser", "testPassword", "email@gmail.com"};

    @Before
    public void setUp() throws SQLException {
        SqliteHelper.setDatabasePath("C:/sqlite3/JUnitTests/testAccounts.db");
        database = new Database();
    }

    @After
    public void tearDown() throws SQLException {
        List<String> members = database.getAllMembers();

        for (String member : members) {
            String[] memberArray = {"", member};
            List<String> friends = database.getFriends(memberArray);
            for (String friend : friends) {
                String[] friendArray = {"", member, friend};
                database.removeFriend(friendArray);
            }
            database.deleteUser(memberArray);
        }
    }

    @Test
    public void afterRegisterNewUser_WillHaveOneUser() throws SQLException {
        assertEquals(0, database.getAllMembers().size());

        database.registerNewUser(accountInfo);
        List<String> members = database.getAllMembers();

        assertEquals(1, members.size());
        assertEquals("testUser", members.get(0));
    }

    @Test
    public void afterRegisterUser_UsernameWillBeTaken() throws SQLException {
        assertFalse(database.isUsernameTaken(accountInfo[1]));
        database.registerNewUser(accountInfo);
        assertTrue(database.isUsernameTaken(accountInfo[1]));
    }

    @Test (expected = SQLException.class)
    public void willThrowException_WhenUsernameOfNewRegisterIsTaken() throws SQLException {
        String[] registeredUser = {"Register", "testUser", "otherPassword", "other@gmail.com"};
        database.registerNewUser(registeredUser);
        database.registerNewUser(accountInfo);
    }

    @Test
    public void afterRegisterAndDeleteUser_WillHaveZeroUsers() throws SQLException {
        assertEquals(0, database.getAllMembers().size());
        database.registerNewUser(accountInfo);
        database.deleteUser(accountInfo);
        List<String> members = database.getAllMembers();
        assertEquals(0, members.size());
    }

    @Test
    public void willChangePassword() throws SQLException {
        database.registerNewUser(accountInfo);
        String[] newAccountInfo = {"", "testUser", "newPassword"};
        database.ChangePassword(newAccountInfo);
        assertTrue(database.canLogin(newAccountInfo));
    }

    @Test
    public void willAddFriend() throws SQLException {
        database.registerNewUser(accountInfo);
        database.registerNewUser(new String[]{"", "friend", "password", "friend@gmail.com"});
        database.addFriend(new String[]{"", "testUser", "friend"});
        List<String> friends = database.getFriends(accountInfo);
        assertTrue(friends.contains("friend"));
    }

    @Test
    public void willRemoveFriend() throws SQLException {
        database.registerNewUser(accountInfo);
        database.registerNewUser(new String[]{"", "friend", "password", "friend@gmail.com"});
        database.addFriend(new String[]{"", "testUser", "friend"});
        List<String> friends = database.getFriends(accountInfo);
        assertTrue(friends.contains("friend"));

        database.removeFriend(new String[]{"", "testUser", "friend"});
        List<String> friendsAfter = database.getFriends(accountInfo);
        assertFalse(friendsAfter.contains("friend"));
    }

}