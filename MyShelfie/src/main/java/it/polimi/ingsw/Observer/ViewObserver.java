package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Coords;

import java.io.IOException;
import java.util.ArrayList;
//TODO rewrite the observers javadocs
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
    public void onSelectTile(int x, int y);

    /**
     * Sends a message to the server when the user select a column of the shelf
     * @param col - the selected column
     */
    public void onSelectCol(int col);
    public void onNicknameUpdate(String name);


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
    public void onConnection(String serverAddr, int port);

    /**
     * When the user reply to the server to the request of the maximum number of player for the game
     *
     * @param numPlayers the max number of players allowed in the game
     * @author Guido Gonnella
     */
    public void onPlayerNumberReply(int numPlayers);
}
