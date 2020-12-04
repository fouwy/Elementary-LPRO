import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerStart {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6789);

        ArrayList<ServerThread> clients = new ArrayList<>();

        while(true) {
            System.out.println("Server waiting for connections...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            ServerThread client = new ServerThread(socket, clients);
            clients.add(client);
            Thread thread = new Thread(client);
            thread.start();
        }


    }

    private static void printNumberAndNameOfMembers(Database database) {
        System.out.println("Number of members: " + database.getAllMembers().size());
        System.out.println(database.getAllMembers().toString());
    }
}
