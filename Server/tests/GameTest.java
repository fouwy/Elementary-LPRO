import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        NavigableMap<String, Pair<Scanner, PrintWriter>> players;
        players = new TreeMap<>();
        players.put("D_player4", null);
        players.put("C_player3", null);
        players.put("B_player2", null);
        players.put("A_player1", null);
        game = new Game(players);
    }

    @Test
    public void playersAreInAlphabeticOrder() {
        String order = game.getPlayerOrder();
        String[] eachPlayer = order.substring(4).split(",");
        assertEquals("A_player1", eachPlayer[0]);
        assertEquals("B_player2", eachPlayer[1]);
        assertEquals("C_player3", eachPlayer[2]);
        assertEquals("D_player4", eachPlayer[3]);
    }

    @Test
    public void allPlayersReceiveDifferentCards() {
        String[] player1_cards = game.getPlayerCards("A_player1");
        String[] player2_cards = game.getPlayerCards("B_player2");
        String[] player3_cards = game.getPlayerCards("C_player3");
        String[] player4_cards = game.getPlayerCards("D_player4");

        assertDifferentCards(player1_cards, player2_cards, player3_cards, player4_cards);

        assertDifferentCards(player2_cards, player1_cards, player3_cards, player4_cards);

        assertDifferentCards(player3_cards, player1_cards, player2_cards, player4_cards);

        assertDifferentCards(player4_cards, player1_cards, player2_cards, player3_cards);
    }

    private void assertDifferentCards(String[] cardsToCheck, String[] player2_cards,
                                      String[] player3_cards, String[] player4_cards) {
        for (String card : cardsToCheck) {
            for (String otherCard : player2_cards)
                assertNotEquals(card, otherCard);
            for (String otherCard : player3_cards)
                assertNotEquals(card, otherCard);
            for (String otherCard : player4_cards)
                assertNotEquals(card, otherCard);
        }
    }

    @Test
    public void mysteryCardsAreOneOfEachType() {
       String[] cards = game.getMysteryCards();
       assertTrue(cards[0].startsWith("person"));
       assertTrue(cards[1].startsWith("weapon"));
       assertTrue(cards[2].startsWith("place"));
    }

    @Test
    public void willMoveIfItsPlayersTurn() {
        String result = game.move("W", "A_player1");
        assertTrue(result.startsWith("MOVE"));
    }

    @Test (expected = IllegalStateException.class)
    public void willNotMoveIfNotPlayersTurn() {
        game.move("W", "B_player2");
    }

    @Test (expected = IllegalStateException.class)
    public void willNotEndTurnIfNotPlayersTurn() {
        game.endTurn("B_player2");
    }

    @Test
    public void afterEndingTurnNextPlayerCanMove() {
        String nextPlayer = game.endTurn("A_player1");
        assertEquals("B_player2", nextPlayer);
        String result = game.move("W", "B_player2");
        assertTrue(result.startsWith("MOVE"));
    }

    @Test
    public void willShowPlayerThatHasSuggestedCard() {
        String[] player2_cards = game.getPlayerCards("B_player2");
        String playerWithCardSuggested = game.makeSuggestion(player2_cards,
                "A_player1", "A_player1");
        assertEquals("B_player2", playerWithCardSuggested.substring(0, 9));
    }

    @Test
    public void willReturnNullIfNoPlayerHasSuggestedCard() {
        String[] player1_cards = game.getPlayerCards("A_player1");
        String playerWithCardSuggested = game.makeSuggestion(player1_cards,
                "A_player1", "A_player1");
        assertNull(playerWithCardSuggested);
    }

    @Test
    public void willReturnTrueIfAccusationIsCorrect() {
        String[] cards = game.getMysteryCards();
        assertTrue(game.isAccusationCorrect(cards));
        assertFalse(game.isAccusationCorrect(new String[]{"ran","dom","123"}));
    }
}