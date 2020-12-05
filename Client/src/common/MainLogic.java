package common;

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

        new LobbyPage();
        main_page.disposeMain();
    }
}
