package game;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;
import javax.swing.*;

public class Game extends JFrame {

    Panel board;
    GamePage gamePage;

    public Game(Map<String, Integer> playerPicks, LobbyLogic lobbyLogic) {
        
        //board = new Panel(playerPicks, 90, 90, 900, 720, lobbyLogic);	//1260, 720
        gamePage = new GamePage(playerPicks, lobbyLogic);
        board = gamePage.getBoard();
        this.add(gamePage.$$$getRootComponent$$$());
        this.setTitle("ELEMENTARY");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(null);
        this.pack();
        this.setVisible(true);
    }

    public void movePlayer(String playerName, char direction) {
        board.movePlayerCharacter(playerName, direction);
    }

    public void nextTurn() {
        gamePage.nextTurn();
    }

    public void showWinStatus(String status, String player) {
        System.out.println("status = " + status);
        System.out.println("player = " + player);
        if (status.equals("WIN")) {
            JOptionPane.showMessageDialog(this, "GAME OVER\n"+player+" WON!");
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (status.equals("LOSE")) {
            JOptionPane.showMessageDialog(this, player+" lost the game.\nTheir accusation was wrong");

        }
    }
}
