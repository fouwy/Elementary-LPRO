package common;

import authentication.Client;
import game.Account;
import game.LobbyPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLogic implements ActionListener {

    private final MainPage main_page;

    public MainLogic(MainPage main_page) {
        this.main_page = main_page;
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
        String username = Account.getUsername();
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
        String username = Account.getUsername();
        String friendUsername = main_page.getRemoveFriend().getText();
        String[] accountInformation = {type, username, friendUsername};

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
