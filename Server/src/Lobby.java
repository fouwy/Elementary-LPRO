import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Lobby {

    private final int port;
    private final String host;
    private boolean waitingToStart;
    private final String[] characters;
    private static final Map<String, PrintWriter> players =  new LinkedHashMap<>();

    public Lobby(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        waitingToStart = true;
        characters = new String[7];
        Arrays.fill(characters, "");
        startLobby();
    }

    private void startLobby() throws IOException {
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Lobby Server Is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(6);
            while (waitingToStart) {
                pool.execute(this.new Player(listener.accept()));   //maybe change "this" to another class
                System.out.println("User connected");
            }
        }
    }

    class Player implements Runnable {
        private String name;
        private final Socket socket;
        private Scanner input;
        private PrintWriter output;

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
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processCommands() {
            name = input.nextLine();
            if (name == null)
                return;
            welcomePlayer();
            synchronized (players) {
                players.put(name, output);
            }

            while(input.hasNextLine()) {
                String command = input.nextLine();
                if (command.startsWith("CHAR")) {
                    System.out.println(command);
                    int characterNumber = Integer.parseInt(String.valueOf(command.charAt(4)));

                    if (characters[characterNumber].isBlank()) {
                        characters[characterNumber] = name;
                        broadcast("CHAR"+characterNumber+name);
                    }
                }

            }
        }

        private void welcomePlayer() {
            output.println(Arrays.toString(characters));
            output.println("Welcome " + name);
            broadcast(name + " just joined.");
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
    }

    private void broadcast(String message) {
        for (PrintWriter writer : players.values()) {
            writer.println(message);
        }
    }


}
