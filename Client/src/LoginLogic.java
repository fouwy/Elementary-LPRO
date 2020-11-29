
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginLogic implements ActionListener {

    private final LoginPage login_page;

    public LoginLogic(LoginPage login_page) {
        this.login_page = login_page;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*if (e.getSource().equals(login_page.getLoginButton()))
            tryLogin();*/
        /*else*/ if (e.getSource() == login_page.getRegisterButton()) {
            enterRegisterPage();
        }
    }


    //For login button
    /*private void tryLogin(){

        String user = login_page.getUserField().getText();
        char[] password = login_page.getPasswordField().getPassword();
        String type = "Login";

        //Verify username
        String[] accountInformation = {type, user, String.valueOf(password)};

        Client client = new Client("localhost");
        client.sendInformation(accountInformation); //Enviar com identificador de login

        if(client.hasInfo()==1){
            //TODO: go to main page
        }
        else{
            login_page.showMessage("The username or/and password and invalid");
        }

    }*/

    //For register button
    private void enterRegisterPage(){

        //Open Register page
        new RegisterPage();
        login_page.disposeLogin();
    }

}
