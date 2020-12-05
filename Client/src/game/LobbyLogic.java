package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LobbyLogic implements ActionListener {

    private final LobbyPage lobby_page;
    int choose = 0;

    public LobbyLogic(LobbyPage lobby_page) {
        this.lobby_page = lobby_page;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(lobby_page.getCharacter1Button())){
            chooseCharacter1();
        }else if(e.getSource().equals(lobby_page.getCharacter2Button())){
            chooseCharacter2();
        }else if(e.getSource().equals(lobby_page.getCharacter3Button())){
            chooseCharacter3();
        }else if(e.getSource().equals(lobby_page.getCharacter4Button())){
            chooseCharacter4();
        }else if(e.getSource().equals(lobby_page.getCharacter5Button())){
            chooseCharacter5();
        }else if(e.getSource().equals(lobby_page.getCharacter6Button())){
            chooseCharacter6();
        }

        /*if (e.getSource().equals(lobby_page.getCharacter1Button())
                || e.getSource().equals(lobby_page.getCharacter2Button())
                || e.getSource().equals(lobby_page.getCharacter3Button())
                || e.getSource().equals(lobby_page.getCharacter4Button())
                || e.getSource().equals(lobby_page.getCharacter5Button())
                || e.getSource().equals(lobby_page.getCharacter6Button())
        ){
            chooseCharacter();
        }*/

        //TODO: OPTIONS, START GAME, LEAVE GAME
    }

    /*private void chooseCharacter(){

    }*/

    private void manageChooseCharacter(){
    }

    private boolean chooseCharacter1(){
        lobby_page.setCharacter1ButtonEnabled(false);
        choose = 1;
        return true;
    }

    private boolean chooseCharacter2(){
        lobby_page.setCharacter2ButtonEnabled(false);
        choose = 1;
        return true;
    }

    private boolean chooseCharacter3(){
        lobby_page.setCharacter3ButtonEnabled(false);
        choose = 1;
        return true;
    }

    private boolean chooseCharacter4(){
        lobby_page.setCharacter4ButtonEnabled(false);
        choose = 1;
        return true;
    }

    private boolean chooseCharacter5(){
        lobby_page.setCharacter5ButtonEnabled(false);
        choose = 1;
        return true;
    }

    private boolean chooseCharacter6(){
        lobby_page.setCharacter6ButtonEnabled(false);
        choose = 1;
        return true;
    }



}
