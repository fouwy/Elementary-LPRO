import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerStart {

    private static HashSet<String> usersOnline;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6789);

        ArrayList<ServerThread> clients = new ArrayList<>();

        usersOnline = new HashSet<>();


        while(true) {
            System.out.println("Server waiting for connections...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            ServerThread client = new ServerThread(socket, clients);
            clients.add(client);
            System.out.println(clients.toString());
            Thread thread = new Thread(client);
            thread.start();
        }


    }

    private static void printNumberAndNameOfMembers(Database database) {
        System.out.println("Number of members: " + database.getAllMembers().size());
        System.out.println(database.getAllMembers().toString());
    }

    public static void userLoggedIn(String username) {
        //TODO: Check for duplicates trying to log in
        usersOnline.add(username);
    }

    public static void userLoggedOut(String username) {
        usersOnline.remove(username);
    }
}
