package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Model.PrivateObjective;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Send to the player the {@link PrivateObjective}.
 */
public class PrivateObjectiveMessage extends Message {
    private PrivateObjective privateObjective;
    public PrivateObjectiveMessage(PrivateObjective priv){
        super(MsgType.PRIVATE_OBJECTIVE);
        this.privateObjective=priv;
    }

    public PrivateObjective getPrivateObjective() {
        return privateObjective;
    }
}
