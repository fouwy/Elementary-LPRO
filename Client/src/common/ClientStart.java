package common;

import authentication.LoginPage;
import game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for starting the client
 * and creating the frame for the UI.
 */
public class ClientStart extends JFrame {
    public static JFrame frame;
    public static CardLayout cardLayout;
    public static JPanel rootPanel;
    public static String serverIP = "176.79.140.197";

    public static void main(String[] args) {

        cardLayout = new CardLayout();
        frame = new JFrame();
        JPanel loginPanel = (JPanel) new LoginPage().$$$getRootComponent$$$();
        rootPanel = new JPanel(cardLayout);
        rootPanel.add(loginPanel, "Login");
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("ELEMENTARY");
        frame.setSize(560, 560);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
