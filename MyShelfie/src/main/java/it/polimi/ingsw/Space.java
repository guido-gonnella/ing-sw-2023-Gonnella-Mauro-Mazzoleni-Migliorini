package it.polimi.ingsw;

import java.util.Optional;

/**
 * Class aggregate of board, it describes each space of the grid of the board
 * It describes methods for the availability of every single space and the tile it contains
 *
 * @implNote The availability depends on the number of players and if the space can keep a tile
 * @author Samuele Mazzoleni
 */

public class Space {
    private Optional<Tile> tile;
    private Boolean available;

    /**
     * Constructor of the space class
     */
    public Space() {
        this.tile = Optional.empty();
        this.available = false;
    }

    /**
     * Support method to get the tile attribute
     *
     * @return the tile attribute
     */
    public Optional<Tile> getTile() {
        Optional<Tile> temp = this.tile;
        return temp;
    }

    /**
     * It sets the space of the grid as available
     */
    public void setAvailable() {
        this.available = true;
    }

    /**
     * It says if the space is available
     *
     * @return true if available, false if not
     */
    public Boolean isAvailable() {
        return this.available;
    }

    /**
     * It sets the tile attribute of this specific space as the tile parameter
     *
     * @param tile the tile to be set
     */
    public void setTile(Optional<Tile> tile) {
        this.tile = tile;
    }
}
