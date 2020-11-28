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
    private boolean validated = false;

    public Client(String serverIP) {
        this.serverIP = serverIP;
    }

    public void startRunning() {
        try {
            connectToServer();
            setupStreams();
            //TODO: send register or login information and wait for response
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
        validated = (boolean) input.readObject();
    }

    public void sendInformation(String[] accountInfo) {
        this.accountInfo = accountInfo;
        startRunning();
    }

    public boolean isValidated() {
        return validated;
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
