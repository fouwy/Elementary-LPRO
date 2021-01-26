package game;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

/**
 * Provides the UI for the game page.
 * Also controls
 */
public class GamePage {
    private Panel board;
    private MyTable notepad;
    private JPanel panel1;
    private JPanel gamePanel;
    private JPanel topPanel;
    private JPanel botPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JButton endTurnButton;
    private JButton accuButton;
    private JButton suggButton;
    private JPanel optionPanel;
    private JButton rollButton;
    private JPanel playPanel;
    private JLabel playerLabel;
    private JPanel cardPanel;
    private JLabel card1;
    private JLabel card3;
    private JLabel card2;
    private JLabel card4;
    private JPanel dicePanel;

    private final Map<String, Integer> playerPicks;
    private final LobbyLogic lobbyLogic;
    private DicePanel dice;
    private DicePanel dice2;

    private JLabel player1;
    private JLabel player2;
    private JLabel player3;
    private JLabel player4;
    private JLabel player5;
    private JLabel player6;
    private JTextField userText;
    private JTextArea infoWindow;
    private JLabel charP1;
    private JLabel charP2;
    private JLabel charP3;
    private JLabel charP4;
    private JLabel charP5;
    private JLabel charP6;
    private JScrollPane scrollPane;
    private JLabel p1;
    private JLabel p2;
    private JLabel p3;
    private JLabel p4;
    private JLabel p5;
    private JLabel p6;

    private final ImageIcon leftArrow, redx;
    private final JLabel[] playerLabels;
    private final JLabel[] notepadIcons;
    private final ArrayList<String> playersInOrderList;
    private final int numberOfPlayers;
    private final ArrayList<Integer> losers;
    private String currentPlayer;
    private boolean lastAccusation;


    public GamePage(Map<String, Integer> playerPicks, LobbyLogic lobbyLogic) {
        this.playerPicks = playerPicks;
        this.lobbyLogic = lobbyLogic;
        leftArrow = new ImageIcon((getClass().getResource("/img/left-arrow-20px.gif")));
        redx = new ImageIcon(getClass().getResource("/img/redx.png"));

        losers = new ArrayList<>();
        playersInOrderList = new ArrayList<>();

        $$$setupUI$$$();

        JLabel[] cardLabels = {card1, card2, card3, card4};
        String[] cards = lobbyLogic.getCards();
        for (int i = 0; i < cards.length; i++) {
            cardLabels[i].setIcon(getCardIcon(cards[i]));
        }

        String[] playersInOrder = lobbyLogic.getPlayersInOrder();
        currentPlayer = playersInOrder[0];

        numberOfPlayers = playersInOrder.length;
        playerLabels = new JLabel[]{player1, player2, player3, player4, player5, player6};
        JLabel[] iconLabel = {charP1, charP2, charP3, charP4, charP5, charP6};
        //Notepad player icons
        notepadIcons = new JLabel[]{p1, p2, p3, p4, p5, p6};

        for (JLabel noteIcon : notepadIcons)
            noteIcon.setIcon(new ImageIcon(
                    new BufferedImage(35, 35, BufferedImage.TYPE_INT_ARGB)));

        createTransparentIcons();
        for (int i = 0; i < numberOfPlayers; i++) {
            playersInOrderList.add(playersInOrder[i]);
            playerLabels[i].setText(playersInOrder[i]);

            int characterNumber;
            if (playersInOrder[i].equals(Account.getUsername()))
                characterNumber = Account.getCharNumber();
            else
                characterNumber = playerPicks.get(playersInOrder[i]);

            String imagePath = board.getImagePath(characterNumber);
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            iconLabel[i].setIcon(icon);
            notepadIcons[i].setIcon(icon);
        }

        playerLabels[0].setIcon(leftArrow);


        GameLogic handler = new GameLogic(this, board);
        suggButton.addActionListener(handler);
        accuButton.addActionListener(handler);
        endTurnButton.addActionListener(handler);
        rollButton.addActionListener(handler);
        userText.addActionListener(handler);
    }

