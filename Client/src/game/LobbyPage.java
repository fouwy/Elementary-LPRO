package game;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class LobbyPage {
    private JButton character1Button;
    private JButton character3Button;
    private JButton character2Button;
    private JButton character4Button;
    private JButton character5Button;
    private JButton character6Button;
    private JButton OPTIONSButton;
    private JButton LEAVEGAMEButton;
    private JButton STARTGAMEButton;
    private JButton FRIENDSButton;
    private JPanel panel1;
    private JLabel char1Label;
    private JLabel char2Label;
    private JLabel char3Label;
    private JLabel char4Label;
    private JLabel char5Label;
    private JLabel char6Label;

    public LobbyPage(int port_number) {
        ActionListener handler = new LobbyLogic(this, port_number);
        character1Button.addActionListener(handler);
        character2Button.addActionListener(handler);
        character3Button.addActionListener(handler);
        character4Button.addActionListener(handler);
        character5Button.addActionListener(handler);
        character6Button.addActionListener(handler);
    }

    public JButton getCharacterButton(int charNumber) {
        switch (charNumber) {
            case 1:
                return character1Button;
            case 2:
                return character2Button;
            case 3:
                return character3Button;
            case 4:
                return character4Button;
            case 5:
                return character5Button;
            case 6:
                return character6Button;
            default:
                return null;
        }
    }


    public void setCharacterButtonEnabled(int characterNumber, boolean condition) {
        switch (characterNumber) {
            case 1:
                character1Button.setEnabled(condition);
                break;
            case 2:
                character2Button.setEnabled(condition);
                break;
            case 3:
                character3Button.setEnabled(condition);
                break;
            case 4:
                character4Button.setEnabled(condition);
                break;
            case 5:
                character5Button.setEnabled(condition);
                break;
            case 6:
                character6Button.setEnabled(condition);
                break;
        }
    }

    public void setCharacterName(int characterNumber, String playerName) {
        switch (characterNumber) {
            case 1:
                char1Label.setText(playerName);
                break;
            case 2:
                char2Label.setText(playerName);
                break;
            case 3:
                char3Label.setText(playerName);
                break;
            case 4:
                char4Label.setText(playerName);
                break;
            case 5:
                char5Label.setText(playerName);
                break;
            case 6:
                char6Label.setText(playerName);
                break;
        }
    }

    public JButton getLeaveGameButton() {
        return LEAVEGAMEButton;
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
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-11398558));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-11398558));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-12511684));
        Font label1Font = this.$$$getFont$$$("Stencil", Font.BOLD, 36, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setHorizontalTextPosition(11);
        label1.setText("ELEMENTARY");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-11398558));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-11398558));
        panel3.add(panel4, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FRIENDSButton = new JButton();
        FRIENDSButton.setBackground(new Color(-12511684));
        Font FRIENDSButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, FRIENDSButton.getFont());
        if (FRIENDSButtonFont != null) FRIENDSButton.setFont(FRIENDSButtonFont);
        FRIENDSButton.setForeground(new Color(-1));
        FRIENDSButton.setHorizontalAlignment(2);
        FRIENDSButton.setText("FRIENDS");
        panel4.add(FRIENDSButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-11398558));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, Font.BOLD, -1, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-13332258));
        label2.setText("PARTY");
        panel5.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel5.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-11398558));
        panel6.setForeground(new Color(-13332258));
        panel3.add(panel6, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        character1Button = new JButton();
        character1Button.setForeground(new Color(-16777216));
        character1Button.setText("Character 1");
        panel6.add(character1Button, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character3Button = new JButton();
        character3Button.setForeground(new Color(-16777216));
        character3Button.setText("Character 3");
        panel6.add(character3Button, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character2Button = new JButton();
        character2Button.setForeground(new Color(-16777216));
        character2Button.setText("Character 2");
        panel6.add(character2Button, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character4Button = new JButton();
        character4Button.setForeground(new Color(-16777216));
        character4Button.setText("Character 4");
        panel6.add(character4Button, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character5Button = new JButton();
        character5Button.setForeground(new Color(-16777216));
        character5Button.setText("Character 5");
        panel6.add(character5Button, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character6Button = new JButton();
        character6Button.setForeground(new Color(-16777216));
        character6Button.setText("Character 6");
        panel6.add(character6Button, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char1Label = new JLabel();
        char1Label.setBackground(new Color(-13332258));
        Font char1LabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, char1Label.getFont());
        if (char1LabelFont != null) char1Label.setFont(char1LabelFont);
        char1Label.setForeground(new Color(-13332258));
        char1Label.setHorizontalAlignment(0);
        char1Label.setHorizontalTextPosition(0);
        char1Label.setText("Available");
        panel6.add(char1Label, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char2Label = new JLabel();
        char2Label.setBackground(new Color(-13332258));
        Font char2LabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, char2Label.getFont());
        if (char2LabelFont != null) char2Label.setFont(char2LabelFont);
        char2Label.setForeground(new Color(-13332258));
        char2Label.setHorizontalAlignment(0);
        char2Label.setHorizontalTextPosition(0);
        char2Label.setText("Available");
        panel6.add(char2Label, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char3Label = new JLabel();
        Font char3LabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, char3Label.getFont());
        if (char3LabelFont != null) char3Label.setFont(char3LabelFont);
        char3Label.setForeground(new Color(-13332258));
        char3Label.setHorizontalAlignment(0);
        char3Label.setHorizontalTextPosition(0);
        char3Label.setText("Available");
        panel6.add(char3Label, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char4Label = new JLabel();
        Font char4LabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, char4Label.getFont());
        if (char4LabelFont != null) char4Label.setFont(char4LabelFont);
        char4Label.setForeground(new Color(-13332258));
        char4Label.setHorizontalAlignment(0);
        char4Label.setHorizontalTextPosition(0);
        char4Label.setText("Available");
        panel6.add(char4Label, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char5Label = new JLabel();
        Font char5LabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, char5Label.getFont());
        if (char5LabelFont != null) char5Label.setFont(char5LabelFont);
        char5Label.setForeground(new Color(-13332258));
        char5Label.setHorizontalAlignment(0);
        char5Label.setHorizontalTextPosition(0);
        char5Label.setText("Available");
        panel6.add(char5Label, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char6Label = new JLabel();
        Font char6LabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, char6Label.getFont());
        if (char6LabelFont != null) char6Label.setFont(char6LabelFont);
        char6Label.setForeground(new Color(-13332258));
        char6Label.setHorizontalAlignment(0);
        char6Label.setHorizontalTextPosition(0);
        char6Label.setText("Available");
        panel6.add(char6Label, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-11398558));
        panel3.add(panel7, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-11398558));
        panel7.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setBackground(new Color(-11398558));
        panel7.add(panel9, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel10.setBackground(new Color(-11398558));
        panel7.add(panel10, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        OPTIONSButton = new JButton();
        OPTIONSButton.setBackground(new Color(-12511684));
        Font OPTIONSButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, OPTIONSButton.getFont());
        if (OPTIONSButtonFont != null) OPTIONSButton.setFont(OPTIONSButtonFont);
        OPTIONSButton.setForeground(new Color(-1));
        OPTIONSButton.setText("OPTIONS");
        panel10.add(OPTIONSButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        STARTGAMEButton = new JButton();
        STARTGAMEButton.setBackground(new Color(-12511684));
        Font STARTGAMEButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, STARTGAMEButton.getFont());
        if (STARTGAMEButtonFont != null) STARTGAMEButton.setFont(STARTGAMEButtonFont);
        STARTGAMEButton.setForeground(new Color(-1));
        STARTGAMEButton.setText("START GAME");
        panel10.add(STARTGAMEButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LEAVEGAMEButton = new JButton();
        LEAVEGAMEButton.setBackground(new Color(-12511684));
        Font LEAVEGAMEButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, LEAVEGAMEButton.getFont());
        if (LEAVEGAMEButtonFont != null) LEAVEGAMEButton.setFont(LEAVEGAMEButtonFont);
        LEAVEGAMEButton.setForeground(new Color(-1));
        LEAVEGAMEButton.setText("LEAVE GAME");
        panel10.add(LEAVEGAMEButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel10.add(spacer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel10.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel3.add(spacer4, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel3.add(spacer5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel3.add(spacer6, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
