import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.Arrays;

public class Server {

    private ServerSocket server;
    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int outputMessage = -1;

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
                sendAndReceiveInfo();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeServer();
        }

    }


    private void sendAndReceiveInfo() throws IOException, ClassNotFoundException {
        String[] accountInfo = (String[]) input.readObject();
        System.out.println(Arrays.toString(accountInfo));

        Database database = connectToDatabase();
        //TODO: Change outputMessage to integer to know difference between error and username already taken
        try {
            if(database.isRegisterAllowed(accountInfo)) {
                outputMessage = 1;
                try {
                    database.registerNewUser(accountInfo);
                } catch (SQLException e) {
                    e.printStackTrace();
                    outputMessage = -1;
                }
            }
            else
                outputMessage = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            outputMessage = -1;
        }

        output.writeObject(outputMessage);
        output.flush();
    }

    private Database connectToDatabase() {
        Database database = null;
        try {
            database = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
            outputMessage = -1;
        }
        return database;
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
