package game;

import java.awt.*;

public class Player {

    private static final int LIMIT_Y = 24;
    private static final int LIMIT_X = 19;

    private final String name;
    private final Image character;
    private int line;
    private int column;
    private int room;

                            //   0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24
    final int[][] board ={      {2, 2, 2,-1, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2}, //0
                                {2,-1,-1,-1,-2,-2, 2, 1, 1, 2,-3,-3,-3,-3,-3,-3,-3, 2, 1, 1, 2,-4,-4,-4, 2}, //1
                                {2,-1,-1,-1,-2,-2,-2, 0, 1, 2,-3,-3,-3,-3,-3,-3,-3, 2, 1, 1, 2,-4,-4,-4, 2}, //2
                                {2,-1,-1,-1, 2, 2, 2, 1, 1, 2,-3,-3,-3,-3,-3,-3,-3, 2, 1, 1, 2,-4,-4,-4, 2}, //3
                                {2,-1,-1, 2, 1, 1, 1, 0, 1, 2,-3, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2,-4,-4,-4, 2}, //4
                                {2,-1,-1, 2, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 2, 2, 2,-4, 2}, //5
                                {2,-1,-1, 2, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1}, //6
                                {2,-1,-1, 2, 1, 0, 0, 1, 2, 2, 2, 2, 2, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //7
                                {2,-1,-1,-1, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //8
                                {2, 2, 2, 2, 1, 0, 0, 1, 2, 0, 0, 0, 2, 1, 0, 1, 2,-5,-5,-5,-5,-5,-5,-5, 2}, //9
                                {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 0,-5,-5,-5,-5,-5,-5,-5,-5, 2}, //10
                                {0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 1, 0, 1, 2,-5,-5,-5,-5,-5,-5,-5, 2}, //11
                                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2}, //12
                                {1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //13
                                {2, 2,-6, 2, 2, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, //14
                                {2,-6,-6,-6, 2, 1, 0, 1, 2, 2,-7, 2, 2, 2, 2, 1, 0, 0, 1, 2, 2, 2, 2, 2, 2}, //15
                                {2,-6,-6,-6, 2, 1, 0, 1, 2,-7,-7,-7,-7,-7, 2, 1, 0, 0, 0,-8,-8,-8,-8,-8, 2}, //16
                                {2,-6,-6,-6, 2, 1, 0, 1, 2,-7,-7,-7,-7,-7, 2, 1, 0, 0, 1, 2,-8,-8,-8,-8, 2}, //17
                                {2,-6,-6,-6, 2, 1, 0, 1, 2,-7,-7,-7,-7,-7, 2, 1, 0, 0, 1, 2,-8,-8,-8,-8, 2}, //18
                                {2, 2, 2, 2, 2, 1, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 1, 2, 2, 2, 2, 2, 2}, //19
                        };

    public Player(String name, Image character, int positionX, int positionY) {
        this.name = name;
        this.character = character;
        this.line = positionX;
        this.column = positionY;
        room = 0;
    }

    private void setRoom() {
        if (line == 3 && column == 4)   //fix bug in junction between hospital and morgue
            room = 2;
        if (board[line][column] == 2) {
            return;     //Stays in the same room
        }
        room = -board[line][column];
    }

    public void moveInDirection(char direction) {
        switch (direction) {
            case 'W':
                line -= 1;
                break;
            case 'A':
                column -= 1;
                break;
            case 'S':
                line += 1;
                break;
            case 'D':
                column += 1;
                break;
        }
        setRoom();
        System.out.println("room = " + room);
//        System.out.println("pos   = (" + line + ", " + column + ")");
//        System.out.println("board =  " + board[line][column]);
    }

    public boolean canMove(char direction) {
        switch (direction) {
            case 'W':
                if (line == 0)
                    return false;
                if ((board[line][column]==1 && board[line -1][column]==2) ||
                    (board[line][column]==2 && board[line -1][column]==1)) {
//                    System.out.println("up is =  " + board[line-1][column]);
                    return false;
                }
                break;
            case 'A':
                if (column == 0)
                    return false;
                if ((board[line][column]==1 && board[line][column -1]==2) ||
                    (board[line][column]==2 && board[line][column -1]==1)) {
//                    System.out.println("left is =  " + board[line][column-1]);
                    return false;
                }
                break;
            case 'S':
                if (line == LIMIT_X)
                    return false;
                if ((board[line][column]==1 && board[line +1][column]==2) ||
                    (board[line][column]==2 && board[line +1][column]==1)) {
//                    System.out.println("down is =  " + board[line+1][column]);
                    return false;
                }
                break;
            case 'D':
                if (column == LIMIT_Y)
                    return false;
                if ((board[line][column]==1 && board[line][column +1]==2) ||
                    (board[line][column]==2 && board[line][column +1]==1)) {
//                    System.out.println("right is =  " + board[line][column+1]);
                    return false;
                }
                break;
        }
        return true;
    }

    public Image getCharacter() {
        return character;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getName() {
        return name;
    }
}
