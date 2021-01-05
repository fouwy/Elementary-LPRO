package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class Panel extends JPanel implements KeyListener {

    private static final int startingLine = 9;
    private static final int startingColumn = 9;
    private static final int distance = 36;

    private final Image backgroundImage;
    private Player myPlayer;
    private final Map<String, Integer> playerPicks;
    private final List<Player> players;
    private final LobbyLogic lobbyLogic;

    Panel(Map<String, Integer> playerPicks, LobbyLogic lobbyLogic){
        this.playerPicks = playerPicks;
        this.lobbyLogic = lobbyLogic;
        players = new ArrayList<>();

        backgroundImage = new ImageIcon("Client/src/img/map4.png").getImage();
        this.setFocusable(true);
        addKeyListener(this);

        setMyCharacter();
        if (playerPicks != null)
            setOtherPlayersCharacter();
    }

    public void movePlayerCharacter(String playerName, char direction) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                player.moveInDirection(direction);
                repaint();
            }
        }
    }

    private void setOtherPlayersCharacter() {
        for (Map.Entry<String, Integer> entry: playerPicks.entrySet()) {
            String imagePath = getImagePath(entry.getValue());
            Image characterImg = new ImageIcon(imagePath).getImage();
            Player player = new Player (entry.getKey(), characterImg,
                    startingLine, startingColumn);
            players.add(player);
        }
    }

    private void setMyCharacter() {
        int charNumber = Account.getCharNumber();
        String characterImage;

        characterImage = getImagePath(charNumber);
        Image characterImg = new ImageIcon(characterImage).getImage();
        myPlayer = new Player(Account.getUsername(), characterImg, startingLine, startingColumn);
        players.add(myPlayer);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        for (Player player : players) {
            g2D.drawImage(player.getCharacter(), player.getColumn()*distance*getWidth()/900,
                    player.getLine()*distance*getHeight()/720, getDistanceSide(), getDistanceUp(),this);
        }
    }

    public void setDiceValue(int total) {

    }

    public String getCurrentRoom() {
        return "place 1";
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        char direction;

        switch (key) {
            case (KeyEvent.VK_W):
                direction = 'W';
                break;

            case (KeyEvent.VK_S):
                direction = 'S';
                break;

            case (KeyEvent.VK_A):
                direction = 'A';
                break;

            case (KeyEvent.VK_D):
                direction = 'D';
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + key);
        }
        if (myPlayer.canMove(direction)) {
            lobbyLogic.tellServerToUpdatePosition("MOVE"+direction);
        }
    }

    private int getDistanceUp() {
        return getHeight()/20 + 1;
    }

    private int getDistanceSide() {
        return getWidth()/25 + 1;
    }

    private String getImagePath(int charNumber) {
        String characterImage;
        switch (charNumber) {
            case 1:
                characterImage = "Client/src/img/char_yellow.png";
                break;
            case 2:
                characterImage = "Client/src/img/char_blue.png";
                break;
            case 3:
                characterImage = "Client/src/img/char_red.png";
                break;
            case 4:
                characterImage = "Client/src/img/char_purple.png";
                break;
            case 5:
                characterImage = "Client/src/img/char_green.png";
                break;
            case 6:
                characterImage = "Client/src/img/char_black.png";
                break;
            default:
                characterImage = "Client/src/img/nocolor.gif";
        }
        return characterImage;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
