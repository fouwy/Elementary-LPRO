package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogic implements ActionListener {

    private Panel board;
    private GamePage gamePage;
    private Popup suggest, accuse;
    private JButton suggestB, accuseB;

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

            Suggestion suggestionPanel = new Suggestion();
            suggestionPanel.add(suggestB);
            PopupFactory pf = new PopupFactory();
            suggest = pf.getPopup(gamePage.$$$getRootComponent$$$(), suggestionPanel, 300, 500);
            suggest.show();
        }
        if (e.getSource()==gamePage.getAccuButton()) {
            Suggestion accusationPanel = new Suggestion();
            accusationPanel.add(accuseB);
            PopupFactory pf = new PopupFactory();
            accuse = pf.getPopup(gamePage.$$$getRootComponent$$$(), accusationPanel, 300, 500);
            accuse.show();
        }
        if (e.getSource()==suggestB) {
            suggest.hide();
            gamePage.getBoard().setFocusable(true);
        }
        if (e.getSource()==accuseB) {
            accuse.hide();
            gamePage.getBoard().setFocusable(true);
        }
        if (e.getSource()==gamePage.getEndTurnButton()) {

        }
    }
}
