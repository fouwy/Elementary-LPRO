import org.javatuples.Pair;

import java.io.PrintWriter;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: Can only be one object of this class and the multiple player threads like in lobby

public class Game {
    private final NavigableMap<String, Pair<Scanner, PrintWriter>> players;

    Player currentPlayer;

    public Game(NavigableMap<String, Pair<Scanner, PrintWriter>> players) {
        this.players = players;
        int i=0;
        String nextPlayer;

        ExecutorService threadPool = Executors.newFixedThreadPool(players.size());

        for (Map.Entry<String, Pair<Scanner, PrintWriter>> player : players.entrySet()) {

            if (i < players.size() - 1)
                nextPlayer = players.higherEntry(player.getKey()).getKey();
            else
                nextPlayer = players.firstEntry().getKey();

            System.out.println(nextPlayer);
            threadPool.execute(this.new Player(player, nextPlayer));
            i++;
        }
    }

    public synchronized void move(int location, Player player) {

    }

    private void broadcast() {
    }

    private void quitGame() {
    }

    private void makeSuggestion(String command) {
    }

    private void makeAccusation(String command) {
    }

    class Player implements Runnable {
        private String name;
        private String nextPlayer;
        private Scanner input;
        private PrintWriter output;

        public Player(Map.Entry<String, Pair<Scanner, PrintWriter>> player, String nextPlayer) {
            this.name = player.getKey();
            this.input = player.getValue().getValue0();
            this.output = player.getValue().getValue1();
            this.nextPlayer = nextPlayer;
        }

        @Override
        public void run() {
            while (input.hasNextLine()) {
                String command = input.nextLine();
                String type = command.substring(0, 4);

                switch (type) {
                    case "MOVE":
                        processMove(command);
                        break;
                    case "ACCU":    //ACCUSATION
                        processAccusation(command);
                        break;
                    case "SUGG":    //SUGGESTION
                        processSuggestion(command);
                        break;
                    case "QUIT":
                        quitGame();
                        break;
                    default:
                        //TODO: Make superclass Communication and put broadcast method in it
                        broadcast();
                }
            }
        }

        private void processSuggestion(String command) {
        }

        private void processAccusation(String command) {
        }

        private void processMove(String command) {

        }
    }
}