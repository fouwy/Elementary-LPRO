package game;
import common.MainLogic;

public class Account {
    private static String username;
    private static char[] password;
    private static int myCharacter;
    private static String friendUsername;

    public Account(String username, char[] password) {
        Account.username = username;
        Account.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setMyCharacter(int character) {
        myCharacter = character;
    }

    public static String getFriendUsername (){
        return friendUsername;
    }

    public static char[] getPassword() { return password;}

    public static int getCharNumber() {
        return myCharacter;
    }
}
