package authentication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    String serverIP;
    Socket connection;
    ObjectInputStream input;
    ObjectOutputStream output;
    private String[] accountInfo;
    private int validated = 0;
    private int valid_info = 0;
    private int port_number;

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
            case "Logout":

                break;
        }
    }

    public void sendInformation(String[] accountInfo) {
        this.accountInfo = accountInfo;
        startRunning();
    }

    public int isValidated() {
        return validated;
    }

    public int isInfoCorrect() {
        return valid_info;
    }

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
}
