package game;

import java.awt.*;


import java.awt.event.*;
import javax.swing.*;

public class Dice extends JPanel implements ActionListener{

    JButton okButton;
    JLabel lb1;
    JLabel lb2;
    JLabel lb3;
    JLabel lb4;
    JLabel lb5;
    JLabel lb6;
    JLabel[] dice1Labels;
    JLabel[] dice2Labels;
    int value1;
    int value2;

    Dice(int value1, int value2){

        this.value1=value1;
        this.value2=value2;

        //icons
        ImageIcon ic1 = new ImageIcon(getClass().getResource("img/1.png"));
        ImageIcon ic2 = new ImageIcon(getClass().getResource("img/2.png"));
        ImageIcon ic3 = new ImageIcon(getClass().getResource("img/3.png"));
        ImageIcon ic4 = new ImageIcon(getClass().getResource("img/4.png"));
        ImageIcon ic5 = new ImageIcon(getClass().getResource("img/5.png"));
        ImageIcon ic6 = new ImageIcon(getClass().getResource("img/6.png"));

        //labels
        this.lb1 = new JLabel();
        lb1.setIcon(ic1);
        lb1.setBounds(150,200,150,150);
        lb1.setVisible(false);

        this.lb2 = new JLabel();
        lb2.setIcon(ic2);
        lb2.setBounds(150,200,150,150);
        lb2.setVisible(false);

        this.lb3 = new JLabel();
        lb3.setIcon(ic3);
        lb3.setBounds(150,200,150,150);
        lb3.setVisible(false);

        this.lb4 = new JLabel();
        lb4.setIcon(ic4);
        lb4.setBounds(150,200,150,150);
        lb4.setVisible(false);

        this.lb5 = new JLabel();
        lb5.setIcon(ic5);
        lb5.setBounds(150,200,150,150);
        lb5.setVisible(false);

        this.lb6 = new JLabel();
        lb6.setIcon(ic6);
        lb6.setBounds(150,200,150,150);
        lb6.setVisible(false);

        dice1Labels[0]=lb1;
        dice1Labels[1]=lb2;
        dice1Labels[2]=lb3;
        dice1Labels[3]=lb4;
        dice1Labels[4]=lb5;
        dice1Labels[5]=lb6;

        dice2Labels[0]=lb1;
        dice2Labels[1]=lb2;
        dice2Labels[2]=lb3;
        dice2Labels[3]=lb4;
        dice2Labels[4]=lb5;
        dice2Labels[5]=lb6;

        dice1Labels[value1].setVisible(true);
        dice2Labels[value2].setVisible(true);

        okButton = new JButton();
        okButton.setBounds(100, 20, 250, 100);
        okButton.addActionListener(this);
        okButton.setText("OK");
        okButton.setFocusable(false);
        okButton.setHorizontalTextPosition(JButton.CENTER);
        okButton.setVerticalTextPosition(JButton.BOTTOM);
        okButton.setFont(new Font("Comic Sans",Font.BOLD,25));
        okButton.setForeground(Color.black);
        okButton.setBackground(Color.red);
        okButton.setBorder(BorderFactory.createEtchedBorder());




        this.setLayout(null);
        this.setSize(400,400);
        this.setVisible(true);
        this.add(okButton);
        this.add(dice1Labels[value1]);
        this.add(dice2Labels[value2]);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==okButton){
            okButton.setVisible(false);
            dice1Labels[value1].setVisible(false);
            dice2Labels[value2].setVisible(false);
        }
    }

}