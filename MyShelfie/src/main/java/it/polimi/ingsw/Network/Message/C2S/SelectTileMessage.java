package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Message from client to server<br>
 * It sends the server the coords of the board's tile the player has just chosen.
 * The server will remember the chosen tiles to permit the player to redo the action and to cancel a wrong tiles selection
 *
 * @author Samuele Mazzoleni
 */
public class SelectTileMessage extends Message {
    private final int x, y;

    public SelectTileMessage(String u, int x, int y) {
        super(MsgType.SELECT_TILE, u);
        this.x = x;
        this.y = y;
    }

    // GETTER
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
