package game;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class LobbyLogic implements ActionListener {

    private final LobbyPage lobby_page;
    Scanner in;
    PrintWriter out;

    public LobbyLogic(LobbyPage lobby_page, int port_number) {
        this.lobby_page = lobby_page;

        try {
            Socket socket = new Socket("localhost", port_number);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Handler handler = new Handler();
        Thread thread = new Thread(handler);
        thread.start();
    }

    private class Handler implements Runnable {

        @Override
        public void run() {
            try {
                startCommunication();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void startCommunication() throws Exception {
            out.println(Account.getUsername());
            String response = in.nextLine();
            System.out.println("SERVER - "+ response);

            while(in.hasNextLine()) {
                response = in.nextLine();
                System.out.println(response);
                if (response.startsWith("CHAR")) {
                    handleCharacterPick(response);
                }
            }
            System.out.println("no nextLine");
        }

        private void handleCharacterPick(String response) throws Exception {
            int characterNumber = Integer.parseInt(String.valueOf(response.charAt(4)));
            String playerName = response.substring(5);

            if(playerName.equals(Account.getUsername())) {
                Account.setMyCharacter(characterNumber);
                chooseCharacter(characterNumber);
            } else {
                setOtherPlayerChar(playerName, characterNumber);
            }
        }

        private void setOtherPlayerChar(String playerName, int characterNumber) throws Exception {
            chooseCharacter(characterNumber);
            System.out.println("Player " +playerName+ " chose character " +characterNumber);
            //TODO: Set some text saying which player chose the character
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(lobby_page.getCharacterButton(1))){
            askServerForChar(1);
        }else if(e.getSource().equals(lobby_page.getCharacterButton(2))){
            askServerForChar(2);
        }else if(e.getSource().equals(lobby_page.getCharacterButton(3))){
            askServerForChar(3);
        }else if(e.getSource().equals(lobby_page.getCharacterButton(4))){
            askServerForChar(4);
        }else if(e.getSource().equals(lobby_page.getCharacterButton(5))){
            askServerForChar(5);
        }else if(e.getSource().equals(lobby_page.getCharacterButton(6))){
            askServerForChar(6);
        }
        //LEAVE GAME
        else if(e.getSource().equals(lobby_page.getLeaveGameButton())){
            leaveGame();
        }

        //TODO: OPTIONS, START GAME, LEAVE GAME
    }

    private void askServerForChar(int charNumber) {
        out.println("CHAR"+charNumber);
    }

    private void leaveGame() {
        out.println("QUIT");
        //Leaves Lobby
    }

    private void chooseCharacter(int number_character) throws Exception {
        //enableAllButtons();
        SwingUtilities.invokeAndWait(
                () -> {
                    if(number_character == 1) {
                        lobby_page.setCharacter1ButtonEnabled(false);
                    }
                    else if(number_character == 2) {
                        lobby_page.setCharacter2ButtonEnabled(false);
                    }
                    else if(number_character == 3) {
                        lobby_page.setCharacter3ButtonEnabled(false);
                    }
                    else if(number_character == 4) {
                        lobby_page.setCharacter4ButtonEnabled(false);
                    }
                    else if(number_character == 5) {
                        lobby_page.setCharacter5ButtonEnabled(false);
                    }
                    else if(number_character == 6) {
                        lobby_page.setCharacter6ButtonEnabled(false);
                    }
                }
        );


    }

    private void enableAllButtons() {
        lobby_page.setCharacter1ButtonEnabled(true);
        lobby_page.setCharacter2ButtonEnabled(true);
        lobby_page.setCharacter3ButtonEnabled(true);
        lobby_page.setCharacter4ButtonEnabled(true);
        lobby_page.setCharacter5ButtonEnabled(true);
        lobby_page.setCharacter6ButtonEnabled(true);
    }
}