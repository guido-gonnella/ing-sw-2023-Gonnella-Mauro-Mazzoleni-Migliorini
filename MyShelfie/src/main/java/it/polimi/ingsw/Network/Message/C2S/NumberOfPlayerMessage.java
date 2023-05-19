package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * When the first player connects to the server, the first thing he sends is the number of player for the game
 *
 * @author Guido Gonnella
 */
public class NumberOfPlayerMessage extends Message {
    private final int num;

    public NumberOfPlayerMessage(String u, int num) {
        super(MsgType.NUMBER_PLAYER_REPLY);
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
