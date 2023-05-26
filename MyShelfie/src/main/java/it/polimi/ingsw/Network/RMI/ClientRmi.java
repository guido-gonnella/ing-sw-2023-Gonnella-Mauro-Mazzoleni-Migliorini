package it.polimi.ingsw.Network.RMI;

import it.polimi.ingsw.Network.ClientPack.ClientConnection;
import it.polimi.ingsw.Network.Message.Message;

public class ClientRmi extends ClientConnection {

    public ClientRmi(String address, int port){

    }
    @Override
    public Message readMessage() {
        return null;
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void disconnect() {

    }
}
