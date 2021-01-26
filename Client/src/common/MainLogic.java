package common;

import authentication.Client;
import game.Account;
import game.LobbyPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This class handles the logic and actions performed
 * by a client in the  the main page.
 * <br><br>
 * If the user clicks "HOST", it is created a lobby and the
 * lobby page appears.
 * <br>
 * If the user clicks "Join", it attempts to join the lobby
 * with the code specified in the adjacent textbox.
 * <br>
 * If the user clicks "Remove", it attempts to remove the
 * friend with the username specified in the adjacent textbox.
 * <br>
 * If the user clicks "Change", it attempts to change the
 * password of this client to the one specified in the
 * adjacent textbox.
 * <br>
 * If the user clicks "LEAVE", it logs the user out and returns
 * to the login page.
 * <br>
 * If the user clicks "DELETE ACCOUNT", it attempts to delete
 * the account and return to the login page.
 */
public class MainLogic implements ActionListener, MouseListener {

    private final MainPage main_page;
    private List<String> friends;
    private JPopupMenu menu;
    private JMenuItem item;
    private String lastClickedFriend;

    /**
     * Creates the handler for the main page and
     * creates a thread of {@link CommsFromServerThread}.
     * @param main_page the object responsible for the
     *                  main page UI.
     */
    public MainLogic(MainPage main_page) {
        this.main_page = main_page;
        System.out.println("I'm " + Account.getUsername());
        CommsFromServerThread comms = new CommsFromServerThread(this);
        Thread thread = new Thread(comms);
        thread.start();

        getFriendsList();
        showFriendsList();

        menu = new JPopupMenu();
        item = new JMenuItem("Join lobby");
        item.addActionListener(this);
        menu.add(item);
    }

    /**
     * Receives a message from the server.
     * <br>
     * The first string in {@code serverMessage}
     * determines the type.
     * <br>
     * If the type is "Add", a popup appears
     * asking if you want to accept as a friend the user
     * with the username specified in the second string
     * of {@code serverMessage}. If so, a request is
     * sent to the server to set them as friends.
     * @param serverMessage the message received.
     */
    public void receiveMessage(String[] serverMessage) {
        System.out.println("serverMessage = " + Arrays.toString(serverMessage));
        String type = serverMessage[0];
        switch (type) {
            case "Add":
                int result = main_page.openOptionDialog("Friend Request from: " + serverMessage[1] + "\n Do to accept?", "Friend Request");
                if (result == JOptionPane.YES_OPTION) {
                    setFriendToAdd(Account.getUsername(), serverMessage[1]);
                    getFriendsList();
                    showFriendsList();
                    System.out.println("Friend Request Accepted");
                    acceptFriendShip(true, serverMessage[1]);
                } else {
                    System.out.println("Friend Request Refused");
                    acceptFriendShip(false, serverMessage[1]);
                }
                break;
            case "Accepted":
                if (serverMessage[2].equals("yes")) {
                    showFriendsList();
                    main_page.showMessage(serverMessage[1] + " accepted your friend request.\nYou are now friends!");
                } else
                    main_page.showMessage(serverMessage[1] + " did not accept your friend request");
                getFriendsList();
                showFriendsList();
                break;
            case "Offline":
                main_page.showMessage("The friend you are trying to reach is offline :(");
                break;
            case "askLobby":
                sendLobbyCode(serverMessage[1]);
                break;
            case "Code":
                joinLobby(Integer.parseInt(serverMessage[1]));
                break;
        }
    }

    private void sendLobbyCode(String friend) {
        String[] info = {"getLobby", friend, String.valueOf(Account.getCurrentLobbyCode())};
        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(info);
    }

    private void acceptFriendShip(boolean accepted, String friend) {
        String[] accountInfo = {"acceptFriend", Account.getUsername(), friend, null};
        if (accepted)
            accountInfo[3] = "yes";
        else
            accountInfo[3] = "no";

        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(accountInfo);
    }

