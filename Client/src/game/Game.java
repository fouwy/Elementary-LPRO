package game;

import common.ClientStart;
import java.util.Map;
import javax.swing.*;

public class Game extends JFrame {

    private final Panel board;
    private final GamePage gamePage;

    public Game(Map<String, Integer> playerPicks, LobbyLogic lobbyLogic) {

        gamePage = new GamePage(playerPicks, lobbyLogic);
        board = gamePage.getBoard();
        this.add(gamePage.$$$getRootComponent$$$());
        this.setTitle("ELEMENTARY");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void movePlayer(String playerName, char direction) {
        board.movePlayerCharacter(playerName, direction);
    }

    public void nextTurn(String playerName) {
        gamePage.nextTurn(playerName);
    }

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

    public void showMyCard(String cardToShow) {
        JOptionPane.showMessageDialog(this, "You showed your card \""+cardToShow+"\"!");
    }

    public void showOtherPlayerCard(String whoHasTheCard) {
        JOptionPane.showMessageDialog(this, whoHasTheCard+" showed a card!");
    }

    public void showNoOneHadCard() {
        JOptionPane.showMessageDialog(this, "No one had the cards!");
    }
}
