import database.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ServerStart {

    private static HashSet<String> usersOnline;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6789);

        usersOnline = new HashSet<>();


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

    private static void printNumberAndNameOfMembers(Database database) {
        System.out.println("Number of members: " + database.getAllMembers().size());
        System.out.println(database.getAllMembers().toString());
    }

    public static boolean userLoggedIn(String username) {
        return !usersOnline.add(username);
    }

    public static void userLoggedOut(String username) {
        usersOnline.remove(username);
    }
}
