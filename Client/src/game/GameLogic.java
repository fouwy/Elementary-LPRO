package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the actions performed in game by
 * a client.
 * <br>
 * If the user clicks on the "Make Suggestion" or
 * "Make Accusation" button, a popup appears that
 * asks the user to input the suggestion or accusation.
 * After that, the suggestior or accusation is sent to
 * the server.
 * <br>
 * If the user clicks the "End Turn" button, sends a
 * message to the server to end their turn.
 * <br>
 * If the user clicks the "Roll the Dice" button,
 * two dice are rolled and the result is shown on screen.
 */
public class GameLogic implements ActionListener {

    private final Panel board;
    private final GamePage gamePage;
    private Suggestion accusationPanel, suggestionPanel;
    private Popup suggest, accuse, cluePopup;
    private JButton suggestB, accuseB, clueB;
    private String[] suggestionChosen, accusationChosen;
    private String currentRoom;
    private boolean alreadyMadeASuggestion;

    public GameLogic(GamePage gamePage, Panel board) {
        this.board = board;
        this.gamePage = gamePage;
        gamePage.getPlayerLabel().setText(Account.getUsername());
        alreadyMadeASuggestion = false;
        setup();
    }

    private void setup() {
        suggestB = new JButton("Suggest!");
        accuseB = new JButton("Accuse!");
        clueB = new JButton("Thanks!");
        suggestB.addActionListener(this);
        accuseB.addActionListener(this);
        clueB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gamePage.isMyTurn()) {
            if (e.getSource() == gamePage.getSuggButton()) {
                hidePreviousPopup();
                currentRoom = board.getCurrentRoom();

                if (alreadyMadeASuggestion)
                    gamePage.showMessage("You can only make one suggestion per turn");
                else if (currentRoom.isBlank())
                    gamePage.showMessage("You need to be in a room to make a suggestion");
                else {
                    alreadyMadeASuggestion = true;
                    suggestionPanel = new Suggestion(currentRoom);
                    suggestionPanel.add(suggestB);
                    PopupFactory pf = new PopupFactory();
                    suggest = pf.getPopup(gamePage.$$$getRootComponent$$$(), suggestionPanel, 300, 500);
                    suggest.show();
                }
            }
            if (e.getSource() == gamePage.getAccuButton()) {
                hidePreviousPopup();
                if (gamePage.isLastAccusation()) {
                    accusationPanel = new Suggestion();
                    accusationPanel.add(accuseB);
                    PopupFactory pf = new PopupFactory();
                    accuse = pf.getPopup(gamePage.$$$getRootComponent$$$(), accusationPanel, 300, 500);
                    accuse.show();
                } else {
                    currentRoom = board.getCurrentRoom();
                    if (currentRoom.isBlank())
                        gamePage.showMessage("You need to be in a room to make a accusation");
                    else {
                        accusationPanel = new Suggestion(currentRoom);
                        accusationPanel.add(accuseB);
                        PopupFactory pf = new PopupFactory();
                        accuse = pf.getPopup(gamePage.$$$getRootComponent$$$(), accusationPanel, 300, 500);
                        accuse.show();
                    }
                }
            }
            if (e.getSource() == suggestB) {
                suggestionChosen = suggestionPanel.getSelectedSuggestion();
                suggest.hide();
                gamePage.getLobbyLogic().sendSuggestionToServer(
                        suggestionChosen[0] + "," + suggestionChosen[1] + "," + currentRoom);

            }
            if (e.getSource() == accuseB) {
                if (gamePage.isLastAccusation()) {
                    accusationChosen = accusationPanel.getLastAccusation();
                    gamePage.getLobbyLogic().sendAccusationToServer(
                            accusationChosen[0] + "," + accusationChosen[1] + "," + accusationChosen[2]);
                } else {
                    accusationChosen = accusationPanel.getSelectedSuggestion();
                    gamePage.getLobbyLogic().sendAccusationToServer(
                            accusationChosen[0] + "," + accusationChosen[1] + "," + currentRoom);
                }
                accuse.hide();
            }
            if (e.getSource() == gamePage.getEndTurnButton()) {
                alreadyMadeASuggestion = false;
                board.resetAttributes();
                gamePage.getLobbyLogic().tellServertoEndTurn();
                board.requestFocus();
            }
            if (e.getSource() == gamePage.getRollButton()) {
                if (board.IAlreadyRolledTheDice())
                    gamePage.showMessage("You already rolled the dice!\nMove or End Turn.");
                else {
                    int value1 = gamePage.getDice(1).rollDice();
                    int value2 = gamePage.getDice(2).rollDice();
                    gamePage.getLobbyLogic().sendMessageToParty("Rolled a " + (value1 + value2) + ".");
                    if ((value1 == 1) && (value2 == 1)) {
                        watsonClue();
                    }
                    board.requestFocus();
                    board.setDiceValue(value1 + value2);
                }
            }
            if (e.getSource() == clueB) {
                cluePopup.hide();
                board.requestFocus();
            }
        }

        if (e.getSource() == gamePage.getUserText()) {
            gamePage.getLobbyLogic().sendMessageToParty(e.getActionCommand());
            gamePage.getUserText().setText("");
        }
        board.requestFocus();

    }

    private void hidePreviousPopup() {
        if (accuse != null)
            accuse.hide();
        if (suggest != null)
            suggest.hide();
        if (cluePopup != null)
            cluePopup.hide();
    }

    private void watsonClue() {
        hidePreviousPopup();
        WatsonClue cluePanel = new WatsonClue();
        cluePanel.add(clueB);
        PopupFactory cluePf = new PopupFactory();
        cluePopup = cluePf.getPopup(gamePage.$$$getRootComponent$$$(), cluePanel, 300, 500);
        cluePopup.show();
    }
}
