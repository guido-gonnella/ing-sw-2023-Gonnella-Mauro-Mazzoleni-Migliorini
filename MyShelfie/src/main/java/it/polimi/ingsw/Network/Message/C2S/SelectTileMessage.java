package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

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
