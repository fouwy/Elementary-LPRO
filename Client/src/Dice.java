

import java.awt.*;


import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Dice extends JFrame implements ActionListener{

    JButton bt;
    JLabel lb1;
    JLabel lb2;
    JLabel lb3;
    JLabel lb4;
    JLabel lb5;
    JLabel lb6;




    Dice(){


        ImageIcon ic1 = new ImageIcon(getClass().getResource("1.png"));
        ImageIcon ic2 = new ImageIcon(getClass().getResource("2.png"));
        ImageIcon ic3 = new ImageIcon(getClass().getResource("3.png"));
        ImageIcon ic4 = new ImageIcon(getClass().getResource("4.png"));
        ImageIcon ic5 = new ImageIcon(getClass().getResource("5.png"));
        ImageIcon ic6 = new ImageIcon(getClass().getResource("6.png"));


        this.lb1 = new JLabel();
        lb1.setIcon(ic1);
        lb1.setBounds(150,250,150,150);
        lb1.setVisible(false);

        this.lb2 = new JLabel();
        lb2.setIcon(ic2);
        lb2.setBounds(150,250,150,150);
        lb2.setVisible(false);

        this.lb3 = new JLabel();
        lb3.setIcon(ic3);
        lb3.setBounds(150,250,150,150);
        lb3.setVisible(false);

        this.lb4 = new JLabel();
        lb4.setIcon(ic4);
        lb4.setBounds(150,250,150,150);
        lb4.setVisible(false);

        this.lb5 = new JLabel();
        lb5.setIcon(ic5);
        lb5.setBounds(150,250,150,150);
        lb5.setVisible(false);

        this.lb6 = new JLabel();
        lb6.setIcon(ic6);
        lb6.setBounds(150,250,150,150);
        lb6.setVisible(false);


        bt = new JButton();
        bt.setBounds(100, 100, 250, 100);
        bt.addActionListener(this);
        bt.setText("Roll the dice");

        bt.setFocusable(false);
        //bt.setIcon(ic1);
        bt.setHorizontalTextPosition(JButton.CENTER);
        bt.setVerticalTextPosition(JButton.BOTTOM);
        bt.setFont(new Font("Comic Sans",Font.BOLD,25));
       // bt.setIconTextGap(-15);
        bt.setForeground(Color.cyan);
        bt.setBackground(Color.lightGray);
        bt.setBorder(BorderFactory.createEtchedBorder());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,500);
        this.setVisible(true);
        this.add(bt);
        this.add(lb1);
        this.add(lb2);
        this.add(lb3);
        this.add(lb4);
        this.add(lb5);
        this.add(lb6);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== bt) {

            System.out.println("poo");
            int diceValue;
            Random rand = new Random();
            diceValue = rand.nextInt(6) + 1;
            System.out.println(diceValue);

            switch (diceValue){
                case(1):
                    System.out.println(11);
                    lb1.setVisible(true);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(false);
                    break;
                case(2):
                    System.out.println(22);
                    lb1.setVisible(false);
                    lb2.setVisible(true);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(false);
                    break;
                case(3):
                    System.out.println(33);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(true);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(false);
                    break;
                case(4):
                    System.out.println(44);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(true);
                    lb5.setVisible(false);
                    lb6.setVisible(false);
                    break;
                case(5):
                    System.out.println(55);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(true);
                    lb6.setVisible(false);
                    break;
                case(6):
                    System.out.println(66);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(true);
                    break;
            }

            //button.setEnabled(false);
            //lb.setVisible(true);
        }
    }
}