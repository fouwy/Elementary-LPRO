package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogic implements ActionListener {

    GamePage gamePage;

    public GameLogic(GamePage gamePage) {
        this.gamePage = gamePage;
        gamePage.getPlayerLabel().setText(Account.getUsername());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
