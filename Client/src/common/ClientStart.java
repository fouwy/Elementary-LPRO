package common;

import authentication.LoginPage;

import javax.swing.*;
import java.awt.*;

public class ClientStart extends JFrame {
    public static JFrame frame;
    public static CardLayout cardLayout;
    public static JPanel rootPanel;

    public static void main(String[] args) {

        cardLayout = new CardLayout(5,5);
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

        //TODO: Close account in server if User closed the game
    }
}
