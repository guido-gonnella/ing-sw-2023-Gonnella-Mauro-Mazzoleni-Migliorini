package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Coords;

import java.io.IOException;

/**
 * Observer interface with the methods from the view
 * @author Guido Gonnella
 */
public interface ViewObserver {
    /**
     * Sends a message to the server when the user select a tile from the board<br>
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public void onSelectTile(int x, int y);

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

    /**
     * When the user wants to connect to a server
     */
    public void onConnection(String serverAddr, int port) throws IOException;
}
