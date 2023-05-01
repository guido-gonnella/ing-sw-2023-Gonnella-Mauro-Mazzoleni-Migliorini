package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

public class EndSelectTilesMessage extends Message {

    public EndSelectTilesMessage(String u) {
        super(MsgType.END_SEL_TILES, u);
    }
}
