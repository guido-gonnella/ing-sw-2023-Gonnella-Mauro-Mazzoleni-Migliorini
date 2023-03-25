package it.polimi.ingsw;

import java.util.Optional;

/**
 * Class that describes the usage and the methods of the board component of the game
 * It contains the grid itself, his filling method, methods used to access its attributes and other support methods
 *
 * @author Samuele Mazzoleni
 */

public class Board {
    private Space[][] grid;
    private final int ROW = 9;
    private final int COL = 9;

    /**
     * Constructor of the board class
     */
    public Board() {
        this.grid = new Space[ROW][COL];
    }

    /**
     * Methods used to fill the board when the conditions are met
     *
     * @param sackOfTiles sack of the remaining tiles used to extract random tiles to be placed
     */
    public void fill(SackOfTiles sackOfTiles) {
        for(int row=0; row<9; row++) {
            for(int col=0; col<9; col++) {
                if(this.grid[row][col].getTile().isEmpty() && this.grid[row][col].isAvailable()){
                    //metodo per settare una random tile nella board dal sacchetto
                    grid[row][col].setTile();
                }
            }
        }
    }

    /**
     * Method used by the game class to take the tile the player clicked
     *
     * @param row number of the row of the clicked tile
     * @param col number of the column of the clicked tile
     * @return the tile that will be placed on the player shelf
     */
    public Optional<Tile> takeTiles(int row, int col) {
        //togliere la tile dalla board
        this.grid[row][col].setTile();

        return grid[row][col].getTile();
    }

    /**
     * Support method used to get the board grid
     *
     * @return the board grid
     */
    public Space[][] getGrid() {

        return grid.clone();
    }

    /**
     * Setter method used to update the grid
     *
     * @param grid updated grid to use
     */
    public void setGrid(Space[][] grid) {
        this.grid = grid.clone();
    }
}
