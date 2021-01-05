import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DiceClue extends JFrame implements ActionListener {

    JButton roll_dice;
    JTextPane result;
    JTextPane youGetClue;
    JTextPane clue;
    JLabel john;
    int dice1Value;
    int dice2Value;

    public DiceClue(){

        roll_dice = new JButton();
        roll_dice.setText("Roll the dice");
        roll_dice.setBounds(10,10,50,30);
        roll_dice.setVisible(true);
        roll_dice.setFocusable(false);
        roll_dice.addActionListener(this);

        result = new JTextPane();
        result.setFont(new Font("Arial",Font.BOLD,12));
        result.setVisible(false);

        youGetClue = new JTextPane();
        youGetClue.setFont(new Font("Arial",Font.BOLD,12));
       // youGetClue.setBounds(20,100,50,50);
        youGetClue.setVisible(false);

        clue = new JTextPane();
        clue.setText(" \" Maybe you'll find something on the hospital... \" ");
        clue.setFont(new Font("Arial",Font.BOLD,16));
        clue.setVisible(false);
        clue.setBackground(null);

        ImageIcon ic_john = new ImageIcon(getClass().getResource("img/john.jpg"));
        john = new JLabel();
        john.setIcon(ic_john);
       // john.setBounds(100,100,50,50);
        john.setSize(40,40);
        john.setVisible(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);
        this.setSize(400,400);
        this.add(roll_dice);
        this.add(result);
        this.add(youGetClue);
        this.add(john);
        this.add(clue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==roll_dice){
            Random rand1 = new Random();
            dice1Value = rand1.nextInt(6) + 1;
            Random rand2 = new Random();
            dice2Value = rand2.nextInt(6) + 1;
           // System.out.println(dice1Value +" "+ dice2Value);
            result.setText(dice1Value +" "+ dice2Value);
            result.setVisible(true);

            if((dice1Value==1)&&(dice2Value==1)){
              //  System.out.println("You get a clue!");
                youGetClue.setText("You get a clue!");
                youGetClue.setVisible(true);
                john.setVisible(true);
                clue.setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        new DiceClue();
    }
}
