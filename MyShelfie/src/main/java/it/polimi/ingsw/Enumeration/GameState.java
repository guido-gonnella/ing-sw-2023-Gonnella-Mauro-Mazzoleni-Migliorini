package it.polimi.ingsw.Enumeration;

/**
 * States in where the game can be
 */
public enum GameState {
    /**
     * where the gamecontroller initialized the board and picked the objectives
     */
    INIT,

    /**
     * where the game is played
     */
    IN_GAME,

    /**
     * where the points are calculated, and broadcast to all the players
     */
    END
}
