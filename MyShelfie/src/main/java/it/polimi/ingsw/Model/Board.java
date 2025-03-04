package it.polimi.ingsw.Model;

import java.io.Serializable;

/**
 * Class that describes the usage and the methods of the board component of the game
 * It contains the grid itself, his filling method, methods used to access its attributes and other support methods
 *
 * @author Samuele Mazzoleni
 */

public class Board implements Serializable {
    private Space[][] grid;
    final int ROW = 9;
    final int COL = 9;

    /**
     * Constructor of the board class
     */
    public Board() {
        this.grid = new Space[ROW][COL];

        for(int i=0; i<ROW; i++){
            for(int j=0; j<COL; j++){
                this.grid[i][j] = new Space();
            }
        }
    }

    /**
     * Methods used to fill the board when the conditions are met
     *
     * @param sackOfTiles sack of the remaining tiles used to extract random tiles to be placed
     */
    public void fill(SackOfTiles sackOfTiles){
        for(int row=0; row<ROW; row++) {
            for(int col=0; col<COL; col++) {
                if(this.grid[row][col].isAvailable() && this.grid[row][col].getTile().isEmpty()){
                    if(!sackOfTiles.getLeftTiles().isEmpty()) {
                        Tile tile = sackOfTiles.getRandomTile();
                        this.grid[row][col].setTile(tile);
                    }
                }
            }
        }
    }
    /**
     * Method used by the game class to take the tile the player clicked
     *
     * @param row row number of the clicked tile
     * @param col column number of the clicked tile
     * @return the tile that will be placed on the player shelf
     */
    public SerializableOptional<Tile> takeTiles(int row, int col) {
        //togliere la tile dalla board
        SerializableOptional<Tile> temp = this.grid[row][col].getTile();
        this.grid[row][col].removeTile();

        return temp;
    }

    /**
     * Support method used to get the board grid
     *
     * @return the board grid
     */
    public Space[][] getGrid() {
        return grid;
    }

    /**
     * Setter method used to update the grid
     *
     * @param grid updated grid to use
     */
    public void setGrid(Space[][] grid) {
        this.grid = grid;
    }

    /**
     * @return true if there is at least a tile with a tile near, false otherwise
     * @author Pierantonio Mauro
     */
    public boolean checkFill(){
        int flagNotAlone = 0;
        for(int i=0; i<ROW && flagNotAlone==0; i++){
            for(int j=0; j<COL && flagNotAlone==0; j++){
                if(this.grid[i][j].getTile().isPresent()){
                  if(i!=0 && this.grid[i-1][j].getTile().isPresent())
                      flagNotAlone = 1;
                  if(i!=ROW-1 && this.grid[i+1][j].getTile().isPresent())
                      flagNotAlone = 1;
                  if(j!=0 && this.grid[i][j-1].getTile().isPresent())
                      flagNotAlone = 1;
                  if(j!=COL-1 && this.grid[i][j+1].getTile().isPresent())
                      flagNotAlone = 1;
                }
            }
        }

        return flagNotAlone != 0;
    }


}
