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

        if (!Arrays.equals(pwd, repeatPwd)) {
            registerPage.showMessage("Passwords don't match.");
            registerPage.setPwdField_repeatPwdFieldEmpty();
            registerPage.setRegisterButtonEnabled(true);
        } else {
            registerPage.setRegisterButtonEnabled(false);
            String email = registerPage.getEmailField().getText();
            String username = registerPage.getUserField().getText();

            //TODO: Send email, username and password to server to validate
        }
    }

    private void leavePage() {
        registerPage.disposeFrame();
    }
}
