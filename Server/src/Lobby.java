import org.javatuples.Pair;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * This class handles the communication with the clients
 * for the Lobby and Game, having a thread for each connected
 * client and using a Socket and a Scanner as input and a
 * PrintWriter as output.
 */
public class Lobby {

    private final int port;
    private final String host;
    private boolean waitingToStart;
    private final String[] charactersTaken;
    private static final NavigableMap<String, Pair<Scanner, PrintWriter>> players =  new TreeMap<>();
    private Game game;

    /**
     * Creates a new lobby on the specified port and accepts
     * players into it until a maximum of 6 players.
     * @param host the username of the host of the lobby.
     * @param port the port this lobby is hosted on.
     * @throws IOException if error occurs when connecting to lobby.
     */
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

    /**
     * This class represents each player in a lobby which is
     * identified by their username.
     */
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

        /**
         * Handles the messages received according to the type of
         * message. The type is specified in the first 4 characters
         * of the message.
         */
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
                    case "QUIT":
                        if (username.equals(host)) {
                            players.remove(username);
                            broadcast("LEAV");
                        }
                        players.remove(username);
                        broadcast("LEFT"+username);
                        return;
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
                            broadcast(game.getPlayerOrder());
                        }
                        break;
                    case "CARD":
                        output.println("CARD"+Arrays.toString(game.getPlayerCards(username)));
                        break;
                    case "MOVE":
                        processMovement(command.substring(4));
                        break;
                    case "ENDT": //END TURN
                        processEndTurn();
                        break;
                    case "ACCU":
                        processAccusation(command.substring(4));
                        break;
                    case "SUGG":
                        processSuggestion(command.substring(4));
                        break;
                    default:
                        broadcast(command);
                }
            }
        }

        /**
         * Processes the suggestion and sends a response to all
         * clients depending on the result.
         * @param suggestion a string with the components of the
         *                  suggestion separated by commas.
         */
        private void processSuggestion(String suggestion) {
            String[] suggestionAttempt = suggestion.split(",");
            String result = game.makeSuggestion(suggestionAttempt, username, username); //need to send twice bc its recursive
            System.out.println("result = " + result);
            if (result == null)
                broadcast("SUGGNOCARD");
            else
                broadcast("SUGG"+result);
        }

        /**
         * Processes the accusation and sends a response to all
         * clients depending on the result.
         * @param accusation a string with the components of the
         *                   accusation separated by commas.
         */
        private void processAccusation(String accusation) {
            String[] accusationAttempt = accusation.split(",");
            System.out.println("accusationAttempt = " + Arrays.toString(accusationAttempt));
            if (game.isAccusationCorrect(accusationAttempt))
                broadcast("WIN_WIN,"+username);
            else {
                game.addLoser(username);
                broadcast("WIN_LOSE," + username);
            }
        }

        /**
         * Processes the request to end a player's turn and
         * broadcasts the result.
         */
        private void processEndTurn() {
            try {
                String nextPlayer = game.endTurn(username);
                broadcast("ENDT"+nextPlayer);
            } catch (IllegalStateException e) {
                output.println("MESG" + e.getMessage());
            }
        }

        /**
         * Processes the request to move a character and
         * tells all players where this player moved if
         * the request was valid.
         * @param direction the direction where to move.
         */
        private void processMovement(String direction) {
            try {
               String message = game.move(direction, username);
               broadcast(message);
            } catch (IllegalStateException e) {
                output.println("MESG" + e.getMessage());
            }
        }

        /**
         * Starts the game and tells all players
         * to start it on their clients.
         */
        private void startGame() {
            broadcast("GAME");
            game = new Game(players);
        }

        /**
         * Sends a welcome message when a player
         * joins a lobby.
         */
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

    /**
     * Broadcasts a message to all players in a lobby.
     * @param message the message to be sent.
     */
    private void broadcast(String message) {
        for (Pair<Scanner, PrintWriter> writer : players.values()) {
            writer.getValue1().println(message);
        }
    }
}
