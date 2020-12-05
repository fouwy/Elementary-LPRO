import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lobby {

    private final int port;
    private String host;
    private boolean waitingToStart;

    public Lobby(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        waitingToStart = true;
        startLobby();
    }

    private void startLobby() throws IOException {
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Lobby Server Is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(6);
            while (waitingToStart) {
                pool.execute(this.new Player(listener.accept()));   //maybe change "this" to another class
            }
        }
    }

    class Player implements Runnable {
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Player(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        private void processCommands() {
            while(input.hasNextLine()) {
                String command = input.nextLine();
                if (command.startsWith("char1"))
                    System.out.println("Player has chosen char1!");
            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream());
            output.println("Welcome" + host);
        }
    }


}
