package game;

import common.ClientStart;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LobbyLogic implements ActionListener {
    private final LobbyPage lobby_page;
    Scanner in;
    PrintWriter out;

    private String[] playersInOrder;
    private String[] cardArray;
    private final Map<String, Integer> playerPicks;
    private Game game;

    public LobbyLogic(LobbyPage lobby_page, Scanner in, PrintWriter out) {
        this.lobby_page = lobby_page;
        this.in = in;
        this.out = out;

        playerPicks = new HashMap<>();
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

                switch (response.substring(0, 4)) {
                    case "CHAR":
                        handleCharacterPick(response);
                        break;
                    case "GAME":
                        //startGame();
                        //GAME WILL START IN CASE "CARD" after getting the player names in order and the cards for this client
                        System.out.println("Game Starting...");
                        break;
                    case "MOVE":
                        //handle the movement of this char or other
                        handleMovement(response);
                        break;
                    case "MESG":
                        //show popup with the message
                        showMessage(response);
                        System.out.println("response = " + response);
                        break;
                    case "ENDT":
                        handleTurn(response);
                        break;
                    case "CARD":
                        System.out.println("response = " + response);
                        handleCards(response);
                        startGame();
                        break;
                    case "ORDE":
                        out.println("CARD");
                        handlePlayerOrder(response);
                        break;
                    case "WIN_":
                        handleWinStatus(response);
                        break;
                    case "SUGG":
                        handleSuggestion(response);
                        break;
                    default:
                        showMessage(response);
                }
            }
            System.out.println("no nextLine");
        }

        private void handleSuggestion(String response) {
            String result = response.substring(4);
            System.out.println("result = " + result);
            if (result.equals("NOCARD"))
                game.showNoOneHadCard();
            else {
                String[] status = result.split(",");
                String whoHasTheCard = status[0];
                String cardToShow = status[1];
                if (whoHasTheCard.equals(Account.getUsername()))
                    game.showMyCard(cardToShow);
                else
                    game.showOtherPlayerCard(whoHasTheCard);
            }
        }

        private void handleWinStatus(String response) {
            String[] status = response.substring(4).split(",");
            game.showWinStatus(status[0], status[1]);
        }

        private void handleCards(String response) {
            String cards = response.substring(4);
            cardArray = convertToStringArray(cards);
            for (String card : cardArray)
                System.out.println("card = " + card);
        }

        private void handlePlayerOrder(String response) {
            String players = response.substring(4);
            playersInOrder = players.split(",");
            System.out.println("Players in order: "+ Arrays.toString(playersInOrder));
        }

        private void handleTurn(String response) {
            String playerName = response.substring(4);

            System.out.println("It's "+playerName+"'s turn.");
            game.nextTurn(playerName);
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

        /*Example response: MOVEWfouwy
            MOVE    -  type
            W       -  direction
            fouwy   -  player's name
        * */
        private void handleMovement(String response) {
            char direction = response.charAt(4);
            String playerName = response.substring(5);

            game.movePlayer(playerName, direction);
        }

        private String[] convertToStringArray(String charsTaken) {
            return charsTaken.replaceAll("\\[", "")
                             .replaceAll("\\]", "")
                             .replaceAll("\\s", "")
                             .split(",");
        }
    }

    public String[] getPlayersInOrder() {
        return playersInOrder;
    }
    public String[] getCards() {
        return cardArray;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(lobby_page.getCharacterButton(1))){
            askServerForChar(1);
        } else if(e.getSource().equals(lobby_page.getCharacterButton(2))){
            askServerForChar(2);
        } else if(e.getSource().equals(lobby_page.getCharacterButton(3))){
            askServerForChar(3);
        } else if(e.getSource().equals(lobby_page.getCharacterButton(4))){
            askServerForChar(4);
        } else if(e.getSource().equals(lobby_page.getCharacterButton(5))){
            askServerForChar(5);
        } else if(e.getSource().equals(lobby_page.getCharacterButton(6))){
            askServerForChar(6);
        } else if(e.getSource().equals(lobby_page.getLeaveGameButton())){
            leaveGame();
        } else if(e.getSource().equals(lobby_page.getStartButton())) {
            askServerToStartGame();
        }
    }

    public void sendSuggestionToServer(String suggestionChosen) {
        out.println("SUGG"+suggestionChosen);
    }

    public void sendAccusationToServer(String accusationChosen) {
        out.println("ACCU"+accusationChosen);
    }

    public void tellServerToUpdatePosition(String message) {
        out.println(message);
    }

    public void tellServertoEndTurn() {
        out.println("ENDT");
    }

    private void askServerToStartGame() {
        out.println("STRT");
    }

    private void askServerForChar(int charNumber) {
        out.println("CHAR"+charNumber);
    }

    private void leaveGame() {
        lobby_page.showMessage("You can't leave.");
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
                setOtherPlayerChar(characters[i], i);
            }
        }
    }

    private void startGame() {
        ClientStart.frame.setVisible(false);
        game = new Game(playerPicks, this);
    }

    private void setOtherPlayerChar(String playerName, int characterNumber) throws Exception {
        playerPicks.put(playerName, characterNumber);
        chooseCharacter(characterNumber, playerName);
        showMessage(playerName+ " picked char " +characterNumber);
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(
                () -> lobby_page.getInfoWindow().append(message + "\n")
        );
    }
}