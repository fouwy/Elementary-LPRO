import org.javatuples.Pair;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

//TODO: Change this class name
public class Lobby {

    private final int port;
    private final String host;
    private boolean waitingToStart;
    private final String[] charactersTaken;
    private static final NavigableMap<String, Pair<Scanner, PrintWriter>> players =  new TreeMap<>();
    private Game game;

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
        private String username;
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
            username = input.nextLine();
            if (username == null)
                return;
            welcomePlayer();
            synchronized (players) {
                players.put(username, new Pair<>(input, output));
            }

            while(input.hasNextLine()) {
                String command = input.nextLine();
                String type = command.substring(0, 4);
                System.out.println(command);
                switch (type) {
                    case "CHAR":
                        int characterNumber = Integer.parseInt(String.valueOf(command.charAt(4)));
                        if (charactersTaken[characterNumber].isBlank()) {
                            charactersTaken[characterNumber] = username;
                            broadcast("CHAR"+characterNumber+ username);
                        }
                        break;
                    case "STRT":    //START
                        if (username.equals(host)) {
                            waitingToStart = false;
                            startGame();
                        }
                        break;
                    case "MOVE":
                        processMovement(command.substring(4));
                        break;
                    case "ENDT": //END TURN
                        processEndTurn(username);

                        break;
                    default:
                        broadcast(command);
                }
            }
        }

        private void processEndTurn(String username) {
            try {
                String nextPlayer = game.endTurn(username);
                broadcast(nextPlayer);
            } catch (IllegalStateException e) {
                output.println("MESG" + e.getMessage());
            }
        }

        private void processMovement(String direction) {
            try {
               String message = game.move(direction, username);
               broadcast(message);
            } catch (IllegalStateException e) {
                output.println("MESG" + e.getMessage()); //MESG-MESSAGE
            }
        }

        private void startGame() {
            //TODO:-Start game logic in server
            //     -Kill all player threads in this Lobby class
            //     -Tell players to go to game panel
            broadcast("GAME");
            game = new Game(players);
        }

        private void welcomePlayer() {
            output.println(Arrays.toString(charactersTaken));
            output.println("Welcome " + username);
            broadcast(username + " just joined.");
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
    }


    private void broadcast(String message) {
        //System.out.println("broadcast: "+message);
        for (Pair<Scanner, PrintWriter> writer : players.values()) {
            writer.getValue1().println(message);
        }
    }


}
