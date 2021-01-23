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
        try {
            if (args[0].matches("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}")) {
                serverIP = args[0];
                System.out.println("Using "+serverIP+" as server IP");
            }
            else
                System.out.println("Invalid server IP, using default...");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Server IP not specified, using default...");
        }

        cardLayout = new CardLayout();
        frame = new JFrame();
        JPanel loginPanel = (JPanel) new LoginPage().$$$getRootComponent$$$();
        rootPanel = new JPanel(cardLayout);
        rootPanel.add(loginPanel, "Login");
        frame.setContentPane(rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("ELEMENTARY");
        frame.setSize(630, 560);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
