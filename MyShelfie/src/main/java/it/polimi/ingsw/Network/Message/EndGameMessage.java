package it.polimi.ingsw.Network.Message;

/**
 * Message that indicates that the game has ended or has been suspended
 */
public class EndGameMessage extends Message{

    public EndGameMessage(){
        super(MsgType.END_GAME);
    }
}
