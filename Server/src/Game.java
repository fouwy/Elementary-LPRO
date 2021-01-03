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

        String[] cards = {"person 1","person 2","person 3","person 4","person 5","person 6",
                          "weapon 1", "weapon 2", "weapon 3","weapon 4", "weapon 5",
                          "place 1", "place 2", "place 3","place 4", "place 5", "place 6","place 7", "place 8"};

        String[] cardsToDeal = choose3MysteryCards(cards);

        int numberOfPlayers = players.size();
        int numberOfCardsToDeal = cardsToDeal.length - 3;
        int randomCard;

        int playerCard = 4;
        String playerCards[][] = new String[numberOfPlayers][playerCard];

        Random random = new Random();

        while(numberOfCardsToDeal > 0){
            for(int i=0; i<playerCard; i++){
                for(int j=0; j<numberOfPlayers; j++){
                    randomCard = random.nextInt(numberOfCardsToDeal);
                    playerCards[j][i] = cardsToDeal[randomCard];
                    cardsToDeal = shiftCards(cardsToDeal, randomCard, numberOfCardsToDeal);
                    numberOfCardsToDeal--;
                }
            }
        }


    }


    private String [] choose3MysteryCards(String[] cards){
        Random random = new Random();
        int cardsNumber = cards.length;

        int mysteryPerson = random.nextInt(6);        //0 a 5 - Person
        cards = shiftCards(cards, mysteryPerson, cardsNumber);
        cardsNumber--;
        int mysteryWeapon = 5 + random.nextInt(10);   // 5 a 9 - Weapon
        cards = shiftCards(cards, mysteryWeapon, cardsNumber);
        cardsNumber--;
        int mysteryPlace = 9 + random.nextInt(17);  // 9 a 16 - Place
        cards = shiftCards(cards, mysteryPlace, cardsNumber);
        cardsNumber--;

        return cards;
    }

    private String[] shiftCards(String[] cards, int init, int end){
        for(int i=init; i<end; i++){
            cards[i] = cards[i+1];
        }
        return cards;
    }

}