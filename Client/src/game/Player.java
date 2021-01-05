package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Player {
    private final int distance;
    private String name;
    private final Image character;
    private int positionX;
    private int positionY;
    private Popup dicePopup;
    private DicePanel dicePanel;

    public Player(String name, Image character, int positionX, int positionY, int distance) {
        this.name = name;
        this.character = character;
        this.positionX = positionX;
        this.positionY = positionY;
        this.distance = distance;
    }


    public Image getCharacter() {
        return character;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }

    public String getName() {
        return name;
    }


    public void moveInDirection(char direction) {

         switch (direction) {
            case 'W':
                if(((this.positionY==1)&&((this.positionY-=distance)==2))||((this.positionY==2)&&((this.positionY-=distance)==1))){
                    break;
                }
                this.positionY -= distance;
                break;

            case 'A':
                if(((this.positionX==1)&&((this.positionX-=distance)==2))||((this.positionX==2)&&((this.positionX-=distance)==1))){
                    break;
                }
                this.positionX -= distance;
                break;
            case 'S':
                if(((this.positionY==1)&&((this.positionY+=distance)==2))||((this.positionY==2)&&((this.positionY+=distance)==1))){
                    break;
                }
                this.positionY += distance;
                break;
            case 'D':
                if(((this.positionX==1)&&((this.positionX+=distance)==2))||((this.positionX==2)&&((this.positionX+=distance)==1))){
                    break;
                }
                this.positionX += distance;
                break;
        }


    }

    private int diceRoll() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }




}
