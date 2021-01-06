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
        myPlayer.setMovesLeft(total);
    }

    public boolean IAlreadyRolledTheDice() {
        return myPlayer.AlreadyRolleddTheDice();
    }

    public void resetAttributes() {
        myPlayer.setNoMoreMoves(false);
        myPlayer.setRolledTheDice(false);
    }

    public String getCurrentRoom() {
        switch (myPlayer.getRoom()) {
            case 1:
                return "place 1";
            case 2:
                return "place 2";
            case 3:
                return "place 3";
            case 4:
                return "place 4";
            case 5:
                return "place 5";
            case 6:
                return "place 6";
            case 7:
                return "place 7";
            case 8:
                return "place 8";
            default:
                return "";
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        char direction;

        if (!myPlayer.AlreadyRolleddTheDice())
            JOptionPane.showMessageDialog(null, "You need to roll the dice before trying to move!");
        else if (myPlayer.hasNoMoreMoves())
            JOptionPane.showMessageDialog(null, "You have no more moves!\nTry to make a suggestion!");
        else {
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
                lobbyLogic.tellServerToUpdatePosition("MOVE" + direction);
            }
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
                characterImage = "Client/src/img/char/char_yellow.png";
                break;
            case 2:
                characterImage = "Client/src/img/char/char_blue.png";
                break;
            case 3:
                characterImage = "Client/src/img/char/char_red.png";
                break;
            case 4:
                characterImage = "Client/src/img/char/char_purple.png";
                break;
            case 5:
                characterImage = "Client/src/img/char/char_green.png";
                break;
            case 6:
                characterImage = "Client/src/img/char/char_black.png";
                break;
            default:
                characterImage = "Client/src/img/char/nocolor.gif";
        }
        return characterImage;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
