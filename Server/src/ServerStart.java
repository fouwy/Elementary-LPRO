//import database.Database;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

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

    public synchronized static void addUserComms(String username, ObjectOutputStream output) {
        usersComms.put(username, output);
    }

    public synchronized static ObjectOutputStream getUserOutput(String username) {
        return usersComms.get(username);
    }

    public synchronized static void addToLoggedInUsers(String username) {
        usersOnline.add(username);
    }

    public synchronized static boolean isUserLoggedIn(String username) {
        return usersOnline.contains(username);
    }

    public synchronized static void userLoggedOut(String username) {
        usersOnline.remove(username);
    }
}
