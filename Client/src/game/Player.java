package game;

import java.awt.*;

public class Player {
    private final int distance;
    private String name;
    private final Image character;
    private int positionX;
    private int positionY;

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
                this.positionY -= distance;
                break;
            case 'A':
                this.positionX -= distance;
                break;
            case 'S':
                this.positionY += distance;
                break;
            case 'D':
                this.positionX += distance;
                break;
        }
    }
}
