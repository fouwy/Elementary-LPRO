import database.Database;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.Arrays;

public class ServerThread implements Runnable{

    private final Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int outputMessage = -1;

    public ServerThread(Socket socket) {
        this.connection = socket;
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

        if (type.equals("Host")) {
            //TODO:-Generate port number and send it back;
            //     -Start Lobby and open a socket with that port number;
            int port_number = 6666;     //this is just for test, later can generate port numbers random
            output.writeObject(port_number);
            output.flush();
            new Lobby(accountInfo[1], port_number);

        } else {
            Database database = connectToDatabase();

            try {
                if (type.equals("Register")) {
                    if (database.isRegisterAllowed(accountInfo)) {
                        outputMessage = 1;
                        try {
                            database.registerNewUser(accountInfo);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            outputMessage = -1;
                        }
                    } else
                        outputMessage = 0;

                } else if (type.equals("Login")) {
                    String username = accountInfo[1];
                    if (ServerStart.userLoggedIn(username))
                        outputMessage = 2;                      //User Already Logged in
                    else if (database.canLogin(accountInfo)) {
                        outputMessage = 1;
                    } else if (!database.isUsernameTaken(username)) {
                        outputMessage = 0;
                    } else
                        outputMessage = -1;

                } else if (type.equals("AddFriend")){
                    String friendUsername = accountInfo[1];

                    if(database.canAddFriend(accountInfo)){
                        outputMessage = 1;
                        try {
                            database.addFriend(accountInfo);
                        } catch (SQLException e) {
                            outputMessage = 2;
                        }

                    } else if (!database.isUsernameTaken(friendUsername)){
                        outputMessage = 0;

                    } else
                        outputMessage = -1;

                } else if (type.equals("RemoveFriend")){

                    if(database.canRemoveFriend(accountInfo)) {

                        try {
                            database.removeFriend(accountInfo);
                        } catch (SQLException e) {
                            outputMessage = -1;
                        }
                        outputMessage = 1;
                    } else
                        outputMessage = 0;

                } else if (type.equals("ChangePassword")){

                    if(database.ChangePassword(accountInfo)){
                        outputMessage = 1;
                    } else
                        outputMessage = -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                outputMessage = -1;
            }

            output.writeObject(outputMessage);
            output.flush();
        }
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
