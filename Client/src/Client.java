import java.io.EOFException;
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

    public Client(String serverIP) {
        this.serverIP = serverIP;
    }

    public void startRunning() {
        try {
            connectToServer();
            setupStreams();
            //TODO: send register or login information

        } catch(EOFException eofException) {

        } catch(IOException ioException) {
            ioException.printStackTrace();
        } finally {
            closeClient();
        }
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
