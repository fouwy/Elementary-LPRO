package game;

import java.awt.*;

/**
 * This class represents a player and their character,
 * position and amount of moves left in a game.
 * <br><br>
 * It also represents the map of the game as a two
 * dimensional array of integers.
 * <br>
 * In this 2D array:
 * <ul>
 *     <li>0 is normal terrain</li>
 *     <li>1 is the outside edge of a wall</li>
 *     <li>2 is the inside edge of a wall</li>
 *     <li>negative numbers represent the diferent rooms</li>
 * </ul>
 */
public class Player {

    private static final int LIMIT_Y = 24;
    private static final int LIMIT_X = 19;

    private final String name;
    private final Image character;
    private int line;
    private int column;
    private int room;
    private int movesLeft;
    private boolean rolledTheDice;
    private boolean noMoreMoves;

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

    /**
     * Creates a new player with the specified name
     * and places their character in the specified position
     * in the board.
     * @param name the username of the player.
     * @param character the image of the character of this player.
     * @param positionX the line in the boardwhere to place the character.
     * @param positionY the column in the boardwhere to place the character.
     */
    public Player(String name, Image character, int positionX, int positionY) {
        this.name = name;
        this.character = character;
        this.line = positionX;
        this.column = positionY;
        movesLeft = 0;
        room = 0;
        rolledTheDice = false;
        noMoreMoves = false;
    }

    /**
     * Sets the number of moves this player has
     * left in their turn.
     * @param movesLeft number of moves left.
     */
    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
        rolledTheDice = true;
    }

    /**
     * Tells if this player already rolled the
     * dice in this turn.
     * @return true if they already rolled the dice,
     * false if they didn't yet.
     */
    public boolean AlreadyRolleddTheDice() {
        return rolledTheDice;
    }

    /**
     * Tells if the player has no more moves left
     * in this turn.
     * @return true if they don't have moves left,
     * false if they have.
     */
    public boolean hasNoMoreMoves() {
        return noMoreMoves;
    }

    /**
     * Returns the number of the room
     * where this player is.
     * @return the number of the room or
     * zero if they aren't in a room.
     */
    public int getRoom() {
        return room;
    }

    private void setRoom() {
        if (line == 3 && column == 4)   //fix bug in junction between hospital and morgue
            room = 2;
        if (board[line][column] == 2) {
            return;     //Stays in the same room
        }
        room = -board[line][column];
    }

    /**
     * Moves this client's player character in the direction
     * specified.
     * @param direction the direction in which to move
     *                  (W,A,S,D).
     */
    public void moveInDirection(char direction) {
        if (movesLeft == 0)
            noMoreMoves = true;
        else{
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
            movesLeft--;
            if (movesLeft == 0)
                noMoreMoves = true;
            System.out.println("room = " + room);
        }
    }

    /**
     * Moves the other player characters in a specified
     * direction.
     * @param direction the direction in which to move
     *                  (W,A,S,D).
     */
    public void moveOtherCharacters(char direction) {
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
    }

    /**
     * Checks if this player can move in the specified
     * direction.
     * <br><br>
     * If they are at one of the edges of the map they
     * cannot move out of the map, and if they are next
     * to a wall they cannot move into that wall.
     * <br>
     * As in the 2D board array the walls are not directly
     * specified as a integer, but as inner wall(1) and
     * outer wall(2) values, a player that moves into a
     * square with value 2 cannot move into a square with
     * value 1 and vice versa.
     * @param direction the direction in which to move
     *                  (W,A,S,D).
     * @return true if this player can move
     * in the specified direction, false if
     * it cannot.
     */
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

    public void setRolledTheDice(boolean rolledTheDice) {
        this.rolledTheDice = rolledTheDice;
    }

    public void setNoMoreMoves(boolean noMoreMoves) {
        this.noMoreMoves = noMoreMoves;
    }
}