    private ImageIcon getCardIcon(String card) {
        return switch (card) {
            case "SherlockHolmes" -> new ImageIcon(getClass().getResource("/img/sherock_card.png"));
            case "Moriarty" -> new ImageIcon(getClass().getResource("/img/moriarty_card.png"));
            case "MrsHudson" -> new ImageIcon(getClass().getResource("/img/hudson_card.png"));
            case "IreneAdler" -> new ImageIcon(getClass().getResource("/img/irene_card.png"));
            case "EnolaHolmes" -> new ImageIcon(getClass().getResource("/img/enola_card.png"));
            case "MycroftHolmes" -> new ImageIcon(getClass().getResource("/img/mycroft_card.png"));
            case "Hospital" -> new ImageIcon(getClass().getResource("/img/hospital_card.png"));
            case "Morgue" -> new ImageIcon(getClass().getResource("/img/morgue_card.png"));
            case "Palace" -> new ImageIcon(getClass().getResource("/img/palace_card.png"));
            case "Pool" -> new ImageIcon(getClass().getResource("/img/pool_card.png"));
            case "H.O.U.N.D.Labs" -> new ImageIcon(getClass().getResource("/img/hound_card.png"));
            case "Prison" -> new ImageIcon(getClass().getResource("/img/prison_card.png"));
            case "Museum" -> new ImageIcon(getClass().getResource("/img/museum_card.png"));
            case "Magnussen" -> new ImageIcon(getClass().getResource("/img/magnussen_card.png"));
            case "Pills" -> new ImageIcon(getClass().getResource("/img/pills_card.png"));
            case "Vest" -> new ImageIcon(getClass().getResource("/img/bomb_card.png"));
            case "Pistol" -> new ImageIcon(getClass().getResource("/img/gun_card.png"));
            case "Glasses" -> new ImageIcon(getClass().getResource("/img/glasses_card.png"));
            case "Belt" -> new ImageIcon(getClass().getResource("/img/belt_card.png"));
            default -> null;
        };
    }

