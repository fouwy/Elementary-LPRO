package game;
import common.MainLogic;

public class Account {
    private static String username;
    private static int myCharacter;
    private static String friendUsername;

    public Account(String username) {
        Account.username = username;
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
}
