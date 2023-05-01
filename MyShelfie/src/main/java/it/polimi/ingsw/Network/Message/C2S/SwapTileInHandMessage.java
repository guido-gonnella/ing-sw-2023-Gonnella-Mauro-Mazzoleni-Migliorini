package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Message from client to server<br>
 * It sends the server a message to change the tiles' order the player is keeping in their hand
 *
 * @author Samuele Mazzoleni
 */
public class SwapTileInHandMessage extends Message {
    private final int from, to;
    public SwapTileInHandMessage(String u, int from, int to) {
        super(MsgType.HAND_TILE_SWAP, u);
        this.from = from;
        this.to = to;
    }

    //GETTER
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
