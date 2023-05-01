package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

public class ShelfColumnMessage extends Message {
    private final int col;

    public ShelfColumnMessage(String u, int col) {
        super(MsgType.SELECT_COL, u);
        this.col = col;
    }

    // GETTER
    public int getCol() {
        return col;
    }
}
