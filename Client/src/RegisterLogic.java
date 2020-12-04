import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegisterLogic implements ActionListener {
    private final RegisterPage registerPage;
    private String errorMessage = "Unknown Error";

    public RegisterLogic(RegisterPage registerPage) {
        this.registerPage = registerPage;
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        if (action.getSource().equals(registerPage.getLeaveButton()))
            leavePage();
        else if (action.getSource().equals(registerPage.getRegisterButton()))
            tryToRegister();
    }

    private void tryToRegister() {
        registerPage.setRegisterButtonEnabled(false);
        String[] accountInformation = getAccountInformation();

        if (!areParameterAcceptable(accountInformation)) {
            registerPage.showMessage(errorMessage);
            registerPage.setPwdField_repeatPwdFieldEmpty();
            registerPage.setRegisterButtonEnabled(true);
        } else {
            registerPage.setRegisterButtonEnabled(false);

            Client client = new Client("localhost");
            client.sendInformation(accountInformation);

            switch (client.isValidated()) {
                case 1:
                    registerPage.showMessage("Registration complete!");
                    leavePage();
                    break;
                case 0:
                    registerPage.showMessage("Username already taken.");
                    registerPage.setRegisterButtonEnabled(true);
                    break;
                case -1:
                    registerPage.showMessage("Could not register. Try again later.");
                    registerPage.setRegisterButtonEnabled(true);
                    break;
            }
        }
    }

    //needs a better name
    private boolean areParameterAcceptable( String[] accountInfo) {
        char[] pwd = registerPage.getPwdField().getPassword();
        char[] repeatPwd = registerPage.getRepeatPwdField().getPassword();
        String username = accountInfo[1];
        String email = accountInfo[3];

        int minPwdSize = 3;
        int minUsernameSize = 3;
        if (!Arrays.equals(pwd, repeatPwd))
            errorMessage = "Passwords don't match";
        else if (pwd.length < minPwdSize)
            errorMessage = "Password is too short (min is "+ minPwdSize;
        else if (username.length() <= minUsernameSize)
            errorMessage = "Username too short (min is "+ minUsernameSize;
        else if (!email.contains("@"))
            errorMessage = "Enter a valid email address";
        else
            return true;

        return false;
    }

    private String[] getAccountInformation() {
        String type = "Register";
        String email = registerPage.getEmailField().getText();
        String username = registerPage.getUserField().getText();
        char[] pwd = registerPage.getPwdField().getPassword();

        return new String[]{type, username, String.valueOf(pwd),  email};
    }

    private void leavePage() {
        registerPage.disposeFrame();
        new LoginPage();
    }
}
