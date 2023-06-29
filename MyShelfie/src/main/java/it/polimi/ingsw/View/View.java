package it.polimi.ingsw.View;

import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Model.*;

import java.util.ArrayList;
import java.util.Map;

public interface View {
    void init();

    /**
     * Ask the player their nickname.
     */
    void askNickname();

    /**
     * Ask the player to select the tile from the board.
     */
    void askSelectTile();

    /**
     * Ask the player to select the column where to put the selected tiles.
     */
    void askInsertCol();

    /**
     * Show on screen the {@link Board} passed as the parameter.
     * @param board
     */
    void boardShow(Space[][] board);

    /**
     * Show on screen the {@link Shelf} passed as the parameter.
     * @param shelf
     */
    void shelfShow(SerializableOptional<Tile>[][] shelf);

    /**
     * Show on screen the player's selected tiles.
     * @param hand
     */
    void showTilesInHand(ArrayList<Tile> hand);

    /**
     * Ask the player the number of players allowed to join the game.
     */
    void askPlayerNumber();

    /**
     * Show on screen the points and the completion of the {@link PublicObjective public objetive} passed in the form of the {@link PubObjType type}as the parameter.
     *      * @param code} for each player.
     * @param mappoints the map containing the player nickname and the corresponding points
     * @param mapobjective the map containing the player nickname and the corresponding completion of the objectives
     */
    void showPoints(Map<String, Integer> mappoints, Map<String, boolean[]> mapobjective);

    /**
     * SHow on screen the description of the {@link PublicObjective public objetive} passed in the form of the {@link PubObjType type}as the parameter.
     * @param code
     */
    void showPublicObjective(PubObjType code);

    /**
     * Show on screen the {@link PrivateObjective private objective}
     * @param objective
     */
    void showPrivateObjective(PrivateObjective objective);

    /**
     * Show on screen the error when the selection of the tile at the passed coordinates does not exist.
     * @param x x coordinate
     * @param y y coordinate
     */
    void invalidTile(int x, int y);

    /**
     * Show on screen the error when the selected tiles are not adjacent or does not make a straight line.
     */
    void invalidCombo();

    /**
     * Show on screen the error when the selected column is not valid (out of bounds or already full).
     * @param column
     */
    void invalidColumn(int column);

    /**
     * Show on screen the {@link String} passed as the parameter.
     * @param text the String to be showed.
     */
    void showText(String text);
}
