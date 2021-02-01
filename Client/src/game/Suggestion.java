package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A suggestion or accusation phrase, using a dropdown menu to
 * select the intended person and weapon to suggest,
 * in a JPanel.
 */
public class Suggestion extends JPanel implements ActionListener {
    JComboBox lastLocation;
    JComboBox person;
    JComboBox weapon;
    JLabel location;
    JTextPane accusation_sentence;
    Boolean p=false;
    Boolean l=false;
    Boolean w=false;

    /**
     * Creates the suggestion or accusation phrase according
     * to the specified location and the input from the user
     * to get the person and weapon.
     * @param location the place to be put in the phrase.
     */
    public Suggestion(String location) {
        String[] people = {"SherlockHolmes","Moriarty","MrsHudson","IreneAdler","EnolaHolmes","MycroftHolmes"};
        this.person = new JComboBox(people);
        person.addActionListener(this);


        String[] weapons = {"Pills", "Vest", "Pistol","Glasses", "Belt"};
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

    public Suggestion() {
        String[] people = {"SherlockHolmes","Moriarty","MrsHudson","IreneAdler","EnolaHolmes","MycroftHolmes"};
        this.person = new JComboBox(people);
        person.addActionListener(this);


        String[] weapons = {"Pills", "Vest", "Pistol", "Glasses", "Belt"};
        this.weapon = new JComboBox(weapons);
        weapon.addActionListener(this);

        String[] locations = {"Hospital", "Morgue", "Palace", "Pool", "H.O.U.N.D.Labs", "Prison", "Museum", "Magnussen"};
        this.lastLocation = new JComboBox(locations);
        lastLocation.addActionListener(this);

        accusation_sentence = new JTextPane();
        accusation_sentence.setFont(new Font("Arial",Font.BOLD,12));
        accusation_sentence.setVisible(false);

        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setSize(500,170);
        this.add(person);
        this.add(weapon);
        this.add(lastLocation);
        this.add(accusation_sentence);
    }

    /**
     * Returns the person and weapon selected
     * by the user.
     * @return a string array with the person
     * and weapon input by the user.
     */
    public String[] getSelectedSuggestion() {
        return new String[]{(String) person.getSelectedItem(), (String) weapon.getSelectedItem()};
    }

    public String[] getLastAccusation() {
        return new String[]{(String) person.getSelectedItem(), (String) weapon.getSelectedItem(),
                (String) lastLocation.getSelectedItem()};
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
        if (e.getSource()==lastLocation) {
            l = true;
        }
        if(p&&l&&w){
            System.out.println("I suggest that " + person.getSelectedItem() + " murdered the victim with " + weapon.getSelectedItem() + " on " + location);
        }
    }
}