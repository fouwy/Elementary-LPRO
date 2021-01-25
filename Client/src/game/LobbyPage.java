package game;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Provides the UI for the lobby page.
 */
public class LobbyPage {
    private JButton character1Button;
    private JButton character3Button;
    private JButton character2Button;
    private JButton character4Button;
    private JButton character5Button;
    private JButton character6Button;
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
    private JLabel titleLabel;
    private JPanel titlePanel;
    private JPanel bodyPanel;
    private JPanel FriendsPanel;
    private JPanel PartyPanel;
    private JPanel characterPanel;
    private JPanel sectionPanel;
    private JPanel panel3;
    private JPanel menuPanel;
    private JTextArea infoWindow;
    private JLabel lobbyCode;

    public LobbyPage(Scanner in, PrintWriter out) {
        $$$setupUI$$$();
        lobbyCode.setText("Lobby Code: " + Account.getCurrentLobbyCode());
        ActionListener handler = new LobbyLogic(this, in, out);
        character1Button.addActionListener(handler);
        character2Button.addActionListener(handler);
        character3Button.addActionListener(handler);
        character4Button.addActionListener(handler);
        character5Button.addActionListener(handler);
        character6Button.addActionListener(handler);
        STARTGAMEButton.addActionListener(handler);
        LEAVEGAMEButton.addActionListener(handler);
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

    /**
     * Sets the specified character as picked or available depending on
     * the condition.
     *
     * @param characterNumber the number representing the character.
     * @param condition       true if picked, false if available.
     */
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

    /**
     * Sets the Label of a character to show the player
     * who picked it.
     *
     * @param characterNumber the number representing the character picked.
     * @param playerName      the username of the player that pick said character.
     */
    public void setCharacterName(int characterNumber, String playerName) {
        switch (characterNumber) {
            case 1:
                char1Label.setText(playerName);
                if (playerName.equals("Available"))
                    char1Label.setForeground(Color.WHITE);
                else
                    char1Label.setForeground(new Color(52, 144, 222));
                break;
            case 2:
                char2Label.setText(playerName);
                if (playerName.equals("Available"))
                    char2Label.setForeground(Color.WHITE);
                else
                    char2Label.setForeground(new Color(52, 144, 222));
                break;
            case 3:
                char3Label.setText(playerName);
                if (playerName.equals("Available"))
                    char3Label.setForeground(Color.WHITE);
                else
                    char3Label.setForeground(new Color(52, 144, 222));
                break;
            case 4:
                char4Label.setText(playerName);
                if (playerName.equals("Available"))
                    char4Label.setForeground(Color.WHITE);
                else
                    char4Label.setForeground(new Color(52, 144, 222));
                break;
            case 5:
                char5Label.setText(playerName);
                if (playerName.equals("Available"))
                    char5Label.setForeground(Color.WHITE);
                else
                    char5Label.setForeground(new Color(52, 144, 222));
                break;
            case 6:
                char6Label.setText(playerName);
                if (playerName.equals("Available"))
                    char6Label.setForeground(Color.WHITE);
                else
                    char6Label.setForeground(new Color(52, 144, 222));
                break;
        }
    }

    public JButton getStartButton() {
        return STARTGAMEButton;
    }

    public JButton getLeaveGameButton() {
        return LEAVEGAMEButton;
    }

    public JTextArea getInfoWindow() {
        return infoWindow;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
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
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 5, 5, 0), -1, -1));
        panel1.setBackground(new Color(-11398558));
        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        titlePanel.setBackground(new Color(-11398558));
        panel1.add(titlePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleLabel = new JLabel();
        titleLabel.setBackground(new Color(-12511684));
        Font titleLabelFont = this.$$$getFont$$$("Stencil", Font.BOLD, 36, titleLabel.getFont());
        if (titleLabelFont != null) titleLabel.setFont(titleLabelFont);
        titleLabel.setForeground(new Color(-1));
        titleLabel.setHorizontalTextPosition(11);
        titleLabel.setText("ELEMENTARY");
        titlePanel.add(titleLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        bodyPanel.setBackground(new Color(-11398558));
        panel1.add(bodyPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FriendsPanel = new JPanel();
        FriendsPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        FriendsPanel.setBackground(new Color(-11398558));
        bodyPanel.add(FriendsPanel, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FRIENDSButton = new JButton();
        FRIENDSButton.setBackground(new Color(-12511684));
        Font FRIENDSButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, FRIENDSButton.getFont());
        if (FRIENDSButtonFont != null) FRIENDSButton.setFont(FRIENDSButtonFont);
        FRIENDSButton.setForeground(new Color(-1));
        FRIENDSButton.setHorizontalAlignment(0);
        FRIENDSButton.setText("FRIENDS");
        FriendsPanel.add(FRIENDSButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PartyPanel = new JPanel();
        PartyPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        PartyPanel.setBackground(new Color(-11398558));
        FriendsPanel.add(PartyPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Eras Bold ITC", Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-13332258));
        label1.setText("PARTY");
        PartyPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        PartyPanel.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        characterPanel = new JPanel();
        characterPanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 5, 0, 5), -1, -1));
        characterPanel.setBackground(new Color(-11398558));
        characterPanel.setForeground(new Color(-13332258));
        bodyPanel.add(characterPanel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        character1Button = new JButton();
        character1Button.setForeground(new Color(-16777216));
        character1Button.setIcon(new ImageIcon(getClass().getResource("/img/char/sherlock.png")));
        character1Button.setText("Sherlock Holmes");
        characterPanel.add(character1Button, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character3Button = new JButton();
        character3Button.setForeground(new Color(-16777216));
        character3Button.setIcon(new ImageIcon(getClass().getResource("/img/char/mshudson.png")));
        character3Button.setText("Mrs Hudson");
        characterPanel.add(character3Button, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character2Button = new JButton();
        character2Button.setForeground(new Color(-16777216));
        character2Button.setIcon(new ImageIcon(getClass().getResource("/img/char/moriarty.png")));
        character2Button.setText("Moriarty");
        characterPanel.add(character2Button, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character4Button = new JButton();
        character4Button.setForeground(new Color(-16777216));
        character4Button.setIcon(new ImageIcon(getClass().getResource("/img/char/irene.png")));
        character4Button.setText("Irene Adler");
        characterPanel.add(character4Button, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character5Button = new JButton();
        character5Button.setForeground(new Color(-16777216));
        character5Button.setIcon(new ImageIcon(getClass().getResource("/img/char/enola.png")));
        character5Button.setText("Enola Holmes");
        characterPanel.add(character5Button, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        character6Button = new JButton();
        character6Button.setForeground(new Color(-16777216));
        character6Button.setIcon(new ImageIcon(getClass().getResource("/img/char/mycroft.png")));
        character6Button.setText("Mycroft Holmes");
        characterPanel.add(character6Button, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char1Label = new JLabel();
        char1Label.setBackground(new Color(-13332258));
        Font char1LabelFont = this.$$$getFont$$$("Eras Bold ITC", Font.PLAIN, 20, char1Label.getFont());
        if (char1LabelFont != null) char1Label.setFont(char1LabelFont);
        char1Label.setForeground(new Color(-1));
        char1Label.setHorizontalAlignment(0);
        char1Label.setHorizontalTextPosition(0);
        char1Label.setText("Available");
        characterPanel.add(char1Label, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char2Label = new JLabel();
        char2Label.setBackground(new Color(-13332258));
        Font char2LabelFont = this.$$$getFont$$$("Eras Bold ITC", Font.PLAIN, 20, char2Label.getFont());
        if (char2LabelFont != null) char2Label.setFont(char2LabelFont);
        char2Label.setForeground(new Color(-1));
        char2Label.setHorizontalAlignment(0);
        char2Label.setHorizontalTextPosition(0);
        char2Label.setText("Available");
        characterPanel.add(char2Label, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char3Label = new JLabel();
        Font char3LabelFont = this.$$$getFont$$$("Eras Bold ITC", Font.PLAIN, 20, char3Label.getFont());
        if (char3LabelFont != null) char3Label.setFont(char3LabelFont);
        char3Label.setForeground(new Color(-1));
        char3Label.setHorizontalAlignment(0);
        char3Label.setHorizontalTextPosition(0);
        char3Label.setText("Available");
        characterPanel.add(char3Label, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char4Label = new JLabel();
        Font char4LabelFont = this.$$$getFont$$$("Eras Bold ITC", Font.PLAIN, 20, char4Label.getFont());
        if (char4LabelFont != null) char4Label.setFont(char4LabelFont);
        char4Label.setForeground(new Color(-1));
        char4Label.setHorizontalAlignment(0);
        char4Label.setHorizontalTextPosition(0);
        char4Label.setText("Available");
        characterPanel.add(char4Label, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char5Label = new JLabel();
        Font char5LabelFont = this.$$$getFont$$$("Eras Bold ITC", Font.PLAIN, 20, char5Label.getFont());
        if (char5LabelFont != null) char5Label.setFont(char5LabelFont);
        char5Label.setForeground(new Color(-1));
        char5Label.setHorizontalAlignment(0);
        char5Label.setHorizontalTextPosition(0);
        char5Label.setText("Available");
        characterPanel.add(char5Label, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        char6Label = new JLabel();
        Font char6LabelFont = this.$$$getFont$$$("Eras Bold ITC", Font.PLAIN, 20, char6Label.getFont());
        if (char6LabelFont != null) char6Label.setFont(char6LabelFont);
        char6Label.setForeground(new Color(-1));
        char6Label.setHorizontalAlignment(0);
        char6Label.setHorizontalTextPosition(0);
        char6Label.setText("Available");
        characterPanel.add(char6Label, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sectionPanel = new JPanel();
        sectionPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        sectionPanel.setBackground(new Color(-11398558));
        bodyPanel.add(sectionPanel, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(10, 5, 10, 5), -1, -1));
        panel3.setBackground(new Color(-11398558));
        sectionPanel.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(50, 180), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(30, 150), null, 0, false));
        infoWindow = new JTextArea();
        scrollPane1.setViewportView(infoWindow);
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        menuPanel.setBackground(new Color(-11398558));
        sectionPanel.add(menuPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        STARTGAMEButton = new JButton();
        STARTGAMEButton.setBackground(new Color(-12511684));
        Font STARTGAMEButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, STARTGAMEButton.getFont());
        if (STARTGAMEButtonFont != null) STARTGAMEButton.setFont(STARTGAMEButtonFont);
        STARTGAMEButton.setForeground(new Color(-1));
        STARTGAMEButton.setText("START GAME");
        menuPanel.add(STARTGAMEButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LEAVEGAMEButton = new JButton();
        LEAVEGAMEButton.setBackground(new Color(-12511684));
        Font LEAVEGAMEButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, LEAVEGAMEButton.getFont());
        if (LEAVEGAMEButtonFont != null) LEAVEGAMEButton.setFont(LEAVEGAMEButtonFont);
        LEAVEGAMEButton.setForeground(new Color(-1));
        LEAVEGAMEButton.setText("LEAVE GAME");
        menuPanel.add(LEAVEGAMEButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        menuPanel.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lobbyCode = new JLabel();
        Font lobbyCodeFont = this.$$$getFont$$$("Eras Bold ITC", Font.BOLD, 16, lobbyCode.getFont());
        if (lobbyCodeFont != null) lobbyCode.setFont(lobbyCodeFont);
        lobbyCode.setForeground(new Color(-13332258));
        lobbyCode.setText("Lobby Code");
        menuPanel.add(lobbyCode, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        bodyPanel.add(spacer3, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        bodyPanel.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        bodyPanel.add(spacer5, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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
    }
}
