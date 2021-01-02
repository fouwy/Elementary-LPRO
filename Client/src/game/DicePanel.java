package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DicePanel extends JPanel {
    JLabel[] diceFaces;

    DicePanel() {
        diceFaces = new JLabel[6];
        ImageIcon ic1 = new ImageIcon("Client/src/1.png");
        ImageIcon ic2 = new ImageIcon("Client/src/2.png");
        ImageIcon ic3 = new ImageIcon("Client/src/3.png");
        ImageIcon ic4 = new ImageIcon("Client/src/4.png");
        ImageIcon ic5 = new ImageIcon("Client/src/5.png");
        ImageIcon ic6 = new ImageIcon("Client/src/6.png");

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

    public void rollDice() {
        Random rand = new Random();
        int diceValue = rand.nextInt(6) + 1;

        setAllDicesInvisible();
        diceFaces[diceValue-1].setVisible(true);
    }

    private void setAllDicesInvisible() {
        for (JLabel dice : diceFaces) {
            dice.setVisible(false);
        }
    }
}