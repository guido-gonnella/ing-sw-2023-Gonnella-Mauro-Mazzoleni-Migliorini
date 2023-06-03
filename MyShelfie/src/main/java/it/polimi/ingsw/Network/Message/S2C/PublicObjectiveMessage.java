package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Model.PublicObjective;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;


public class PublicObjectiveMessage extends Message{
    private final PublicObjective[] publicObjectives;
    public PublicObjectiveMessage (PublicObjective[] pubObj) {
        super(MsgType.PUBLIC_OBJECTIVE);
        this.publicObjectives = pubObj;
    }

    public PublicObjective[] getPublicObjectives() {
        return publicObjectives;
    }
}
