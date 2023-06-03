package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 *
 * @author Guido Gonnella
 */
public class MaxNumPlayerRequestMsg extends Message {
    public MaxNumPlayerRequestMsg() {
        super(MsgType.NUMBER_PLAYER_REQUEST);
    }
}
