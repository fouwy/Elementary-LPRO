import org.javatuples.Pair;

import java.io.PrintWriter;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;

//TODO: Can only be one object of this class and the multiple player threads like in lobby

public class Game {
    private final NavigableMap<String, Pair<Scanner, PrintWriter>> players;

    private String currentPlayer;
    private int currentNumber;
    private int movesLeft;
    private String playerOrder;

    public Game(NavigableMap<String, Pair<Scanner, PrintWriter>> players) {
        this.players = players;
        currentPlayer = players.firstKey();
        playerOrder = "ORDE";

        for (Map.Entry<String, Pair<Scanner, PrintWriter>> player : players.entrySet()) {
            playerOrder += player.getKey() + ",";
        }
        System.out.println("playerOrder = " + playerOrder);
    }

    public String getPlayerOrder() {
        return playerOrder;
    }

    public synchronized String move(String direction, String player) {
        if (!player.equals(currentPlayer)) {
            throw new IllegalStateException("Not your turn");
        }
        movesLeft--;

        return "MOVE"+direction+""+player;
    }

    public synchronized String endTurn(String player) {
        //TODO:Refactor this
        if (!player.equals(currentPlayer))
            throw new IllegalStateException("Not your turn");

        nextPlayer();
        return "ENDT"+currentPlayer;
    }

    private void nextPlayer() {
        if (currentNumber < players.size() - 1) {
            currentPlayer = players.higherKey(currentPlayer);
            currentNumber++;
        } else {
            currentPlayer = players.firstKey();
            currentNumber = 0;
        }
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