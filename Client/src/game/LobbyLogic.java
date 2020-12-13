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
            String charsTaken = in.nextLine();
            disableAllTakenCharacters(convertToStringArray(charsTaken));

            String response = in.nextLine();
            showMessage(response);

            while(in.hasNextLine()) {
                response = in.nextLine();
                System.out.println(response);

                switch (response.substring(0, 4)) {
                    case "CHAR":
                        handleCharacterPick(response);
                        break;
                    default:
                        showMessage(response);
                }
            }
            System.out.println("no nextLine");
        }

        private void showMessage(String message) {
            SwingUtilities.invokeLater(
                    () -> {
                        lobby_page.getInfoWindow().append(message + "\n");
                    }
            );
        }

        private void handleCharacterPick(String response) throws Exception {
            int characterNumber = Integer.parseInt(String.valueOf(response.charAt(4)));
            String playerName = response.substring(5);

            if(playerName.equals(Account.getUsername())) {
                Account.setMyCharacter(characterNumber);
                chooseCharacter(characterNumber, playerName);
            } else {
                setOtherPlayerChar(playerName, characterNumber);
            }
        }

        private void setOtherPlayerChar(String playerName, int characterNumber) throws Exception {
            chooseCharacter(characterNumber, playerName);
            showMessage(playerName+ " picked char " +characterNumber);
        }

        private String[] convertToStringArray(String charsTaken) {
            return charsTaken.replaceAll("\\[", "")
                             .replaceAll("\\]", "")
                             .replaceAll("\\s", "")
                             .split(",");
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

    private void chooseCharacter(int characterNumber, String playerName) throws Exception {
        SwingUtilities.invokeAndWait(
                () -> {
                    lobby_page.setCharacterButtonEnabled(characterNumber, false);
                    lobby_page.setCharacterName(characterNumber, playerName);
                }
        );
    }

    private void disableAllTakenCharacters(String[] characters) throws Exception {
        for (int i = 0; i<characters.length; i++) {
            if(!characters[i].isBlank()) {
                chooseCharacter(i, characters[i]);
            }
        }
    }
}