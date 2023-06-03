package it.polimi.ingsw.Network.Message.S2C;

import it.polimi.ingsw.Model.Shelf;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

/**
 * Message from server to the client, notifying to update its it.polimi.ingsw.view to update the shelf<br>
 * It sends the information about the updated shelf
 * @author Guido Gonnella
 */
public class UpdateShelfMessage extends Message {
    private final Shelf shelf;
    public UpdateShelfMessage(Shelf shelf) {
        super(MsgType.SHELF_UPDATE);
        this.shelf = shelf;
    }

    public Shelf getShelf() {
        return shelf;
    }
}
