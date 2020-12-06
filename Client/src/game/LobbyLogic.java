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
            chooseCharacter(1);
        }else if(e.getSource().equals(lobby_page.getCharacter2Button())){
            chooseCharacter(2);
        }else if(e.getSource().equals(lobby_page.getCharacter3Button())){
            chooseCharacter(3);
        }else if(e.getSource().equals(lobby_page.getCharacter4Button())){
            chooseCharacter(4);
        }else if(e.getSource().equals(lobby_page.getCharacter5Button())){
            chooseCharacter(5);
        }else if(e.getSource().equals(lobby_page.getCharacter6Button())){
            chooseCharacter(6);
        }
        //LEAVE GAME
        else if(e.getSource().equals(lobby_page.getLeaveGameButton())){
            leaveGame();
        }

        //TODO: OPTIONS, START GAME, LEAVE GAME
    }

    private void leaveGame() {
        lobby_page.disposeLobby();
        new MainPage();
        FLAG = true;
    }

    private void chooseCharacter(int number_character){
        //int selected = 0;

        if(number_character == 1) {
            lobby_page.setCharacter1ButtonEnabled(false);
            lobby_page.setCharacter2ButtonEnabled(true);
            lobby_page.setCharacter3ButtonEnabled(true);
            lobby_page.setCharacter4ButtonEnabled(true);
            lobby_page.setCharacter5ButtonEnabled(true);
            lobby_page.setCharacter6ButtonEnabled(true);
        }
        else if(number_character == 2) {
            lobby_page.setCharacter1ButtonEnabled(true);
            lobby_page.setCharacter2ButtonEnabled(false);
            lobby_page.setCharacter3ButtonEnabled(true);
            lobby_page.setCharacter4ButtonEnabled(true);
            lobby_page.setCharacter5ButtonEnabled(true);
            lobby_page.setCharacter6ButtonEnabled(true);
        }
        else if(number_character == 3) {
            lobby_page.setCharacter1ButtonEnabled(true);
            lobby_page.setCharacter2ButtonEnabled(true);
            lobby_page.setCharacter3ButtonEnabled(false);
            lobby_page.setCharacter4ButtonEnabled(true);
            lobby_page.setCharacter5ButtonEnabled(true);
            lobby_page.setCharacter6ButtonEnabled(true);
        }
        else if(number_character == 4) {
            lobby_page.setCharacter1ButtonEnabled(true);
            lobby_page.setCharacter2ButtonEnabled(true);
            lobby_page.setCharacter3ButtonEnabled(true);
            lobby_page.setCharacter4ButtonEnabled(false);
            lobby_page.setCharacter5ButtonEnabled(true);
            lobby_page.setCharacter6ButtonEnabled(true);
        }
        else if(number_character == 5) {
            lobby_page.setCharacter1ButtonEnabled(true);
            lobby_page.setCharacter2ButtonEnabled(true);
            lobby_page.setCharacter3ButtonEnabled(true);
            lobby_page.setCharacter4ButtonEnabled(true);
            lobby_page.setCharacter5ButtonEnabled(false);
            lobby_page.setCharacter6ButtonEnabled(true);
        }
        else if(number_character == 6) {
            lobby_page.setCharacter1ButtonEnabled(true);
            lobby_page.setCharacter2ButtonEnabled(true);
            lobby_page.setCharacter3ButtonEnabled(true);
            lobby_page.setCharacter4ButtonEnabled(true);
            lobby_page.setCharacter5ButtonEnabled(true);
            lobby_page.setCharacter6ButtonEnabled(false);
        }

    }
}