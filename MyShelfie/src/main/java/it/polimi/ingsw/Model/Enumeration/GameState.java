package it.polimi.ingsw.Model.Enumeration;

public enum GameState {

    /**
     * where player connects to the server and log in
     */
    LOGIN,

    /**
     * where the gamecontroller initialized the board and picks the objectives
     */
    INIT,

    /**
     * where the game is played
     */
    IN_GAME,

    /**
     * where the points are calculated, and broadcasted to all the players
     */
    END
}
