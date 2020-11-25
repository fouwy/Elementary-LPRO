import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegisterPage {
    private JPanel panel1;
    private JTextField userField;


    private JPasswordField pwdField;
    private JLabel titleLabel;
    private JLabel userLabel;
    private JLabel pwdLabel;
    private JTextField emailField;
    private JLabel emailLabel;
    private JLabel repeatPwdLabel;
    private JPasswordField repeatPwdField;


    private JButton leaveButton;
    JFrame frame;

    public RegisterPage() {
        frame = new JFrame("Register");
        frame.add(panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ActionListener handler = new RegisterLogic(this);
        registerButton.addActionListener(handler);
        leaveButton.addActionListener(handler);
    }

    public void setRegisterButtonEnabled(boolean condition) {
        registerButton.setEnabled(condition);
    }

    public JTextField getUserField() {
        return userField;
    }

    public JPasswordField getPwdField() {
        return pwdField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getRepeatPwdField() {
        return repeatPwdField;
    }

    public void setPwdField_repeatPwdFieldEmpty() {
        pwdField.setText("");
        repeatPwdField.setText("");
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(frame, msg);
    }

    public void disposeFrame() {
        frame.dispose();
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    private JButton registerButton;

    public JButton getLeaveButton() {
        return leaveButton;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(6, 2, new Insets(5, 5, 5, 5), -1, -1));
        panel1.setBackground(new Color(-12511684));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        titleLabel = new JLabel();
        Font titleLabelFont = this.$$$getFont$$$("Stencil", Font.BOLD, 23, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setForeground(new Color(-1));
        titleLabel.setText("Elementary");
        panel1.add(titleLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userLabel = new JLabel();
        userLabel.setForeground(new Color(-1));
        userLabel.setText("Username");
        panel1.add(userLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pwdLabel = new JLabel();
        pwdLabel.setForeground(new Color(-1));
        pwdLabel.setText("Password");
        panel1.add(pwdLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userField = new JTextField();
        panel1.add(userField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        pwdField = new JPasswordField();
        panel1.add(pwdField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        emailField = new JTextField();
        panel1.add(emailField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        emailLabel = new JLabel();
        emailLabel.setForeground(new Color(-1));
        emailLabel.setText("Email");
        panel1.add(emailLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        repeatPwdLabel = new JLabel();
        repeatPwdLabel.setForeground(new Color(-1));
        repeatPwdLabel.setText("Repeat Password");
        panel1.add(repeatPwdLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        repeatPwdField = new JPasswordField();
        panel1.add(repeatPwdField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        registerButton = new JButton();
        registerButton.setBackground(new Color(-16498432));
        registerButton.setForeground(new Color(-1));
        registerButton.setText("Register");
        panel1.add(registerButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leaveButton = new JButton();
        leaveButton.setBackground(new Color(-2686936));
        leaveButton.setForeground(new Color(-1));
        leaveButton.setText("Leave");
        panel1.add(leaveButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userLabel.setLabelFor(userField);
        pwdLabel.setLabelFor(pwdField);
        emailLabel.setLabelFor(emailField);
        repeatPwdLabel.setLabelFor(repeatPwdField);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
