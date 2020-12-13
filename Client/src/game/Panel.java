package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Panel extends JPanel implements KeyListener {

    int xpanel, ypanel, width, height;
    Image character;
    Image backgroundImage;
    int dx = 324;
    int dy = 324;
    int distance = 36;
    int edge = 0, canmove = 0;

    //block limits
    int BAKER_LEFT = 288;//dx
    int BAKER_RIGHT = 468;//-character width, dx
    int BAKER_UP = 252;//dy
    int BAKER_DOWN = 432;//-character height, dy
    //--- Initial Block SetUp
    int upper_limit=BAKER_UP;
    int down_limit=BAKER_DOWN;
    int left_limit=BAKER_LEFT;
    int right_limit=BAKER_RIGHT;
    //---
    int PALACE_LEFT = 324; //dx
    int PALACE_RIGHT = 648; //- character width, dx
    int PALACE_DOWN = 180; //- character height, dy
    int HOSPITAL_RIGHT = 144; //-character width, dx
    int HOSPITAL_DOWN = 360; //-character height, dy
    int MORGUE_RIGHT = 252; //-character width, dx
    int MORGUE_DOWN = 144; //-character height, dy
    int PRISON_RIGHT = 180; //-character width, dx
    int PRISON_UP = 504; //dy
    int CIRCUS_LEFT = BAKER_LEFT;//dx
    int CIRCUS_RIGHT = 540;//-character width, dx
    int CIRCUS_UP = 540;// dy
    int MAGNUSSEN_LEFT = 684;//dx
    int MAGNUSSEN_UP = 540;//dy
    int LABS_UP = 288;//dy
    int LABS_DOWN = 468; //-character height, dy
    int LABS_LEFT = 576;//dx
    int POOL_LEFT = 720;//dx
    int POOL_DOWN = 216;//-character height, dy

    Panel(int xpanel, int ypanel, int width, int height){

        this.xpanel = xpanel;
        this.ypanel = ypanel;
        this.width = width;
        this.height = height;

        backgroundImage = new ImageIcon("Client/src/img/map4.png").getImage();
        this.setBounds(xpanel, ypanel, width, height);
        this.setLayout(null);

        this.setFocusable(true);
        addKeyListener(this);
        character = new ImageIcon("Client/src/img/character.png").getImage();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(backgroundImage, 0, 0, null);
        g2D.drawImage(character, dx, dy, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case (KeyEvent.VK_W):
                canmove=1;
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
                        edge = 1;
                        if((dx == 360 || dx == 72) && dy != 252) {
                            canmove = 1;
                        } else canmove = 0;
                    } else edge = 0;
                }
                //and surroundings	(troca os limites)
                if(dy != upper_limit )	//faz isto varias vezes
                    edge=0;

                if (dy == down_limit && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    canmove = 0;
                    edge = 1;
                    //if he's next to the door (on the lower side) he can move in
                    if( (dx == 360 && dy == 180) || (dx == 828 && dy == 216) ) {
                        canmove=1;
                    } else
                        canmove=0;
                }
                //============================================
                //main panel
                if(dy == 0) {
                    edge=1;
                    canmove=0;
                }
                //============================================
                if(canmove == 1) {
                    canmove=0;
                    dy -= distance;
                } else {
                    if (edge != 1) {
                        canmove = 1;
                        dy -= distance;
                    }
                }
                break;
            case (KeyEvent.VK_S):
                canmove=1;
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
                        edge = 1;
                        if((dx == 360 || dx == 828) && dy != 396) {
                            canmove = 1;
                        }else canmove = 0;
                    }else edge = 0;
                }
                //and surroundings
                if(dy != down_limit - (character.getHeight(null)+1) )	//faz isto varias vezes
                    edge=0;
                if (dy + ((character.getHeight(null)+1)) == upper_limit && dx >= left_limit && dx <= (right_limit - (character.getWidth(null)+1))) {
                    canmove = 0;
                    edge = 1;
                    //if he's next to the door (on the upper side) he can move in
                    if( (dx == 360 && dy == 504) || (dx == 72 && dy == 468) ) {
                        canmove=1;
                    }else canmove=0;
                }
                //============================================
                //main panel
                if(dy == 684) {
                    edge=1;
                    canmove=0;
                }
                //============================================
                if(canmove == 1) {
                    canmove=0;
                    dy += distance;
                }else {
                    if (edge != 1) {
                        canmove = 1;
                        dy += distance;
                    }
                }
                break;
            case (KeyEvent.VK_A):
                canmove=1;
                //set new boundaries for hospital on the entrance
                if(dx == 144 && dy == 288 || dx == 252 && dy == 72) {
                    upper_limit = 0;
                    down_limit = HOSPITAL_DOWN;
                    right_limit = HOSPITAL_RIGHT;
                    left_limit = 0;
                }
                //set new boundaries for morgue on the entrance
                if(dx == 252 && dy == 72 ) {
                    upper_limit = 0;
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

                        edge = 1;

                        if(dy == 360 ||dy == 576) {
                            canmove = 1;
                        }else canmove = 0;

                    }else edge = 0;
                }
                //and surroundings
                if(dx != left_limit )	//faz isto varias vezes
                    edge=0;

                if (dx == right_limit && dy >= upper_limit && dy <= (down_limit - (character.getHeight(null)+1))) {
                    canmove = 0;
                    edge = 1;
                    //if he's next to the door (on the right side) he can move in
                    if( (dx == 468 && dy == 288) || (dx == 144 && dy == 288) || (dx == 252 && dy == 72) ) {

                        canmove=1;
                    }else canmove=0;
                }
                //============================================
                //main panel
                if( dx == 0 ) {
                    edge=1;
                    canmove=0;
                }
                //============================================
                if(canmove == 1) {
                    dx -= distance;
                }else {
                    if(edge == 1) {
                        canmove=0;//fica preso aqui se clicar mos de fora do painel para dentro (nos cantos), canmove=0 e nunca fica canmove=1 nesta tecla
                    }else {
                        canmove=1;
                        dx -= distance;
                    }
                }
                break;
            case (KeyEvent.VK_D):
                canmove=1;
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
                        edge = 1;
                        if(dy == 288 || dy == 72) {	//leave the panel
                            canmove = 1;
                        }else canmove = 0;
                    }else edge = 0;
                }
                //and surroundings
                if(dx != (right_limit - (character.getWidth(null)+1)) ) {
                    edge=0;
                    //faz isto varias vezes
                }
                if (dx + (character.getWidth(null)+1) == left_limit && dy >= upper_limit && dy <= (down_limit - (character.getHeight(null)+1))) {
                    canmove = 0;
                    edge = 1;
                    if((dx == 252 && dy == 360) || (dx == 540 && dy == 360) || (dx == 648 && dy == 576)) {
                        canmove=1;
                    }else canmove=0;
                }
                //============================================
                //main panel
                if(dx == 864) {
                    edge=1;
                    canmove=0;
                }
                //============================================
                if(canmove == 1) {
                    dx += distance;
                }else {
                    if(edge == 1) {
                        canmove=0;
                    }else {
                        canmove=1;
                        dx += distance;
                    }
                }
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
