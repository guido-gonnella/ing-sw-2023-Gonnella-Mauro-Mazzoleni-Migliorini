package it.polimi.ingsw;

import java.util.Optional;

public class Space {
    private Optional<Tile> tile;
    private Boolean available;

    public Space() {
        this.tile = Optional.empty();
        this.available = false;
    }

    public Optional<Tile> getTile() {
        Optional<Tile> temp = this.tile;
        return temp;
    }

    public void setAvailable() {
        this.available = true;
    }

    public Boolean isAvailable() {
        return this.available;
    }

    public void setTile(Optional<Tile> tile) {
        this.tile = tile;
    }
}
