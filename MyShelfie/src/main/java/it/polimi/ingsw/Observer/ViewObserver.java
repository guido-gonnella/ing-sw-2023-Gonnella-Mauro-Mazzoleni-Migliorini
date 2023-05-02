package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Coords;

/**
 * Observer interface with the methods from the view
 * @author Guido Gonnella
 */
public interface ViewObserver {
    /**
     * Sends a message to the server when the user select a tile from the board<br>
     *
     * @param pos selected tile position
     */
    public void onSelectTile(Coords pos);

    /**
     * Sends a message to the server when the user select a column of the shelf
     * @param col - the selected column
     */
    public void onSelectCol(int col);

    /**
     * Sends a message to the server with the position in the tiles in hand array, to swap
     *
     * @param to position 1
     * @param from position 2
     */
    public void onSwap(int to, int from);

    /**
     * Send a message to the server when the user wants to end the selection of tiles
     */
    public void onEndSelection();

    /**
     * Sends a message to the server when the user wants to end their turn
     */
    public void onEndTurn();
}
