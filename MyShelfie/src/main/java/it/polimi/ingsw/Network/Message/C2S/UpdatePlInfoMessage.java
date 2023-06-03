package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Message from client to server, notifying to update the nickname of the sender player<br>
 * It sends the new nickname
 * @author Guido Gonnella
 */
public class UpdatePlInfoMessage extends Message {
    public final String user;

    public UpdatePlInfoMessage(String user) {
        super(MsgType.SEND_NICKNAME);
        this.user = user;
    }

    public String getNickname(){
        return user;
    }
}
