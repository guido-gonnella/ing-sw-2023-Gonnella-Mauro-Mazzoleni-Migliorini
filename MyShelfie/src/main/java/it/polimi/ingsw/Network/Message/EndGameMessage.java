package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Message that indicates that the game has ended or has been suspended
 */
public class EndGameMessage extends Message{

    public EndGameMessage(){
        super(MsgType.END_GAME);
    }
}
