package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LobbyLogic implements ActionListener {

    private final LobbyPage lobby_page;
    int choose = 0;

    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public LobbyLogic(LobbyPage lobby_page, int port_number) {
        this.lobby_page = lobby_page;

        try {
            socket = new Socket("localhost", port_number);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startCommunication();
    }

    private void startCommunication() {
        String response = in.nextLine();
        System.out.println("SERVER - "+ response);
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

    private void chooseCharacter1(){
        lobby_page.setCharacter1ButtonEnabled(false);
        choose = 1;
    }

    private void chooseCharacter2(){
        lobby_page.setCharacter2ButtonEnabled(false);
        choose = 1;
    }

    private void chooseCharacter3(){
        lobby_page.setCharacter3ButtonEnabled(false);
        choose = 1;
    }

    private void chooseCharacter4(){
        lobby_page.setCharacter4ButtonEnabled(false);
        choose = 1;
    }

    private void chooseCharacter5(){
        lobby_page.setCharacter5ButtonEnabled(false);
        choose = 1;
    }

    private void chooseCharacter6(){
        lobby_page.setCharacter6ButtonEnabled(false);
        choose = 1;
    }



}
