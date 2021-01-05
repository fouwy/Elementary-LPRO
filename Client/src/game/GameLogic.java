package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogic implements ActionListener {

    private final Panel board;
    private final GamePage gamePage;
    private Suggestion accusationPanel, suggestionPanel;
    private Popup suggest, accuse, cluePopup;
    private JButton suggestB, accuseB;
    private String[] suggestionChosen, accusationChosen;
    private String currentRoom;

    public GameLogic(GamePage gamePage, Panel board) {
        this.board = board;
        this.gamePage = gamePage;
        gamePage.getPlayerLabel().setText(Account.getUsername());
        setup();
    }

    private void setup() {
        suggestB = new JButton("Suggest!");
        accuseB = new JButton("Accuse!");
        suggestB.addActionListener(this);
        accuseB.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==gamePage.getSuggButton()) {
            //TODO:Verificar se está dentro da sala onde faz a acusaçao
            //podia mandar como argumento do Suggestion a sala onde está

            hidePreviousPopup();
            if (checkIfPlayerInRoom()) {
                board.requestFocus();
                return;
            }
            currentRoom = board.getCurrentRoom();
            suggestionPanel = new Suggestion(currentRoom);
            suggestionPanel.add(suggestB);
            PopupFactory pf = new PopupFactory();
            suggest = pf.getPopup(gamePage.$$$getRootComponent$$$(), suggestionPanel, 300, 500);
            suggest.show();
        }
        if (e.getSource()==gamePage.getAccuButton()) {

            hidePreviousPopup();
            if (checkIfPlayerInRoom()) {
                board.requestFocus();
                return;
            }
            currentRoom = board.getCurrentRoom();
            accusationPanel = new Suggestion(currentRoom);
            accusationPanel.add(accuseB);
            PopupFactory pf = new PopupFactory();
            accuse = pf.getPopup(gamePage.$$$getRootComponent$$$(), accusationPanel, 300, 500);
            accuse.show();
        }
        if (e.getSource()==suggestB) {
            suggestionChosen = suggestionPanel.getSelectedSuggestion();
            suggest.hide();
            board.requestFocus();
            gamePage.getLobbyLogic().sendSuggestionToServer(
                    suggestionChosen[0]+","+suggestionChosen[1]+","+currentRoom);

        }
        if (e.getSource()==accuseB) {
            accusationChosen = accusationPanel.getSelectedSuggestion();
            accuse.hide();
            board.requestFocus();
            gamePage.getLobbyLogic().sendAccusationToServer(
                    accusationChosen[0]+","+accusationChosen[1]+","+currentRoom);
        }
        if (e.getSource()==gamePage.getEndTurnButton()) {
            gamePage.getLobbyLogic().tellServertoEndTurn();
            board.requestFocus();
        }
        if (e.getSource()==gamePage.getRollButton()) {
            int value1 = gamePage.getDice(1).rollDice();
            int value2 = gamePage.getDice(2).rollDice();
            if((value1==1)&&(value2==1)) {
                watsonClue();
            }
            board.requestFocus();
            board.setDiceValue(value1+value2);
        }
    }

    private boolean checkIfPlayerInRoom() {
        if (board.getCurrentRoom() == null) {
            gamePage.showMessage("You are not in a room");
            return true;
        }
        return false;
    }

    private void hidePreviousPopup() {
        if (accuse != null)
            accuse.hide();
        if (suggest != null)
            suggest.hide();
    }
    private void watsonClue() {
        WatsonClue cluePanel = new WatsonClue();
        PopupFactory cluePf = new PopupFactory();
        cluePopup = cluePf.getPopup(gamePage.$$$getRootComponent$$$(), cluePanel, 300, 500);
        cluePopup.show();
    }
}
