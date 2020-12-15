import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accusation extends JFrame implements ActionListener {
    JComboBox person;
    JComboBox location;
    JTextPane accusation_sentence;
    Boolean p=false;
    Boolean l=false;
    public Accusation(){


        String[] people = {"Person A","Person B","Person C"};
        this.person = new JComboBox(people);
        person.addActionListener(this);


        String[] places = {"Place 1", "Place 2", "Place 3"};
        this.location = new JComboBox(places);
        location.addActionListener(this);

        accusation_sentence = new JTextPane();
        //accusation_sentence.setLocation(20,200);
        accusation_sentence.setFont(new Font("Arial",Font.BOLD,12));
        accusation_sentence.setVisible(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);
        this.setSize(400,170);
        this.add(person);
        this.add(location);
        this.add(accusation_sentence);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==person){
            System.out.println(person.getSelectedItem());
            p=true;


        }
        if(e.getSource()==location){
            System.out.println(location.getSelectedItem());
            l = true;

        }
        if(p&&l){
            System.out.println("I accuse " + person.getSelectedItem() + " of murdering the victim on " + location.getSelectedItem());
            accusation_sentence.setText("I accuse " + person.getSelectedItem() + " of murdering the victim on " + location.getSelectedItem());
            accusation_sentence.setVisible(true);
        }
    }


    public static void main(String[] args) {
        new Accusation();
    }
}