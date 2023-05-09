package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Used when the server receives the client information.<br>
 * Accepted refers to the success of the login
 *
 * @author Guido Gonnella
 */
public class LoginReplyMessage extends Message {
    private final boolean accepted;

    public LoginReplyMessage(String u, boolean accepted) {
        super(MsgType.LOGIN_REPLY, u);
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
