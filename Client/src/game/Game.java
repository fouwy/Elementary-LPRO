package game;

import common.ClientStart;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;
import javax.swing.*;

/**
 * The game window.
 */
public class Game extends JFrame {

    private final Panel board;
    private final GamePage gamePage;

    /**
     * Creates the game window.
     * @param playerPicks the characters each player picked.
     * @param lobbyLogic a reference to the object to be used to
     *                   communicate with the server.
     */
    public Game(Map<String, Integer> playerPicks, LobbyLogic lobbyLogic) {

        gamePage = new GamePage(playerPicks, lobbyLogic);
        board = gamePage.getBoard();
        this.add(gamePage.$$$getRootComponent$$$());
        this.setTitle("ELEMENTARY");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "You can't leave while the\n" +
                        "game is in progress!");
                }
            };
        this.addWindowListener(listener);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Tells the board to move a specified player character.
     * @param playerName the username of the player.
     * @param direction the direction where to move
     *                  (W,A,S,D).
     */
    public void movePlayer(String playerName, char direction) {
        board.movePlayerCharacter(playerName, direction);
    }

    /**
     * Tells the game to advance to the next turn.
     * @param playerName the username of the player whose
     *                   turn is next.
     */
    public void nextTurn(String playerName) {
        gamePage.nextTurn(playerName);
    }

    /**
     * Shows the Win or Lose status of a specified player in this round in a {@code MessageDialog}.<br>
     * If the player won the game, the game ends for all players.
     * If the players lost the game, the game continues.
     * @param status a string "WIN" or "LOSE".
     * @param player the username of the player who won or lost.
     */
    public void showWinStatus(String status, String player) {
        if (status.equals("WIN")) {
            JOptionPane.showMessageDialog(this, "GAME OVER\n"+player+" WON!");
            ClientStart.frame.setVisible(true);
            ClientStart.cardLayout.show(ClientStart.rootPanel, "Lobby");
            this.dispose();
        } else if (status.equals("LOSE")) {
            JOptionPane.showMessageDialog(this, player+" lost the game.\nTheir accusation was wrong");
            gamePage.addLoser(player);
        }
    }

    /**
     * Tells which card of this client was showed in this round in a {@code MessageDialog}.
     * @param cardToShow the card you showed.
     */
    public void showMyCard(String cardToShow) {
        JOptionPane.showMessageDialog(this, "You showed your card \""+cardToShow+"\"!");
    }

    /**
     * Tells which player showed a card in this round in a {@code MessageDialog}.
     * @param whoHasTheCard the username of the player who showed
     *                      the card.
     */
    public void showOtherPlayerCard(String whoHasTheCard) {
        JOptionPane.showMessageDialog(this, whoHasTheCard+" showed a card!");
    }

    /**
     * Tells in a {@code MessageDialog} if no one showed a card this round.
     */
    public void showNoOneHadCard() {
        JOptionPane.showMessageDialog(this, "No one had the cards!");
    }

    public void showAskedCard(String cardToShow, String whoHasTheCard) {
        JOptionPane.showMessageDialog(this, whoHasTheCard+" showed the card\n" +
                cardToShow);
    }

    public void showMessage(String message) {
        SwingUtilities.invokeLater(
                () -> gamePage.getInfoWindow().append(message+"\n")
        );

    }
}
