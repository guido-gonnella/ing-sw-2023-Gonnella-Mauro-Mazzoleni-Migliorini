package it.polimi.ingsw;

import java.util.Optional;

public class Board {
    private Space[][] grid;
    private final int ROW = 9;
    private final int COL = 9;

    public Board() {
        this.grid = new Space[ROW][COL];
    }

    public void Fill(SackOfTiles sackOfTiles) {
        for(int row=0; row<9; row++) {
            for(int col=0; col<9; col++) {
                if(this.grid[row][col].getTile().isEmpty() && this.grid[row][col].isAvailable()){
                    //metodo per settare un random tile nella board dal sacchetto
                    grid[row][col].setTile();
                }
            }
        }
    }

    public Optional<Tile> takeTiles(int row, int col) {

        return grid[row][col].getTile();
    }

    public Space[][] getGrid() {

        return grid.clone();
    }

    public void setGrid(Space[][] grid) {
        this.grid = grid.clone();
    }
}
