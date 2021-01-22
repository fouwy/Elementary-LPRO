
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class is responsible for starting the server and to create a new
 * thread when a client wants to communicate with the server.<br>
 * It also keeps track of the users that are logged in.
 */
public class ServerStart {

    private static HashSet<String> usersOnline;
    private static HashMap<String, ObjectOutputStream> usersComms;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6789);

        usersOnline = new HashSet<>();
        usersComms = new HashMap<>();

        while(true) {
            System.out.println("Server waiting for connections...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            System.out.println("Users logged in: " + usersOnline.toString());
            ServerThread client = new ServerThread(socket);
            Thread thread = new Thread(client);
            thread.start();
        }


    }

//    private static void printNumberAndNameOfMembers(Database database) {
//        System.out.println("Number of members: " + database.getAllMembers().size());
//        System.out.println(database.getAllMembers().toString());
//    }

    /**
     * Adds a user with the specified username and their output to a
     * HashMap to be able to send messages directly to them.
     * @param username the username to be added.
     * @param output the output to be used to send messages to this user.
     */
    public synchronized static void addUserComms(String username, ObjectOutputStream output) {
        usersComms.put(username, output);
    }

    public synchronized static ObjectOutputStream getUserOutput(String username) {
        return usersComms.get(username);
    }

    /**
     * Adds a username to a HashSet of logged in users.
     * @param username the username to be added.
     */
    public synchronized static void addToLoggedInUsers(String username) {
        usersOnline.add(username);
    }

    /**
     * Check if the user specified is logged in.
     * @param username the username to check.
     * @return true if the user is logged in,
     *         false if they are not.
     */
    public synchronized static boolean isUserLoggedIn(String username) {
        return usersOnline.contains(username);
    }

    /**
     * Removes the user from the Set of logged in users.
     * @param username the username to be removed.
     */
    public synchronized static void userLoggedOut(String username) {
        usersOnline.remove(username);
    }
}
