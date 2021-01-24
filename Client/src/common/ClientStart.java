package common;

import authentication.Client;
import authentication.LoginPage;
import game.Account;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    closeEverything();
                    frame.setVisible(false);
                    frame.dispose();
                    System.exit(0);
                }
            }
        };
        frame.addWindowListener(listener);
        frame.setVisible(true);
    }

    private static void closeEverything() {
        if (Account.getUsername() != null) {
            if (Account.getLobbyOutput() != null) {
                Account.getLobbyOutput().println("QUIT");
            }
            Client client = new Client(ClientStart.serverIP);
            String[] accountInformation = {"Exit", Account.getUsername()};
            client.sendInformation(accountInformation);
        }
    }
}
