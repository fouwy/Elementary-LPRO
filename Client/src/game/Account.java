package game;

public class Account {
    private static String username;
    private static int myCharacter;

    public Account(String username) {
        Account.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setMyCharacter(int character) {
        myCharacter = character;
    }
}
