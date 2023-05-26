package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * the client sends this when they want to login in to the server<br>
 * The client, after, wants the {@link it.polimi.ingsw.Network.Message.S2C.LoginReplyMessage LoginReply}
 *
 * @author Guido Gonnella
 */
public class LoginRequestMessage extends Message {
    public LoginRequestMessage() {
        super(MsgType.LOGIN_REQUEST);
    }
}
