package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Network.Message.Message;

public abstract class ServerConnection {

    public abstract Message readMessage();
    public abstract void sendMessage(Message message);
    public abstract void disconnect();
}
