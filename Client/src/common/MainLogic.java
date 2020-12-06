package common;

import authentication.Client;
import game.Account;
import game.LobbyPage;
import authentication.LoginPage;

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
        }
        else if(e.getSource().equals(main_page.getHostButton())){
            enterLobbyPage();
        }
    }

    private void enterLoginPage(){
        new LoginPage();
        main_page.disposeMain();
    }

    private void enterLobbyPage(){
        new LobbyPage(tellServerToCreateLobby(main_page.getAccount()));
        main_page.disposeMain();
    }

    private int tellServerToCreateLobby(Account account) {
        Client client = new Client("localhost");
        String[] lobbyInfo = {"Host", account.getUsername()};
        client.sendInformation(lobbyInfo);
        int port_number = client.getPort_number();
        System.out.println("Port number is " + port_number);

        return port_number;
    }


}
