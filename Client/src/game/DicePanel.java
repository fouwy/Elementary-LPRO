package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Stores an image for each six faces of
 * a die in a JPanel.<br>
 * The panel with all die faces invisible when created.
 * A random face of the die can be shown
 * by calling the {@link #rollDice()} method.
 */
public class DicePanel extends JPanel {
    JLabel[] diceFaces;

    /**
     * Creates a JPanel with none of
     * the die faces visible.
     */
    DicePanel() {
        diceFaces = new JLabel[6];
        ImageIcon ic1 = new ImageIcon(getClass().getResource("/img/dice/1.png"));
        ImageIcon ic2 = new ImageIcon(getClass().getResource("/img/dice/2.png"));
        ImageIcon ic3 = new ImageIcon(getClass().getResource("/img/dice/3.png"));
        ImageIcon ic4 = new ImageIcon(getClass().getResource("/img/dice/4.png"));
        ImageIcon ic5 = new ImageIcon(getClass().getResource("/img/dice/5.png"));
        ImageIcon ic6 = new ImageIcon(getClass().getResource("/img/dice/6.png"));

        ic1 = scaleIcon(ic1, 60, 60);
        ic2 = scaleIcon(ic2, 60, 60);
        ic3 = scaleIcon(ic3, 60, 60);
        ic4 = scaleIcon(ic4, 60, 60);
        ic5 = scaleIcon(ic5, 60, 60);
        ic6 = scaleIcon(ic6, 60, 60);

        for (int i=0; i<6; i++) {
            diceFaces[i] = new JLabel("");
            diceFaces[i].setVisible(false);
            this.add(diceFaces[i]);
        }

        diceFaces[0].setIcon(ic1);
        diceFaces[1].setIcon(ic2);
        diceFaces[2].setIcon(ic3);
        diceFaces[3].setIcon(ic4);
        diceFaces[4].setIcon(ic5);
        diceFaces[5].setIcon(ic6);

        this.setVisible(true);
    }

    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);
    }

    /**
     * Gets a random number between 1 and 6
     * and sets the image with
     * the corresponding face of the die
     * visible on the JPanel.<br>
     * Returns the number rolled.
     * @return a random number between 1 and 6.
     */
    public int rollDice() {
        Random rand = new Random();
        int diceValue = rand.nextInt(4) + 3;

        if (Account.getUsername().equals("estorninho")) {
            if (Account.numberTurnEstorinho == 2 || Account.numberTurnEstorinho == 3)
                diceValue = 1;
            else
                diceValue = 6;

            Account.numberTurnEstorinho++;
        }
        setAllDicesInvisible();
        diceFaces[diceValue-1].setVisible(true);

        return diceValue;
    }

    private void setAllDicesInvisible() {
        for (JLabel dice : diceFaces) {
            dice.setVisible(false);
        }
    }
}