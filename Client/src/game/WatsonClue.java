package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * A random clue shown in a JPanel.
 */
public class WatsonClue extends JPanel {

    String[] clueList;
    ImageIcon ic_john = new ImageIcon(getClass().getResource("/img/john.jpg"));
    JLabel watsonClue;
    JTextPane clue;

    /**
     * Creates and shows a random clue phrase.
     */
    WatsonClue(){
        watsonClue = new JLabel();
        watsonClue.setIcon(ic_john);

        clue = new JTextPane();
        clue.setFont(new Font("Arial",Font.BOLD,16));
        clue.setBackground(null);

        clueList = new String[4];
        clueList[0] = "Maybe you'll find something on the hospital";
        clueList[1] = "I can tell you for sure that Weapon 2 is not the one you're looking for";
        clueList[2] = "Mycroft's alibi is confirmed, he's not the criminal";
        clueList[3] = "Isn't it suspicious that Ms Adler was so close to the pool?";

        watsonClue.setText(clueList[2]);
        this.setLayout(new FlowLayout());
        this.add(watsonClue);
        this.setVisible(true);
    }
}
