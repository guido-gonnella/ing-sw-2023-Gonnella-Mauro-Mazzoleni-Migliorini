package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Message from client to server<br>
 * Notifies the server the ending of the current player's choice action of the board's tiles.
 * It's sent when the player has chosen 1 to 3 tiles from the board.
 *
 * @author Samuele Mazzoleni
 */
public class EndSelectTilesMessage extends Message {

    public EndSelectTilesMessage(String u) {
        super(MsgType.END_SEL_TILES, u);
    }
}
