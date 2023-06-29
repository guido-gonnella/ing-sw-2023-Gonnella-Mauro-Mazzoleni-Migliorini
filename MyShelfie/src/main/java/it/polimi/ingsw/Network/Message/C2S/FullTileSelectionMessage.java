package it.polimi.ingsw.Network.Message.C2S;

import it.polimi.ingsw.Model.Coords;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Enumeration.MsgType;

import java.util.ArrayList;

/**
 * Used to communicate to the server the selected tiles from the board, and the selected column where the player wants to place their tiles.
 */
public class FullTileSelectionMessage extends Message{
    private final ArrayList<Coords> selection;
    private final Integer column;

    public FullTileSelectionMessage(ArrayList<Coords> selectionList, Integer col) {
        super(MsgType.FULL_TILE_SELECTION);
        this.selection = selectionList;
        this.column = col;
    }

    public ArrayList<Coords> getCoords() {
        return selection;
    }


    public Integer getColumn() {
        return column;
    }
}
