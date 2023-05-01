package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Message from client to server, notifying to update the nickname of the sender player<br>
 * It sends the new nickname
 * @author Guido Gonnella
 */
public class UpdatePlInfoMessage extends Message {
    private final String user;

    public UpdatePlInfoMessage(String u, String user) {
        super(MsgType.PLAYER_UPDATE, u);
        this.user = user;
    }
}
