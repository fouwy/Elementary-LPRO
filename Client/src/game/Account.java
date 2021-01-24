package game;

import java.io.PrintWriter;

/**
 * Stores information about this client.
 */
public class Account {
    private static String username;
    private static char[] password;
    private static int myCharacter;
    private static int currentLobbyCode;
    private static PrintWriter lobbyOutput;

    /**
     * Creates the account and stores the information
     * specified.
     * @param username the username of this client.
     * @param password the password of this client.
     */
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

    public static char[] getPassword() { return password;}

    public static int getCharNumber() {
        return myCharacter;
    }

    public static void setLobbyCode(int port_number) {
        currentLobbyCode = port_number;
    }

    public static int getCurrentLobbyCode() {
        return currentLobbyCode;
    }

    public static PrintWriter getLobbyOutput() {
        return lobbyOutput;
    }

    public static void setLobbyOutput(PrintWriter lobbyOutput) {
        Account.lobbyOutput = lobbyOutput;
    }

}
