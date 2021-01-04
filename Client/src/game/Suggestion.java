package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Suggestion extends JPanel implements ActionListener {
    JComboBox person;
    JComboBox weapon;
    JLabel location;
    JTextPane accusation_sentence;
    Boolean p=false;
    Boolean l=false;
    Boolean w=false;

    public Suggestion(String location) {
        String[] people = {"person 1","person 2","person 3","person 4","person 5","person 6"};
        this.person = new JComboBox(people);
        person.addActionListener(this);


        String[] weapons = {"weapon 1", "weapon 2", "weapon 3","weapon 4", "weapon 5"};
        this.weapon = new JComboBox(weapons);
        weapon.addActionListener(this);

        this.location = new JLabel();
        this.location.setText(location);

        accusation_sentence = new JTextPane();
        accusation_sentence.setFont(new Font("Arial",Font.BOLD,12));
        accusation_sentence.setVisible(false);

        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setSize(500,170);
        this.add(person);
        this.add(weapon);
        this.add(this.location);
        this.add(accusation_sentence);

    }

    public String[] getSelectedSuggestion() {
        return new String[]{(String) person.getSelectedItem(), (String) weapon.getSelectedItem()};
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==person){
            System.out.println(person.getSelectedItem());
            p=true;
        }
        if(e.getSource()==weapon){
            System.out.println(weapon.getSelectedItem());
            w = true;
        }
        if(p&&l&&w){
            System.out.println("I suggest that " + person.getSelectedItem() + " murdered the victim with " + weapon.getSelectedItem() + " on " + location);
        }
    }
}