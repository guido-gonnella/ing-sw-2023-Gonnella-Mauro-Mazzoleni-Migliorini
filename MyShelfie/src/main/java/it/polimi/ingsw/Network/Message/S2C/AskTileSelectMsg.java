package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 *
 * @author Guido Gonnella
 */
public class AskTileSelectMsg extends Message {
    public AskTileSelectMsg() {
        super(MsgType.SELECT_TILE_REQUEST);
    }
}
