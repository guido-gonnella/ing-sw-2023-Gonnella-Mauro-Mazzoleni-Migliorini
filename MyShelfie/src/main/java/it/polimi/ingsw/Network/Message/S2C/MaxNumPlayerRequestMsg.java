package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Request the first player connected to the server the maximum number of the player for that game.
 */
public class MaxNumPlayerRequestMsg extends Message {
    public MaxNumPlayerRequestMsg() {
        super(MsgType.NUMBER_PLAYER_REQUEST);
    }
}
