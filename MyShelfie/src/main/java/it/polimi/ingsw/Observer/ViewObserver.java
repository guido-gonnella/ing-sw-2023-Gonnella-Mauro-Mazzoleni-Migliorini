package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Coords;

import java.util.ArrayList;
/**
 * Observer interface with the methods from the it.polimi.ingsw.view
 * @author Guido Gonnella
 */
public interface ViewObserver {
    /**
     * Sends a message to the server when the user select a tile from the board<br>
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    void onSelectTile(int x, int y);

    /**
     * Sends a message to the server when the user selects a column of the shelf
     * @param col - the selected column
     */
    void onSelectCol(int col);
    void onNicknameUpdate(String name);

    /**
     * When the user wants to connect to a server
     */
    void onConnection(String serverAddr, int port);

    /**
     * When the user reply to the server to the request of the maximum number of players for the game
     *
     * @param numPlayers the max number of players allowed in the game
     * @author Guido Gonnella
     */
    void onPlayerNumberReply(int numPlayers);

    void onSelection(ArrayList<Coords> coords, int col);
}
