import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Server {

    private ServerSocket server;
    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Server() {
        try {
            server = new ServerSocket(6789, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startRunning() {
        try {
            while(true) {
                waitForConnection();
                setupStreams();
                try {
                    sendAndReceiveInfo();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServer();
        }

    }


    private void sendAndReceiveInfo() throws IOException, ClassNotFoundException {
        String[] accountInfo = (String[]) input.readObject();
        System.out.println(Arrays.toString(accountInfo));

    }

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for connection...");
        connection = server.accept();
        System.out.println("Connected to "+connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    private void closeServer() {
        System.out.println("Closing Server...");
        try {
            output.close();
            input.close();
            connection.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
