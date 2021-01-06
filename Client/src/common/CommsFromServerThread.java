package common;

import authentication.Client;
import game.Account;

public class CommsFromServerThread implements Runnable{

    private final MainLogic communicator;
    public CommsFromServerThread(MainLogic communicator) {
        this.communicator = communicator;
    }

    @Override
    public void run() {
        Client client = new Client(ClientStart.serverIP);
        client.setCommunicationWith(communicator);
        String[] startCommunication = {"Comms", Account.getUsername()};
        client.sendInformation(startCommunication);
    }
}
