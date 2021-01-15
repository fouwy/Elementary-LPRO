package authentication;

import common.MainLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketOption;
import java.util.List;

/**
 * This class handles the all the communication with the server,
 * except the Lobby and Game, using a Socket and ObjectOutputStream
 * and ObjectInputStream.
 */
public class Client {
    String serverIP;
    Socket connection;
    ObjectInputStream input;
    ObjectOutputStream output;
    private String[] accountInfo;
    private List<String> friends;
    private int validated = 0;
    private int valid_info = 0;
    private int port_number;
    private int addFriendValidation = 0;
    private int removeFriendValidation = 0;
    private int changedPasswordValidation = 0;
    private int deleteAccountValidation = 0;
    private MainLogic communicator;

    /**
     * Creates a unconnected client.
     * @param serverIP The IP address of the server.
     */
    public Client(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     * Makes the connection to the server, sends it the data present in {@code accountInfo}
     * and stores server response in the appropriate field according to the "type" of the message.
     * Finally, closes the connection.
     * <p>
     * To get the response, call the corresponding method depending on the "type":
     * <table>
     *     <thead>
     *     <tr>
     *       <th scope="col">Type</th>
     *       <th scope="col">Method to get response</th>
     *     </tr>
     *   </thead>
     *   <tbody>
     *      <tr>
     *          <td>Register:</td>
     *          <td>{@link #isValidated() isValidated}</td>
     *      </tr>
     *      <tr>
     *          <td>Login:</td>
     *          <td>{@link #isInfoCorrect() isInfoCorrect}</td>
     *      </tr>
     *      <tr>
     *          <td>Host:</td>
     *          <td>{@link #getPort_number() getPortNumber}</td>
     *      </tr>
     *      <tr>
     *          <td>AddFriend:</td>
     *          <td>{@link #isFriendAdded() isFriendAdded}
     *          WARNING: For internal use only. Use askFriendship for the intended result</td>
     *      </tr>
     *      <tr>
     *          <td>RemoveFriend:</td>
     *          <td>{@link #isFriendRemoved() isFriendRemoved}</td>
     *      </tr>
     *      <tr>
     *          <td>ChangePassword:</td>
     *          <td>{@link #isPasswordChanged() isPasswordChanged}</td>
     *      </tr>
     *      <tr>
     *          <td>DeleteAccount:</td>
     *          <td>{@link #isAccountDeleted() isAccountDeleted}</td>
     *      </tr>
     *      <tr>
     *          <td>FriendsList:</td>
     *          <td>{@link #getFriends() getFriends}</td>
     *      </tr>
     *      <tr>
     *          <td>askFriendship:</td>
     *          <td>-</td>
     *      </tr>
     *      <tr>
     *          <td>Comms:</td>
     *          <td>(Sends response directly to {@link #communicator} object
     *          set in {@link #setCommunicationWith(MainLogic)})</td>
     *      </tr>
     *   </tbody>
     *</table>
     * @see #sendInformation(String[] accountInfo)
     * @see #setCommunicationWith(MainLogic)
     */
    public void startRunning() {
        try {
            connectToServer();
            setupStreams();
            sendAndWaitResponse();
        } catch(IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeClient();
        }
    }

    private void sendAndWaitResponse() throws IOException, ClassNotFoundException {
        output.writeObject(accountInfo);
        output.flush();
        String type = accountInfo[0];

        switch (type) {
            case "Register":
                validated = (int) input.readObject();
                break;
            case "Login":
                valid_info = (int) input.readObject();
                break;
            case "Host":
                port_number = (int) input.readObject();
                break;
            case "AddFriend":
                addFriendValidation = (int) input.readObject();
                break;
            case "RemoveFriend":
                removeFriendValidation = (int) input.readObject();
                break;
            case "ChangePassword":
                changedPasswordValidation = (int) input.readObject();
                break;
            case "DeleteAccount":
                deleteAccountValidation = (int) input.readObject();
                break;
            case "FriendsList":
                friends = (List<String>) input.readObject();
                break;
            case "askFriendship":

                break;
            case "Comms":
                String[] serverMessage;
                do {
                    serverMessage = (String[]) input.readObject();
                    communicator.receiveMessage(serverMessage);
                } while(!serverMessage[0].equals("END"));
                break;
        }
    }

    /**
     * Saves the specified accountInfo message and sends it to the server
     * by calling {@link #startRunning()}.
     * <p>
     * The first string in {@code accountInfo} determines the "type" of the
     * message.
     * What the contents of the String array represent for each "type"
     * is displayed in the list below:
     *
     * <ul>
     *     <li>{Register, (username), (password), (email)}</li>
     *     <li>{Login, (username), (password)} </li>
     *     <li>{Logout, (username)}</li>
     *     <li>{RemoveFriend, (username), (username of friend to remove)}</li>
     *     <li>{AddFriend, (username), (username of person to add)}</li>
     *     <li>{ChangePassword, (username), (new password)}</li>
     *     <li>{DeleteAccount, (username)}</li>
     *     <li>{Host, (username)}</li>
     *     <li>{FriendsList, (username)}</li>
     *     <li>{askFriendship, (username), (username of person to ask)}</li>
     *     <li>{Comms, (username)}</li>
     * </ul>
     *
     * @param accountInfo the String array to send.
     * @see #startRunning()
     */
    public void sendInformation(String[] accountInfo) {
        this.accountInfo = accountInfo;
        startRunning();
    }

    /**
     * Returns a list of friends of this client stored in the database.
     * @return a {@code List<String>} of friends of this client.
     */
    public List<String> getFriends() {
        return friends;
    }

    /**
     * Returns a value that represents the result of the registration attempt.
     * @return 1 if register completed,<p>
    *          0 if username was already taken,<p>
     *         -1 if there was an error connecting with the database.
     */
    public int isValidated() {
        return validated;
    }

    /**
     * Returs a value that represents the result of the login attempt.
     * @return  1 if login succesfull,<p>
     *          0 if username is not in the database,<p>
     *          -1 if the password is wrong or there was an error
     *          connecting with the database,<p>
     *          2 if this username is already logged in.
     */
    public int isInfoCorrect() {
        return valid_info;
    }

    /**
     * Returns a value that represents the result of the attempt
     * to add a friend.
     * @return  1 if friend was added,<p>
     *          0 if the username of the friend to add is NOT in
     *          the database,<p>
     *          -1 if there was an error connecting with the database,<p>
     *          2 if the friend is already in the friend list of this user.
     */
    public int isFriendAdded(){ return addFriendValidation; }

    /**
     * Returns a value that represents the result of the attempt
     * to remove a friend.
     * @return  1 if friend was removed,<p>
     *          0 if the user you are trying to unfriend is not listed
     *          as your friend in the database,<p>
     *          -1 if there was an error connecting with the database.
     */
    public int isFriendRemoved() { return removeFriendValidation;}

    /**
     * Returns a value that represents the result of the attempt
     * to change the the account's password.
     * @return  1 if password was changed succesfully,<p>
     *          -1 if there was an error connecting with the database.
     */
    public int isPasswordChanged() { return changedPasswordValidation; }

    /**
     * Returns a value that represents the result of the attempt
     * to delete the user's account.
     * @return  1 if account was deleted from database succesfully,<p>
     *          -1 if there was an error connecting with the database.
     */
    public int isAccountDeleted() { return deleteAccountValidation;}

    /**
     * Returns the port number to be used to connect to the game lobby.
     * @return the port number in which the server hosts the lobby.
     */
    public int getPort_number() {
        return port_number;
    }

    private void connectToServer() throws IOException{
        connection = new Socket(InetAddress.getByName(serverIP), 6789);
    }

    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    private void closeClient() {
        try {
            output.close();
            input.close();
            connection.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Sets the {@code communicator} to where the responses from the server with "type" Comms
     * will be directly sent to.
     * <p>
     * Messages of this "type", unlike all the other types, will not close
     * the connection between client and server after being received until
     * the message received is <b>"END"</b>.
     * @param communicator Object which will be used to receive communications of
     *                     "type" <b>Comms</b>.
     */
    public void setCommunicationWith(MainLogic communicator) {
        this.communicator = communicator;
    }
}
