package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Enumeration.MsgType;

public class PingMessage extends Message{
    public PingMessage() {
        super(MsgType.PING);
    }
}
