package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

public class AskColumnSelectMsg extends Message{
    public AskColumnSelectMsg(){super(MsgType.SELECT_COL_REQUEST);}
}
