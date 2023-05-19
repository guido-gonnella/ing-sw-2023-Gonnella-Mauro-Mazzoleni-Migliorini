package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Message from client to server<br>
 * Notifies the server of the chosen column of the player's shelf for inserting the selected tiles
 *
 * @author Samuele Mazzoleni
 */
public class SelectColumnMessage extends Message {
    private final int col;

    public SelectColumnMessage(String u, int col) {
        super(MsgType.SELECT_COL);
        this.col = col;
    }

    // GETTER
    public int getCol() {
        return col;
    }
}
