package it.polimi.ingsw.Model.Enumeration;

/**
 * States in where the game can be
 */
public enum GameState {
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
