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
        if(e.getSource().equals(main_page.getLeaveButton())){
            enterLoginPage();
        } else if(e.getSource().equals(main_page.getHostButton())){
            enterLobbyPage();
        } else if(e.getSource().equals(main_page.getJoinButton())) {
            joinLobby();
        } else if(e.getSource().equals(main_page.getDeleteAccountButton())){
            deleteAccount();
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
        Client client = new Client("localhost");
        String[] accountInformation = {"Logout", Account.getUsername()};
        client.sendInformation(accountInformation);
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


    private void deleteAccount(){
        Client client = new Client("localhost");
        String[] accountInformation = {"Logout", Account.getUsername()};
        client.sendInformation(accountInformation);
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Logout");
    }


}
