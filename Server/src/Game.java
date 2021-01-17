import org.javatuples.Pair;

import java.io.PrintWriter;
import java.util.*;

/**
 * This class is used to handle the interactions between players and
 * the game in a syncronized way.
 */
public class Game {
    private final NavigableMap<String, Pair<Scanner, PrintWriter>> players;

    private String currentPlayer;
    private int currentNumber;
    private String playerOrder;
    private String[] mysteryCards;

    private final String[][] playerCards;
    private final ArrayList<String> playerList;
    private final ArrayList<String> losers;

    /**
     * Creates a game and orders the players turns in the game
     * in alphabetical order and deals the cards to them.
     * @param players a NavigableMap of the players in the game.
     */
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

    /**
     * Adds a player to the list of players who lost the game
     * so that their turn will be skipped.
     * @param username the username of the player that lost
     *                 the game.
     */
    public void addLoser(String username) {
        losers.add(username);
    }

    /**
     * Checks if any of the players in the game have any of the cards
     * that were suggested. Each player is checked in the order of their
     * turns until a player has a card that matches any of the suggested,
     * then the username of the player that has that card and the
     * card itself is returned as a string with the valuesseparated by a comma.<br>
     * Example: "nickname1,place 2".<br><br>
     * If a player has more than one card that matches the suggestion,
     * then the card that is chosen is picked at random.
     * If no one has the card returns null.
     * @param suggestionAttempt a string array with the cards of the
     *                          suggestion.
     * @param username the username of the player that made the suggestion.
     * @param currentPlayer the next player to be checked if they have any
     *                      of the suggested cards.
     * @return the username and card of the first player who has any of the cards
     *         suggested or null if no one has any card.
     */
    public String makeSuggestion(String[] suggestionAttempt, String username, String currentPlayer) {
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

    /**
     * Checks if the accusation is correct, or if the
     * accusation cards match the mystery cards picked
     * at random by the the server at the beginning of
     * the game.
     * @param cards a string array with the cards of the
     *              accusation.
     * @return true if the accusation is correct.
     *         False if it is incorrect.
     */
    public boolean isAccusationCorrect(String[] cards) {
        return Arrays.equals(cards, mysteryCards);
    }

    /**
     * Returns the turn order for the players in the game.<br>
     * Example: For 3 players with usernames nick1, nick2, nick3.
     * The returned string is: "ORDEnick1,nick2,nick3"
     *
     * @return  a string with the player's username separated
     *          by commas.
     */
    public synchronized String getPlayerOrder() {
        return playerOrder;
    }

    /**
     * Returns the cards assigned to a specified player.
     * @param username the username of the player.
     * @return a string array of the cards assigned to this username.
     */
    public synchronized String[] getPlayerCards(String username) {
        return playerCards[playerList.indexOf(username)];
    }

    /**
     * Returns the message to be broadcasted to all players to indicate
     * where this player moved.
     * @throws IllegalStateException if it is not this player's turn.
     * @param direction the direction to move.
     * @param player the username of the player to move.
     * @return a string with the message to be sent.
     */
    public synchronized String move(String direction, String player) {
        if (!player.equals(currentPlayer)) {
            throw new IllegalStateException("Not your turn");
        }

        return "MOVE"+direction+""+player;
    }

    /**
     * Ends the turn of the current player and stores the username
     * of the next player as the current player.
     * @throws IllegalStateException if it is not this player's turn.
     * @param player the username of the player who want to
     *              end the turn.
     * @return the username of the next player.
     */
    public synchronized String endTurn(String player) {
        if (!player.equals(currentPlayer))
            throw new IllegalStateException("Not your turn");

        nextPlayer();
        return currentPlayer;
    }

    /**
     * Advances to the next player in the order.
     */
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

    /**
     * Performs the dealing of the cards.<br>
     * First 3 mystery cards are picked. One of each
     * type: person, weapon and place.<br>
     * These cards are taken of the deck and then the
     * cards are dealt to the players.<br>
     * Each player gets one random card in order of their turns
     * and this is repeated until there are no more cards.<br>
     * <b>NOTE:</b> Depending on the number of players, the number
     * of cards each player gets may vary between 3 and 4.
     * @return a two dimensional string array with pairs of player's
     * usernames and their cards.
     */
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