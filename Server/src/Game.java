import org.javatuples.Pair;

import java.io.PrintWriter;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;

//TODO: Can only be one object of this class and the multiple player threads like in lobby

public class Game {
    private final NavigableMap<String, Pair<Scanner, PrintWriter>> players;

    String currentPlayer;
    private int movesLeft;

    public Game(NavigableMap<String, Pair<Scanner, PrintWriter>> players) {
        this.players = players;
        currentPlayer = players.firstKey();
        int i=0;
        String nextPlayer;

        for (Map.Entry<String, Pair<Scanner, PrintWriter>> player : players.entrySet()) {

            if (i < players.size() - 1)
                nextPlayer = players.higherEntry(player.getKey()).getKey();
            else
                nextPlayer = players.firstEntry().getKey();

            System.out.println(nextPlayer);
            i++;
        }
    }

    public synchronized String move(String direction, String player) {
//        if (!player.equals(currentPlayer)) {
//            throw new IllegalStateException("Not your turn");
//        }
        movesLeft--;

        return "MOVE"+direction+""+player;
    }

    public synchronized void endTurn(String player) {
        //TODO:Refactor this
        if (!player.equals(currentPlayer)) {
            throw new IllegalStateException("Not your turn");
        }

        currentPlayer = players.higherKey(currentPlayer);
    }

    private void broadcast() {
    }

    private void quitGame() {
    }

    private void makeSuggestion(String command) {
    }

    private void makeAccusation(String command) {
    }


}

//    @Override
//    public void run() {
//        while (input.hasNextLine()) {
//            String command = input.nextLine();
//            String type = command.substring(0, 4);
//
//            switch (type) {
//                case "MOVE":
//                    processMove(command);
//                    break;
//                case "ACCU":    //ACCUSATION
//                    processAccusation(command);
//                    break;
//                case "SUGG":    //SUGGESTION
//                    processSuggestion(command);
//                    break;
//                case "QUIT":
//                    quitGame();
//                    break;
//                default:
//                    //TODO: Make superclass Communication and put broadcast method in it
//                    broadcast();
//            }
//        }
//    }