package it.polimi.ingsw.Model;

import java.io.Serializable;

public class Space implements Serializable {
    private SerializableOptional<Tile> tile;
    private Boolean available;

    /**
     * The constructor of the class.
     */
    public Space() {
        this.tile = SerializableOptional.empty();
        this.available = false;
    }

    /**
     * Getter for the tile attribute.
     * @return it returns the {@link SerializableOptional} object tile.
     */
    public SerializableOptional<Tile> getTile() {
        return this.tile;
    }

    /**
     * changes the status of the availability of the space
     */
    public void setAvailable() {
        this.available = true;
    }

    /**
     * To check if on this space of the {@link Board} can be placed a tile or not.
     * @return true if on the space can be placed a tile, false otherwise.
     */
    public Boolean isAvailable() {
        return this.available;
    }

    /**
     * Set the attribute tile with a {@link SerializableOptional} with the value of the tile passed as the parameter.
     * @param tile the tile to be placed in the space
     */
    public void setTile(Tile tile) {
        this.tile = SerializableOptional.of(tile);
    }

    /**
     * Set the attribute tile with an empty {@link SerializableOptional} object;
     */
    public void removeTile(){
        this.tile = SerializableOptional.empty();
    }
}