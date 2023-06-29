package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.PublicObjective;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Send the player the {@link PublicObjective}
 */
public class PublicObjectiveMessage extends Message{
    private final PubObjType[] publicObjectives;
    public PublicObjectiveMessage (PubObjType[] pubObj) {
        super(MsgType.PUBLIC_OBJECTIVE);
        this.publicObjectives = pubObj;
    }

    public PubObjType[] getPublicObjectives() {
        return publicObjectives;
    }
}