    private void createTransparentIcons() {
        for (JLabel player : playerLabels)
            //transparent image to line up menu
            player.setIcon(new ImageIcon(
                    new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB)));
    }

    private void createUIComponents() {
        board = new Panel(playerPicks, lobbyLogic);
        notepad = new MyTable();
        dice = new DicePanel();
        dice2 = new DicePanel();
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog($$$getRootComponent$$$(), msg);
    }

    /**
     * Adds a player to a List of players who have
     * lost this game.
     *
     * @param player the username of the player
     *               who lost.
     */
    public void addLoser(String player) {
        for (int turn = 0; turn < numberOfPlayers; turn++) {
            if (playersInOrderList.get(turn).equals(player))
                losers.add(turn);
        }
    }

    /**
     * Shows an icon on screen indicating whose
     * turn it is to play.
     * <br>
     * Shows a icon indicating all the players that
     * have lost the game.
     *
     * @param playerName the next player to play.
     */
    public void nextTurn(String playerName) {
        createTransparentIcons();
        for (Integer loser : losers)
            playerLabels[loser].setIcon(redx);
        int turn = playersInOrderList.indexOf(playerName);
        playerLabels[turn].setIcon(leftArrow);
        currentPlayer = playerName;


        if ((losers.size() == numberOfPlayers - 1) && numberOfPlayers > 1) {
            showMessage("As the last player left, " + playerName + " will get one final accusation!");
            showMessage(playerName + ", please make your final accusation.");
            lastAccusation = true;
        }
    }

    public boolean isLastAccusation() {
        return lastAccusation;
    }

    public boolean isMyTurn() {
        return currentPlayer.equals(Account.getUsername());
    }

    public Panel getBoard() {
        return board;
    }

    public JLabel getPlayerLabel() {
        return playerLabel;
    }

    public JButton getSuggButton() {
        return suggButton;
    }

    public JButton getAccuButton() {
        return accuButton;
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    public LobbyLogic getLobbyLogic() {
        return lobbyLogic;
    }

    public JButton getRollButton() {
        return rollButton;
    }

    public DicePanel getDice(int number) {
        if (number == 1)
            return dice;
        else
            return dice2;
    }

    public JTextArea getInfoWindow() {
        return infoWindow;
    }

    public JTextField getUserText() {
        return userText;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setForeground(new Color(-1));
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(topPanel, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        botPanel = new JPanel();
        botPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 5, 0, 5), -1, -1));
        panel1.add(botPanel, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 10, 0), -1, -1));
        botPanel.add(optionPanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        endTurnButton = new JButton();
        endTurnButton.setText("End Turn");
        optionPanel.add(endTurnButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        accuButton = new JButton();
        accuButton.setText("Make Accusation");
        optionPanel.add(accuButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        suggButton = new JButton();
        suggButton.setText("Make Suggestion");
        optionPanel.add(suggButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        playPanel = new JPanel();
        playPanel.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 0), -1, -1));
        botPanel.add(playPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rollButton = new JButton();
        rollButton.setText("Roll the Dice");
        playPanel.add(rollButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        playerLabel = new JLabel();
        Font playerLabelFont = this.$$$getFont$$$("Eras Bold ITC", -1, 16, playerLabel.getFont());
        if (playerLabelFont != null) playerLabel.setFont(playerLabelFont);
        playerLabel.setHorizontalAlignment(0);
        playerLabel.setHorizontalTextPosition(0);
        playerLabel.setText("playerName");
        playPanel.add(playerLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dicePanel = new JPanel();
        dicePanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        playPanel.add(dicePanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        dicePanel.add(dice, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        dicePanel.add(dice2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        botPanel.add(cardPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        card1 = new JLabel();
        card1.setIcon(new ImageIcon(getClass().getResource("/img/sherock_card.png")));
        card1.setText("");
        cardPanel.add(card1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        card3 = new JLabel();
        card3.setIcon(new ImageIcon(getClass().getResource("/img/sherock_card.png")));
        card3.setText("");
        cardPanel.add(card3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        card2 = new JLabel();
        card2.setIcon(new ImageIcon(getClass().getResource("/img/sherock_card.png")));
        card2.setText("");
        cardPanel.add(card2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        card4 = new JLabel();
        card4.setIcon(new ImageIcon(getClass().getResource("/img/sherock_card.png")));
        card4.setText("");
        cardPanel.add(card4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 5), -1, -1));
        panel1.add(rightPanel, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rightPanel.add(notepad, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 300), null, 0, false));
        userText = new JTextField();
        userText.setEditable(true);
        rightPanel.add(userText, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(31);
        rightPanel.add(scrollPane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(250, 200), null, 0, false));
        infoWindow = new JTextArea();
        infoWindow.setEditable(false);
        infoWindow.setLineWrap(true);
        infoWindow.setWrapStyleWord(true);
        scrollPane.setViewportView(infoWindow);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 7, new Insets(0, 10, 0, 10), -1, -1));
        rightPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, -1, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Players");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        p1 = new JLabel();
        p1.setHorizontalAlignment(0);
        p1.setHorizontalTextPosition(4);
        p1.setText("");
        panel2.add(p1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        p2 = new JLabel();
        p2.setText("");
        panel2.add(p2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        p3 = new JLabel();
        p3.setText("");
        panel2.add(p3, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        p4 = new JLabel();
        p4.setText("");
        panel2.add(p4, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        p5 = new JLabel();
        p5.setText("");
        panel2.add(p5, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        p6 = new JLabel();
        p6.setText("");
        panel2.add(p6, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayoutManager(8, 2, new Insets(0, 10, 0, 0), -1, -1));
        panel1.add(leftPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        player1 = new JLabel();
        player1.setHorizontalTextPosition(10);
        player1.setIcon(new ImageIcon(getClass().getResource("/img/left-arrow-20px.gif")));
        player1.setText("");
        leftPanel.add(player1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        player2 = new JLabel();
        player2.setHorizontalTextPosition(10);
        player2.setText("");
        leftPanel.add(player2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        player3 = new JLabel();
        player3.setHorizontalTextPosition(10);
        player3.setText("");
        leftPanel.add(player3, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        player4 = new JLabel();
        player4.setHorizontalTextPosition(10);
        player4.setText("");
        leftPanel.add(player4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        player5 = new JLabel();
        player5.setHorizontalTextPosition(10);
        player5.setText("");
        leftPanel.add(player5, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        player6 = new JLabel();
        player6.setHorizontalTextPosition(10);
        player6.setText("");
        leftPanel.add(player6, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        leftPanel.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        leftPanel.add(spacer1, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        charP1 = new JLabel();
        charP1.setText("");
        leftPanel.add(charP1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        charP2 = new JLabel();
        charP2.setText("");
        leftPanel.add(charP2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        charP3 = new JLabel();
        charP3.setText("");
        leftPanel.add(charP3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        charP4 = new JLabel();
        charP4.setText("");
        leftPanel.add(charP4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        charP5 = new JLabel();
        charP5.setText("");
        leftPanel.add(charP5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        charP6 = new JLabel();
        charP6.setText("");
        leftPanel.add(charP6, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(0, 0));
        panel1.add(gamePanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(900, 720), null, 0, false));
        gamePanel.add(board, BorderLayout.CENTER);
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
