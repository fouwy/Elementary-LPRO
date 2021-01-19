package common;

import authentication.Client;
import game.Account;

/**
 * This class is used to create a permanent connection to
 * the server with this client while they are logged in,
 * in order to receive unprompted messages from the server
 * such as friend requests from other users.
 */
public class CommsFromServerThread implements Runnable{

    private final MainLogic communicator;
    public CommsFromServerThread(MainLogic communicator) {
        this.communicator = communicator;
    }

    /**
     * Starts the connection to the server.
     */
    @Override
    public void run() {
        Client client = new Client(ClientStart.serverIP);
        client.setCommunicationWith(communicator);
        String[] startCommunication = {"Comms", Account.getUsername()};
        client.sendInformation(startCommunication);
    }
}
