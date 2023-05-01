package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

public class EndPlTurnMessage extends Message {
    public EndPlTurnMessage(String u) {
        super(MsgType.END_PL_TURN, u);
    }
}