    private void showFriendsList() {
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 50;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        for(String friend : friends) {
            JLabel friendLabel = new JLabel(friend);
            friendLabel.addMouseListener(this);
            friendLabel.setAlignmentX(JLabel.CENTER);
            friendLabel.setHorizontalAlignment(SwingConstants.CENTER);
            if (c.gridy % 2 == 0)
                friendLabel.setBackground(new Color(211,211,211));
            else
                friendLabel.setBackground(Color.white);

            friendLabel.setOpaque(true);
            main_page.getFriendListPanel().add(friendLabel, c);
            c.gridy++;
        }

        SwingUtilities.invokeLater(() -> {
            main_page.getFriendListPanel().validate();
            main_page.getFriendListPanel().repaint();
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(((JLabel)e.getSource()).getText());
        lastClickedFriend = ((JLabel) e.getSource()).getText();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void getFriendsList() {
        String[] accountInformation = {"FriendsList", Account.getUsername()};
        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(accountInformation);
        friends = client.getFriends();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(main_page.getLeaveButton())) {
            enterLoginPage();
        } else if (e.getSource().equals(main_page.getHostButton())) {
            enterLobbyPage();
        } else if (e.getSource().equals(main_page.getJoinButton())) {
            joinLobby();
        } else if (e.getSource().equals(main_page.getAddButton())) {
            canIBeYourFriend(main_page.getFriendToAdd().getText());
        } else if (e.getSource().equals(main_page.getRemoveButton())) {
            setFriendToRemove();
        } else if (e.getSource().equals(main_page.getChangeButton())) {
            setNewPassword();
        } else if (e.getSource().equals(main_page.getDeleteAccountButton())){
            deleteAccount();
            enterLoginPage();
        } else if (e.getSource().equals(item)) {
            getFriendLobby(lastClickedFriend);
        }
    }

    private void getFriendLobby(String lastClickedFriend) {
        String[] info = {"askLobby", Account.getUsername(), lastClickedFriend};
        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(info);
    }

    private void canIBeYourFriend(String potencialFriend) {
        String[] info = {"askFriendship", Account.getUsername(), potencialFriend};
        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(info);
    }

    private void joinLobby() {
        int port = Integer.parseInt(main_page.getPortNumberField().getText());
        Scanner in;
        PrintWriter out;
        try {
            Socket socket = new Socket(ClientStart.serverIP, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            main_page.showMessage("Could not join this lobby.\nTry again with a valid code.");
            return;
        }
        Account.setLobbyCode(port);
        ClientStart.rootPanel.add(new LobbyPage(in, out).$$$getRootComponent$$$(), "Lobby");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Lobby");

    }

    private void joinLobby(int code) {
        Scanner in;
        PrintWriter out;
        try {
            Socket socket = new Socket(ClientStart.serverIP, code);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            main_page.showMessage("Could not join this lobby.\nTry again with a valid code.");
            return;
        }
        Account.setLobbyCode(code);
        ClientStart.rootPanel.add(new LobbyPage(in, out).$$$getRootComponent$$$(), "Lobby");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Lobby");

    }

    private void enterLoginPage(){
        Client client = new Client(ClientStart.serverIP);
        String[] accountInformation = {"Logout", Account.getUsername()};
        client.sendInformation(accountInformation);
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Login");
    }

    private void enterLobbyPage(){
        int port = tellServerToCreateLobby();
        Scanner in = null;
        PrintWriter out = null;
        try {
            Socket socket = new Socket(ClientStart.serverIP, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            main_page.showMessage("Could not create this lobby.\nTry again later (or click run on ServerStart).");
        }
        ClientStart.rootPanel.add(new LobbyPage(in, out).$$$getRootComponent$$$(), "Lobby");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Lobby");
    }

    private int tellServerToCreateLobby() {
        Client client = new Client(ClientStart.serverIP);
        String[] lobbyInfo = {"Host", Account.getUsername()};
        client.sendInformation(lobbyInfo);
        int port_number = client.getPort_number();
        Account.setLobbyCode(port_number);
        System.out.println("Lobby code is " + port_number);
        return port_number;
    }

    private void setFriendToAdd(String username, String friendUsername){
        String type = "AddFriend";
        String[] accountInformation = {type, username, friendUsername};

        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(accountInformation);

        switch (client.isFriendAdded()) {
            case -1:
                main_page.showMessage("Could not add friend. Try again later");
                break;
            case 0:
                main_page.showMessage("The friend you are trying to add does not exist.");
                break;
            case 1:
                main_page.showMessage("Friend added.");
                break;
            case 2:
                main_page.showMessage("This user is already your friend.");
                break;
        }
    }

    private void setFriendToRemove(){
        String type = "RemoveFriend";
        String username = Account.getUsername();
        String friendUsername = main_page.getRemoveFriend().getText();
        String[] accountInformation = {type, username, friendUsername};

        Client client = new  Client(ClientStart.serverIP);
        client.sendInformation(accountInformation);

        switch(client.isFriendRemoved()){
            case -1:
                main_page.showMessage("Could not remove friend. Try again later");
                break;
            case 0:
                main_page.showMessage("The friend you are trying to remove does not exist.");
                break;
            case 1:
                main_page.showMessage("Friend removed.");
                break;
        }
        getFriendsList();
        showFriendsList();
    }

    private void setNewPassword(){
        String type = "ChangePassword";
        String username = Account.getUsername();
        char[] pwd = main_page.getNewPassword().getPassword();
        String[] accountInformation = {type, username, String.valueOf(pwd)};

        if (!isNewPasswordAcceptable()) {
            main_page.setNewPasswordTextFieldEmpty();
            main_page.setChangeButtonEnabled(true);
        } else {

            Client client = new Client(ClientStart.serverIP);
            client.sendInformation(accountInformation);

            switch (client.isPasswordChanged()) {
                case -1:
                    main_page.showMessage("Couldn't change password. Try again later");
                    break;
                case 1:
                    main_page.showMessage("password changed");
                    break;

            }
        }
    }

    private boolean isNewPasswordAcceptable() {
        char[] pwd = main_page.getNewPassword().getPassword();

        int minPwdSize = 3;
        if (pwd.length < minPwdSize)
            main_page.showMessage("Password is too short (min is "+ minPwdSize);
        else
            return true;

        return false;
    }

    private void deleteAccount(){
        String type = "DeleteAccount";
        String username = Account.getUsername();
        String[] accountInformation = {type, username};

        Client client = new  Client(ClientStart.serverIP);
        client.sendInformation(accountInformation);

        switch(client.isAccountDeleted()){
            case -1:
                main_page.showMessage("Could not delete account. Try again later");
                break;
            case 1:
                main_page.showMessage("Account deleted.");
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
