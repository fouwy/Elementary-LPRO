package authentication;

import common.ClientStart;
import common.MainPage;
import game.Account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginLogic implements ActionListener {

    private final LoginPage login_page;
    private String username;

    public LoginLogic(LoginPage login_page) {
        this.login_page = login_page;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(login_page.getLoginButton()))
            tryLogin();
        else if (e.getSource() == login_page.getRegisterButton()) {
            enterRegisterPage();
        }
    }

    //For login button
    private void tryLogin(){

        username = login_page.getUserField().getText();
        char[] password = login_page.getPasswordField().getPassword();
        String type = "Login";

        //Verify username
        String[] accountInformation = {type, username, String.valueOf(password)};

        Client client = new Client("localhost");
        client.sendInformation(accountInformation);

        switch (client.isInfoCorrect()) {
            case 1:
                enterMainPage();
                break;
            case 0:
                login_page.showMessage("Username is not registered.");
                break;
            case -1:
                login_page.showMessage("The password is not correct");
                break;
            case 2:
                login_page.showMessage("User already logged in");
                break;
        }

    }

    private void enterRegisterPage(){
        ClientStart.rootPanel.add(new RegisterPage().$$$getRootComponent$$$(), "Register");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Register");
    }

    private void enterMainPage(){
        new Account(username);
        ClientStart.rootPanel.add(new MainPage().$$$getRootComponent$$$(), "Main");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Main");
    }

}