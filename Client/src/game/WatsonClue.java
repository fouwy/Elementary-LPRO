package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WatsonClue extends JPanel {

    String[] clueList;
    ImageIcon ic_john = new ImageIcon(getClass().getResource("/img/john.jpg"));
    JLabel watsonClue;
    JTextPane clue;

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

        Random random = new Random();
        int clueNumber = random.nextInt(4);
        watsonClue.setText(clueList[clueNumber]);
        this.setLayout(new FlowLayout());
        this.add(watsonClue);
        this.setVisible(true);
    }
}
