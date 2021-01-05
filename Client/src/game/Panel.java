package game;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class Panel extends JPanel implements KeyListener {

    Image character;
    final Image backgroundImage;
    int dx = 324;
    int dy = 324;
    int count;
    char keydirection;
    private final int distance = 36;
    boolean edge = false;
    boolean canMove = false;

    //block limits
    final int BAKER_LEFT = 288;//dx
    final int BAKER_RIGHT = 468;//-character width, dx
    final int BAKER_UP = 252;//dy
    final int BAKER_DOWN = 432;//-character height, dy
    //--- Initial Block SetUp
    int upper_limit=BAKER_UP;
    int down_limit=BAKER_DOWN;
    int left_limit=BAKER_LEFT;
    int right_limit=BAKER_RIGHT;
    //---
    final int PALACE_LEFT = 324; //dx
    final int PALACE_RIGHT = 648; //- character width, dx
    final int PALACE_DOWN = 180; //- character height, dy
    final int HOSPITAL_RIGHT = 144; //-character width, dx
    final int HOSPITAL_DOWN = 360; //-character height, dy
    final int MORGUE_RIGHT = 252; //-character width, dx
    final int MORGUE_DOWN = 144; //-character height, dy
    final int PRISON_RIGHT = 180; //-character width, dx
    final int PRISON_UP = 504; //dy
    final int CIRCUS_LEFT = BAKER_LEFT;//dx
    final int CIRCUS_RIGHT = 540;//-character width, dx
    final int CIRCUS_UP = 540;// dy
    final int MAGNUSSEN_LEFT = 684;//dx
    final int MAGNUSSEN_UP = 540;//dy
    final int LABS_UP = 288;//dy
    final int LABS_DOWN = 468; //-character height, dy
    final int LABS_LEFT = 576;//dx
    final int POOL_LEFT = 720;//dx
    final int POOL_DOWN = 216;//-character height, dy

                            //   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
    final int[][] boardArray={  {2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2}, //0
                                {2, 0, 0, 0, 0, 0, 2, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 2, 0, 0, 0, 2}, //1
                                {2, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 2, 0, 0, 0, 2}, //2
                                {2, 0, 0, 0, 2, 2, 2, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2, 1, 1, 2, 0, 0, 0, 2}, //3
                                {2, 0, 0, 2, 1, 1, 1, 0, 1, 2, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 0, 0, 0, 2}, //4
                                {2, 0, 0, 2, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 2, 2, 2, 0, 2}, //5
                                {2, 0, 0, 2, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1}, //6
                                {2, 0, 0, 2, 1, 0, 0, 1, 2, 2, 2, 2, 2, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //7
                                {2, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //8
                                {2, 2, 2, 2, 1, 0, 0, 1, 2, 0, 0, 0, 2, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2}, //9
                                {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}, //10
                                {0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2}, //11
                                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //12
                                {1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //13
                                {2, 2, 0, 2, 2, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, //14
                                {2, 0, 0, 0, 2, 1, 0, 1, 2, 2, 0, 2, 2, 2, 2, 1, 0, 0, 1, 2, 2, 2, 2, 2, 2}, //15
                                {2, 0, 0, 0, 2, 1, 0, 1, 2, 0, 0, 0, 0, 0, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2}, //16
                                {2, 0, 0, 0, 2, 1, 0, 1, 2, 0, 0, 0, 0, 0, 2, 1, 0, 0, 1, 2, 0, 0, 0, 0, 2}, //17
                                {2, 0, 0, 0, 2, 1, 0, 1, 2, 0, 0, 0, 0, 0, 2, 1, 0, 0, 1, 2, 0, 0, 0, 0, 2}, //18
                                {2, 2, 2, 2, 2, 1, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 1, 2, 2, 2, 2, 2, 2}, //19
                             };

    private final Map<String, Integer> playerPicks;
    private final List<Player> players;
    private final LobbyLogic lobbyLogic;

    Panel(Map<String, Integer> playerPicks, int xpanel, int ypanel, int width, int height, LobbyLogic lobbyLogic){
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

    public void setDiceRoll(int total) {
        //TODO: Set in Player
    }

    private int getDistanceUp() {
        return getHeight()/20 + 1;
    }

    private int getDistanceSide() {
        return getWidth()/25 + 1;
    }

    public void movePlayerCharacter(String playerName, int dicevalue1, int dicevalue2) {
       /* Player player = new Player(playerName,players.,distance)
            if (player.getName().equals(playerName)) {
                count=dicevalue1+dicevalue2;
                while(count>0)
                {

                }

                repaint();
            }
        }*/
    }

    private void setOtherPlayersCharacter() {
        for (Map.Entry<String, Integer> entry: playerPicks.entrySet()) {
            String imagePath = getImagePath(entry.getValue());
            Image characterImg = new ImageIcon(imagePath).getImage();
            Player player = new Player (entry.getKey(), characterImg,
                                        dx, dy, distance);
            players.add(player);
        }
    }


    private void setMyCharacter() {
        int charNumber = Account.getCharNumber();
        String characterImage;
        characterImage = getImagePath(charNumber);
        character = new ImageIcon(characterImage).getImage();
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
                throw new IllegalStateException("Unexpected value: " + charNumber);
        }
        return characterImage;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        //my character - could put it in the players list
        g2D.drawImage(character, dx*getWidth()/900, dy*getHeight()/720, getDistanceSide(), getDistanceUp(),this);

        for (Player player : players) {
            g2D.drawImage(player.getCharacter(), player.getX()*getWidth()/900, player.getY()*getHeight()/720, getDistanceSide(), getDistanceUp(),this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        switch (key) {
            case (KeyEvent.VK_W):
                //set new boundaries for Palace on the entrance
                keydirection='W';
               // player.moveInDirection(keydirection);
                count--;
                break;

            case (KeyEvent.VK_S):
                keydirection='S';
              //  player.moveInDirection(keydirection);
                count--;
                break;

            case (KeyEvent.VK_A):
                keydirection='A';
              //  player.moveInDirection(keydirection);
                count--;
                break;

            case (KeyEvent.VK_D):

                keydirection='D';
               // player.moveInDirection(keydirection);
                count--;
                break;
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
