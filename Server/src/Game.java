import org.javatuples.Pair;

import java.io.PrintWriter;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
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

    private void dealCards(){
        //get number of players
        String[] playersInOrder = lobbyLogic.getPlayersInOrder();
        int numberOfPlayers = playersInOrder.length;

        Random random = new Random();
        int mysteryCards [];
        mysteryCards = choose3MysteryCards();
        String [] player1Cards, player2Cards, player3Cards, player4Cards, player5Cards, player6Cards;
        int card1, card2, card3, card4;
        int pickedCards [];

        String[] cards = {"person 1","person 2","person 3","person 4","person 5","person 6",
                "weapon 1", "weapon 2", "weapon 3","weapon 4", "weapon 5",
                "place 1", "place 2", "place 3","place 4", "place 5", "place 6","place 7", "place 8"};

        switch(numberOfPlayers) {
            case 4:
                for (int i = 0; i < numberOfPlayers; i++) {
                    card1 = random.nextInt(19);
                }


                playersInOrder[i] =;
                break;

            case 5:
                break;

            case 6:
                break;
        }

    }


    private int [] choose3MysteryCards(String[] cards){
        Random random = new Random();
        int mysteryCards [];
        mysteryCards = new int[3];

        mysteryCards[0] = random.nextInt(6);        //0 a 5 - Person
        for(int i=)
            mysteryCards[1] = 6 + random.nextInt(12);   // 6 a 10 - Weapon
        mysteryCards[2] = 12 + random.nextInt(20);  // 11 a 18 - Place

        return mysteryCards;
    }
}