import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerThread implements Runnable{

    private Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ArrayList<ServerThread> clients;
    private int outputMessage = -1;

    public ServerThread(Socket socket, ArrayList<ServerThread> clients) {
        this.connection = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        startRunning();
    }

    public void startRunning() {
        try {
            setupStreams();
            while (!connection.isClosed())
                sendAndReceiveInfo();

        } catch (EOFException e) {
            System.out.println("EOF");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            closeServer();
        }
    }

    private void sendAndReceiveInfo() throws IOException, ClassNotFoundException {
        String[] accountInfo = (String[]) input.readObject();
        if (input == null)  return;

        String type = accountInfo[0];
        System.out.println(Arrays.toString(accountInfo));

        Database database = connectToDatabase();

        try {
            if(type.equals("Register")){
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
            }
            else if(type.equals("Login")){
                String username = accountInfo[2];
                if(database.canLogin(accountInfo)){
                    outputMessage = 1;
                }
                else if (!database.isUsernameTaken(username)){
                    outputMessage = 0;
                }
                else
                    outputMessage = -1;
            }
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
