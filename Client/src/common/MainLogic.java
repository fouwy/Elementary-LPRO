package common;

import authentication.Client;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.Spacer;
import game.Account;
import game.LobbyPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainLogic implements ActionListener {

    private final MainPage main_page;
    private List<String> friends;

    public MainLogic(MainPage main_page) {
        this.main_page = main_page;

        getFriendsList();
        showFriendsList();
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

    private void getFriendsList() {
        String[] accountInformation = {"FriendsList", Account.getUsername()};
        Client client = new Client("localhost");
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
            setFriendToAdd();
        } else if (e.getSource().equals(main_page.getRemoveButton())) {
            setFriendToRemove();
        } else if (e.getSource().equals(main_page.getChangeButton())) {
            setNewPassword();
        }
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
        Client client = new Client("localhost");
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

        Client client = new Client("localhost");
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

        Client client = new  Client("localhost");
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

        Client client = new Client("localhost");
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
}
