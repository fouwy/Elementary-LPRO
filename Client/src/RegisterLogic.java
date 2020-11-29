import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegisterLogic implements ActionListener {
    private final RegisterPage registerPage;

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

        char[] pwd = registerPage.getPwdField().getPassword();
        char[] repeatPwd = registerPage.getRepeatPwdField().getPassword();
        String type = "Register";

        if (!Arrays.equals(pwd, repeatPwd)) {
            registerPage.showMessage("Passwords don't match.");
            registerPage.setPwdField_repeatPwdFieldEmpty();
            registerPage.setRegisterButtonEnabled(true);
        } else {
            registerPage.setRegisterButtonEnabled(false);
            String email = registerPage.getEmailField().getText();
            String username = registerPage.getUserField().getText();
            String[] accountInformation = {type, email, username, String.valueOf(pwd)};

            Client client = new Client("localhost");
            client.sendInformation(accountInformation);

            switch (client.isValidated()) {
                case 1:
                    registerPage.showMessage("Registration complete!");
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

    private void leavePage() {
        registerPage.disposeFrame();
        //TODO: Set login page visible
    }
}
