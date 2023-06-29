package it.polimi.ingsw.Network.ServerPack;

import it.polimi.ingsw.Network.Message.Message;

/**
 * Abstract class containing the methods which the server uses to read and send {@link Message messages} from the client.
 */
public abstract class ServerConnection {

    public abstract Message readMessage();
    public abstract void sendMessage(Message message);
    public abstract void disconnect();
}
