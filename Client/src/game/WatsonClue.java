package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WatsonClue extends JPanel implements ActionListener {

    String[] clueList;
    ImageIcon ic_john = new ImageIcon(getClass().getResource("img/john.jpg"));
    JLabel watsonClue;
    JTextPane clue;
    JButton okButton;

    WatsonClue(){

        watsonClue = new JLabel();
        watsonClue.setIcon(ic_john);
        watsonClue.setVisible(false);

        clue = new JTextPane();
        clue.setFont(new Font("Arial",Font.BOLD,16));
        clue.setVisible(false);
        clue.setBackground(null);

        okButton = new JButton();
        okButton.setText("OK");
        okButton.setVisible(false);
        okButton.setFocusable(false);
        okButton.setEnabled(true);
        okButton.addActionListener(this);

        clueList[0] = "Maybe you'll find something on the hospital";
        clueList[1] = "I can tell you for sure that Weapon 2 is not the one you're looking for";
        clueList[2] = "Mycroft's alibi is confirmed, he's not the criminal";
        clueList[3] = "Isn't it suspicious that Ms Adler was so close to the pool?";

        Random random = new Random();
        int clueNumber = random.nextInt(4);
        watsonClue.setText(clueList[clueNumber]);
        watsonClue.setVisible(true);
        okButton.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==okButton){
            okButton.setVisible(false);
            watsonClue.setVisible(false);
        }
    }
}
