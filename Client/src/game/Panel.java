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
    final int distance = 36;
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
        //this.setBounds(xpanel, ypanel, width, height);
//        this.setLayout(null);
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
            default:
                characterImage = "Client/src/img/char_red.png";
        }
        return characterImage;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(backgroundImage, 0, 0, null);
        //my character - could put it in the players list
        g2D.drawImage(character, dx, dy, null);

        for (Player player : players) {
            g2D.drawImage(player.getCharacter(), player.getX(), player.getY(), null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*
            Delete this two variables later just for testing movement
            oldX, oldY
         */
        int oldX = dx;
        int oldY = dy;

        int key = e.getKeyCode();
        canMove = true;

        switch (key) {
            case (KeyEvent.VK_W):
                //set new boundaries for Palace on the entrance
                if( dx == 360 && dy == 144) {
                    upper_limit = 0;
                    down_limit = PALACE_DOWN;
                    right_limit = PALACE_RIGHT;
                    left_limit = PALACE_LEFT;
                }
                //set new boundaries for Pool on the entrance
                if( dx == 828 && dy == 180) {
                    upper_limit = 0;
                    down_limit = POOL_DOWN;
                    right_limit = 864;
                    left_limit = POOL_LEFT;
                }
                System.out.println("upper_limit "+ upper_limit);
                //block limits
                if(dy >= upper_limit && dy <= (down_limit - (character.getHeight(null)+1)) && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    if(dy == upper_limit) {
                        edge = true;
                        canMove = (dx == 360 || dx == 72) && dy != 252;
                    } else edge = false;
                }
                //and surroundings	(troca os limites)
                if(dy != upper_limit )	//faz isto varias vezes
                    edge = false;

                if (dy == down_limit && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    canMove = false;
                    edge = true;
                    //if he's next to the door (on the lower side) he can move in
                    canMove = (dx == 360 && dy == 180) || (dx == 828 && dy == 216);
                }
                //============================================
                //main panel
                if(dy == 0) {
                    edge = true;
                    canMove =false;
                }
                //============================================
                if(canMove) {
                    canMove =false;
                    dy -= distance;
                } else {
                    if (!edge) {
                        canMove = true;
                        dy -= distance;
                    }
                }
                break;

            case (KeyEvent.VK_S):
                //set new boundaries for Prison on the entrance
                if( dx == 72 && dy == 540) {
                    upper_limit = PRISON_UP;
                    down_limit = 720;
                    right_limit = PRISON_RIGHT;
                    left_limit = 0;
                }
                //set new boundaries for Circus on the entrance
                if( dx == 360 && dy == 540) {
                    upper_limit = CIRCUS_UP;
                    down_limit = 720;
                    right_limit = CIRCUS_RIGHT;
                    left_limit = CIRCUS_LEFT;
                }
                //block limits
                if(dy >= upper_limit && dy <= (down_limit - (character.getHeight(null)+1)) && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    if(dy == down_limit - (character.getHeight(null)+1) || dy == upper_limit) {
                        edge = true;
                        canMove = (dx == 360 || dx == 828) && dy != 396;
                    }else edge = false;
                }
                //and surroundings
                if(dy != down_limit - (character.getHeight(null)+1) )	//faz isto varias vezes
                    edge = false;
                if (dy + ((character.getHeight(null)+1)) == upper_limit && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    canMove = false;
                    edge = true;
                    //if he's next to the door (on the upper side) he can move in
                    canMove = (dx == 360 && dy == 504) || (dx == 72 && dy == 468);
                }
                //============================================
                //main panel
                if(dy == 684) {
                    edge = true;
                    canMove =false;
                }
                //============================================
                if(canMove) {
                    canMove =false;
                    dy += distance;
                }else {
                    if (!edge) {
                        canMove = true;
                        dy += distance;
                    }
                }
                break;

            case (KeyEvent.VK_A):
                //set new boundaries for hospital on the entrance
                if(dx == 144 && dy == 288 || dx == 252 && dy == 72) {
                    upper_limit = 0;
                    down_limit = HOSPITAL_DOWN;
                    right_limit = HOSPITAL_RIGHT;
                    left_limit = 0;
                }
                //set new boundaries for morgue on the entrance
                if(dx == 252 && dy == 72 ) {
                    down_limit = MORGUE_DOWN;
                    right_limit = MORGUE_RIGHT;
                    left_limit = 144;
                }
                //set new boundaries for baker on the entrance
                if(dx == 468 && dy == 288 ) {
                    upper_limit = BAKER_UP;
                    down_limit = BAKER_DOWN;
                    right_limit = BAKER_RIGHT;
                    left_limit = BAKER_LEFT;
                }
                //============================================
                //block limits
                if(dy >= upper_limit && dy <= (down_limit- (character.getHeight(null)+1)) && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    if(dx == left_limit) {
                        edge = true;
                        canMove = dy == 360 || dy == 576;
                    }else edge = false;
                }
                //and surroundings
                if(dx != left_limit )	//faz isto varias vezes
                    edge = false;

                if (dx == right_limit && dy >= upper_limit && dy <= (down_limit - (character.getHeight(null)+1))) {
                    canMove = false;
                    edge = true;
                    //if he's next to the door (on the right side) he can move in
                    canMove = (dx == 468 && dy == 288) || (dx == 144 && dy == 288) || (dx == 252 && dy == 72);
                }
                //============================================
                //main panel
                if( dx == 0 ) {
                    edge = true;
                    canMove =false;
                }
                //============================================
                if(canMove) {
                    dx -= distance;
                }else {
                    if(edge) {
                        canMove =false;//fica preso aqui se clicar mos de fora do painel para dentro (nos cantos), canmove=false e nunca fica canmove=true nesta tecla
                    }else {
                        canMove =true;
                        dx -= distance;
                    }
                }
                break;

            case (KeyEvent.VK_D):
                //set new boundaries for Labs on the entrance
                if( dx == 576 && dy == 360) {
                    upper_limit = LABS_UP;
                    down_limit = LABS_DOWN;
                    right_limit = 900;
                    left_limit = LABS_LEFT;
                }
                //set new boundaries for Magnussen on the entrance
                if(dx == 684 && dy == 576) {
                    upper_limit = MAGNUSSEN_UP;
                    down_limit = 720;
                    right_limit = 900;
                    left_limit = MAGNUSSEN_LEFT;
                }
                //set new boundaries for baker on the entrance
                if(dx == 288 && dy == 360 ) {
                    upper_limit = BAKER_UP;
                    down_limit = BAKER_DOWN;
                    right_limit = BAKER_RIGHT;
                    left_limit = BAKER_LEFT;
                }
                //block limit
                if(dy >= upper_limit && dy <= (down_limit - (character.getHeight(null)+1)) && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    if(dx == (right_limit - (character.getWidth(null)+1)) || (dy == upper_limit)) {
                        edge = true;
                        //leave the panel
                        canMove = dy == 288 || dy == 72;
                    }else edge = false;
                }
                //and surroundings
                if(dx != (right_limit - (character.getWidth(null)+1)) ) {
                    edge = false;
                    //faz isto varias vezes
                }
                if (dx + (character.getWidth(null)+1) == left_limit && dy >= upper_limit && dy <= (down_limit - (character.getHeight(null)+1))) {
                    canMove = false;
                    edge = true;
                    canMove = (dx == 252 && dy == 360) || (dx == 540 && dy == 360) || (dx == 648 && dy == 576);
                }
                //============================================
                //main panel
                if(dx == 864) {
                    edge = true;
                    canMove =false;
                }
                //============================================
                if(canMove) {
                    dx += distance;
                }else {
                    if (!edge) {
                        canMove =true;
                        dx += distance;
                    }
                }
                break;
        }

        if (dx != oldX) {
            if (dx <  oldX)
                lobbyLogic.tellServerToUpdatePosition("MOVEA");
            else
                lobbyLogic.tellServerToUpdatePosition("MOVED");
        } else if (dy != oldY) {
            if (dy < oldY)
                lobbyLogic.tellServerToUpdatePosition("MOVEW");
            else
                lobbyLogic.tellServerToUpdatePosition("MOVES");
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
