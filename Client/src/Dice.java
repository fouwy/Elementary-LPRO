

import java.awt.*;


import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Dice extends JFrame implements ActionListener{

    JButton bt;
    JButton get_result;
    JLabel lb1;
    JLabel lb2;
    JLabel lb3;
    JLabel lb4;
    JLabel lb5;
    JLabel lb6;
    JLabel dice1;
    JLabel dice2;
    JLabel dice3;
    JLabel dice4;
    JLabel dice5;
    JLabel dice6;
    JTextPane position;
    JTextPane possible_positions;
    JTextPane choice;
    JTextPane final_pos;
    JTextField chosen_character;
    int x=0;
    int y=0;
    int diceValue;
    String s;
    char c=' ';

    Dice(){
        ImageIcon ic1 = new ImageIcon(getClass().getResource("img/1.png"));
        ImageIcon ic2 = new ImageIcon(getClass().getResource("img/2.png"));
        ImageIcon ic3 = new ImageIcon(getClass().getResource("img/3.png"));
        ImageIcon ic4 = new ImageIcon(getClass().getResource("img/4.png"));
        ImageIcon ic5 = new ImageIcon(getClass().getResource("img/5.png"));
        ImageIcon ic6 = new ImageIcon(getClass().getResource("img/6.png"));
        ImageIcon dado1 = new ImageIcon(getClass().getResource("img/dice1.png"));
        ImageIcon dado2 = new ImageIcon(getClass().getResource("img/dice2.png"));
        ImageIcon dado3 = new ImageIcon(getClass().getResource("img/dice3.png"));
        ImageIcon dado4 = new ImageIcon(getClass().getResource("img/dice4.png"));
        ImageIcon dado5 = new ImageIcon(getClass().getResource("img/dice5.png"));
        ImageIcon dado6 = new ImageIcon(getClass().getResource("img/dice6.png"));

        this.position = new JTextPane();
        position.setText("Current position: [ " + x +  " ]  [ " + y + " ]");
        position.setBounds(600,20,400,50);
        position.setFont(new Font("Comic Sans",Font.BOLD,18));
        position.setOpaque(false);
        position.setVisible(true);
        position.setEditable(true);

        this.possible_positions = new JTextPane();
        possible_positions.setText("You can move to these points: ");
        possible_positions.setBounds(600,50,400,50);
        possible_positions.setFont(new Font("Comic Sans",Font.BOLD,18));
        possible_positions.setOpaque(false);
        possible_positions.setVisible(true);
        possible_positions.setEditable(false);

        this.choice = new JTextPane();
        choice.setText("Which one do you want to move to?");
        choice.setBounds(570,470,340,500);
        choice.setFont(new Font("Comic Sans",Font.BOLD,18));
        choice.setOpaque(false);
        choice.setVisible(true);
        choice.setEditable(false);

        this.chosen_character = new JTextField();
        chosen_character.setBounds(995,470,50,50);
        chosen_character.setFont(new Font("Comic Sans",Font.BOLD,15));
        chosen_character.setEditable(true);
        chosen_character.setVisible(true);

        this.final_pos = new JTextPane();
        final_pos.setText("You are now in position [ " + x + " ] , [ " + y + " ]");
        final_pos.setBounds(570,500,340,500);
        final_pos.setFont(new Font("Comic Sans",Font.BOLD,18));
        final_pos.setOpaque(false);
        final_pos.setVisible(true);
        final_pos.setEditable(false);


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

        this.dice1 = new JLabel();
        dice1.setIcon(dado1);
        dice1.setBounds(650,30,500,500);
        dice1.setVisible(false);

        this.dice2 = new JLabel();
        dice2.setIcon(dado2);
        dice2.setBounds(640,30,500,500);
        dice2.setVisible(false);

        this.dice3 = new JLabel();
        dice3.setIcon(dado3);
        dice3.setBounds(630,30,500,500);
        dice3.setVisible(false);

        this.dice4 = new JLabel();
        dice4.setIcon(dado4);
        dice4.setBounds(620,30,500,500);
        dice4.setVisible(false);

        this.dice5 = new JLabel();
        dice5.setIcon(dado5);
        dice5.setBounds(610,30,500,500);
        dice5.setVisible(false);

        this.dice6 = new JLabel();
        dice6.setIcon(dado6);
        dice6.setBounds(600,30,500,500);
        dice6.setVisible(false);


        bt = new JButton();
        bt.setBounds(100, 20, 250, 100);
        bt.addActionListener(this);
        bt.setText("Roll the dice");

        bt.setFocusable(false);
        bt.setHorizontalTextPosition(JButton.CENTER);
        bt.setVerticalTextPosition(JButton.BOTTOM);
        bt.setFont(new Font("Comic Sans",Font.BOLD,25));
        bt.setForeground(Color.black);
        bt.setBackground(Color.red);
        bt.setBorder(BorderFactory.createEtchedBorder());


        get_result = new JButton();
        get_result.setBounds(1100, 470,100,100);
        get_result.addActionListener(this);
        get_result.setText("GO");
        get_result.setFocusable(false);
        get_result.setFont(new Font("Comic Sans",Font.BOLD,18));
        get_result.setForeground(Color.black);
        get_result.setBackground(Color.RED);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1920,1080);
        this.setVisible(true);
        this.add(bt);
        this.add(get_result);
        this.add(lb1);
        this.add(lb2);
        this.add(lb3);
        this.add(lb4);
        this.add(lb5);
        this.add(lb6);
        this.add(dice1);
        this.add(dice2);
        this.add(dice3);
        this.add(dice4);
        this.add(dice5);
        this.add(dice6);
        this.add(position);
        this.add(possible_positions);
        this.add(choice);
        this.add(chosen_character);
        this.add(final_pos);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()== bt) {
            position.setText("Current position: [ " + x +  " ]  [ " + y + " ]");
            //System.out.println("poo");

            Random rand = new Random();
            this.diceValue = rand.nextInt(6) + 1;
           // System.out.println(diceValue);


            x=this.x;
            y=this.y;
            switch (diceValue){
                case(1):
                   // System.out.println(11);
                    lb1.setVisible(true);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(false);

                    dice1.setVisible(true);
                    dice2.setVisible(false);
                    dice3.setVisible(false);
                    dice4.setVisible(false);
                    dice5.setVisible(false);
                    dice6.setVisible(false);



                    break;

                case(2):
                    //System.out.println(22);
                    lb1.setVisible(false);
                    lb2.setVisible(true);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(false);

                    dice1.setVisible(false);
                    dice2.setVisible(true);
                    dice3.setVisible(false);
                    dice4.setVisible(false);
                    dice5.setVisible(false);
                    dice6.setVisible(false);

                    break;

                case(3):
                    //System.out.println(33);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(true);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(false);

                    dice1.setVisible(false);
                    dice2.setVisible(false);
                    dice3.setVisible(true);
                    dice4.setVisible(false);
                    dice5.setVisible(false);
                    dice6.setVisible(false);

                    break;

                case(4):
                    System.out.println(44);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(true);
                    lb5.setVisible(false);
                    lb6.setVisible(false);

                    dice1.setVisible(false);
                    dice2.setVisible(false);
                    dice3.setVisible(false);
                    dice4.setVisible(true);
                    dice5.setVisible(false);
                    dice6.setVisible(false);
                    break;

                case(5):
                   // System.out.println(55);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(true);
                    lb6.setVisible(false);

                    dice1.setVisible(false);
                    dice2.setVisible(false);
                    dice3.setVisible(false);
                    dice4.setVisible(false);
                    dice5.setVisible(true);
                    dice6.setVisible(false);

                    break;

                case(6):
                   // System.out.println(66);
                    lb1.setVisible(false);
                    lb2.setVisible(false);
                    lb3.setVisible(false);
                    lb4.setVisible(false);
                    lb5.setVisible(false);
                    lb6.setVisible(true);

                    dice1.setVisible(false);
                    dice2.setVisible(false);
                    dice3.setVisible(false);
                    dice4.setVisible(false);
                    dice5.setVisible(false);
                    dice6.setVisible(true);

                    break;
            }

            //button.setEnabled(false);
            //lb.setVisible(true);
        }
        if(e.getSource()==get_result){
            s=chosen_character.getText();
         //   System.out.println(s);
            c=s.charAt(0);
          //  System.out.println(c);

            switch (diceValue){
                case(1):
                switch (c){
                    case('A'):
                        y+=1;
                        break;

                    case('B'):
                        x+=1;
                        break;

                    case('C'):
                        y-=1;
                        break;

                    case('D'):
                        x-=1;
                        break;

                    default:break;
                }
                break;
                case(2):
                    switch (c){
                        case('A'):
                            y+=2;
                            break;

                        case('B'):
                            x+=1;
                            y+=1;
                            break;

                        case('C'):
                            x+=2;
                            break;

                        case('D'):
                            x+=1;
                            y-=1;
                            break;

                        case('E'):
                            y-=2;
                            break;

                        case('F'):
                            y-=1;
                            x-=1;
                            break;

                        case('G'):
                            x-=2;
                            break;

                        case('H'):
                            x-=1;
                            y+=1;
                            break;

                        default:break;
                    }
                    break;
                case(3):
                    switch (c){
                        case('A'):
                            y+=3;
                            break;

                        case('B'):
                            x+=1;
                            y+=2;
                            break;

                        case('C'):
                            x+=2;
                            y+=1;
                            break;

                        case('D'):
                            x+=3;
                            break;

                        case('E'):
                            x+=2;
                            y-=1;
                            break;

                        case('F'):
                            y-=2;
                            x+=1;
                            break;

                        case('G'):
                            y-=3;
                            break;

                        case('H'):
                            x-=1;
                            y-=2;
                            break;

                        case('I'):
                            x-=2;
                            y-=1;
                            break;

                        case('J'):
                            x-=3;
                            break;

                        case('K'):
                            x-=2;
                            y+=1;
                            break;

                        case('L'):
                            x-=1;
                            y+=2;
                            break;

                        case('M'):
                            y+=1;
                            break;

                        default:break;
                    }
                    break;

                case(4):

                    switch (c){
                        case('A'):
                            y+=4;
                            break;

                        case('B'):
                            x+=1;
                            y+=3;
                            break;

                        case('C'):
                            x+=2;
                            y+=2;
                            break;

                        case('D'):
                            x+=3;
                            y+=1;
                            break;

                        case('E'):
                            x+=4;
                            break;

                        case('F'):
                            x+=3;
                            y-=1;
                            break;

                        case('G'):
                            x+=2;
                            y-=2;
                            break;

                        case('H'):
                            x+=1;
                            y-=3;
                            break;

                        case('I'):
                            y-=4;
                            break;

                        case('J'):
                            x-=1;
                            y-=3;
                            break;

                        case('K'):
                            x-=2;
                            y-=2;
                            break;

                        case('L'):
                            x-=3;
                            y-=1;
                            break;

                        case('M'):
                            x-=4;
                            break;

                        case('N'):
                            x-=3;
                            y+=1;
                            break;

                        case('O'):
                            x-=2;
                            y+=2;
                            break;

                        case('P'):
                            x-=1;
                            y+=3;
                            break;

                        case('Q'):
                            y+=2;
                            break;

                        case('R'):
                            x+=1;
                            y+=1;
                            break;

                        case('S'):
                            x+=2;
                            break;

                        case('T'):
                            x+=1;
                            y-=1;
                            break;

                        case('U'):
                            y-=2;
                            break;

                        case('V'):
                            x-=1;
                            y-=1;
                            break;

                        case('W'):
                            x-=2;
                            break;

                        case('X'):
                            x-=1;
                            y+=1;
                            break;

                        default:break;
                    }

                    break;
                case(5):
                    switch (c){
                        case('A'):
                            y+=5;
                            break;

                        case('B'):
                            x+=1;
                            y+=4;
                            break;

                        case('C'):
                            x+=2;
                            y+=3;
                            break;

                        case('D'):
                            x+=3;
                            y+=2;
                            break;

                        case('E'):
                            x+=4;
                            y+=1;
                            break;

                        case('F'):
                            x+=5;
                            break;

                        case('G'):
                            x+=4;
                            y-=1;
                            break;

                        case('H'):
                            x+=3;
                            y-=2;
                            break;

                        case('I'):
                            x+=2;
                            y-=3;
                            break;

                        case('J'):
                            x+=1;
                            y-=4;
                            break;

                        case('K'):
                            y-=5;
                            break;

                        case('L'):
                            x-=1;
                            y-=4;
                            break;

                        case('M'):
                            x-=2;
                            y-=3;
                            break;

                        case('N'):
                            x-=3;
                            y-=2;
                            break;

                        case('O'):
                            x-=4;
                            y-=1;
                            break;

                        case('P'):
                            x-=5;
                            break;

                        case('Q'):
                            x-=4;
                            y+=1;
                            break;

                        case('R'):
                            x-=3;
                            y+=2;
                            break;

                        case('S'):
                            x-=2;
                            y+=3;
                            break;

                        case('T'):
                            x-=1;
                            y+=4;
                            break;

                        case('U'):
                            y+=3;
                            break;

                        case('V'):
                            x+=1;
                            y+=2;
                            break;

                        case('W'):
                            x+=2;
                            y+=1;
                            break;

                        case('X'):
                            x+=3;
                            break;

                        case('Y'):
                            x+=2;
                            y-=1;
                            break;

                        case('Z'):
                            x+=1;
                            y-=2;
                            break;

                        case('1'):
                            y+=3;
                            break;

                        case('2'):
                            x-=1;
                            y-=2;
                            break;

                        case('3'):
                            x-=2;
                            y-=1;
                            break;

                        case('4'):
                            x-=3;
                            break;

                        case('5'):
                            x-=2;
                            y+=1;
                            break;

                        case('6'):
                            x-=1;
                            y+=2;
                            break;

                        case('7'):
                            y+=1;
                            break;

                        case('8'):
                            x+=1;
                            break;

                        case('9'):
                            y-=1;
                            break;

                        case('0'):
                            x-=1;
                            break;

                        default:break;
                    }
                    break;
                case(6):
                    switch (c){
                        case('A'):
                            y+=4;
                            break;

                        case('B'):
                            x+=1;
                            y+=3;
                            break;

                        case('C'):
                            x+=2;
                            y+=2;
                            break;

                        case('D'):
                            x+=3;
                            y+=1;
                            break;

                        case('E'):
                            x+=4;
                            break;

                        case('F'):
                            x+=3;
                            y-=1;
                            break;

                        case('G'):
                            x+=2;
                            y-=2;
                            break;

                        case('H'):
                            x+=1;
                            y-=3;
                            break;

                        case('I'):
                            y-=4;
                            break;

                        case('J'):
                            x-=1;
                            y-=3;
                            break;

                        case('K'):
                            x-=2;
                            y-=2;
                            break;

                        case('L'):
                            x-=3;
                            y-=1;
                            break;

                        case('M'):
                            x-=4;
                            break;

                        case('N'):
                            x-=3;
                            y+=1;
                            break;

                        case('O'):
                            x-=2;
                            y+=2;
                            break;

                        case('P'):
                            x-=1;
                            y+=3;
                            break;

                        case('Q'):
                            y+=2;
                            break;

                        case('R'):
                            x+=1;
                            y+=1;
                            break;

                        case('S'):
                            x+=2;
                            break;

                        case('T'):
                            x+=1;
                            y-=1;
                            break;

                        case('U'):
                            y-=2;
                            break;

                        case('V'):
                            x-=1;
                            y-=1;
                            break;

                        case('W'):
                            x-=2;
                            break;

                        case('X'):
                            x-=1;
                            y+=1;
                            break;

                        case('Y'):
                            y+=6;
                            break;

                        case('Z'):
                            x+=1;
                            y+=5;
                            break;

                        case('1'):
                            x+=2;
                            y+=4;
                            break;

                        case('2'):
                            x+=3;
                            y+=3;
                            break;

                        case('3'):
                            x+=4;
                            y+=2;
                            break;

                        case('4'):
                            x+=5;
                            y+=1;
                            break;

                        case('5'):
                            x+=6;
                            break;

                        case('6'):
                            x+=5;
                            y-=1;
                            break;

                        case('7'):
                            x+=4;
                            y-=2;
                            break;

                        case('8'):
                            x+=3;
                            y-=3;
                            break;

                        case('9'):
                            x+=2;
                            y-=4;
                            break;

                        case('0'):
                            x+=1;
                            y-=5;
                            break;

                        case('!'):
                            y-=6;
                            break;

                        case('"'):
                            x-=1;
                            y-=5;
                            break;

                        case('#'):
                            x-=2;
                            y-=4;
                            break;

                        case('$'):
                            x-=3;
                            y-=3;
                            break;

                        case('%'):
                            x-=4;
                            y-=2;
                            break;

                        case('&'):
                            x-=5;
                            y-=1;
                            break;

                        case('/'):
                            x-=6;
                            break;

                        case('('):
                            x-=5;
                            y+=1;
                            break;

                        case(')'):
                            x-=4;
                            y+=2;
                            break;

                        case('='):
                            x-=3;
                            y+=3;
                            break;

                        case('?'):
                            x-=2;
                            y+=4;
                            break;

                        case('»'):
                            x-=1;
                            y+=5;
                            break;

                        default:break;
                    }
                    break;

            }

          //  System.out.println(x +" , " + y);
            final_pos.setText("You are now in position [ " + x + " ] , [ " + y + " ]");
        }
    }
}