package it.polimi.ingsw.Network.ClientPack;

import it.polimi.ingsw.Network.Message.Message;

/**
 * Is the abstract class where are defined the core method used by the client to communicate with the server.
 */
public abstract class ClientConnection {

    /**
     * Read the {@link Message} sent.
     * @return the {@link Message} read.
     */
    public abstract Message readMessage();

    /**
     * Send the {@link Message} passed as the parameter.
     * @param message the {@link Message} to be sent.
     */
    public abstract void sendMessage(Message message);

    /**
     * Disconnect the client.
     */
    public abstract void disconnect();
}
