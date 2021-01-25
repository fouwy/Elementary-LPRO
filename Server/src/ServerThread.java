import database.Database;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class handles all the communication with the clients,
 * except the Lobby and Game, using a Socket and ObjectOutputStream
 * and ObjectInputStream.
 */
public class ServerThread implements Runnable{

    private final Socket connection;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int outputMessage = -1;

    /**
     * Creates an unconnected ServerThread.
     * @param socket The socket used to connect to the client.
     */
    public ServerThread(Socket socket) {
        this.connection = socket;
    }

    @Override
    public void run() {
        startRunning();
    }

    /**
     * Makes the connection to the client and keeps it open until
     * it is closed by the client.
     * The behavior of this method depends on the "type" or the
     * first String from the String array received in the input.
     * <p>
     * For the following "types" this is the behavior:<p>
     * <b>Host</b><p>
     * -Creates a new Lobby hosted on a random port number between
     * 4000 and 6999 and outputs the port chosen.
     * <b>Note:</b> If having connectivity problems
     * check if this range of ports are open on the router.
     * <br><br>
     * <b>FriendsList</b><p>
     * -Query database for friends list and output the list of friends.
     * <br><br>
     * <b>Comms</b><p>
     * -Adds a reference of the output connection and the client's
     * username to a HashMap {@code userComms} in {@link ServerStart}
     * to be used to communicate between players when they are NOT in
     * a game or lobby together.
     * <br><br>
     * <b>askFriendship</b><p>
     * -Sends a friend request to the username indicated at index "1" of
     * the String array received in input, using the the HashMap {@code userComms}
     * to know where to send the message. <b>Note:</b> The Client will
     * receive this message in the "type" Comms.
     * <br><br>
     * <b>Register, Login, AddFriend, RemoveFriend, ChangePassword,
     * DeleteAccount, Logout</b><p>
     * -All these types talk to database to try to perform their
     * respective tasks indicated by their names and output the answer.
     * <p>
     * If Login is succesfull the username will be saved in a HashSet of
     * logged in users in {@link ServerStart}.
     */
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
        if (input == null) return;
        String type = accountInfo[0];
        System.out.println(Arrays.toString(accountInfo));

        Database database = connectToDatabase();

        switch (type) {
            case "Host" -> {
                Random randomPort = new Random();
                int port_number = 4000 + randomPort.nextInt(3000);
                output.writeObject(port_number);
                output.flush();
                new Lobby(accountInfo[1], port_number);
            }
            case "FriendsList" -> {
                List<String> friends = database.getFriends(accountInfo);
                output.writeObject(friends);
                output.flush();
            }
            case "Comms" -> {
                String clientMessage;
                ServerStart.addUserComms(accountInfo[1], output);
                do {
                    clientMessage = (String) input.readObject();
                } while (!clientMessage.equals("END"));
            }
            case "askFriendship" -> {
                ObjectOutputStream friendOutput = ServerStart.getUserOutput(accountInfo[2]);
                ObjectOutputStream thisClient;
                if (friendOutput == null) {
                    thisClient = ServerStart.getUserOutput(accountInfo[1]);
                    thisClient.writeObject(new String[]{"Offline"});
                } else {
                    String[] message = {"Add", accountInfo[1]};
                    friendOutput.writeObject(message);
                    friendOutput.flush();
                }
            }
            case "acceptFriend" -> {
                ObjectOutputStream friendOutput = ServerStart.getUserOutput(accountInfo[2]);
                ObjectOutputStream thisClient;
                if (friendOutput == null) {
                    thisClient = ServerStart.getUserOutput(accountInfo[1]);
                    thisClient.writeObject(new String[]{"Offline"});
                } else {
                    String[] message = {"Accepted", accountInfo[1], accountInfo[3]};
                    friendOutput.writeObject(message);
                    friendOutput.flush();
                }
            }
            case "askLobby" -> {
                ObjectOutputStream friendOutput = ServerStart.getUserOutput(accountInfo[2]);
                ObjectOutputStream thisClient;
                if (friendOutput == null) {
                    thisClient = ServerStart.getUserOutput(accountInfo[1]);
                    thisClient.writeObject(new String[]{"Offline"});
                } else {
                    String[] message = {"askLobby", accountInfo[1]};
                    friendOutput.writeObject(message);
                    friendOutput.flush();
                }
            }
            case "getLobby" -> {
                ObjectOutputStream friendOutput = ServerStart.getUserOutput(accountInfo[1]);
                ObjectOutputStream thisClient;
                if (friendOutput == null) {
                    thisClient = ServerStart.getUserOutput(accountInfo[1]);
                    thisClient.writeObject(new String[]{"Offline"});
                } else {
                    String[] message = {"Code", accountInfo[2]};
                    friendOutput.writeObject(message);
                    friendOutput.flush();
                }
            }
            case "Exit" -> {
                ServerStart.userLoggedOut(accountInfo[1]);
                ServerStart.removeUsersComms(accountInfo[1]);
            }
            default -> {
                try {
                    switch (type) {
                        case "Register":
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
                            break;
                        case "Login":
                            String username = accountInfo[1];

                            if (database.canLogin(accountInfo)) {
                                ServerStart.addToLoggedInUsers(username);
                                outputMessage = 1;
                            } else if (!database.isUsernameTaken(username))
                                outputMessage = 0;
                            else if (ServerStart.isUserLoggedIn(username))
                                outputMessage = 2;                      //User Already Logged in
                            else
                                outputMessage = -1;

                            break;
                        case "AddFriend":
                            String friendUsername = accountInfo[1];

                            if (database.canAddFriend(accountInfo)) {
                                outputMessage = 1;
                                database.addFriend(accountInfo);

                            } else if (!database.isUsernameTaken(friendUsername)) {
                                outputMessage = 0;

                            } else
                                outputMessage = -1;

                            break;
                        case "RemoveFriend":

                            if (database.canRemoveFriend(accountInfo)) {

                                try {
                                    database.removeFriend(accountInfo);
                                } catch (SQLException e) {
                                    outputMessage = -1;
                                }
                                outputMessage = 1;
                            } else
                                outputMessage = 0;

                            break;
                        case "ChangePassword":

                            if (database.ChangePassword(accountInfo)) {
                                outputMessage = 1;
                            } else
                                outputMessage = -1;
                            break;

                        case "DeleteAccount":
                            database.deleteAccount(accountInfo);
                            outputMessage = 1;
                            break;
                        case "Logout":
                            String userToLogout = accountInfo[1];
                            ServerStart.userLoggedOut(userToLogout);
                            break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    outputMessage = -1;
                }
                output.writeObject(outputMessage);
                output.flush();
            }
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
