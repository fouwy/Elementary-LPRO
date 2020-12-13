package game;

import java.awt.*;
import javax.swing.*;

public class Game extends JFrame {

    Panel backgroundp;

    public Game() {
        backgroundp = new Panel(90, 90, 900, 720);	//1260, 720

        this.setTitle("ELEMENTARY");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(750,750);
        this.getContentPane().setBackground(new Color(0,19,51));
        this.add(backgroundp);
        this.setVisible(true);
    }


}
