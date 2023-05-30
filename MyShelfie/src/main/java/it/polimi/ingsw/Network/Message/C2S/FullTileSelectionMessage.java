package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Model.Coords;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MsgType;

import java.util.ArrayList;

public class FullTileSelectionMessage extends Message{
    private final ArrayList <Coords> selection;
    private final Integer column;
    public FullTileSelectionMessage(ArrayList<Coords>selectionlist, Integer col) {
        super(MsgType.FULL_TILE_SELECTION);
        this.selection=selectionlist;
        this.column=col;
    }

    public ArrayList<Coords> getSelection() {
        return selection;
    }


    public Integer getColumn() {
        return column;
    }
}
