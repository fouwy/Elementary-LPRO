package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Suggestion extends JPanel implements ActionListener {
    JComboBox person;
    JComboBox location;
    JComboBox weapon;
    JTextPane accusation_sentence;
    Boolean p=false;
    Boolean l=false;
    Boolean w=false;
    public Suggestion(){


        String[] people = {"Person A","Person B","Person C"};
        this.person = new JComboBox(people);
        person.addActionListener(this);


        String[] weapons = {"weapon 1", "weapon 2", "weapon 3"};
        this.weapon = new JComboBox(weapons);
        weapon.addActionListener(this);


        String[] places = {"place 1", "place 2", "place 3"};
        this.location = new JComboBox(places);
        location.addActionListener(this);

        accusation_sentence = new JTextPane();
        //accusation_sentence.setLocation(20,200);
        accusation_sentence.setFont(new Font("Arial",Font.BOLD,12));
        accusation_sentence.setVisible(false);

        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setSize(500,170);
        this.add(person);
        this.add(weapon);
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
        if(e.getSource()==weapon){
            System.out.println(weapon.getSelectedItem());
            w = true;

        }
        if(p&&l&&w){
            System.out.println("I suggest that " + person.getSelectedItem() + " murdered the victim with " + weapon.getSelectedItem() + " on " + location.getSelectedItem());
//            accusation_sentence.setText("I suggest that " + person.getSelectedItem() + " murdered the victim with " + weapon.getSelectedItem() + " on " + location.getSelectedItem());
//            accusation_sentence.setVisible(true);
        }
    }


    public static void main(String[] args) {
        new Suggestion();
    }
}