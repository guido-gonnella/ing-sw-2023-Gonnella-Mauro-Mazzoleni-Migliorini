package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

/**
 * Message from client to server<br>
 * Notifies the server the ending of the current player's turn to pass on the next one
 *
 * @author Samuele Mazzoleni
 */
public class EndPlTurnMessage extends Message {

    public EndPlTurnMessage() {
        super(MsgType.END_PL_TURN);
    }
}
