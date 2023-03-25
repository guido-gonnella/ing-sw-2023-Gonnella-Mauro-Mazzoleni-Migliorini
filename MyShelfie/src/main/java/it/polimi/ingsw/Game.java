package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Game {
    private Board board;
    private SackOfTiles sackOfTiles;
    private PublicObjective[] publicObjectives;
    private ArrayList<Player> players;
    private Player firstPlayer;
    private Player currentPlayer;
    private Stack<Integer>[] pointsPubObj;

    public Game() {
        this.board = new Board();
        this.sackOfTiles = new SackOfTiles();
        this.publicObjectives = new PublicObjective[2];
        this.players = new ArrayList<Player>();
        //non so cosa che mi dice quanti giocatori ci sono
        this.pointsPubObj = new Stack[this.players.size()];

        Random rand = new Random();
        int index = rand.nextInt(this.players.size());
        this.firstPlayer = new Player(this.players.get(index).getNickname());
        this.currentPlayer = new Player(this.players.get(index).getNickname());
    }

    public void takeTiles() {
        int x, y, col;
        //coordinate e colonna prese da input
        Tile temp = this.board.takeTiles(x, y);
        currentPlayer.placeTile(temp, col);
    }

    public void setPublicObjectives(DeckOfPublicObjectives deckOfPublicObjectives) {
        this.publicObjectives[0] = deckOfPublicObjectives.getPublicObjective().get(0);
        this.publicObjectives[1] = deckOfPublicObjectives.getPublicObjective().get(1);
    }

    public void setPrivateObjectives(DeckOfPrivateObjectives deckOfPrivateObjectives) {
        for(int i=0; i<3; i++) {
            this.privateObjectives[i] = deckOfPrivateObjectives.getPrivateObjective().get(i);
        }
    }

    public void setBoard(Board board) {
        Space[][] grid = this.board.getGrid();

        if(this.players.size()>2) {
            grid[0][3].setAvailable();
            grid[2][2].setAvailable();
            grid[2][6].setAvailable();
            grid[3][8].setAvailable();
            grid[5][0].setAvailable();
            grid[6][2].setAvailable();
            grid[6][6].setAvailable();
            grid[8][5].setAvailable();

            if(this.players.size()>3) {
                grid[0][4].setAvailable();
                grid[1][5].setAvailable();
                grid[3][1].setAvailable();
                grid[4][0].setAvailable();
                grid[4][8].setAvailable();
                grid[5][7].setAvailable();
                grid[7][3].setAvailable();
                grid[8][4].setAvailable();
            }
        }

        for(int row=1; row<8; row++) {
            switch (row) {
                case 1 -> {
                    grid[row][3].setAvailable();
                    grid[row][4].setAvailable();
                }
                case 2, 6 -> {
                    grid[row][3].setAvailable();
                    grid[row][4].setAvailable();
                    grid[row][5].setAvailable();
                }
                case 3 -> {
                    for (int col = 2; col < 8; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 4 -> {
                    for (int col = 1; col < 8; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 5 -> {
                    for (int col = 1; col < 7; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 7 -> {
                    grid[row][4].setAvailable();
                    grid[row][5].setAvailable();
                }
            }
        }

        this.board.setGrid(grid);
    }

    public void setSackOfTiles(SackOfTiles sackOfTiles) {
        this.sackOfTiles = sackOfTiles;
    }

    public void reachPubObj() {

    }
}
