package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;


public class PublicObjectiveMessage extends Message{
    private String[] publicObjectives;
    public PublicObjectiveMessage (String[] pubobj) {
        super(MsgType.PUBLIC_OBJECTIVE);
        this.publicObjectives=pubobj;
    }

    public String[] getPublicObjectives() {
        return publicObjectives;
    }
}
