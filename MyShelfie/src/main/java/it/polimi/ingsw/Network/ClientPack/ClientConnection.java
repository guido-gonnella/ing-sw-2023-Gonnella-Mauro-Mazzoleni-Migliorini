package it.polimi.ingsw.Network.ClientPack;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Observer.Observable;

public abstract class ClientConnection extends Observable {

    public abstract void readMessage();

    public abstract void sendMessage(Message message);
    public abstract void disconnect();
}
