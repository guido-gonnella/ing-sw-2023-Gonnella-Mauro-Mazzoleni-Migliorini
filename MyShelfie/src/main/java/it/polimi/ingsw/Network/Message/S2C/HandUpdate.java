package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

import java.util.ArrayList;

public class HandUpdate extends Message {
    private final ArrayList<MsgType> tilesInHand;

    public HandUpdate(ArrayList<MsgType> hand) {
        super(MsgType.HAND_UPDATE);
        this.tilesInHand = hand;
    }

    public ArrayList<MsgType> getTilesInHand() {
        return tilesInHand;
    }
}
