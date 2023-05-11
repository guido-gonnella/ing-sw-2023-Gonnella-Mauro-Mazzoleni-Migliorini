package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.Optional;

public class Space implements Serializable {
    private Optional<Tile> tile;
    private Boolean available;

    public Space() {
        this.tile = Optional.empty();
        this.available = false;
    }

    public Optional<Tile> getTile() {
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
        this.tile = Optional.of(tile);
    }

    public void removeTile(){
        this.tile = Optional.empty();
    }
}