import org.javatuples.Pair;

import java.io.PrintWriter;
import java.util.*;

//TODO: Can only be one object of this class and the multiple player threads like in lobby

public class Game {
    private final NavigableMap<String, Pair<Scanner, PrintWriter>> players;

    private String currentPlayer;
    private int currentNumber;
    private String playerOrder;
    private String[] mysteryCards;

    private final String[][] playerCards;
    private final ArrayList<String> playerList;
    private final ArrayList<String> losers;

    public Game(NavigableMap<String, Pair<Scanner, PrintWriter>> players) {
        this.players = players;
        losers = new ArrayList<>();
        currentPlayer = players.firstKey();
        playerOrder = "ORDE";
        playerList = new ArrayList<>();
        for (Map.Entry<String, Pair<Scanner, PrintWriter>> player : players.entrySet()) {
            playerList.add(player.getKey());
            playerOrder += player.getKey() + ",";
        }
        System.out.println("playerOrder = " + playerOrder);

        playerCards = dealCards();
        System.out.println("mysteryCards = " + Arrays.toString(mysteryCards));
    }
    public void addLoser(String username) {
        losers.add(username);
    }

    public String makeSuggestion(String[] suggestionAttempt, String username, String currentPlayer) {
        //TODO: Check if it is their turn
        int nextPlayerNumber = playerList.indexOf(currentPlayer) + 1;

        if (nextPlayerNumber == playerList.size())
            nextPlayerNumber = 0;

        String player = playerList.get(nextPlayerNumber);
        if (player.equals(username))
            return null;

        String[] nextPlayerCards = playerCards[nextPlayerNumber];

        for (String card : nextPlayerCards) {
            if (card.equals(suggestionAttempt[0]))
                return player+","+card;
            if (card.equals(suggestionAttempt[1]))
                return player+","+card;
            if (card.equals(suggestionAttempt[2]))
                return player+","+card;
        }

        return makeSuggestion(suggestionAttempt, username, player);
    }

    public boolean isAccusationCorrect(String[] cards) {
        //TODO:check if it is their turn, could do this in client tbh
        //TODO:delete this line, just for testing
        mysteryCards[2] = "place 1";

        return Arrays.equals(cards, mysteryCards);
    }

    public synchronized String getPlayerOrder() {
        return playerOrder;
    }

    public synchronized String[] getPlayerCards(String username) {
        return playerCards[playerList.indexOf(username)];
    }

    public synchronized String move(String direction, String player) {
        if (!player.equals(currentPlayer)) {
            throw new IllegalStateException("Not your turn");
        }

        return "MOVE"+direction+""+player;
    }

    public synchronized String endTurn(String player) {
        //TODO:Refactor this
        if (!player.equals(currentPlayer))
            throw new IllegalStateException("Not your turn");

        nextPlayer();
        return currentPlayer;
    }

    private void nextPlayer() {
        if (currentNumber < players.size() - 1) {
            currentPlayer = players.higherKey(currentPlayer);
            currentNumber++;
        } else {
            currentPlayer = players.firstKey();
            currentNumber = 0;
        }
        if (losers.contains(currentPlayer)) {
            nextPlayer();
        }
    }

    private String[][] dealCards(){

        String[] cards = {"person 1","person 2","person 3","person 4","person 5","person 6",
                "weapon 1", "weapon 2", "weapon 3","weapon 4", "weapon 5",
                "place 1", "place 2", "place 3","place 4", "place 5", "place 6","place 7", "place 8"};

        mysteryCards = choose3MysteryCards(cards);
        int numberOfPlayers = players.size();
        System.out.println("numberOfPlayers = " + numberOfPlayers);
        int numberOfCardsToDeal = cards.length - 3;
        int randomCard;

        int playerCard = 4;
        String[][] playerCards = new String[numberOfPlayers][playerCard];

        Random random = new Random();


        for(int i=0; i<playerCard; i++){
            for(int j=0; j<numberOfPlayers; j++){
                if (numberOfCardsToDeal == 0)
                    return playerCards;

                randomCard = random.nextInt(numberOfCardsToDeal);
                playerCards[j][i] = cards[randomCard];
                shiftCards(cards, randomCard, numberOfCardsToDeal);
                numberOfCardsToDeal--;
            }
        }
        return playerCards;
    }

    private String[] choose3MysteryCards(String[] cards){
        Random random = new Random();
        int cardsNumber = cards.length;
        String[]  mysteryCards = new String[3];
        int mysteryPerson = random.nextInt(6);        //0 a 5 - Person
        mysteryCards[0] = cards[mysteryPerson];
        shiftCards(cards, mysteryPerson, cardsNumber);
        cardsNumber--;
        int mysteryWeapon = 5 + random.nextInt(5);   // 5 a 9 - Weapon
        mysteryCards[1] = cards[mysteryWeapon];
        shiftCards(cards, mysteryWeapon, cardsNumber);
        cardsNumber--;
        int mysteryPlace = 9 + random.nextInt(8);  // 9 a 16 - Place
        mysteryCards[2] = cards[mysteryPlace];
        shiftCards(cards, mysteryPlace, cardsNumber);

        return mysteryCards;
    }

    private void shiftCards(String[] cards, int init, int end){
        for(int i=init; i<end-1; i++)
            cards[i] = cards[i + 1];
    }
}