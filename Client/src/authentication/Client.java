package authentication;

import common.MainLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

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
    private MainLogic communicator;

    public Client(String serverIP) {
        this.serverIP = serverIP;
    }

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

    public void sendInformation(String[] accountInfo) {
        this.accountInfo = accountInfo;
        startRunning();
    }

    public List<String> getFriends() {
        return friends;
    }

    public int isValidated() {
        return validated;
    }

    public int isInfoCorrect() {
        return valid_info;
    }

    public int isFriendAdded(){ return addFriendValidation; }

    public int isFriendRemoved() { return removeFriendValidation;}

    public int isPasswordChanged() { return changedPasswordValidation; }

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

    public void setCommunicationWith(MainLogic communicator) {
        this.communicator = communicator;
    }
}
