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

/**
 * This class handles the communication with the server
 * for the Lobby and Game using a Socket and a Scanner
 * as input and a PrintWriter as output.
 */
public class LobbyLogic implements ActionListener {
    private final LobbyPage lobby_page;
    Scanner in;
    PrintWriter out;

    private String[] playersInOrder;
    private String[] cardArray;
    private final Map<String, Integer> playerPicks;
    private Game game;

    /**
     * Creates a handler for the Lobby and Game pages.
     * @param lobby_page the object that ...
     * @param in the input to be used to receive messages from the server
     * @param out the output to be used to send messages the server
     */
    public LobbyLogic(LobbyPage lobby_page, Scanner in, PrintWriter out) {
        this.lobby_page = lobby_page;
        this.in = in;
        this.out = out;

        Account.setLobbyOutput(out);
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

        /**
         * Starts the communication with the server and handles the
         * incoming messages while the connection is open.
         * <br>
         * The incoming messages (or response) from the server
         * are a String in the format where the first 4 characters
         * determine the type of message and the rest depend on the type.
         * If the type is not recognized, the incoming message is displayed
         * in the lobby page.
         * <br><br>
         * The recognized <b>types</b> and the respective format of the messages
         * are the following:<br>
         * <br><b>CHAR</b><br>
         *     Used to show characters that other players picked.<br>
         *     Format: CHAR(character number)(username)<br>
         *     Example: "CHAR5nickname"
         *     <br>
         * <br><b>GAME</b><br>
         *     Used to start the game.<br>
         *     Format: GAME
         *     <br>
         * <br><b>MOVE</b><br>
         *     Used to update the position of this and the other
         *     player characters.<br>
         *     Format: MOVE(direction)(username)<br>
         *     Example: "MOVEDnickname"
         *     <br>
         * <br><b>MESG</b><br>
         *     Used to show a message from the server.<br>
         *     Format: MESG(message)<br>
         *     Example: "MESGNot your turn"
         *     <br>
         * <br><b>ENDT</b><br>
         *     Used to move on to next turn.<br>
         *     Format: ENDT(next player)<br>
         *     Example: "ENDTnickname"
         *     <br>
         * <br><b>CARD</b><br>
         *     Used to get the card assigned to this player.<br>
         *     Format: CARD(card names)<br>
         *     Example: "CARD[place3, weapon1, place5]"
         *     <br>
         * <br><b>ORDE</b><br>
         *     Used at the start of the game to get the player order.<br>
         *     Format: ORDE(username1),(username2),(username3)...<br>
         *     Example: "ORDEnickname1,nick2,nick3"
         *     <br>
         * <br><b>WIN_</b><br>
         *     Used to know if some player won or lost the game.<br>
         *     Format: WIN_(result),(username)<br>
         *     Example: "WIN_LOSE,nick" or "WIN_WIN,nick2"
         *     <br>
         * <br><b>SUGG</b><br>
         *     Used to know if someone showed a card, and if
         *     it is this player who showed the card also what
         *     card they showed.<br>
         *     If no one showed a card, will receive "SUGGNOCARD"<br>
         *     Format: SUGG(player who has the card)(card)<br>
         *     Example: "SUGGnick2,place3"
         * @throws Exception when it can no longer communicate
         * with the server.
         */
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
                    case "LEFT":
                        handlePlayerLeaving(response);
                        break;
                    case "LEAV":
                        handleHostLeaving();
                        break;
                    case "CHAT":
                        String[] msg = response.substring(4).split(",");
                        game.showMessage(msg[1]+": "+msg[0]);
                        break;
                    case "INFO":
                        game.showMessage(response.substring(4));
                    default:
                        showMessage(response);
                }
            }
            System.out.println("no nextLine");
        }

        private void handleHostLeaving() {
            lobby_page.showMessage("The Host left the lobby.\nClosing lobby...");
            Account.setLobbyCode(0);
            Account.setLobbyOutput(null);
            leaveGame();
        }

        private void handlePlayerLeaving(String response) {
            String player = response.substring(4);
            showMessage(player + " left.");
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
                String whoAsked = status[2];
                if (whoHasTheCard.equals(Account.getUsername()))
                    game.showMyCard(cardToShow);
                else if (whoAsked.equals(Account.getUsername()))
                    game.showAskedCard(cardToShow, whoHasTheCard);
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

    /**
     * Returns the players ordered by their turns.
     * @return a array of usernames of the players
     * in the game in order of turns.
     */
    public String[] getPlayersInOrder() {
        return playersInOrder;
    }

    /**
     * Returns the cards attributed by the server to
     * this player.
     * @return a array of strings with the name of
     * the cards.
     */
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

    /**
     * Sends a suggestion to the server to check if
     * any players have any of the cards suggested.
     * <br>
     * A suggestion is composed of 3 cards: One person,
     * one weapon and one room card.
     * @param suggestionChosen the suggestion in the
     *        format (person),(weapon),(room)
     */
    public void sendSuggestionToServer(String suggestionChosen) {
        out.println("SUGG"+suggestionChosen);
    }

    /**
     * Sends a accusation to the server to check if
     * they match the mystery cards.
     * <br>
     * A accusation is composed of 3 cards: One person,
     * one weapon and one room card.
     * @param accusationChosen the accusation in the
     *        format (person),(weapon),(room)
     */
    public void sendAccusationToServer(String accusationChosen) {
        out.println("ACCU"+accusationChosen);
    }

    /**
     * Tell the server to update the position of this player
     * to all the other players in the game. <b>NOTE:</b> If
     * it is not this player's turn, the position will not be
     * updated.
     * <br>
     * The message parameter is in the format MOVE(direction).
     * This "direction" is represented by a {@code char}: W is up,
     * A is left, D is right and S is down.
     * <br>
     * For example, if this player moves up, the message parameter
     * should be: "MOVEW"
     * @param message String in the format: MOVE(direction).
     */
    public void tellServerToUpdatePosition(String message) {
        out.println(message);
    }

    /**
     * Tell the server to end this player's turn.
     * If it is not their turn, the server will
     * do nothing and send a message to this player
     * warning them that it is not their turn.
     */
    public void tellServertoEndTurn() {
        out.println("ENDT");
    }

    public void sendMessageToParty(String message) {
        out.println("CHAT"+message);
    }

    /**
     * Tell the server to start the game.
     */
    private void askServerToStartGame() {
        out.println("STRT");
    }

    /**
     * Tells the server the character this player
     * picked.
     * @param charNumber the number corresponding
     *                   to the picked character.
     */
    private void askServerForChar(int charNumber) {
        out.println("CHAR"+charNumber);
    }

    private void leaveGame() {
        out.println("QUIT");
        Account.setLobbyCode(0);
        Account.setLobbyOutput(null);
        ClientStart.cardLayout.show(ClientStart.rootPanel, "Main");
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

        String character ;
        switch (characterNumber) {
            case 1: character = "Sherlock Holmes";
                break;
            case 2: character = "Moriarty";
                break;
            case 3: character = "Mrs Hudson";
                break;
            case 4: character = "Irene Adler";
                break;
            case 5: character = "Enola Holmes";
                break;
            case 6: character = "Mycroft Holmes";
                break;
            default:
                character = "Special Character";
        }
        showMessage(playerName+ " picked " +character);
    }

    private void showMessage(String message) {
        SwingUtilities.invokeLater(
                () -> lobby_page.getInfoWindow().append(message + "\n")
        );
    }
}