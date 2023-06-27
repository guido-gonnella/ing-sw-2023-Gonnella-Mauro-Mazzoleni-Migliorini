package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Used b the server to ask the player for its name.
 */
public class AskNicknameMessage extends Message {
    public AskNicknameMessage() {
        super(MsgType.ASK_NICKNAME);
    }
}
