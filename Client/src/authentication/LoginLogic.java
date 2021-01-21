package authentication;

import common.ClientStart;
import common.MainPage;
import game.Account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the logic and actions performed
 * by a client in the login page.
 * <br><br>
 * If the user clicks "Login", all the fields are
 * collected and then they are sent to the server
 * to attempt to log in the user. The server
 * response will show up in a popup.
 * <br>
 * If the user clicks "Register", it will go to the register page.
 */
public class LoginLogic implements ActionListener {

    private final LoginPage login_page;
    private String username;
    private char[] password;

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

    private void tryLogin(){

        username = login_page.getUserField().getText();
        password = login_page.getPasswordField().getPassword();
        String type = "Login";

        String[] accountInformation = {type, username, String.valueOf(password)};

        Client client = new Client(ClientStart.serverIP);
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

    private void enterRegisterPage() {
        ClientStart.rootPanel.add(new RegisterPage().$$$getRootComponent$$$(), "Register");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Register");
    }

    private void enterMainPage(){
        new Account(username, password);
        ClientStart.rootPanel.add(new MainPage().$$$getRootComponent$$$(), "Main");
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Main");
    }
}
