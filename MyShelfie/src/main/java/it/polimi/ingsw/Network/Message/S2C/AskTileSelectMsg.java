package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 *
 * @author Guido Gonnella
 */
public class AskTileSelectMsg extends Message {
    public AskTileSelectMsg(String u) {
        super(MsgType.SELECT_TILE_REQUEST, u);
    }
}
