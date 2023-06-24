package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Enumeration.MsgType;
import it.polimi.ingsw.Network.Message.Message;

/**
 * Message that indicates that the game has ended or has been suspended
 */
public class EndGameMessage extends Message {

    public EndGameMessage(){
        super(MsgType.END_GAME);
    }
}
