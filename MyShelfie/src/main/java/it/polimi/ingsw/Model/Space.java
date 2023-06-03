package it.polimi.ingsw.Model;

import java.io.Serializable;

public class Space implements Serializable {
    private SerializableOptional<Tile> tile;
    private Boolean available;

    public Space() {
        this.tile = SerializableOptional.empty();
        this.available = false;
    }

    public SerializableOptional<Tile> getTile() {
        return this.tile;
    }

    /**
     * changes the status of the availability of the space
     **/
    public void setAvailable() {
        this.available = true;
    }

    public Boolean isAvailable() {
        return this.available;
    }

    public void setTile(Tile tile) {
        this.tile = SerializableOptional.of(tile);
    }

    public void removeTile(){
        this.tile = SerializableOptional.empty();
    }
}