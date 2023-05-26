package it.polimi.ingsw.Network.ClientPack;

import it.polimi.ingsw.Network.Message.Message;

public abstract class ClientConnection {

    public abstract Message readMessage();

    public abstract void sendMessage(Message message);
    public abstract void disconnect();
}
