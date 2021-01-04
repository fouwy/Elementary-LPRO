package common;

import authentication.Client;
import game.Account;
import game.LobbyPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class MainLogic implements ActionListener, MouseListener {

    private final MainPage main_page;
    private List<String> friends;

    public MainLogic(MainPage main_page) {
        this.main_page = main_page;
        System.out.println("I'm " + Account.getUsername());
        CommsFromServerThread comms = new CommsFromServerThread(this);
        Thread thread = new Thread(comms);
        thread.start();

        getFriendsList();
        showFriendsList();
    }

    public void receiveMessage(String[] serverMessage) {
        System.out.println("serverMessage = " + Arrays.toString(serverMessage));
        String type = serverMessage[0];
        if (type.equals("Add")) {
            int result = main_page.openOptionDialog("Friend Request from: "+ serverMessage[1]+ "\n Do to accept?", "Friend Request");
            if (result == JOptionPane.YES_OPTION) {
                System.out.println("Friend Request Accepted");
            } else {
                //Maybe tell them they go denied lol
            }
        }
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
//            setFriendToAdd();
//            getFriendsList();
//            showFriendsList();
        } else if (e.getSource().equals(main_page.getRemoveButton())) {
            setFriendToRemove();
        } else if (e.getSource().equals(main_page.getChangeButton())) {
            setNewPassword();
        }
    }

    private void canIBeYourFriend(String potencialFriend) {
        String[] info = {"askFriendship", Account.getUsername(), potencialFriend};
        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(info);
    }

    private void joinLobby() {
        String port = main_page.getPortNumberField().getText();

        if (port != null) {
            ClientStart.rootPanel.add(new LobbyPage(Integer.parseInt(port)).$$$getRootComponent$$$(), "Lobby");
            ClientStart.cardLayout.show(ClientStart.rootPanel, "Lobby");
        }
    }

    private void enterLoginPage(){
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Login");
    }

    private void enterLobbyPage(){
        ClientStart.rootPanel.add(new LobbyPage(tellServerToCreateLobby()).$$$getRootComponent$$$(), "Lobby");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Lobby");
    }

    private int tellServerToCreateLobby() {
        Client client = new Client(ClientStart.serverIP);
        String[] lobbyInfo = {"Host", Account.getUsername()};
        client.sendInformation(lobbyInfo);
        int port_number = client.getPort_number();
        System.out.println("Port number is " + port_number);
        return port_number;
    }

    private void setFriendToAdd(){
        String type = "AddFriend";
        String friendUsername = main_page.getFriendToAdd().getText();
        String[] accountInformation = {type, Account.getUsername(), friendUsername};

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
        String friendUsername = main_page.getRemoveFriend().getText();
        String[] accountInformation = {type, Account.getUsername(), friendUsername};

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
    }

    private void setNewPassword(){
        String type = "ChangePassword";
        String username = Account.getUsername();
        char[] pwd = main_page.getNewPassWord().getPassword();
        String[] accountInformation = {type, String.valueOf(username), String.valueOf(pwd)};
        /*String[] password = main_page.getNewPassWord().getPassword();*/

        Client client = new Client(ClientStart.serverIP);
        client.sendInformation(accountInformation);
        int minPwdSize = 3;

        switch (client.isPasswordChanged()) {
            case -1:
                main_page.showMessage("Couldn't change password. Try again later");
                break;
            case 1:
                if(pwd.length < minPwdSize){
                    main_page.showMessage("Password is too short (min is "+ minPwdSize);
                    break;
                }
                main_page.showMessage("password changed");
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
