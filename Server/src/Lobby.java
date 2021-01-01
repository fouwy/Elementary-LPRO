import org.javatuples.Pair;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Lobby {

    private final int port;
    private final String host;
    private boolean waitingToStart;
    private final String[] charactersTaken;
    private static final NavigableMap<String, Pair<Scanner, PrintWriter>> players =  new TreeMap<>();

    public Lobby(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        waitingToStart = true;
        charactersTaken = new String[7];
        Arrays.fill(charactersTaken, "");
        startLobby();
    }

    private void startLobby() throws IOException {
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Lobby Server Is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(6);
            while (waitingToStart) {
                pool.execute(this.new Player(listener.accept()));
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
                players.put(name, new Pair<>(input, output));
            }

            while(input.hasNextLine()) {
                String command = input.nextLine();
                String type = command.substring(0, 4);
                System.out.println(command);
                switch (type) {
                    case "CHAR":
                        int characterNumber = Integer.parseInt(String.valueOf(command.charAt(4)));
                        if (charactersTaken[characterNumber].isBlank()) {
                            charactersTaken[characterNumber] = name;
                            broadcast("CHAR"+characterNumber+name);
                        }
                        break;
                    case "START": //START
                        if (name.equals(host)) {
                            waitingToStart = false;
                            startGame();
                            return;
                        }
                        break;
                    default:
                        broadcast(command);
                }
            }
        }

        private void welcomePlayer() {
            output.println(Arrays.toString(charactersTaken));
            output.println("Welcome " + name);
            broadcast(name + " just joined.");
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
    }

    private void startGame() {
        //TODO:-Start game logic in server
        //     -Kill all player threads in this Lobby class
        //     -Tell players to go to game panel
        new Game(players);
        broadcast("GAME");
    }

    private void broadcast(String message) {
        for (Pair<Scanner, PrintWriter> writer : players.values()) {
            writer.getValue1().println(message);
        }
    }


}
