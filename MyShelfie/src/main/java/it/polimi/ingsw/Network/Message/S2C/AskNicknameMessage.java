package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

public class AskNicknameMessage extends Message {
    public AskNicknameMessage() {
        super(MsgType.ASK_NICKNAME);
    }
}
