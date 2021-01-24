package game;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class represents the game board and handles
 * the interactions the players have with it.
 */
public class Panel extends JPanel implements KeyListener {

    private static final int startingLine = 9;
    private static final int startingColumn = 9;
    private static final int distance = 36;

    private Image backgroundImage;
    private Player myPlayer;
    private final Map<String, Integer> playerPicks;
    private final List<Player> players;
    private final LobbyLogic lobbyLogic;

    /**
     *  Creates the board panel and the player characters
     *  according to the specified {@link #playerPicks}.
     *  @param playerPicks the characters each player picked.
     *  @param lobbyLogic a reference to the object to be used to
     *                   communicate with the server.
     */
    Panel(Map<String, Integer> playerPicks, LobbyLogic lobbyLogic) {
        this.playerPicks = playerPicks;
        this.lobbyLogic = lobbyLogic;
        players = new ArrayList<>();
        backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/img/map4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setFocusable(true);
        addKeyListener(this);

        setMyCharacter();
        if (playerPicks != null)
            setOtherPlayersCharacter();
    }

    /**
     * Tells the specified player to move and updates
     * the board after moving.
     * @param playerName the username of the player to move.
     * @param direction the direction in which to move
     *                  (W,A,S,D).
     */
    public void movePlayerCharacter(String playerName, char direction) {
        if (playerName.equals(Account.getUsername()))
            myPlayer.moveInDirection(direction);
        else {
            for (Player player : players) {
                if (player.getName().equals(playerName)) {
                    player.moveOtherCharacters(direction);
                }
            }
        }
        repaint();
    }

    private void setOtherPlayersCharacter() {
        for (Map.Entry<String, Integer> entry: playerPicks.entrySet()) {
            String imagePath = getImagePath(entry.getValue());
            Image characterImg = new ImageIcon(getClass().getResource(imagePath)).getImage();
            Player player = new Player (entry.getKey(), characterImg,
                    startingLine, startingColumn);
            players.add(player);
        }
    }

    private void setMyCharacter() {
        int charNumber = Account.getCharNumber();
        String characterImage;

        characterImage = getImagePath(charNumber);
        Image characterImg = new ImageIcon(getClass().getResource(characterImage)).getImage();
        myPlayer = new Player(Account.getUsername(), characterImg, startingLine, startingColumn);
        players.add(myPlayer);
    }

    /**
     * Renders the image of the board and the player characters
     * to fit the game page correctly, using the width and height
     * of the screen.
     * @param g the Graphics to paint.
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        for (Player player : players) {
            g2D.drawImage(player.getCharacter(), player.getColumn()*distance*getWidth()/900,
                    player.getLine()*distance*getHeight()/720, getDistanceSide(), getDistanceUp(),this);
        }
    }

    /**
     * Sets the number of moves this client's
     * player can move in this turn.
     * @param total the sum of the 2 dice rolled.
     */
    public void setDiceValue(int total) {
        myPlayer.setMovesLeft(total);
    }

    /**
     * Checks if this player already rolled the
     * dice this turn.
     * @return true if they already rolled the
     * dice once this turn, false if they did not.
     */
    public boolean IAlreadyRolledTheDice() {
        return myPlayer.AlreadyRolleddTheDice();
    }

    /**
     * Used when a player ends their turn to
     * reset the indicators of no more moves
     * and if the dice was already rolled.
     */
    public void resetAttributes() {
        myPlayer.setNoMoreMoves(false);
        myPlayer.setRolledTheDice(false);
    }

    /**
     * Tells in which room this players is.
     * @return the name of the current room
     * or a empty string if they are not in
     * a room.
     */
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

    /**
     * Sends a message to the server to try to move
     * in the direction corresponding to the key that
     * was pressed (W,A,S,D).
     * @param e the key that was pressed.
     * @throws IllegalStateException if an unexpected key was pressed.
     */
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

    public String getImagePath(int charNumber) {
        String characterImage;
        switch (charNumber) {
            case 1:
                characterImage = "/img/char/sherlock.png";
                break;
            case 2:
                characterImage = "/img/char/moriarty.png";
                break;
            case 3:
                characterImage = "/img/char/mshudson.png";
                break;
            case 4:
                characterImage = "/img/char/irene.png";
                break;
            case 5:
                characterImage = "/img/char/enola.png";
                break;
            case 6:
                characterImage = "/img/char/mycroft.png";
                break;
            default:
                characterImage = "/img/char/nocolor.gif";
        }
        return characterImage;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
